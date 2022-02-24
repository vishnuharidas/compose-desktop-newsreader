// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.type
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.iamvishnu.compose.Compose_Desktop_Newsreader.BuildConfig
import data.NewsRepository
import data.network.NewsApi
import ui.homescreen.HomeScreen

@OptIn(ExperimentalComposeUiApi::class)
fun main() = application {

    val newsRepository = NewsRepository()

    NewsApi.setApiKey( BuildConfig.NEWSAPI_ORG_API_KEY )

    Window(
        onCloseRequest = ::exitApplication,
        title = "Compose Desktop Newsreader",
        onKeyEvent = {

            when {
                (it.key == Key.DirectionDown || it.key == Key.J) && it.type == KeyEventType.KeyUp -> {
                    newsRepository.next()
                    true
                }
                (it.key == Key.DirectionUp || it.key == Key.K) && it.type == KeyEventType.KeyUp -> {
                    newsRepository.prev()
                    true
                }
                else -> false
            }

        }
    ) {

        MaterialTheme {
            HomeScreen(newsRepository)
        }

    }
}
