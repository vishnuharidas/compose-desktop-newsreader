package data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import data.network.NewsApi
import data.state.ApiStatus
import data.state.HomeUiState
import data.state.newOrAdd
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class NewsRepository {

    var homeUiState by mutableStateOf(HomeUiState())
        private set

    private var job: Job? = null

    fun fetchNews() {

        if (homeUiState.apiStatus == ApiStatus.Loading
            || homeUiState.apiStatus == ApiStatus.LoadingMore
        ) return // prevent double-loads

        homeUiState = homeUiState.copy(
            apiStatus = if (homeUiState.topNews == null) ApiStatus.Loading else ApiStatus.LoadingMore
        )

        println("State: ${homeUiState.apiStatus}")

        job?.cancel()
        job = CoroutineScope(Dispatchers.Default).launch {

            val newsHeadlinesResponse = NewsApi.getTopHeadlines(
                pageSize = 10,
                page = homeUiState.topNews?.currentPage?.plus(1) ?: 0
            )

            homeUiState = if (newsHeadlinesResponse.status == "ok") {

                homeUiState.copy(
                    apiStatus = ApiStatus.Success,
                    error = null,
                    topNews = homeUiState.topNews.newOrAdd(
                        totalCount = newsHeadlinesResponse.totalResults ?: 0,
                        list = newsHeadlinesResponse.articles
                    )
                )

            } else {
                homeUiState.copy(
                    apiStatus = if (homeUiState.apiStatus == ApiStatus.LoadingMore) {
                        ApiStatus.LoadMoreFailure(newsHeadlinesResponse.message)
                    } else {
                        ApiStatus.Failure(newsHeadlinesResponse.message)
                    }
                )
            }

        }

    }

    fun fetchMoreNews() = fetchNews()

}