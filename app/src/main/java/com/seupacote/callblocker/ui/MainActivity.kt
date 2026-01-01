package com.seupacote.callblocker.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.seupacote.callblocker.R
import com.seupacote.callblocker.util.TrialManager

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnPermissions = findViewById<Button>(R.id.btnPermissions)
        val btnCallFilter = findViewById<Button>(R.id.btnCallFilter)
        val btnAutostart = findViewById<Button>(R.id.btnAutostart)
        val btnBattery = findViewById<Button>(R.id.btnBattery)
        val btnWhatsapp = findViewById<Button>(R.id.btnWhatsapp)

        btnPermissions.setOnClickListener {
            openAppSettings()
        }

        btnCallFilter.setOnClickListener {
            openCallScreeningSettings()
        }

        btnAutostart.setOnClickListener {
            openGeneralSettings()
        }

        btnBattery.setOnClickListener {
            openBatterySettings()
        }

        btnWhatsapp.setOnClickListener {
            openWhatsapp()
        }

        // 🔑 VERIFICA TRIAL
        if (!TrialManager.isTrialActive(this)) {
            showTrialExpiredDialog()
            btnWhatsapp.visibility = Button.VISIBLE
        }
    }

    private fun showTrialExpiredDialog() {
        AlertDialog.Builder(this)
            .setTitle("Teste gratuito finalizado")
            .setMessage(
                "Seu teste gratuito de 7 dias terminou.\n\n" +
                "Para continuar bloqueando chamadas desconhecidas, ative o plano Premium."
            )
            .setPositiveButton("Ativar Premium") { _, _ ->
                openWhatsapp()
            }
            .setNegativeButton("Agora não", null)
            .show()
    }

    private fun openWhatsapp() {
        val phone = "5547988818203"
        val message = "Olá! Quero ativar o Premium do Call Blocker."
        val uri = Uri.parse(
            "https://wa.me/$phone?text=${Uri.encode(message)}"
        )
        startActivity(Intent(Intent.ACTION_VIEW, uri))
    }

    private fun openAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.data = Uri.parse("package:$packageName")
        startActivity(intent)
    }

    private fun openCallScreeningSettings() {
        startActivity(Intent(Settings.ACTION_CALL_SCREENING_SETTINGS))
    }

    private fun openGeneralSettings() {
        startActivity(Intent(Settings.ACTION_SETTINGS))
    }

    private fun openBatterySettings() {
        startActivity(Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS))
    }
}
