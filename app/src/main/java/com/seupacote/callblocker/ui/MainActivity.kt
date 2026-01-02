package com.seupacote.callblocker.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.seupacote.callblocker.R
import com.seupacote.callblocker.util.PremiumManager
import com.seupacote.callblocker.util.TrialManager

class MainActivity : AppCompatActivity() {

    private lateinit var txtStatus: TextView
    private lateinit var btnPremium: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtStatus = findViewById(R.id.txtStatus)
        btnPremium = findViewById(R.id.btnPremium)

        btnPremium.setOnClickListener {
            startActivity(Intent(this, PremiumActivity::class.java))
        }

        updateStatus()
    }

    override fun onResume() {
        super.onResume()
        updateStatus()
    }

    private fun updateStatus() {
    val isPremium = PremiumManager.isPremiumActive(this)
    val isTrial = TrialManager.isTrialActive(this)

    txtStatus.text = when {
        isPremium ->
            "💎 Premium ativo\nBloqueio total habilitado"

        isTrial ->
            "🎁 Trial ativo\nBloqueio habilitado"

        else ->
            "🔒 Bloqueio desativado\nAssine o Premium"
    }
  }
}
