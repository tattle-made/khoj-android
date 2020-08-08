package `in`.co.tattle.khoj.ui.home.historyfragment

import `in`.co.tattle.khoj.data.network.NoConnectivityException
import `in`.co.tattle.khoj.ui.home.HomepageRepository
import `in`.co.tattle.khoj.utils.Result
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers

class HistoryViewModel(application: Application) : AndroidViewModel(application) {
    private val context = getApplication<Application>().applicationContext

    fun getQueryHistory() = liveData(Dispatchers.IO) {
        emit(Result.loading(null))
        try {
            emit(
                Result.success(
                    data = HomepageRepository.getInstance(context).getQueryHistory()
                )
            )
        } catch (e: NoConnectivityException) {
            emit(Result.noNetwork(null))
        } catch (e: Exception) {
            emit(Result.error(data = null, message = e.message ?: "Error detected"))
        }
    }
}