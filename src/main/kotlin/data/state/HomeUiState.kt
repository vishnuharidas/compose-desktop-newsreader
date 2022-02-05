package data.state

import data.model.Article

data class HomeUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val newsList: List<Article>? = null,
)