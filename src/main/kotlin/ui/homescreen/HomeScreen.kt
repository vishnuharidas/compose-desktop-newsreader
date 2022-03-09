package ui.homescreen

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.NewsRepository
import resources.AppFonts
import ui.homescreen.composable.Contents
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

            var selectedIndex by remember { mutableStateOf(0) }

            var listExpanded by remember { mutableStateOf(false) }

            val typesList = listOf("All News", "Technology", "International", "Sports")


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 2.dp,
                        color = Color.Gray
                    )

            ) {

                // Show selected item
                Text(
                    typesList[selectedIndex],
                    style = TextStyle(
                        color = Color.Gray,
                        fontSize = 18.sp,
                        fontFamily = AppFonts.RobotoSlabRegular
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            listExpanded = !listExpanded
                        }
                        .padding(all = 8.dp)
                )

                // Dropdown icon
                Icon(
                    Icons.Default.ArrowDropDown,
                    "▼",
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 8.dp)
                )

                // Menu
                DropdownMenu(
                    expanded = listExpanded,
                    offset = DpOffset(0.dp, 0.dp),
                    onDismissRequest = {
                        listExpanded = false
                    },
                    modifier = Modifier
                        .width(300.dp)
                ) {

                    typesList.forEachIndexed { index, s ->

                        DropdownMenuItem(
                            onClick = {
                                selectedIndex = index
                                listExpanded = false
                            }
                        ) {
                            Text(
                                s,
                                style = TextStyle(
                                    color = Color.Black,
                                    fontSize = 18.sp,
                                    fontFamily = AppFonts.RobotoSlabRegular
                                )
                            )
                        }

                    }

                }
            }

            Headlines(
                newsRepository.homeUiState,
                onLoadMore = { newsRepository.fetchMoreNews() },
                onRetry = {
                    newsRepository.fetchAgain()
                },
                onClick = {
                    newsRepository.selectArticle(it)
                }
            )

        }

        // Right - selected news contents
        Column(
            modifier = Modifier
                .weight(3f)
                .fillMaxHeight()
        ) {

            val article = newsRepository.selectedArticle.value
            if (article != null) {

                Contents(
                    article = article
                )

            } else {

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        """
                        Select a news from the list to read.
                        
                        Use ⬆️/ ⬇️ keys to navigate.
                    """.trimIndent(),
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            color = Color.Gray,
                            fontSize = 18.sp,
                            fontFamily = AppFonts.RobotoSlabRegular
                        )
                    )
                }

            }
        }
    }

}