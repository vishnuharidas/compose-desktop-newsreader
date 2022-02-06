package data.state

sealed class ApiStatus {
    object None : ApiStatus()
    object Loading : ApiStatus()
    object LoadingMore : ApiStatus()
    object Success : ApiStatus()
    data class LoadMoreFailure(val message: String?) : ApiStatus()
    data class Failure(val message: String?) : ApiStatus()
}