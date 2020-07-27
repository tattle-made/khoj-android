package `in`.co.tattle.khoj.ui.message.create

import `in`.co.tattle.khoj.R
import `in`.co.tattle.khoj.ui.adapters.MessageMediaAdapter
import `in`.co.tattle.khoj.ui.message.create.screenshot.ScreenshotBottomSheet
import `in`.co.tattle.khoj.ui.message.response.MessageResponseActivity
import `in`.co.tattle.khoj.utils.Constants
import `in`.co.tattle.khoj.utils.PermissionUtils
import android.Manifest.permission
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_new_message.*


class NewMessageFragment : Fragment(), View.OnClickListener {

    companion object {
        fun newInstance() =
            NewMessageFragment()
    }

    private val READ_STORAGE_PERMISSION: Int = 1000
    private lateinit var viewModel: NewMessageViewModel
    private lateinit var mediaAdapter: MessageMediaAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_new_message, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(NewMessageViewModel::class.java)
        btnCopy.setOnClickListener(this)
        btnScreenshot.setOnClickListener(this)
        btnMedia.setOnClickListener(this)
        btnSubmit.setOnClickListener(this)

        setupMediaRecycler()

        setupClipboardObserver()
        setupMediaObserver()
    }

    private fun setupMediaRecycler() {
        recyclerMedia.layoutManager = GridLayoutManager(context, 2)
        mediaAdapter = MessageMediaAdapter(onAdapterClick, Constants.SELECTED_MEDIA)
        recyclerMedia.adapter = mediaAdapter
    }

    private fun setupMediaObserver() {
        viewModel.media.observe(viewLifecycleOwner, Observer { mediaList ->
            if (mediaList.size > 0) {
                recyclerMedia.visibility = VISIBLE
                tvNoMedia.visibility = GONE
                mediaAdapter.updateMedia(mediaList)
            } else {
                recyclerMedia.visibility = GONE
                tvNoMedia.visibility = VISIBLE
            }
        })
    }

    private fun setupClipboardObserver() {
        viewModel.getClipBoard().observe(viewLifecycleOwner, Observer { clipboardText ->
            if (TextUtils.isEmpty(clipboardText)) {
                tvOr.visibility = GONE
                btnCopy.visibility = GONE
                tvClipboard.visibility = GONE
            } else {
                tvOr.visibility = VISIBLE
                btnCopy.visibility = VISIBLE
                tvClipboard.visibility = VISIBLE
                tvClipboard.text = clipboardText
            }
        })
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btnCopy -> {
                etMessage.setText(tvClipboard.text)
            }
            R.id.btnScreenshot -> {
                if (PermissionUtils.hasPermission(
                        requireActivity(),
                        permission.READ_EXTERNAL_STORAGE
                    )
                ) {
                    showScreenshotSheet()
                } else {
                    PermissionUtils.requestPermissions(
                        this,
                        arrayOf(permission.READ_EXTERNAL_STORAGE),
                        READ_STORAGE_PERMISSION
                    )
                }
            }
            R.id.btnMedia -> {
                startForResult.launch(null)
            }
            R.id.btnSubmit -> {
                startActivity(Intent(context, MessageResponseActivity::class.java))
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == READ_STORAGE_PERMISSION) {
            handlePermissionResult(grantResults)
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    private fun handlePermissionResult(grantResults: IntArray) {
        val denied =
            grantResults.indices.filter { grantResults[it] != PackageManager.PERMISSION_GRANTED }
        if (denied.isEmpty()) {
            // on permission allowed access screenshots
            showScreenshotSheet()
        } else {
            // on permission denied handle the result
            Toast.makeText(
                requireContext(),
                "No permission to fetch screenshots",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun showScreenshotSheet() {
        val screenshotBottomSheet =
            ScreenshotBottomSheet()
        screenshotBottomSheet.show(activity?.supportFragmentManager!!, null)
    }

    private val startForResult =
        registerForActivityResult(PickGalleryImage()) { selectedUri: Uri? ->
            viewModel.addMedia(selectedUri!!)
        }

    class PickGalleryImage : ActivityResultContract<Void?, Uri?>() {
        override fun createIntent(context: Context, input: Void?) =
            Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )

        override fun parseResult(resultCode: Int, result: Intent?): Uri? {
            if (resultCode != Activity.RESULT_OK) {
                return null
            }
            return result?.data
        }
    }

    private val onAdapterClick: (uri: Uri) -> Unit = { uri ->
        viewModel.removeMedia(uri)
    }

}