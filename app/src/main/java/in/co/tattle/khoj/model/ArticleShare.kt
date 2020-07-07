package `in`.co.tattle.khoj.model

data class ArticleShare(
    val byline: String,
    val heading: String,
    val id: Int,
    val image_thumbnail: String
) {
    constructor() : this("", "", -1, "")
}