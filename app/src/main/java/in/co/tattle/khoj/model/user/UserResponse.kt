package `in`.co.tattle.khoj.model.user

data class UserResponse(
    val jwt: String,
    val user: User
)