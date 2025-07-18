// utils/PasswordUtil.kt
package utils

import at.favre.lib.crypto.bcrypt.BCrypt

object PasswordUtil {
    private const val cost = 12 // Adjust based on performance/security balance

    fun hashPassword(password: String): String {
        return BCrypt.withDefaults().hashToString(cost, password.toCharArray())
    }

    fun verifyPassword(password: String, hashed: String): Boolean {
        val result = BCrypt.verifyer().verify(password.toCharArray(), hashed)
        return result.verified
    }
}
