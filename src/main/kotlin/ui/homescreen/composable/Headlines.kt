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
import data.state.ApiStatus
import data.state.HomeUiState
import data.state.hasMore
import kotlinx.coroutines.runBlocking

@Composable
fun Headlines(
    state: HomeUiState,
    onLoadMore: () -> Unit,
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        if (state.apiStatus is ApiStatus.Failure) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
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
                        fontSize = 16.sp,
                    )
                )

                Button(
                    onClick = {
                        //TODO make an actual call
                    },
                ) {
                    Text("Retry")
                }
            }

        } else if (state.topNews == null || state.topNews.list.isNullOrEmpty()) {

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
                    )
                )

                Button(
                    onClick = {
                        //TODO make an actual call
                    },
                ) {
                    Text("Retry")
                }
            }

        } else {

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.BottomCenter
            ) {

                // Using a state to enable drag-to-scroll (like mobile devices) on desktop applications.
                // Ref: https://github.com/JetBrains/compose-jb/issues/1555#issuecomment-987958210
                val scrollState = rememberLazyListState()

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

                        NewsCard(item)

                        // Load more if there are more items to load.
                        if (index == state.topNews.list.size - 1 // Reached the last item
                            && state.apiStatus !is ApiStatus.LoadMoreFailure // Don't try loading more if there was a failure when loading more.
                            && state.topNews.hasMore
                        ) {
                            onLoadMore()
                        }
                    }

                }

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
                            )
                        )

                        Button(
                            onClick = onLoadMore,
                        ) {
                            Text("Try again")
                        }
                    }

                }

            }
        }

    }
}
