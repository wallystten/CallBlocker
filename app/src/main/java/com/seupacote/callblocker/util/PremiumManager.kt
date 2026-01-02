package com.seupacote.callblocker.util

import android.content.Context
import android.content.SharedPreferences

object PremiumManager {

    private const val PREF_NAME = "premium_prefs"
    private const val KEY_PREMIUM_ACTIVE = "premium_active"
    private const val KEY_PREMIUM_START = "premium_start"
    private const val PREMIUM_DAYS = 30L

    private fun prefs(context: Context): SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    // 🔑 ATIVA PREMIUM MANUALMENTE
    fun activatePremium(context: Context) {
        prefs(context).edit()
            .putBoolean(KEY_PREMIUM_ACTIVE, true)
            .putLong(KEY_PREMIUM_START, System.currentTimeMillis())
            .apply()
    }

    // ❌ DESATIVA PREMIUM
    fun deactivatePremium(context: Context) {
        prefs(context).edit()
            .putBoolean(KEY_PREMIUM_ACTIVE, false)
            .apply()
    }

    // ✅ VERIFICA SE PREMIUM ESTÁ ATIVO
    fun isPremiumActive(context: Context): Boolean {
        val active = prefs(context).getBoolean(KEY_PREMIUM_ACTIVE, false)
        if (!active) return false

        val start = prefs(context).getLong(KEY_PREMIUM_START, 0L)
        val daysUsed =
            (System.currentTimeMillis() - start) / (1000 * 60 * 60 * 24)

        return daysUsed < PREMIUM_DAYS
    }

    // ⏳ DIAS RESTANTES
    fun getDaysLeft(context: Context): Long {
        val start = prefs(context).getLong(KEY_PREMIUM_START, 0L)
        if (start == 0L) return 0

        val daysUsed =
            (System.currentTimeMillis() - start) / (1000 * 60 * 60 * 24)

        return (PREMIUM_DAYS - daysUsed).coerceAtLeast(0)
    }
}
