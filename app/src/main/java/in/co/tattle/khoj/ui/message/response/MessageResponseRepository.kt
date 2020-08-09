package `in`.co.tattle.khoj.ui.message.response

import `in`.co.tattle.khoj.data.network.KhojRetrofitBuilder
import `in`.co.tattle.khoj.model.Feedback
import `in`.co.tattle.khoj.model.queryresponse.QueryResponse
import `in`.co.tattle.khoj.utils.Constants
import android.content.Context

class MessageResponseRepository private constructor(val context: Context) {

    suspend fun getMessageResponse(messageId: String): QueryResponse {
        return KhojRetrofitBuilder.getInstance(context).getKhojApiService(Constants.USER_TOKEN)
            .getQueryResponse(messageId)
    }

    suspend fun submitFeedback(queryId: String, feedback: Feedback): QueryResponse {
        return KhojRetrofitBuilder.getInstance(context).getKhojApiService(Constants.USER_TOKEN)
            .submitFeedback(queryId, feedback)
    }

    companion object {
        @Volatile
        private var instance: MessageResponseRepository? = null

        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance
                    ?: MessageResponseRepository(context).also { instance = it }
            }

    }
}