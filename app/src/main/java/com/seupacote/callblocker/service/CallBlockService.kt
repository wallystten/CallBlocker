package com.seupacote.callblocker.service

import android.provider.ContactsContract
import android.telecom.Call
import android.telecom.CallScreeningService

class CallBlockService : CallScreeningService() {

    override fun onScreenCall(callDetails: Call.Details) {

        val number = callDetails.handle?.schemeSpecificPart

        val isSavedContact = isNumberInContacts(number)

        val response = CallResponse.Builder()

        if (!isSavedContact) {
            // 🔴 BLOQUEIA TUDO QUE NÃO ESTÁ NA AGENDA
            response
                .setDisallowCall(true)
                .setRejectCall(true)
                .setSkipCallLog(false)
                .setSkipNotification(true)
        } else {
            // 🟢 PERMITE CONTATOS SALVOS
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
