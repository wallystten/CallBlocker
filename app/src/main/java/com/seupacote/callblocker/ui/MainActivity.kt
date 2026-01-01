package com.seupacote.callblocker.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.seupacote.callblocker.R

class MainActivity : AppCompatActivity() {

    private val REQUEST_CONTACTS = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 🔐 Conceder permissões
        findViewById<Button>(R.id.btnPermissions).setOnClickListener {
            handlePermissionsFlow()
        }

        // 📞 Ativar filtro de chamadas
        findViewById<Button>(R.id.btnCallFilter).setOnClickListener {
            openCallScreeningInstructions()
        }

        // 🔄 Inicialização automática
        findViewById<Button>(R.id.btnAutostart).setOnClickListener {
            openGeneralSettings()
        }

        // 🔋 Ignorar otimização de bateria
        findViewById<Button>(R.id.btnBattery).setOnClickListener {
            openBatterySettings()
        }

        // 💬 WhatsApp
        findViewById<Button>(R.id.btnWhatsapp).setOnClickListener {
            openWhatsapp()
        }
    }

    /**
     * Fluxo inteligente de permissões
     */
    private fun handlePermissionsFlow() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // 🔵 POPUP AUTOMÁTICO DE CONTATOS
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_CONTACTS),
                REQUEST_CONTACTS
            )
        } else {
            // Já concedido → abre configurações do app
            openAppSettings()
        }
    }

    /**
     * Retorno do popup de permissões
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_CONTACTS) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(
                    this,
                    "Permissão concedida. Agora ative o filtro de chamadas.",
                    Toast.LENGTH_LONG
                ).show()
                openAppSettings()
            } else {
                Toast.makeText(
                    this,
                    "Sem acesso aos contatos o bloqueio não funciona.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    /**
     * Instruções para ativar triagem de chamadas
     */
    private fun openCallScreeningInstructions() {
        openGeneralSettings()
        Toast.makeText(
            this,
            "Vá em Apps padrão > App de triagem de chamadas > Call Blocker",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun openAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.data = Uri.parse("package:$packageName")
        startActivity(intent)
    }

    private fun openGeneralSettings() {
        startActivity(Intent(Settings.ACTION_SETTINGS))
    }

    private fun openBatterySettings() {
        startActivity(Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS))
    }

    private fun openWhatsapp() {
        val phone = "5547988818203"
        val message = "Olá, quero suporte / ativação do Call Blocker"
        val url =
            "https://wa.me/$phone?text=${java.net.URLEncoder.encode(message, "UTF-8")}"
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }
}
