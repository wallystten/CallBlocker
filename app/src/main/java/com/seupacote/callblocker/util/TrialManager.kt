package com.seupacote.callblocker.util

import android.content.Context

object TrialManager {

    private const val PREFS_NAME = "trial_prefs"
    private const val KEY_FIRST_LAUNCH = "first_launch"
    private const val TRIAL_DAYS = 7L

    fun isTrialActive(context: Context): Boolean {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        val firstLaunch = prefs.getLong(KEY_FIRST_LAUNCH, 0L)

        val now = System.currentTimeMillis()

        // Primeira execução do app
        if (firstLaunch == 0L) {
            prefs.edit().putLong(KEY_FIRST_LAUNCH, now).apply()
            return true
        }

        val daysPassed =
            (now - firstLaunch) / (1000 * 60 * 60 * 24)

        return daysPassed < TRIAL_DAYS
    }

    // (opcional – só para testes)
    fun resetTrial(context: Context) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .clear()
            .apply()
    }
}
