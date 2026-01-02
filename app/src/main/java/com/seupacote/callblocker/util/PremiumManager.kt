package com.seupacote.callblocker.util

import android.content.Context

object PremiumManager {

    private const val PREFS_NAME = "premium_prefs"
    private const val KEY_PREMIUM_EXPIRATION = "premium_expiration"

    /**
     * 🔓 Verifica se o Premium está ativo
     */
    fun isPremiumActive(context: Context): Boolean {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val expiration = prefs.getLong(KEY_PREMIUM_EXPIRATION, 0L)
        return System.currentTimeMillis() < expiration
    }

    /**
     * 🔐 Ativa Premium por X dias (ex: 30)
     */
    fun activatePremium(context: Context, days: Int) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val expiration =
            System.currentTimeMillis() + days * 24L * 60L * 60L * 1000L

        prefs.edit().putLong(KEY_PREMIUM_EXPIRATION, expiration).apply()
    }

    /**
     * 📅 Dias restantes
     */
    fun getDaysLeft(context: Context): Int {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val expiration = prefs.getLong(KEY_PREMIUM_EXPIRATION, 0L)

        if (expiration == 0L) return 0

        val millisLeft = expiration - System.currentTimeMillis()
        return (millisLeft / (1000 * 60 * 60 * 24)).toInt().coerceAtLeast(0)
    }
}
