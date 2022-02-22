package data

import androidx.compose.runtime.*
import data.model.Article
import data.network.NewsApi
import data.state.ApiStatus
import data.state.HomeUiState
import data.state.hasMore
import data.state.newOrAdd
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class NewsRepository {

    var homeUiState by mutableStateOf(HomeUiState())
        private set

    val selectedArticle: State<Article?>
        get() = _selectedArticle

    private var _selectedArticle: MutableState<Article?> = mutableStateOf(null)

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

            val result = NewsApi.getTopHeadlines(
                pageSize = 10,
                page = homeUiState.topNews?.currentPage?.plus(1) ?: 0
            )

            result.onSuccess { newsHeadlinesResponse ->

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
            }.onFailure {

                homeUiState = homeUiState.copy(
                    apiStatus = if (homeUiState.apiStatus == ApiStatus.LoadingMore) {
                        ApiStatus.LoadMoreFailure(it.toString())
                    } else {
                        ApiStatus.Failure(it.toString())
                    }
                )

            }

        }

    }

    fun fetchMoreNews() = fetchNews()

    fun fetchAgain() {

        homeUiState = HomeUiState()

        fetchNews()

    }

    fun selectArticle(article: Article?) {
        _selectedArticle.value = article
    }


    fun next() {

        val newsCollection = homeUiState.topNews

        val selectedPosition = newsCollection?.list?.indexOfFirst { _selectedArticle.value?.url == it.url } ?: -1

        selectArticle(
            newsCollection?.list?.get(
                (selectedPosition + 1).coerceAtMost(newsCollection.list.size - 1)
            )
        )

        // Load more if required.
        if (selectedPosition == newsCollection?.list?.size?.minus(3) // load more if it is the second last item,
            && newsCollection.hasMore
        ) { // and has more

            fetchMoreNews()

        }

    }

    fun prev() {

        val newsCollection = homeUiState.topNews

        val selectedPosition = newsCollection?.list?.indexOfFirst { _selectedArticle.value?.url == it.url } ?: -1

        selectArticle(
            newsCollection?.list?.get(
                (selectedPosition - 1).coerceAtLeast(0)
            )
        )

    }


}