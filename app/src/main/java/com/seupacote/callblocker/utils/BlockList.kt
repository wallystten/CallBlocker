package com.seupacote.callblocker.utils

object BlockList {

    // Números no formato internacional ou nacional
    private val blockedNumbers = setOf(
        "+5511999999999",
        "11988888888",
        "5551234"
    )

    fun isBlocked(number: String?): Boolean {
        if (number == null) return false
        return blockedNumbers.any { number.contains(it) }
    }
}
