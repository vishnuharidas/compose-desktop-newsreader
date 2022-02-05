package data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import data.state.HomeUiState
import kotlinx.coroutines.*

class NewsRepository {

    var homeUiState by mutableStateOf(HomeUiState())
        private set

    private var job: Job? = null

    fun fetchNews() {

        if (homeUiState.isLoading) return // prevent double-loads

        homeUiState = HomeUiState(isLoading = true)

        job?.cancel()
        job = CoroutineScope(Dispatchers.Default).launch {

            // TODO make actual API calls
            delay(1000)

            homeUiState = homeUiState.copy(
                isLoading = false,
                error = null,
                newsList = (1..150).map { "News Title $it" }.toList()
            )

        }

    }

}