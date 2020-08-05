package `in`.co.tattle.khoj.ui.home

import `in`.co.tattle.khoj.data.network.KhojRetrofitBuilder
import `in`.co.tattle.khoj.model.homenews.HomepageResponse
import android.content.Context

class HomepageRepository private constructor(private val context: Context) {

    suspend fun getHomepageData(): HomepageResponse {
        return KhojRetrofitBuilder.getInstance(context).khojApiService.getHomepageData()
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