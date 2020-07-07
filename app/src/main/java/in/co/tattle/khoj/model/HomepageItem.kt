package `in`.co.tattle.khoj.model

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