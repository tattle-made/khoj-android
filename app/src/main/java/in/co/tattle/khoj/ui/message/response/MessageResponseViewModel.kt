package `in`.co.tattle.khoj.ui.message.response

import `in`.co.tattle.khoj.data.network.NoConnectivityException
import `in`.co.tattle.khoj.model.Feedback
import `in`.co.tattle.khoj.utils.Result
import android.app.Application
import android.util.Log
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

    fun submitFeedback(queryId: String, feedback: Feedback) = liveData(Dispatchers.IO) {
        emit(Result.loading(null))
        try {
            val data = MessageResponseRepository.getInstance(context).submitFeedback(
                queryId, feedback
            )
            emit(Result.success(data))
        } catch (e: NoConnectivityException) {
            emit(Result.noNetwork(null))
        } catch (e: Exception) {
            emit(Result.error(data = null, message = e.message ?: "Error detected"))
        }
    }

}