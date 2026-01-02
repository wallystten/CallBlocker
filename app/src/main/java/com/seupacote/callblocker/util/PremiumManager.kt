package com.seupacote.callblocker.util

import android.content.Context
import java.util.concurrent.TimeUnit

object PremiumManager {

    private const val PREFS = "premium_prefs"
    private const val KEY_PREMIUM_START = "premium_start"
    private const val PREMIUM_DAYS = 30L

    fun activatePremium(context: Context) {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        prefs.edit().putLong(KEY_PREMIUM_START, System.currentTimeMillis()).apply()
    }

    fun isPremiumActive(context: Context): Boolean {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        val start = prefs.getLong(KEY_PREMIUM_START, 0L)
        if (start == 0L) return false

        val daysUsed = TimeUnit.MILLISECONDS.toDays(
            System.currentTimeMillis() - start
        )
        return daysUsed < PREMIUM_DAYS
    }

    fun getDaysLeft(context: Context): Long {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        val start = prefs.getLong(KEY_PREMIUM_START, 0L)
        if (start == 0L) return 0

        val daysUsed = TimeUnit.MILLISECONDS.toDays(
            System.currentTimeMillis() - start
        )
        return (PREMIUM_DAYS - daysUsed).coerceAtLeast(0)
    }
}
