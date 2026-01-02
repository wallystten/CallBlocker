package com.seupacote.callblocker.util

import android.content.Context

object PremiumManager {

    private const val PREF_NAME = "premium_prefs"
    private const val KEY_PREMIUM = "is_premium"

    // 👉 CÓDIGO DE ATIVAÇÃO VÁLIDO
    private const val VALID_CODE = "CB-2025-999"

    fun isPremiumActive(context: Context): Boolean {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getBoolean(KEY_PREMIUM, false)
    }

    fun activateWithCode(context: Context, code: String): Boolean {
        if (code == VALID_CODE) {
            val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            prefs.edit().putBoolean(KEY_PREMIUM, true).apply()
            return true
        }
        return false
    }
}
