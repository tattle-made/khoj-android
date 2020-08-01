package `in`.co.tattle.khoj.model.queryresponse

import `in`.co.tattle.khoj.model.queryresponse.Formats

data class Media(
    val __v: Int,
    val _id: String,
    val alternativeText: String,
    val caption: String,
    val createdAt: String,
    val created_by: String,
    val ext: String,
    val formats: Formats,
    val hash: String,
    val height: Int,
    val id: String,
    val mime: String,
    val name: String,
    val provider: String,
    val related: List<String>,
    val size: Double,
    val updatedAt: String,
    val updated_by: String,
    val url: String,
    val width: Int
)