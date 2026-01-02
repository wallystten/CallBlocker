package com.seupacote.callblocker.util

import android.content.Context

object PremiumManager {

    private const val PREFS_NAME = "premium_prefs"
    private const val KEY_PREMIUM_ENABLED = "premium_enabled"
    private const val KEY_PREMIUM_CODE = "premium_code"

    fun isPremiumActive(context: Context): Boolean {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getBoolean(KEY_PREMIUM_ENABLED, false)
    }

    fun activatePremium(context: Context, code: String): Boolean {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        // Aqui você decide qual código é válido
        // Ex: “PREMIUM30”, “SUPORTE123”, etc
        // Você pode mudar para o código que quiser
        return if (code == "PREMIUM30") {
            prefs.edit()
                .putBoolean(KEY_PREMIUM_ENABLED, true)
                .putString(KEY_PREMIUM_CODE, code)
                .apply()
            true
        } else {
            false
        }
    }
}
