package `in`.co.tattle.khoj.ui.message.create

import `in`.co.tattle.khoj.data.network.NoConnectivityException
import `in`.co.tattle.khoj.model.Question
import `in`.co.tattle.khoj.utils.Result
import `in`.co.tattle.khoj.utils.Utils
import android.app.Application
import android.content.ClipDescription
import android.content.ClipboardManager
import android.content.Context
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import okhttp3.MultipartBody

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

    private fun <T> MutableLiveData<T>.notifyObserver() {
        this.value = this.value
    }

    fun submitQuery(question: Question) = liveData(Dispatchers.IO) {
        emit(Result.loading(null))
        try {
            val mediaFiles: ArrayList<MultipartBody.Part> = arrayListOf()
            for (i in 0 until media.value!!.size) {
                mediaFiles.add(
                    Utils.prepareFilePart(
                        "files.media",
                        media.value!![i], context
                    )
                )
            }
            val data = NewMessageRepository.getInstance(context).submitQuery(
                question, mediaFiles
            )
            emit(Result.success(data))
        } catch (e: NoConnectivityException) {
            emit(Result.noNetwork(null))
        } catch (e: Exception) {
            emit(Result.error(data = null, message = e.message ?: "Error detected"))
        }
    }

}