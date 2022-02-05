package ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ui.homescreen.composable.Headlines

@Composable
@Preview
fun HomeScreen() {

    Row(
        modifier = Modifier.fillMaxSize()
    ) {
        // Left headlines
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        ) {
            Headlines()
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