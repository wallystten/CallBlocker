package com.seupacote.callblocker.service

import android.provider.ContactsContract
import android.telecom.Call
import android.telecom.CallScreeningService
import com.seupacote.callblocker.util.TrialManager
import com.seupacote.callblocker.util.PremiumManager

class CallBlockService : CallScreeningService() {

    override fun onScreenCall(callDetails: Call.Details) {

        val number = callDetails.handle?.schemeSpecificPart

        val isPremium = PremiumManager.isPremiumActive(this)
        val isTrialActive = TrialManager.isTrialActive(this)

        // ❌ Sem trial e sem premium → NÃO bloqueia
        if (!isPremium && !isTrialActive) {
            respondToCall(callDetails, CallResponse.Builder().build())
            return
        }

        val isSavedContact = isNumberInContacts(number)

        val response = CallResponse.Builder()

        if (!isSavedContact) {
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
        )?.use {
            return it.moveToFirst()
        }

        return false
    }
}
