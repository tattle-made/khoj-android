package `in`.co.tattle.khoj.model.user

data class User(
    val __v: Int,
    val _id: String,
    val blocked: Boolean,
    val confirmed: Boolean,
    val createdAt: String,
    val email: String,
    val id: String,
    val provider: String,
    val role: Role,
    val updatedAt: String,
    val username: String
)