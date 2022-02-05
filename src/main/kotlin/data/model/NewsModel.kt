package data.model

import kotlinx.serialization.Serializable

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
)

@Serializable
data class Source(
    val id: String? = null,
    val name: String? = null
)
