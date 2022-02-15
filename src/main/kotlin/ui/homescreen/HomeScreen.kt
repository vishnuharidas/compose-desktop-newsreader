package ui.homescreen

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import data.NewsRepository
import data.model.Article
import ui.homescreen.composable.Contents
import ui.homescreen.composable.Headlines

@Composable
@Preview
fun HomeScreen(newsRepository: NewsRepository) {

    var selectedArticle by remember { mutableStateOf<Article?>(null) }

    LaunchedEffect(newsRepository) {
        newsRepository.fetchNews()
    }

    Row(
        modifier = Modifier.fillMaxSize()
    ) {

        // Left headlines
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        ) {

            Headlines(
                newsRepository.homeUiState,
                onLoadMore = { newsRepository.fetchMoreNews() },
                onRetry = {
                    newsRepository.fetchAgain()
                },
                onClick = {
                    selectedArticle = it
                }
            )

        }

        // Right - selected news contents
        Column(
            modifier = Modifier
                .weight(3f)
                .fillMaxHeight()
        ) {

            val article = selectedArticle
            if (article != null) {

                Contents(
                    article = article
                )

            } else {

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Select a news from the list to read")
                }

            }
        }
    }

}