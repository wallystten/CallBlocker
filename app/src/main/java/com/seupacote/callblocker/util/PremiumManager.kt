package com.seupacote.callblocker.util

import android.content.Context

object PremiumManager {

    private const val PREFS = "premium_prefs"
    private const val KEY_PREMIUM = "premium_active"

    fun activatePremium(context: Context) {
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .edit()
            .putBoolean(KEY_PREMIUM, true)
            .apply()
    }

    fun isPremiumActive(context: Context): Boolean {
        return context
            .getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .getBoolean(KEY_PREMIUM, false)
    }
}
