package ui.homescreen.composable

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.model.Article
import data.model.Source
import io.kamel.image.KamelImage
import io.kamel.image.lazyPainterResource
import resources.AppFonts


internal val COVER_HEIGHT = 500.dp

@Composable
fun Contents(
    article: Article
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
            .verticalScroll(
                state = rememberScrollState()
            ),
        verticalArrangement = Arrangement.Top
    ) {

        KamelImage(
            resource = lazyPainterResource(article.urlToImage ?: ""),
            alignment = Alignment.Center,
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(COVER_HEIGHT)
                .clip(RoundedCornerShape(4.dp)),
            onFailure = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(COVER_HEIGHT)
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color.LightGray),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Warning,
                        contentDescription = "No image",
                        tint = Color.Gray
                    )
                }
            },
            onLoading = {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(COVER_HEIGHT)
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

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            article.title ?: "-",
            style = TextStyle(
                fontSize = 42.sp,
                fontFamily = AppFonts.RobotoSlabBold
            )
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            article.description ?: "-",
            style = TextStyle(
                fontSize = 24.sp,
                fontFamily = AppFonts.RobotoSlabRegular
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            article.content ?: "-",
            style = TextStyle(
                fontSize = 18.sp,
                fontFamily = AppFonts.RobotoSlabRegular
            )
        )
    }

}

@Preview
@Composable
private fun ContentsPreview() {
    Contents(
        article = Article(
            source = Source(
                name = "Reuters"
            ),
            title = "Foreign automakers see their chance in Japan with electric vehicles - Reuters",
            description = "In September, Narumi Abe did something still rare in Japan: she bought a foreign car, picking a Peugeot e-208 over a Honda e because, she said, the Peugeot can travel longer distances between charges.",
            content = "TOKYO, Feb 17 (Reuters) - In September, Narumi Abe did something still rare in Japan: she bought a foreign car, picking a Peugeot e-208 over a Honda e because, she said, the Peugeot can travel longer… [+2958 chars]",
            publishedAt = "2022-02-17T07:55:00Z",
            urlToImage = "https://www.reuters.com/resizer/KdSbgjDrW08srt-Qr53rFKkgMzA=/1200x628/smart/filters:quality(80)/cloudfront-us-east-2.images.arcpublishing.com/reuters/7R7BNNYFBVIUTBJJOVJFSRKZEE.jpg",
            url = "https://www.reuters.com/business/autos-transportation/foreign-automakers-see-their-chance-japan-with-electric-vehicles-2022-02-17/"
        )
    )
}