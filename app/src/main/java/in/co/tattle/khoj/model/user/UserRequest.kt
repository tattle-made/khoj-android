package `in`.co.tattle.khoj.model.user

data class UserRequest(
    val username: String,
    val password: String,
    val email: String
)