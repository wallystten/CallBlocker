package com.seupacote.callblocker.util

import android.content.Context
import java.util.concurrent.TimeUnit

object TrialManager {

    private const val PREFS_NAME = "call_blocker_prefs"
    private const val KEY_INSTALL_TIME = "install_time"
    private const val TRIAL_DAYS = 7L

    fun isTrialActive(context: Context): Boolean {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        val installTime = prefs.getLong(KEY_INSTALL_TIME, 0L)

        if (installTime == 0L) {
            // Primeira execução
            prefs.edit()
                .putLong(KEY_INSTALL_TIME, System.currentTimeMillis())
                .apply()
            return true
        }

        val now = System.currentTimeMillis()
        val diffDays = TimeUnit.MILLISECONDS.toDays(now - installTime)

        return diffDays < TRIAL_DAYS
    }
}
