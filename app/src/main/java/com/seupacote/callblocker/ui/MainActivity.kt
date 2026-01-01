package com.seupacote.callblocker.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.seupacote.callblocker.R
import com.seupacote.callblocker.util.TrialManager
import kotlin.math.max

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val txtStatusTitle = findViewById<TextView>(R.id.txtStatusTitle)
        val txtStatusSubtitle = findViewById<TextView>(R.id.txtStatusSubtitle)
        val txtTrial = findViewById<TextView>(R.id.txtTrial)
        val btnWhatsapp = findViewById<Button>(R.id.btnWhatsapp)

        // STATUS DO TRIAL
        if (TrialManager.isTrialActive(this)) {
            val daysLeft = TrialManager.getDaysLeft(this)

            txtStatusTitle.text = "🛡️ Proteção ativa"
            txtStatusSubtitle.text =
                "Chamadas desconhecidas estão sendo bloqueadas automaticamente."
            txtTrial.text = "🎁 Teste gratuito: $daysLeft dias restantes"
        } else {
            txtStatusTitle.text = "🔒 Proteção desativada"
            txtStatusSubtitle.text =
                "Seu teste gratuito terminou. Ative o Premium para continuar bloqueando chamadas."
            txtTrial.text = "⛔ Teste gratuito finalizado"

            btnWhatsapp.visibility = Button.VISIBLE
            btnWhatsapp.setOnClickListener {
                openWhatsapp()
            }
        }

        // BOTÕES
        findViewById<Button>(R.id.btnPermissions).setOnClickListener {
            openAppSettings()
        }

        findViewById<Button>(R.id.btnCallFilter).setOnClickListener {
            openAppSettings()
        }

        findViewById<Button>(R.id.btnAutostart).setOnClickListener {
            startActivity(Intent(Settings.ACTION_SETTINGS))
        }

        findViewById<Button>(R.id.btnBattery).setOnClickListener {
            startActivity(Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS))
        }
    }

    private fun openAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.data = Uri.parse("package:$packageName")
        startActivity(intent)
    }

    private fun openWhatsapp() {
        val number = "5547988818203"
        val url = "https://wa.me/$number"
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }
}
  
