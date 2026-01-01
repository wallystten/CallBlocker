package com.seupacote.callblocker.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.seupacote.callblocker.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btnPermissions).setOnClickListener {
            openAppPermissions()
        }

        findViewById<Button>(R.id.btnCallFilter).setOnClickListener {
            openCallScreeningSettings()
        }

        findViewById<Button>(R.id.btnAutostart).setOnClickListener {
            openGeneralSettings()
        }

        findViewById<Button>(R.id.btnBattery).setOnClickListener {
            openBatterySettings()
        }

        findViewById<Button>(R.id.btnWhatsapp).setOnClickListener {
            openWhatsApp()
        }
    }

    /** 🔐 Abre permissões do app (Android 9–15) */
    private fun openAppPermissions() {
        try {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                data = Uri.parse("package:$packageName")
            }
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(this, "Não foi possível abrir permissões", Toast.LENGTH_SHORT).show()
        }
    }

    /** 📞 Abre tela correta para definir app como identificador/filtro de chamadas */
    private fun openCallScreeningSettings() {
        try {
            val intent = Intent(Settings.ACTION_MANAGE_DEFAULT_APPS_SETTINGS)
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(this, "Abra Configurações > Apps padrão", Toast.LENGTH_LONG).show()
        }
    }

    /** ⚙️ Configurações gerais (autostart varia por fabricante) */
    private fun openGeneralSettings() {
        startActivity(Intent(Settings.ACTION_SETTINGS))
    }

    /** 🔋 Ignorar otimização de bateria */
    private fun openBatterySettings() {
        startActivity(Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS))
    }

    /** 💬 WhatsApp suporte */
    private fun openWhatsApp() {
        val phone = "5547988818203"
        val uri = Uri.parse("https://wa.me/$phone")
        startActivity(Intent(Intent.ACTION_VIEW, uri))
    }
}
