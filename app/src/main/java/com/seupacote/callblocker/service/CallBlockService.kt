package com.seupacote.callblocker.service

import android.provider.ContactsContract
import android.telecom.Call
import android.telecom.CallScreeningService
import com.seupacote.callblocker.util.TrialManager

class CallBlockService : CallScreeningService() {

    override fun onScreenCall(callDetails: Call.Details) {

        val number = callDetails.handle?.schemeSpecificPart

        // 🔑 Verifica se o trial está ativo
        val isTrialActive = TrialManager.isTrialActive(this)

        // ⛔ Trial expirado → não bloqueia nada
        if (!isTrialActive) {
            respondToCall(callDetails, CallResponse.Builder().build())
            return
        }

        // 🔍 Verifica se o número está salvo
        val isSavedContact = isNumberInContacts(number)

        val response = CallResponse.Builder()

        if (!isSavedContact) {
            // 🔴 Bloqueia chamadas de números NÃO salvos
            response
                .setDisallowCall(true)
                .setRejectCall(true)
                .setSkipCallLog(false)
                .setSkipNotification(true)
        }

        respondToCall(callDetails, response.build())
    }

    private fun isNumberInContacts(number: String?): Boolean {
        if (number.isNullOrBlank()) return false

        val uri = ContactsContract.PhoneLookup.CONTENT_FILTER_URI
            .buildUpon()
            .appendPath(number)
            .build()

        contentResolver.query(
            uri,
            arrayOf(ContactsContract.PhoneLookup._ID),
            null,
            null,
            null
        )?.use { cursor ->
            return cursor.moveToFirst()
        }

        return false
    }
}
