package com.seupacote.callblocker.util

import android.content.Context

object PremiumManager {

    private const val PREFS_NAME = "premium_prefs"
    private const val KEY_PREMIUM_ACTIVE = "premium_active"

    fun isPremiumActive(context: Context): Boolean {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getBoolean(KEY_PREMIUM_ACTIVE, false)
    }

    fun activatePremium(context: Context) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putBoolean(KEY_PREMIUM_ACTIVE, true).apply()
    }

    fun deactivatePremium(context: Context) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putBoolean(KEY_PREMIUM_ACTIVE, false).apply()
    }
}
