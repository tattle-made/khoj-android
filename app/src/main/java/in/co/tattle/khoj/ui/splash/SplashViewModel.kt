package `in`.co.tattle.khoj.ui.splash

import `in`.co.tattle.khoj.utils.PreferenceUtils
import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel(application: Application) : AndroidViewModel(application) {

    private val context = getApplication<Application>().applicationContext

    val isLoggedInUser: LiveData<Boolean>
        get() = mutableIsNewUser
    private val mutableIsNewUser = MutableLiveData<Boolean>()

    init {
        val remoteConfig = Firebase.remoteConfig
        remoteConfig.fetchAndActivate()
            .addOnCompleteListener(OnCompleteListener { task ->
                if (task.isSuccessful) {
                    val updated = task.result
                    Log.d("TESTTTTTT", "Config params updated: $updated")
                    Toast.makeText(
                        context, "Fetch and activate succeeded",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.d(
                        "TESTTTTTT",
                        "Config params updated: " + remoteConfig.getString("khojTest")
                    )
                } else {
                    Toast.makeText(
                        context, "Fetch failed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                startHandler()
            })
    }

    private fun startHandler() {
        GlobalScope.launch {
            delay(2000)
            mutableIsNewUser.postValue(
                PreferenceUtils.getPrefBoolean(context, PreferenceUtils.IS_LOGGED_IN_USER)
            )
        }
    }

}