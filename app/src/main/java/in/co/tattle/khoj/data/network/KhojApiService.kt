package `in`.co.tattle.khoj.data.network

import `in`.co.tattle.khoj.model.homenews.Homepage
import `in`.co.tattle.khoj.model.queryresponse.QueryResponse
import `in`.co.tattle.khoj.model.user.UserRequest
import `in`.co.tattle.khoj.model.user.UserResponse
import retrofit2.http.*

interface KhojApiService {

    @GET("/homepages")
    suspend fun getHomepageData(): Homepage

    @Headers("Content-Type: application/json")
    @POST("/auth/local/register")
    suspend fun registerUser(
        @Header("Authorization") token: String,
        @Body userRequest: UserRequest
    ): UserResponse

    @GET("/queries/{messageId}")
    suspend fun getQueryResponse(@Path("messageId") messageId: String): QueryResponse

}