package com.seupacote.callblocker.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.Toast
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

        // 🔐 Permissões
        btnPermissions.setOnClickListener {
            openAppSettings()
        }

        // 📞 Filtro de chamadas (abre config do app – caminho correto)
        btnCallFilter.setOnClickListener {
            openAppSettings()
        }

        // 🚀 Autostart
        btnAutostart.setOnClickListener {
            openGeneralSettings()
        }

        // 🔋 Bateria
        btnBattery.setOnClickListener {
            openBatterySettings()
        }

        // 💬 WhatsApp (apenas se trial acabou)
        if (!TrialManager.isTrialActive(this)) {
            btnWhatsapp.visibility = Button.VISIBLE
            btnWhatsapp.setOnClickListener {
                openWhatsapp()
            }
        }
    }

    private fun openAppSettings() {
        try {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            intent.data = Uri.parse("package:$packageName")
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(this, "Não foi possível abrir as configurações", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openGeneralSettings() {
        startActivity(Intent(Settings.ACTION_SETTINGS))
    }

    private fun openBatterySettings() {
        startActivity(Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS))
    }

    private fun openWhatsapp() {
        val number = "5547988818203"
        val url = "https://wa.me/$number"
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }
}
