package utils

import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.ChaCha20ParameterSpec
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object ChaCha20Util {
    private const val ALGORITHM = "ChaCha20"
    private val key = ByteArray(32) { it.toByte() }       // 256-bit key
    private val nonce = ByteArray(12) { it.toByte() }     // 96-bit nonce

    fun encrypt(data: String): String {
        val cipher = Cipher.getInstance(ALGORITHM)
        val keySpec = SecretKeySpec(key, ALGORITHM)
        val paramSpec = ChaCha20ParameterSpec(nonce, 1)
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, paramSpec)
        return Base64.getEncoder().encodeToString(cipher.doFinal(data.toByteArray()))
    }

    fun decrypt(data: String): String {
        val cipher = Cipher.getInstance(ALGORITHM)
        val keySpec = SecretKeySpec(key, ALGORITHM)
        val paramSpec = ChaCha20ParameterSpec(nonce, 1)
        cipher.init(Cipher.DECRYPT_MODE, keySpec, paramSpec)
        return String(cipher.doFinal(Base64.getDecoder().decode(data)))
    }
}
