package `in`.co.tattle.khoj.model.messageresponse

data class MessageResponse(
    val community_responses: ArrayList<CommunityResponse>,
    val created_at: String,
    val feedback: String,
    val id: Int,
    val media: ArrayList<Media>,
    val status: String,
    val updated_at: String
)