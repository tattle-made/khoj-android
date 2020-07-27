package `in`.co.tattle.khoj.ui.message.create

import android.app.Application
import android.content.ClipDescription
import android.content.ClipboardManager
import android.content.Context
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class NewMessageViewModel(application: Application) : AndroidViewModel(application) {
    private val context = getApplication<Application>().applicationContext

    val media: MutableLiveData<ArrayList<Uri>> by lazy {
        MutableLiveData<ArrayList<Uri>>()
    }

    init {
        media.value = arrayListOf()
    }

    private val clipBoardText by lazy {
        val liveData = MutableLiveData<String>()
        liveData.value = ""
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        if (clipboard.primaryClip != null) {
            if (clipboard.primaryClipDescription?.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)!!) {
                liveData.value = clipboard.primaryClip!!.getItemAt(0).text.toString()
            }
        }
        return@lazy liveData
    }

    fun getClipBoard(): LiveData<String> = clipBoardText

    fun addMedia(uri: Uri) {
        media.value?.add(uri)
        media.notifyObserver()
    }

    fun removeMedia(uri: Uri) {
        media.value?.remove(uri)
        media.notifyObserver()
    }

    fun <T> MutableLiveData<T>.notifyObserver() {
        this.value = this.value
    }
}