package `in`.co.tattle.khoj.ui.home

import `in`.co.tattle.khoj.data.network.KhojRetrofitBuilder
import `in`.co.tattle.khoj.model.homenews.HomepageResponse
import `in`.co.tattle.khoj.model.queryhistory.QueryHistory
import `in`.co.tattle.khoj.utils.Constants
import `in`.co.tattle.khoj.utils.PreferenceUtils
import android.content.Context
import android.text.TextUtils

class HomepageRepository private constructor(private val context: Context) {

    suspend fun getHomepageData(): HomepageResponse {
        var language = "english"
        if (TextUtils.equals(
                Constants.HINDI,
                PreferenceUtils.getPrefString(context, PreferenceUtils.SELECTED_LANGUAGE)
            )
        ) {
            language = "hindi"
        }
        return KhojRetrofitBuilder.getInstance(context).getKhojApiService(Constants.USER_TOKEN)
            .getHomepageData(language)
    }

    suspend fun getQueryHistory(): QueryHistory {
        return KhojRetrofitBuilder.getInstance(context).getKhojApiService(Constants.USER_TOKEN)
            .getQueryHistory(
                PreferenceUtils.getPrefString(context, PreferenceUtils.USER_ID)!!
            )
    }

    companion object {
        // Singleton instantiation
        @Volatile
        private var instance: HomepageRepository? = null

        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: HomepageRepository(context).also { instance = it }
            }
    }
}