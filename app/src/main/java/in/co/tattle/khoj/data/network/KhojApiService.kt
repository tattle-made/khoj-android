package `in`.co.tattle.khoj.data.network

import `in`.co.tattle.khoj.model.Question
import `in`.co.tattle.khoj.model.homenews.HomepageResponse
import `in`.co.tattle.khoj.model.queryresponse.QueryResponse
import `in`.co.tattle.khoj.model.user.UserRequest
import `in`.co.tattle.khoj.model.user.UserResponse
import okhttp3.MultipartBody
import retrofit2.http.*

interface KhojApiService {

    @GET("/homepages/5f1f865927729c001cc29f05")
    suspend fun getHomepageData(): HomepageResponse

    @Headers("Content-Type: application/json")
    @POST("/auth/local/register")
    suspend fun registerUser(
        @Header("Authorization") token: String,
        @Body userRequest: UserRequest
    ): UserResponse

    @GET("/queries/{messageId}")
    suspend fun getQueryResponse(@Path("messageId") messageId: String): QueryResponse

    @Multipart
    @POST("/queries")
    suspend fun postQuery(
        @Header("Authorization") token: String,
        @Part("data") question: Question,
        @Part mediaFiles: ArrayList<MultipartBody.Part>
    ): QueryResponse
}