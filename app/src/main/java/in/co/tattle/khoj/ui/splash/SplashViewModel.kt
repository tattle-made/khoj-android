package `in`.co.tattle.khoj.ui.splash

import `in`.co.tattle.khoj.utils.PreferenceUtils
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel(application: Application) : AndroidViewModel(application) {

    private val context = getApplication<Application>().applicationContext

    val isLoggedInUser: LiveData<Boolean>
        get() = mutableIsNewUser
    private val mutableIsNewUser = MutableLiveData<Boolean>()

    init {
        GlobalScope.launch {
            delay(2000)
            mutableIsNewUser.postValue(
                PreferenceUtils.getPrefBoolean(context, PreferenceUtils.IS_LOGGED_IN_USER)
            )
        }
    }

}