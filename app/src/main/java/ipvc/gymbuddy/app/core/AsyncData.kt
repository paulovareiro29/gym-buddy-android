package ipvc.gymbuddy.app.core

data class AsyncData<T>(
    val data: T? = null,
    val status: Status = Status.IDLE,
    val error: Map<String, String>? = null
) {
    enum class Status {
        IDLE,
        LOADING,
        SUCCESS,
        ERROR
    }
}