package utils



import domain.common.auth.model.GoogleUserInfo
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.http.*

suspend fun fetchGoogleUserInfo(accessToken: String): GoogleUserInfo {
    val client = HttpClient.client

    return client.get("https://www.googleapis.com/oauth2/v2/userinfo") {
        headers {
            append(HttpHeaders.Authorization, "Bearer $accessToken")
        }
    }.body()
}
