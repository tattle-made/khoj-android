package `in`.co.tattle.khoj.model.user

import `in`.co.tattle.khoj.model.user.Role

data class User(
    val blocked: Any,
    val confirmed: Boolean,
    val created_at: String,
    val email: String,
    val id: Int,
    val provider: String,
    val role: Role,
    val updated_at: String,
    val username: String
)