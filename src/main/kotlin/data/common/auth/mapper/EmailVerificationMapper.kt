package com.commerce.data.common.auth.mapper

import com.commerce.domain.common.auth.model.EmailVerificationEntity
import data.common.auth.dto.EmailVerificationRequest
import kotlin.String

/**
 * Extension function to map [EmailVerificationRequest] (DTO) to [EmailVerificationEntity] (domain model).
 *
 * This helps maintain separation between the data layer and domain layer,
 * ensuring clean transformation of objects before business logic use.
 *
 * @receiver The DTO containing email verification information.
 * @return A [EmailVerificationEntity] used in the domain layer.
 */
fun EmailVerificationRequest.toDomain(): EmailVerificationEntity {
    return EmailVerificationEntity(
        userId = this.userId,
        email = this.email,
        tokenOrOtp = this.tokenOrOtp,
        expiresAt = this.expiresAt,
        verified = this.verified
    )
}
