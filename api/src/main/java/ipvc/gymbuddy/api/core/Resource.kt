package ipvc.gymbuddy.api.core

enum class ResponseStatus {
    Success, Error
}

abstract class Resource(
    var status: ResponseStatus
) {
    abstract val code: Int
    abstract val message: String
}

data class Success<T> (
    override val code: Int,
    override val message: String,
    val data: T
) : Resource(ResponseStatus.Success)

data class Error(
    override val code: Int,
    override val message: String,
    val errors: Map<String, String>
) : Resource(ResponseStatus.Error)