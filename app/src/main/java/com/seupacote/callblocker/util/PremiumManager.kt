package com.seupacote.callblocker.util

import android.content.Context

object PremiumManager {

    private const val PREF_NAME = "premium_prefs"
    private const val KEY_PREMIUM_ACTIVE = "premium_active"

    // Código fixo (exemplo)
    private const val VALID_CODE = "CALLBLOCKER2025"

    fun isPremiumActive(context: Context): Boolean {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getBoolean(KEY_PREMIUM_ACTIVE, false)
    }

    fun activatePremium(context: Context, code: String): Boolean {
        if (code != VALID_CODE) return false

        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().putBoolean(KEY_PREMIUM_ACTIVE, true).apply()
        return true
    }

    fun deactivatePremium(context: Context) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().putBoolean(KEY_PREMIUM_ACTIVE, false).apply()
    }
}
