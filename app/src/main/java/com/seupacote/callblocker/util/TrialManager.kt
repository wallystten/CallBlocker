package com.seupacote.callblocker.util

import android.content.Context
import kotlin.math.max

object TrialManager {

    private const val PREFS_NAME = "trial_prefs"
    private const val KEY_FIRST_LAUNCH = "first_launch"
    private const val TRIAL_DAYS = 7L

    /**
     * Verifica se o trial ainda está ativo
     */
    fun isTrialActive(context: Context): Boolean {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        val firstLaunch = prefs.getLong(KEY_FIRST_LAUNCH, 0L)
        val now = System.currentTimeMillis()

        // Primeira execução do app
        if (firstLaunch == 0L) {
            prefs.edit().putLong(KEY_FIRST_LAUNCH, now).apply()
            return true
        }

        val daysPassed = (now - firstLaunch) / (1000 * 60 * 60 * 24)

        return daysPassed < TRIAL_DAYS
    }

    /**
     * Retorna quantos dias restam do trial
     */
    fun getDaysLeft(context: Context): Long {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        val firstLaunch = prefs.getLong(KEY_FIRST_LAUNCH, 0L)
        if (firstLaunch == 0L) return TRIAL_DAYS

        val now = System.currentTimeMillis()
        val daysPassed = (now - firstLaunch) / (1000 * 60 * 60 * 24)

        return max(0, TRIAL_DAYS - daysPassed)
    }

    /**
     * (Opcional) Resetar o trial – útil só para testes
     */
    fun resetTrial(context: Context) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .clear()
            .apply()
    }
}
