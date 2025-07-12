package data.common.auth.dto

import kotlinx.serialization.Serializable

@Serializable
data class EmailPayload(
    val from: EmailAddress,
    val to: List<EmailAddress>,
    val subject: String,
    val html: String
)