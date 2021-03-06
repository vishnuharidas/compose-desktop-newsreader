package ui.homescreen.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.model.Article
import data.state.ApiStatus
import data.state.HomeUiState
import data.state.hasMore
import kotlinx.coroutines.runBlocking
import resources.AppFonts

@Composable
fun Headlines(
    state: HomeUiState,
    onLoadMore: () -> Unit,
    onRetry: () -> Unit,
    onClick: (Article) -> Unit,
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        when (state.apiStatus) {

            // Check failure first
            is ApiStatus.Failure -> {

                HeadlinesFailure(
                    state.apiStatus.message,
                    onRetry
                )

            }

            // If started loading the first page, show a full "loading" indicator
            ApiStatus.Loading -> {

                HeadlinesLoading()

            }

            else -> {
                HeadlinesListing(
                    state,
                    onLoadMore,
                    onRetry,
                    onClick,
                )
            }
        }

    }
}

@Composable
internal fun HeadlinesLoading() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEEEEEE)),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
internal fun HeadlinesFailure(
    message: String?,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEEDDDD))
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            message ?: "Some unknown error occurred. Please try again later.",
            textAlign = TextAlign.Center,
            style = TextStyle(
                color = Color(0xFFEE5555),
                fontSize = 16.sp,
                fontFamily = AppFonts.RobotoSlabRegular
            )
        )

        Button(
            onClick = onRetry,
        ) {
            Text(
                "Retry",
                style = TextStyle(fontFamily = AppFonts.RobotoSlabRegular)
            )
        }
    }
}

@Composable
internal fun HeadlinesEmpty(
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEEDDDD))
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "No news at this time. Please try later.",
            textAlign = TextAlign.Center,
            style = TextStyle(
                color = Color(0xFFEE5555),
                fontSize = 16.sp,
                fontFamily = AppFonts.RobotoSlabRegular
            )
        )

        Button(
            onClick = onRetry,
        ) {
            Text(
                "Retry",
                style = TextStyle(fontFamily = AppFonts.RobotoSlabRegular)
            )
        }
    }
}

@Composable
internal fun HeadlinesListing(
    state: HomeUiState,
    onLoadMore: () -> Unit,
    onRetry: () -> Unit,
    onClick: (Article) -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.BottomCenter
    ) {

        CardsList(
            state,
            onLoadMore,
            onRetry,
            onClick,
        )

        // Load more indicator
        if (state.apiStatus == ApiStatus.LoadingMore) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(8.dp)
                    .alpha(0.9f),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        // If there's an error when loading more.
        if (state.apiStatus is ApiStatus.LoadMoreFailure) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFEEDDDD))
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    state.apiStatus.message ?: "Some unknown error occurred. Please try again later.",
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        color = Color(0xFFEE5555),
                        fontSize = 12.sp,
                        fontFamily = AppFonts.RobotoSlabRegular
                    )
                )

                Button(
                    onClick = onLoadMore,
                ) {
                    Text(
                        "Try again",
                        style = TextStyle(
                            fontFamily = AppFonts.RobotoSlabRegular
                        )
                    )
                }
            }

        }

    }
}

@Composable
internal fun CardsList(
    state: HomeUiState,
    onLoadMore: () -> Unit,
    onRetry: () -> Unit,
    onClick: (Article) -> Unit,
) {
    // Using a state to enable drag-to-scroll (like mobile devices) on desktop applications.
    // Ref: https://github.com/JetBrains/compose-jb/issues/1555#issuecomment-987958210
    val scrollState = rememberLazyListState()

    if (state.topNews?.list != null && state.topNews.list.isNotEmpty()) {
        LazyColumn(
            state = scrollState,
            modifier = Modifier
                .draggable(rememberDraggableState {
                    runBlocking {
                        scrollState.scrollBy(-it)
                    }
                }, orientation = Orientation.Vertical),
        ) {

            itemsIndexed(state.topNews.list) { index, item ->

                NewsCard(
                    item,
                    onClick,
                )

                // Load more if there are more items to load.
                if (index == state.topNews.list.size - 1 // Reached the last item
                    && state.apiStatus !is ApiStatus.LoadMoreFailure // Don't try loading more if there was a failure when loading more.
                    && state.topNews.hasMore
                ) {
                    onLoadMore()
                }
            }

        }
    } else if (state.apiStatus == ApiStatus.Success) {
        HeadlinesEmpty(onRetry)
    }
}