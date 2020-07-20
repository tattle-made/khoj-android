package `in`.co.tattle.khoj.model.homenews

import `in`.co.tattle.khoj.model.homenews.ArticleShare
import `in`.co.tattle.khoj.model.homenews.ArticleTrending

data class HomepageItem(
    val Greeting: String,
    val article_share: ArticleShare,
    val article_trending: List<ArticleTrending>,
    val cover_image: String,
    val created_at: String,
    val id: Int,
    val publication_date: String,
    val updated_at: String
) {
    constructor() : this(
        "", ArticleShare(), emptyList(),
        "", "", -1, "", ""
    )
}