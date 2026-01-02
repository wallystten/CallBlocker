package com.seupacote.callblocker.util

import android.content.Context

object PremiumManager {

    private const val PREFS = "premium_prefs"
    private const val KEY_PREMIUM = "premium_active"
    private const val KEY_PREMIUM_DATE = "premium_date"

    fun activatePremium(context: Context) {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        prefs.edit()
            .putBoolean(KEY_PREMIUM, true)
            .putLong(KEY_PREMIUM_DATE, System.currentTimeMillis())
            .apply()
    }

    fun isPremiumActive(context: Context): Boolean {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        return prefs.getBoolean(KEY_PREMIUM, false)
    }
}
