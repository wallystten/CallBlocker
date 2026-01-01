package com.seupacote.callblocker.service

import android.provider.ContactsContract
import android.telecom.Call
import android.telecom.CallScreeningService
import android.telecom.CallScreeningService.CallResponse
import com.seupacote.callblocker.util.TrialManager

class CallBlockService : CallScreeningService() {

    override fun onScreenCall(callDetails: Call.Details) {

        val number = callDetails.handle?.schemeSpecificPart

        // 🔑 VERIFICA SE O TRIAL AINDA ESTÁ ATIVO
        val isTrialActive = TrialManager.isTrialActive(this)

        if (!isTrialActive) {
            // ⛔ Trial acabou → NÃO bloqueia nada
            respondToCall(callDetails, CallResponse.Builder().build())
            return
        }

        val isSavedContact = isNumberInContacts(number)

        val response = CallResponse.Builder()

        if (!isSavedContact) {
            // 🔴 TRIAL ATIVO → BLOQUEIA NÚMEROS NÃO SALVOS
            response
                .setDisallowCall(true)
                .setRejectCall(true)
                .setSkipCallLog(false)
                .setSkipNotification(true)
        } else {
            // 🟢 CONTATO SALVO → PERMITE
            response.setDisallowCall(false)
        }

        respondToCall(callDetails, response.build())
    }

    private fun isNumberInContacts(number: String?): Boolean {
        if (number.isNullOrBlank()) return false

        val uri = ContactsContract.PhoneLookup.CONTENT_FILTER_URI.buildUpon()
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
