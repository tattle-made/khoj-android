package `in`.co.tattle.khoj.model.queryhistory

data class QueryHistoryItem(
    val __v: Int,
    val _id: String,
    val author: Author,
    val createdAt: String,
    val id: String,
    val media: List<Media>,
    val metadata: Any,
    val question: String,
    val responses: List<Any>,
    val source: String,
    val updatedAt: String,
    val user_feedback: String
)