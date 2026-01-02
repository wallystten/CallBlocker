package com.seupacote.callblocker.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.seupacote.callblocker.R
import com.seupacote.callblocker.util.PremiumManager
import com.seupacote.callblocker.util.TrialManager

class MainActivity : AppCompatActivity() {

    private lateinit var txtStatus: TextView
    private lateinit var txtHint: TextView
    private lateinit var btnPremium: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtStatus = findViewById(R.id.txtStatus)
        txtHint = findViewById(R.id.txtHint)
        btnPremium = findViewById(R.id.btnPremium)

        findViewById<Button>(R.id.btnPermissions).setOnClickListener {
            requestPermissions()
        }

        findViewById<Button>(R.id.btnBattery).setOnClickListener {
            openBatterySettings()
        }

        findViewById<Button>(R.id.btnWhatsapp).setOnClickListener {
            openWhatsApp()
        }

        btnPremium.setOnClickListener {
            startActivity(Intent(this, PremiumActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }

    private fun updateUI() {
        val isPremium = PremiumManager.isPremiumActive(this)
        val isTrialActive = TrialManager.isTrialActive(this)
        val daysLeft = TrialManager.getDaysLeft(this)

        when {
            isPremium -> {
                txtStatus.text = "🟢 Premium ativo\nBloqueio total habilitado"
                txtHint.text = "Obrigado por apoiar o projeto 💜"
                btnPremium.visibility = Button.GONE
            }

            isTrialActive -> {
                txtStatus.text = "🟢 Trial ativo: $daysLeft dia(s)\nBloqueio disponível"
                txtHint.text =
                    "⚠️ Para funcionar, ative manualmente:\nConfigurações > Apps padrão > App de triagem de chamadas"
                btnPremium.visibility = Button.VISIBLE
                btnPremium.text = "Assinar Premium – R$ 9,90/mês"
            }

            else -> {
                txtStatus.text = "🔴 Trial expirado\nBloqueio desativado"
                txtHint.text = "Assine o Premium para continuar protegido"
                btnPremium.visibility = Button.VISIBLE
                btnPremium.text = "Assinar Premium – R$ 9,90/mês"
            }
        }
    }

    private fun requestPermissions() {
        val permissions = arrayOf(
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.READ_PHONE_STATE
        )

        val missing = permissions.filter {
            ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }

        if (missing.isNotEmpty()) {
            ActivityCompat.requestPermissions(this, missing.toTypedArray(), 100)
        }
    }

    private fun openBatterySettings() {
        startActivity(Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS))
    }

    private fun openWhatsApp() {
        val uri = Uri.parse("https://wa.me/5547988818203")
        startActivity(Intent(Intent.ACTION_VIEW, uri))
    }
}
  
