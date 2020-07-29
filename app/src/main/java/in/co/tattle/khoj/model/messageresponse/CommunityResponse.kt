package `in`.co.tattle.khoj.model.messageresponse

data class CommunityResponse(
    val created_at: String,
    val id: Int,
    val media: Any,
    val message: Int,
    val payload: Any,
    val text: String,
    val type: String,
    val updated_at: String
)