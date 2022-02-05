package data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import data.network.NewsApi
import data.state.HomeUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class NewsRepository {

    var homeUiState by mutableStateOf(HomeUiState())
        private set

    private var job: Job? = null

    fun fetchNews() {

        if (homeUiState.isLoading) return // prevent double-loads

        homeUiState = HomeUiState(isLoading = true)

        job?.cancel()
        job = CoroutineScope(Dispatchers.Default).launch {

            val newsHeadlinesResponse = NewsApi.getTopHeadlines()

            homeUiState = if (newsHeadlinesResponse.status == "ok") {
                homeUiState.copy(
                    isLoading = false,
                    error = null,
                    newsList = newsHeadlinesResponse.articles
                )
            } else {
                homeUiState.copy(
                    isLoading = false,
                    error = newsHeadlinesResponse.message
                )
            }

        }

    }

}