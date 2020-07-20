package `in`.co.tattle.khoj.ui.home

import `in`.co.tattle.khoj.data.network.KhojRetrofitBuilder
import `in`.co.tattle.khoj.model.user.UserRequest
import `in`.co.tattle.khoj.model.user.UserResponse
import `in`.co.tattle.khoj.utils.PreferenceUtils
import android.content.Context

class SetupRepository private constructor(private val context: Context) {

    suspend fun registerUser(token: String, userRequest: UserRequest): UserResponse {
        return KhojRetrofitBuilder.getInstance(context).khojApiService.registerUser(
            token,
            userRequest
        )
    }

    fun saveUserData(userToken: String) {
        PreferenceUtils.setPrefBoolean(context, PreferenceUtils.IS_LOGGED_IN_USER, true)
        PreferenceUtils.setPrefString(context, PreferenceUtils.USER_TOKEN, userToken)
    }

    companion object {
        // Singleton instantiation
        @Volatile
        private var instance: SetupRepository? = null

        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: SetupRepository(context).also { instance = it }
            }
    }
}