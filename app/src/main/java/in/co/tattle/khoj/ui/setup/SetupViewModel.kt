package `in`.co.tattle.khoj.ui.setup

import `in`.co.tattle.khoj.data.network.NoConnectivityException
import `in`.co.tattle.khoj.model.user.UserRequest
import `in`.co.tattle.khoj.ui.home.SetupRepository
import `in`.co.tattle.khoj.utils.PreferenceUtils
import `in`.co.tattle.khoj.utils.Result
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.liveData
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import kotlinx.coroutines.Dispatchers

class SetupViewModel(application: Application) : AndroidViewModel(application) {
    private val context = getApplication<Application>().applicationContext

    fun setupLanguage(language: String) {
        PreferenceUtils.setPrefString(context, PreferenceUtils.SELECTED_LANGUAGE, language)
//        PreferenceUtils.setPrefBoolean(context, PreferenceUtils.IS_LOGGED_IN_USER, true)
    }

    init {
        fetchTokenFromConfig()
    }

    private fun fetchTokenFromConfig() {
        val remoteConfig = Firebase.remoteConfig
        remoteConfig.fetchAndActivate()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val updated = task.result
                    PreferenceUtils.setPrefString(
                        context,
                        PreferenceUtils.APP_CREATOR_TOKEN,
                        remoteConfig.getString("android_app_user_creator")
                    )
                } else {
                    //fetch failed
                }
            }
    }

    fun registerUser() = liveData(Dispatchers.IO) {
        emit(Result.loading(null))
        try {
            val fcmToken: String? =
                PreferenceUtils.getPrefString(context, PreferenceUtils.FCM_DEVICE_TOKEN)
            val userNameForEmail = fcmToken?.filter { it.isLetterOrDigit() }
            val userRequest = UserRequest(
                fcmToken!!,
                randomID(),
                "$userNameForEmail@tattle.co.in"
            )
            val data = SetupRepository.getInstance(context).registerUser(
                userRequest
            )

            //update user token and save user as logged in
            SetupRepository.getInstance(context).saveUserData(data.jwt, data.user._id)
            emit(Result.success(data))
        } catch (e: NoConnectivityException) {
            emit(Result.noNetwork(null))
        } catch (e: Exception) {
            emit(Result.error(data = null, message = e.message ?: "Error detected"))
//            PreferenceUtils.setPrefBoolean(context, PreferenceUtils.IS_LOGGED_IN_USER, true)
//            emit(Result.success(null))
        }
    }

    private fun randomID(): String = List(12) {
        val chars = ('a'..'Z') + ('A'..'Z') + ('0'..'9')
        chars.random()
    }.joinToString("")
}