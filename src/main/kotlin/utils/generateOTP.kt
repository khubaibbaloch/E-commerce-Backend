package utils

fun generateOTP(length: Int = 6): String {
    val digits = "0123456789"
    return (1..length)
        .map { digits.random() }
        .joinToString("")
}
