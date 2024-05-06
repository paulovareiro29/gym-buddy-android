package ipvc.gymbuddy.api.core
sealed class RequestResult<out T> {
    data class Success<out T>(val code: Int, val message: String, val data: T) : RequestResult<T>()
    data class Error(val code: Int, val message: String, val errors: Map<String, String>) : RequestResult<Nothing>()
}