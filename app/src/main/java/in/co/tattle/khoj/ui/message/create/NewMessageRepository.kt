package `in`.co.tattle.khoj.ui.message.create

import `in`.co.tattle.khoj.data.network.KhojRetrofitBuilder
import `in`.co.tattle.khoj.model.Question
import `in`.co.tattle.khoj.model.queryresponse.QueryResponse
import android.content.Context
import okhttp3.MultipartBody

class NewMessageRepository private constructor(private val context: Context) {

    suspend fun submitQuery(
        token: String,
        question: Question,
        mediaFiles: ArrayList<MultipartBody.Part>
    ): QueryResponse {
        return KhojRetrofitBuilder.getInstance(context).khojApiService.postQuery(
            token,
            question,
            mediaFiles
        )
    }

    companion object {
        // Singleton instantiation
        @Volatile
        private var instance: NewMessageRepository? = null

        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: NewMessageRepository(context).also { instance = it }
            }
    }
}