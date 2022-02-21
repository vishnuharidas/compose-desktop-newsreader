package data.model

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.Serializable
import java.time.format.DateTimeFormatter

// Quickly generated using the cool QuickType.io: https://app.quicktype.io/
@Serializable
data class GetNewsHeadlinesResponse(
    val status: String? = null,
    val totalResults: Long? = null,
    val articles: List<Article>? = null,
    val message: String? = null
)

@Serializable
data class Article(
    val source: Source? = null,
    val author: String? = null,
    val title: String? = null,
    val description: String? = null,
    val url: String? = null,
    val urlToImage: String? = null,
    val publishedAt: String? = null,
    val content: String? = null
) {

    companion object {
        val tz = TimeZone.currentSystemDefault()
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy MMM dd, hh:mm a")
    }

    val publishedAtStr: String
        get() {
            if (publishedAt.isNullOrEmpty()) return "Published recently"

            return formatter.format(
                Instant.parse(publishedAt).toLocalDateTime(tz).toJavaLocalDateTime()
            )

        }
}

@Serializable
data class Source(
    val id: String? = null,
    val name: String? = null
)
