package utils

import java.security.KeyFactory
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.PrivateKey
import java.security.PublicKey
import java.security.spec.X509EncodedKeySpec
import java.util.Base64
import javax.crypto.Cipher

object RSAUtil {
    private const val ALGORITHM = "RSA"

    // Generate a key pair (for testing or in-memory use)
    fun generateKeyPair(): KeyPair {
        val generator = KeyPairGenerator.getInstance(ALGORITHM)
        generator.initialize(2048)
        return generator.generateKeyPair()
    }

    fun encrypt(data: String, publicKey: PublicKey): String {
        val cipher = Cipher.getInstance(ALGORITHM)
        cipher.init(Cipher.ENCRYPT_MODE, publicKey)
        val encryptedBytes = cipher.doFinal(data.toByteArray())
        return Base64.getEncoder().encodeToString(encryptedBytes)
    }

    fun decrypt(encryptedData: String, privateKey: PrivateKey): String {
        val cipher = Cipher.getInstance(ALGORITHM)
        cipher.init(Cipher.DECRYPT_MODE, privateKey)
        val decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData))
        return String(decryptedBytes)
    }

    // If you store public key as base64 (e.g. from frontend)
    fun loadPublicKey(base64: String): PublicKey {
        val keyBytes = Base64.getDecoder().decode(base64)
        val keySpec = X509EncodedKeySpec(keyBytes)
        return KeyFactory.getInstance(ALGORITHM).generatePublic(keySpec)
    }

    fun getBase64PublicKey(keyPair: KeyPair): String {
        return Base64.getEncoder().encodeToString(keyPair.public.encoded)
    }

    fun getBase64PrivateKey(keyPair: KeyPair): String {
        return Base64.getEncoder().encodeToString(keyPair.private.encoded)
    }
}
