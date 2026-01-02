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
        val premiumActive = PremiumManager.isPremiumActive(this)
        val trialActive = TrialManager.isTrialActive(this)

        txtStatus.text = when {
            premiumActive -> {
                val days = PremiumManager.getDaysLeft(this)
                "⭐ Premium ativo\nDias restantes: $days\nBloqueio total ativo"
            }

            trialActive -> {
                val days = TrialManager.getDaysLeft(this)
                "🎁 Trial ativo\n$days dias restantes\nBloqueio ativo"
            }

            else -> {
                "⛔ Bloqueio desativado\nAssine o Premium para continuar"
            }
        }
    }
}
