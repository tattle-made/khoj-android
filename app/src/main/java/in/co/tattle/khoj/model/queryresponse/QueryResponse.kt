package `in`.co.tattle.khoj.model.queryresponse

data class QueryResponse(
    val __v: Int,
    val _id: String,
    val author: Author,
    val createdAt: String,
    val id: String,
    val media: ArrayList<Media>,
    val metadata: Metadata,
    val question: String,
    val response: String,
    val responses: ArrayList<Response>,
    val source: String,
    val updatedAt: String,
    val updated_by: UpdatedBy,
    val user_feedback: String
)