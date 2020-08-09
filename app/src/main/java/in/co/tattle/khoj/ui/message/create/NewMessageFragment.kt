package `in`.co.tattle.khoj.ui.message.create

import `in`.co.tattle.khoj.R
import `in`.co.tattle.khoj.model.Question
import `in`.co.tattle.khoj.model.queryresponse.QueryResponse
import `in`.co.tattle.khoj.ui.adapters.MessageMediaAdapter
import `in`.co.tattle.khoj.ui.message.create.screenshot.PermissionBottomSheet
import `in`.co.tattle.khoj.ui.message.create.screenshot.ScreenshotBottomSheet
import `in`.co.tattle.khoj.ui.message.response.MessageResponseActivity
import `in`.co.tattle.khoj.utils.Constants
import `in`.co.tattle.khoj.utils.PermissionUtils
import `in`.co.tattle.khoj.utils.Status
import `in`.co.tattle.khoj.utils.ui.LoadingDialog
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
import android.widget.Toast.LENGTH_SHORT
import androidx.activity.result.contract.ActivityResultContract
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL
import kotlinx.android.synthetic.main.fragment_new_message.*


class NewMessageFragment : Fragment(), View.OnClickListener {

    companion object {
        fun newInstance() =
            NewMessageFragment()
    }

    private val READ_STORAGE_PERMISSION_GALLERY: Int = 1000
    private val READ_STORAGE_PERMISSION_SCREENSHOTS: Int = 1001
    private lateinit var loadingDialog: LoadingDialog
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

        loadingDialog = LoadingDialog(requireContext())
        viewModel = ViewModelProvider(this).get(NewMessageViewModel::class.java)
        btnScreenshot.setOnClickListener(this)
        btnMedia.setOnClickListener(this)
        btnRecentlyCopied.setOnClickListener(this)
        btnSubmit.setOnClickListener(this)

        setupMediaRecycler()

        setupClipboardObserver()
        setupMediaObserver()

