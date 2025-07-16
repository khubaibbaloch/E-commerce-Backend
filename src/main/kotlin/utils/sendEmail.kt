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
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

/**
 * Sends an HTML email using the MailerSend API.
 *
 * This function prepares an [EmailPayload] with the specified recipient, subject, and HTML content,
 * then makes a POST request to the MailerSend email API endpoint.
 *
 * @param to The recipient email address.
 * @param subject The subject of the email.
 * @param htmlContent The HTML content of the email body.
 *
 * @return `true` if the email was sent successfully (2xx response); `false` otherwise.
 */
