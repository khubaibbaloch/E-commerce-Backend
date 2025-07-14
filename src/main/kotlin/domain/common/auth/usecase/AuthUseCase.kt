package com.commerce.domain.common.auth.usecase

import domain.common.auth.usecase.MarkEmailAsVerifiedUseCase
import domain.common.auth.usecase.UpsertEmailVerificationUseCase

/**
 * Aggregates all authentication-related use cases.
 * This class helps group related functionality together and allows easy injection into the presentation layer.
 *
 * @property registerUseCase Handles new user registration and returns the generated user ID.
 * @property loginUseCase Authenticates a user and returns a user ID if credentials are valid.
 * @property getEmailVerificationUseCase Retrieves email verification details for a given user.
 * @property upsertEmailVerificationUseCase Inserts or updates an email verification entry for a user.
 * @property markEmailAsVerifiedUseCase Marks a user's email as verified in the database.
 */
data class AuthUseCase(
    val registerUseCase: RegisterAndReturnUserIdUseCase,
    val loginUseCase: LoginUseCase,
    val getEmailVerificationUseCase: GetEmailVerificationUseCase,
    val upsertEmailVerificationUseCase: UpsertEmailVerificationUseCase,
    val markEmailAsVerifiedUseCase: MarkEmailAsVerifiedUseCase
)
