package `in`.co.tattle.khoj.data.network

import `in`.co.tattle.khoj.model.Homepage
import retrofit2.http.GET

interface KhojApiService {

    @GET("/homepages")
    suspend fun getHomepageData(): Homepage

}