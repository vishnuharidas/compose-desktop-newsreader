package ui.homescreen.composable

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.model.Article
import io.kamel.image.KamelImage
import io.kamel.image.lazyPainterResource

@Preview
@Composable
fun NewsCard(
    article: Article
) {
    Card(
        modifier = Modifier
            .padding(8.dp),
        elevation = 10.dp,
        shape = RoundedCornerShape(4.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {

                if (!article.urlToImage.isNullOrEmpty()) {

                    KamelImage(
                        resource = lazyPainterResource(article.urlToImage ?: ""),
                        alignment = Alignment.Center,
                        contentScale = ContentScale.Crop,
                        contentDescription = null,
                        modifier = Modifier
                            .weight(1f)
                            .height(120.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        onLoading = {

                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(120.dp)
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(Color.LightGray),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(
                                    strokeWidth = 1.dp,
                                    color = Color.DarkGray
                                )
                            }

                        }
                    )

                }

                Text(
                    article.title ?: "-",
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier
                        .weight(2f)
                        .padding(start = if (article.urlToImage.isNullOrEmpty()) 0.dp else 16.dp)
                )

            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(article.description ?: "-")

            if(article.source?.name != null){
                Text(
                    article.source.name,
                    style = TextStyle(
                        color = Color.Gray,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Light
                    ),
                    modifier = Modifier
                        .padding(top = 16.dp)
                )
            }

        }

    }
}