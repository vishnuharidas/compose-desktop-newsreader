package ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import data.NewsRepository
import ui.homescreen.composable.Headlines

@Composable
@Preview
fun HomeScreen(newsRepository: NewsRepository) {

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

            //Headlines(newsRepository.homeUiState)
            if (newsRepository.homeUiState.isLoading) {

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFEEEEEE)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }

            } else {
                Headlines(newsRepository.homeUiState)
            }

        }

        // Right - selected news contents
        Column(
            modifier = Modifier
                .weight(3f)
                .fillMaxHeight()
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Contents will appear here.")
            }
        }
    }

}