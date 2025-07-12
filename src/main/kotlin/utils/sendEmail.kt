package utils

import data.common.auth.dto.EmailAddress
import data.common.auth.dto.EmailPayload
import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.utils.EmptyContent.contentType
import io.ktor.http.ContentType
import io.ktor.http.ContentType.Application.Json
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

suspend fun sendEmail(to: String, subject: String, htmlContent: String) {
    val payload = EmailPayload(
        from = EmailAddress("MS_biBfmy@test-eqvygm0j3jwl0p7w.mlsender.net"),     // Replace with verified sender
        to = listOf(EmailAddress(to)),
        subject = subject,
        html = htmlContent
    )

    val client = HttpClientHelper.client

    client.post("https://api.mailersend.com/v1/email") {
        header("Authorization", "Bearer mlsn.9b823f78eb6a5de23bc7d955086e03d0345017f5955eb6dbaa6d14932516e72e")
        contentType(ContentType.Application.Json)
        setBody(payload)
    }
}