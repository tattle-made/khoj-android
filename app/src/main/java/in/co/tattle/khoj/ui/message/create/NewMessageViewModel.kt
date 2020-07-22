package `in`.co.tattle.khoj.ui.message.create

import android.app.Application
import android.content.ClipDescription
import android.content.ClipboardManager
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class NewMessageViewModel(application: Application) : AndroidViewModel(application) {
    private val context = getApplication<Application>().applicationContext

    private val clipBoardText by lazy {
        val liveData = MutableLiveData<String>()
        liveData.value = ""
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        if (clipboard.primaryClip != null) {
            if (clipboard.primaryClipDescription?.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)!!) {
                liveData.value = clipboard.primaryClip!!.getItemAt(0).text as String?
            }
        }
        return@lazy liveData
    }

    fun getClipBoard(): LiveData<String> = clipBoardText

}