package `in`.co.tattle.khoj.utils

data class Result<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?): Result<T> =
            Result(status = Status.SUCCESS, data = data, message = null)

        fun <T> error(data: T?, message: String): Result<T> =
            Result(status = Status.ERROR, data = data, message = message)

        fun <T> noNetwork(data: T?): Result<T> =
            Result(status = Status.NO_NETWORK, data = data, message = null)

        fun <T> loading(data: T?): Result<T> =
            Result(status = Status.LOADING, data = data, message = null)
    }
}