package `in`.co.tattle.khoj.ui.message.create.screenshot

import `in`.co.tattle.khoj.R
import `in`.co.tattle.khoj.ui.adapters.MessageMediaAdapter
import `in`.co.tattle.khoj.ui.message.create.ScreenshotViewModel
import `in`.co.tattle.khoj.utils.Constants
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.screenshot_bottom_sheet.*

class ScreenshotBottomSheet(
    private val onScreenshotSelect: (uri: Uri) -> Unit,
    private val onCopyTextClick: (clipboardText: String) -> Unit,
    private val type: String,
    private val clipboardText: String?
) :
    BottomSheetDialogFragment(), View.OnClickListener {
    private lateinit var viewModel: ScreenshotViewModel
    private lateinit var mediaAdapter: MessageMediaAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.screenshot_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ScreenshotViewModel::class.java)

        if (TextUtils.equals(type, Constants.SCREENSHOTS)) {
            layoutScreenshot.visibility = VISIBLE
            layoutClipboard.visibility = GONE
            setupRecycler()
            setupScreenshotObserver()
        } else if (TextUtils.equals(type, Constants.CLIPBOARD)) {
            layoutClipboard.visibility = VISIBLE
            layoutScreenshot.visibility = GONE
            tvClipboardText.setOnClickListener(this)
            tvClipboardText.text = clipboardText
        }
    }

    private fun setupRecycler() {
        recyclerScreenshots.layoutManager = GridLayoutManager(requireContext(), 2)
        mediaAdapter = MessageMediaAdapter(onAdapterClick, Constants.SCREENSHOTS)
        recyclerScreenshots.adapter = mediaAdapter
    }

    private fun setupScreenshotObserver() {
        viewModel.getScreenshots().observe(viewLifecycleOwner, Observer { screenshots ->
            if (screenshots != null) {
                if (screenshots.size > 0) {
                    showScreenshots(screenshots)
                } else {
                    showError(getString(R.string.no_screenshots))
                }
            } else {
                showError(getString(R.string.screenshot_error))
            }
        })
    }

    private fun showScreenshots(screenshots: ArrayList<Uri>) {
        tvSelect.visibility = VISIBLE
        layoutScreenshot.visibility = VISIBLE
        layoutError.visibility = GONE
        mediaAdapter.updateMedia(screenshots)
    }

    private fun showError(error: String) {
        tvSelect.visibility = GONE
        layoutScreenshot.visibility = GONE
        layoutError.visibility = VISIBLE
        tvError.text = error
    }

    private val onAdapterClick: (uri: Uri) -> Unit = { uri ->
        onScreenshotSelect(uri)
        this.dismiss()
    }

    override fun onClick(v: View?) {
        if (v!!.id == R.id.tvClipboardText) {
            onCopyTextClick(clipboardText!!)
            this.dismiss()
        }
    }
}