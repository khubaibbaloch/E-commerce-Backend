package utils

import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import java.security.SecureRandom


object AESUtil {
    private const val ALGORITHM = "AES/CBC/PKCS5Padding"
    private const val KEY = "1234567890123456" // 16 bytes = 128-bit key

    fun encrypt(data: String): String {
        val cipher = Cipher.getInstance(ALGORITHM)

        // 🔐 Create key spec
        val keySpec = SecretKeySpec(KEY.toByteArray(), "AES")

        // 🔁 Generate random IV
        val iv = ByteArray(16)
        SecureRandom().nextBytes(iv)
        val ivSpec = IvParameterSpec(iv)

        // 🔒 Encrypt data
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec)
        val encryptedBytes = cipher.doFinal(data.toByteArray())

        // 📦 Combine IV + encrypted data
        val combined = iv + encryptedBytes

        // 📤 Return Base64 encoded result
        return Base64.getEncoder().encodeToString(combined)
    }

    fun decrypt(data: String): String {
        val combined = Base64.getDecoder().decode(data)

        // 🔁 Extract IV (first 16 bytes) and encrypted data
        val iv = combined.copyOfRange(0, 16)
        val encryptedBytes = combined.copyOfRange(16, combined.size)

        // 🔐 Create key + IV specs
        val keySpec = SecretKeySpec(KEY.toByteArray(), "AES")
        val ivSpec = IvParameterSpec(iv)

        // 🔓 Decrypt
        val cipher = Cipher.getInstance(ALGORITHM)
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec)
        val decryptedBytes = cipher.doFinal(encryptedBytes)

        return String(decryptedBytes)
    }
}
