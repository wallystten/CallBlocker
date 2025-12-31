package com.seupacote.callblocker.service

import android.telecom.Call
import android.telecom.CallScreeningService
import com.seupacote.callblocker.utils.BlockList

class CallBlockService : CallScreeningService() {

    override fun onScreenCall(callDetails: Call.Details) {

        val number = callDetails.handle?.schemeSpecificPart

        val responseBuilder = CallResponse.Builder()

        if (BlockList.isBlocked(number)) {
            // BLOQUEIA A CHAMADA
            responseBuilder
                .setDisallowCall(true)
                .setRejectCall(true)
                .setSkipCallLog(false)
                .setSkipNotification(true)
        } else {
            // PERMITE A CHAMADA
            responseBuilder.setDisallowCall(false)
        }

        respondToCall(callDetails, responseBuilder.build())
    }
}
