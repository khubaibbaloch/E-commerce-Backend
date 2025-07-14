package domain.common.auth.model

/**
 * Represents the result of a user registration attempt.
 *
 * This sealed class helps differentiate between newly registered users and existing ones.
 */
sealed class RegisterResult {

    /**
     * Represents a successful registration of a new user.
     *
     * @property userId The newly created user's unique identifier.
     */
    data class NewUser(val userId: String) : RegisterResult()

    /**
     * Represents a case where the user already exists in the system.
     *
     * @property userId The existing user's unique identifier.
     */
    data class ExistingUser(val userId: String) : RegisterResult()
}
