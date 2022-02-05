package data.network

import data.model.GetNewsHeadlinesResponse
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*

object NewsApi {

    private lateinit var apiKey: String

    private val httpClient: HttpClient = HttpClient(CIO){
        install(JsonFeature){
            serializer = KotlinxSerializer()
        }
    }

    fun setApiKey(key: String){
        apiKey = key
    }

    suspend fun getTopHeadlines(): GetNewsHeadlinesResponse {
        return httpClient.get("https://newsapi.org/v2/top-headlines?country=us&apiKey=$apiKey")
    }

}