        checkIntentActions()
    }

    private fun checkIntentActions() {
        when (requireActivity().intent.action) {
            Intent.ACTION_SEND -> {
                if (requireActivity().intent.type == "text/plain") {
                    etMessage.setText(requireActivity().intent.getStringExtra(Intent.EXTRA_TEXT))
                } else if (requireActivity().intent.type == "image/*") {
                    viewModel.addMedia(requireActivity().intent.getParcelableExtra(Intent.EXTRA_STREAM) as Uri)
                }
            }
        }
    }

    private fun setupMediaRecycler() {
        recyclerMedia.layoutManager = StaggeredGridLayoutManager(2, VERTICAL)
        mediaAdapter = MessageMediaAdapter(onAdapterClick, Constants.SELECTED_MEDIA)
        recyclerMedia.adapter = mediaAdapter
    }

    private fun setupMediaObserver() {
        viewModel.media.observe(viewLifecycleOwner, Observer { mediaList ->
            mediaAdapter.updateMedia(mediaList)
        })
    }

    private fun setupClipboardObserver() {
        viewModel.getClipBoard().observe(viewLifecycleOwner, Observer { clipboardText ->
            if (TextUtils.isEmpty(clipboardText)) {
                btnRecentlyCopied.visibility = GONE
            } else {
                btnRecentlyCopied.visibility = VISIBLE
            }
        })
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btnScreenshot -> {
                checkReadStoragePermission(READ_STORAGE_PERMISSION_SCREENSHOTS)
            }
            R.id.btnMedia -> {
                checkReadStoragePermission(READ_STORAGE_PERMISSION_GALLERY)
            }
            R.id.btnSubmit -> {
                if (validateData()) {
                    submitQuery()
                } else {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.media_or_text),
                        LENGTH_SHORT
                    ).show()
                }
            }
            R.id.btnRecentlyCopied -> {
                showScreenshotSheet(Constants.CLIPBOARD)
            }
        }
    }

    private fun validateData(): Boolean {
        return (!TextUtils.isEmpty(etMessage.text.toString()) || viewModel.media.value!!.size > 0)
    }

    private fun checkReadStoragePermission(requestCode: Int) {
        if (PermissionUtils.hasPermission(
                requireActivity(),
                permission.READ_EXTERNAL_STORAGE
            )
        ) {
            if (requestCode == READ_STORAGE_PERMISSION_SCREENSHOTS) {
                showScreenshotSheet(Constants.SCREENSHOTS)
            } else if (requestCode == READ_STORAGE_PERMISSION_GALLERY) {
                startForResult.launch(null)
            }
        } else {
            showPermissionSheet(requestCode)
        }
    }

    private fun submitQuery() {
        viewModel.submitQuery(Question(etMessage.text.toString()))
            .observe(this, Observer { result ->
                when (result.status) {
                    Status.LOADING -> {
                        loadingDialog.show()
                        loadingDialog.setMessage(getString(R.string.submitting_query))
                    }
                    Status.SUCCESS -> {
                        loadingDialog.dismiss()
                        Toast.makeText(requireContext(), "SUCCESSS", LENGTH_SHORT).show()
                        startResponseActivity(result.data)
                    }
                    Status.ERROR -> {
                        loadingDialog.dismiss()
                        Toast.makeText(requireContext(), "ERROR", LENGTH_SHORT).show()

                    }
                    Status.NO_NETWORK -> {
                        loadingDialog.dismiss()
                        Toast.makeText(
                            requireContext(), getString(R.string.offline),
                            LENGTH_SHORT
                        ).show()
                    }
                }
            })
    }

    private fun startResponseActivity(data: QueryResponse?) {
        val intent = Intent(context, MessageResponseActivity::class.java)
        intent.putExtra(Constants.MESSAGE_ID, data!!.id)
        startActivity(intent)
        requireActivity().finish()
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == READ_STORAGE_PERMISSION_GALLERY ||
            requestCode == READ_STORAGE_PERMISSION_SCREENSHOTS
        ) {
            handlePermissionResult(grantResults, requestCode)
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    private fun handlePermissionResult(grantResults: IntArray, requestCode: Int) {
        val denied =
            grantResults.indices.filter { grantResults[it] != PackageManager.PERMISSION_GRANTED }
        if (denied.isEmpty()) {
            // on permission allowed access screenshots
            if (requestCode == READ_STORAGE_PERMISSION_SCREENSHOTS) {
                showScreenshotSheet(Constants.SCREENSHOTS)
            } else if (requestCode == READ_STORAGE_PERMISSION_GALLERY) {
                startForResult.launch(null)
            }
        } else {
            // on permission denied handle the result
            Toast.makeText(
                requireContext(),
                "No permission to fetch screenshots",
                LENGTH_SHORT
            ).show()
        }
    }

    private fun showScreenshotSheet(type: String) {
        val screenshotBottomSheet =
            ScreenshotBottomSheet(
                onScreenshotSelect,
                onCopyTextClick,
                type,
                viewModel.getClipBoard().value
            )
        screenshotBottomSheet.show(activity?.supportFragmentManager!!, null)
    }

    private fun showPermissionSheet(requestCode: Int) {
        val permissionBottomSheet =
            PermissionBottomSheet(permissionSheetCallback, requestCode)
        permissionBottomSheet.show(activity?.supportFragmentManager!!, null)
    }

    private val startForResult =
        registerForActivityResult(PickGalleryImage()) { selectedUri: Uri? ->
            if (selectedUri != null) {
                viewModel.addMedia(selectedUri)
            }
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

    private val onScreenshotSelect: (uri: Uri) -> Unit = { uri ->
        viewModel.addMedia(uri)
    }

    private val onCopyTextClick: (clipboardText: String) -> Unit = { clipboardText ->
        etMessage.setText(clipboardText)
    }

    private val permissionSheetCallback: (permissionGranted: Boolean, requestCode: Int) -> Unit =
        { b: Boolean, code: Int ->
            if (b) {
                PermissionUtils.requestPermissions(
                    this,
                    arrayOf(permission.READ_EXTERNAL_STORAGE),
                    code
                )
            }
        }

}