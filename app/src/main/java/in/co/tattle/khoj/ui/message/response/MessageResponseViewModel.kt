package `in`.co.tattle.khoj.ui.message.response

import `in`.co.tattle.khoj.data.network.NoConnectivityException
import `in`.co.tattle.khoj.utils.Result
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers

class MessageResponseViewModel(application: Application) : AndroidViewModel(application) {
    private val context = getApplication<Application>().applicationContext

    fun getMessageResponse(messageId: String) = liveData(Dispatchers.IO) {
        emit(Result.loading(null))
        try {
            emit(
                Result.success(
                    MessageResponseRepository.getInstance(context).getMessageResponse(messageId)
                )
            )
        } catch (e: NoConnectivityException) {
            emit(Result.noNetwork(null))
        } catch (e: Exception) {
            emit(Result.error(null, e.message ?: "Error detected"))
        }
    }

}