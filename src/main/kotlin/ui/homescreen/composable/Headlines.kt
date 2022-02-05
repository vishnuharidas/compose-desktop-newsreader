package ui.homescreen.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.state.HomeUiState
import kotlinx.coroutines.runBlocking

@Composable
fun Headlines(state: HomeUiState) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        if (state.newsList.isNullOrEmpty()) {

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

                items(state.newsList) {
                    NewsCard(
                        title = it,
                        subtitle = "Alice reached Wonderland after going through a rabbit hole. She explains the story to us. Click to read more.",
                        url = "https://example.com/news/alice-in-wonderland",
                    )
                }

            }
        }

    }
}
