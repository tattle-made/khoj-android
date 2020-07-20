package `in`.co.tattle.khoj.ui.message

import android.content.ClipDescription
import android.content.ClipboardManager
import android.content.Context
import android.view.View
import androidx.lifecycle.ViewModel

class NewMessageViewModel : ViewModel() {

    /*override fun onResume() {
        super.onResume()
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

        //if the primary clip is not null and is type text show the text to the user to copy in the
        //edittext field
        if (clipboard.primaryClip != null) {
            if (clipboard.primaryClipDescription?.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)!!) {
                tvInfo.text = getString(R.string.use_copied_text)
                btnPaste.visibility = View.VISIBLE
                tvClipboard.visibility = View.VISIBLE
                tvClipboard.text = clipboard.primaryClip!!.getItemAt(0).text
            } else {
                tvInfo.text = getString(R.string.enter_message)
                btnPaste.visibility = View.GONE
                tvClipboard.visibility = View.GONE
            }
        } else {
            tvInfo.text = getString(R.string.enter_message)
            btnPaste.visibility = View.GONE
            tvClipboard.visibility = View.GONE
        }
    }*/
}