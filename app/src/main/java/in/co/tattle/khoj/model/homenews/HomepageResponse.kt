package `in`.co.tattle.khoj.model.homenews

data class HomepageResponse(
    val __v: Int,
    val _id: String,
    val article_share: ArrayList<ArticleShare>,
    val article_trending: ArrayList<ArticleTrending>,
    val cover_image: CoverImage,
    val createdAt: String,
    val created_by: CreatedBy,
    val greeting: String,
    val id: String,
    val message: String,
    val updatedAt: String,
    val updated_by: UpdatedBy
)