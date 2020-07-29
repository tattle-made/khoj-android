package `in`.co.tattle.khoj.ui.message.response

import `in`.co.tattle.khoj.data.network.KhojRetrofitBuilder
import `in`.co.tattle.khoj.model.messageresponse.MessageResponse
import android.content.Context

class MessageResponseRepository private constructor(val context: Context) {

    suspend fun getMessageResponse(messageId: String): MessageResponse {
        return KhojRetrofitBuilder.getInstance(context).khojApiService.getMessageResponse(messageId)
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