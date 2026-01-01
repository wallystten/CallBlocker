package com.seupacote.callblocker.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.seupacote.callblocker.R
import java.net.URLEncoder

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 🔐 Permissões do app
        findViewById<Button>(R.id.btnPermissions).setOnClickListener {
            openAppSettings()
        }

        // 📞 Filtro de chamadas (instrução manual)
        findViewById<Button>(R.id.btnCallFilter).setOnClickListener {
            openGeneralSettings()
            Toast.makeText(
                this,
                "Vá em Apps padrão > App de triagem de chamadas > Call Blocker",
                Toast.LENGTH_LONG
            ).show()
        }

        // 🔄 Inicialização automática
        findViewById<Button>(R.id.btnAutostart).setOnClickListener {
            openGeneralSettings()
        }

        // 🔋 Ignorar otimização de bateria
        findViewById<Button>(R.id.btnBattery).setOnClickListener {
            openBatterySettings()
        }

        // 💬 WhatsApp (SUPORTE / MONETIZAÇÃO)
        findViewById<Button>(R.id.btnWhatsapp).setOnClickListener {
            openWhatsapp()
        }
    }

    private fun openAppSettings() {
        try {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            intent.data = Uri.parse("package:$packageName")
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(this, "Não foi possível abrir as permissões", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openGeneralSettings() {
        startActivity(Intent(Settings.ACTION_SETTINGS))
    }

    private fun openBatterySettings() {
        startActivity(Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS))
    }

    private fun openWhatsapp() {
        try {
            val phone = "5547988818203" // SEU NÚMERO
            val message = "Olá, quero suporte / ativação do Call Blocker"
            val encodedMessage = URLEncoder.encode(message, "UTF-8")
            val url = "https://wa.me/$phone?text=$encodedMessage"

            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        } catch (e: Exception) {
            Toast.makeText(this, "WhatsApp não encontrado", Toast.LENGTH_SHORT).show()
        }
    }
}
