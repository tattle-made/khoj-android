package `in`.co.tattle.khoj.model.queryhistory

import `in`.co.tattle.khoj.model.queryresponse.Response

data class QueryHistoryItem(
    val __v: Int,
    val _id: String,
    val author: Author,
    val createdAt: String,
    val id: String,
    val media: List<Media>,
    val metadata: Any,
    val question: String,
    val responses: List<Response>,
    val source: String,
    val updatedAt: String,
    val user_feedback: String
)