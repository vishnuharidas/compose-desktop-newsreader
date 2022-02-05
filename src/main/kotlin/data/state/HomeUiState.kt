package data.state

data class HomeUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val newsList: List<String>? = null,
)