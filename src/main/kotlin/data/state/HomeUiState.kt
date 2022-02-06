package data.state

import data.model.Article

data class HomeUiState(
    val apiStatus: ApiStatus = ApiStatus.None,
    val error: String? = null,
    val topNews: NewsCollection? = null,
    val techNews: NewsCollection? = null,
)

data class NewsCollection(
    val totalCount: Long = 0,
    val currentPage: Int = 0,
    val list: List<Article>? = null,
)

val NewsCollection.hasMore: Boolean
    get() {
        return this.totalCount > 0
                && !this.list.isNullOrEmpty()
                && this.totalCount != this.list.size.toLong()
    }

fun NewsCollection?.newOrAdd(
    totalCount: Long,
    list: List<Article>?
): NewsCollection {

    if (this == null) return NewsCollection(
        totalCount = totalCount,
        currentPage = 1,
        list = list
    )

    if (list.isNullOrEmpty()) return this

    return this.copy(
        currentPage = this.currentPage + 1,
        list = this.list?.plus(list)
    )
}