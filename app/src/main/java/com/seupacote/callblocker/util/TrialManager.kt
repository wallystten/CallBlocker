package com.seupacote.callblocker.util

import android.content.Context

object TrialManager {

    private const val PREFS_NAME = "trial_prefs"
    private const val KEY_START_DATE = "trial_start_date"
    private const val TRIAL_DAYS = 7

    fun isTrialActive(context: Context): Boolean {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        val startDate = prefs.getLong(KEY_START_DATE, 0L)

        if (startDate == 0L) {
            // Primeira execução → inicia o trial
            prefs.edit().putLong(KEY_START_DATE, System.currentTimeMillis()).apply()
            return true
        }

        val elapsedMillis = System.currentTimeMillis() - startDate
        val elapsedDays = elapsedMillis / (1000 * 60 * 60 * 24)

        return elapsedDays < TRIAL_DAYS
    }

    fun getDaysLeft(context: Context): Int {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val startDate = prefs.getLong(KEY_START_DATE, 0L)

        if (startDate == 0L) return TRIAL_DAYS

        val elapsedMillis = System.currentTimeMillis() - startDate
        val elapsedDays = elapsedMillis / (1000 * 60 * 60 * 24)

        return (TRIAL_DAYS - elapsedDays).toInt().coerceAtLeast(0)
    }
}
