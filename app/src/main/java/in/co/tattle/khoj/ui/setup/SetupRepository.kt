package `in`.co.tattle.khoj.ui.home

import `in`.co.tattle.khoj.data.network.KhojRetrofitBuilder
import `in`.co.tattle.khoj.model.user.UserRequest
import `in`.co.tattle.khoj.model.user.UserResponse
import `in`.co.tattle.khoj.utils.Constants
import `in`.co.tattle.khoj.utils.PreferenceUtils
import android.content.Context

class SetupRepository private constructor(private val context: Context) {

    suspend fun registerUser(userRequest: UserRequest): UserResponse {
        return KhojRetrofitBuilder.getInstance(context).getKhojApiService(Constants.CREATOR_TOKEN)
            .registerUser(
                userRequest
            )
    }

    fun saveUserData(userToken: String, userId: String) {
        PreferenceUtils.setPrefBoolean(context, PreferenceUtils.IS_LOGGED_IN_USER, true)
        PreferenceUtils.setPrefString(context, PreferenceUtils.USER_TOKEN, userToken)
        PreferenceUtils.setPrefString(context, PreferenceUtils.USER_ID, userId)
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