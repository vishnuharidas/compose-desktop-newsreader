package data.network

import data.model.GetNewsHeadlinesResponse
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*

object NewsApi {

    private lateinit var apiKey: String

    private val httpClient: HttpClient = HttpClient(CIO) {
        expectSuccess = false
        install(JsonFeature) {
            serializer = KotlinxSerializer(json = kotlinx.serialization.json.Json {
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }

    fun setApiKey(key: String) {
        apiKey = key
    }

    suspend fun getTopHeadlines(
        pageSize: Int = 10,
        page: Int = 0
    ): Result<GetNewsHeadlinesResponse> = makeApiCall {
        httpClient.get(
            "https://newsapi.org/v2/top-headlines?country=us&apiKey=$apiKey&pageSize=$pageSize&page=$page"
        )
    }

    private inline fun <reified T> makeApiCall(
        block: () -> T
    ): Result<T> {

        return try {
            Result.success(block())
        } catch (e: Exception) {
            Result.failure(e)
        }

    }

}