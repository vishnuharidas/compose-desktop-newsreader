package ui.homescreen.composable

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.coroutines.runBlocking

@Composable
fun Headlines() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
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

            items(100) {
                NewsCard(
                    title = "Alice in Wonderland",
                    subtitle = "Alice reached Wonderland after going through a rabbit hole. She explains the story to us. Click to read more.",
                    url = "https://example.com/news/alice-in-wonderland",
                )
            }

        }

    }
}
