// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import data.NewsRepository
import ui.HomeScreen

fun main() = application {

    val newsRepository = NewsRepository()

    Window(
        onCloseRequest = ::exitApplication,
        title = "Compose Desktop Newsreader",
    ) {

        MaterialTheme {
            HomeScreen(newsRepository)
        }

    }
}
