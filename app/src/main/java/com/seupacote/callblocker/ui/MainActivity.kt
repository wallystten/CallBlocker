package com.seupacote.callblocker.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.seupacote.callblocker.R
import com.seupacote.callblocker.util.PremiumManager
import com.seupacote.callblocker.util.TrialManager

class MainActivity : AppCompatActivity() {

    private lateinit var txtStatus: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtStatus = findViewById(R.id.txtStatus)

        findViewById<Button>(R.id.btnPermissions).setOnClickListener {
            handlePermissionsFlow()
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

        updateStatus()
    }

    // 🔐 Fluxo guiado de permissões
    private fun handlePermissionsFlow() {

        // 1️⃣ CONTATOS
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_CONTACTS),
                100
            )
            return
        }

        // 2️⃣ TELEFONE
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_PHONE_STATE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_PHONE_STATE),
                101
            )
            return
        }

        // 3️⃣ CALL SCREENING (manual, sem API quebrada)
        Toast.makeText(
            this,
            "Agora ative o filtro de chamadas para o Call Blocker",
            Toast.LENGTH_LONG
        ).show()

        startActivity(Intent(Settings.ACTION_SETTINGS))
    }

    // 📊 Atualiza status (Trial / Premium)
    private fun updateStatus() {

        val premiumActive = PremiumManager.isPremiumActive(this)
        val premiumDays = PremiumManager.getDaysLeft(this)

        val trialActive = TrialManager.isTrialActive(this)
        val daysLeft = TrialManager.getDaysLeft(this)

        txtStatus.text = when {
            premiumActive ->
                "✅ Premium ativo\n$premiumDays dia(s) restantes\nBloqueio total ativo"

            trialActive ->
                "🆓 Trial ativo\n$daysLeft dia(s) restantes\nBloqueio ativo"

            else ->
                "⛔ Bloqueio desativado\nAtive o Premium"
        }
    }

    // ⚙️ Configurações gerais
    private fun openGeneralSettings() {
        startActivity(Intent(Settings.ACTION_SETTINGS))
    }

    // 🔋 Ignorar otimização de bateria
    private fun openBatterySettings() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            startActivity(Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS))
        } else {
            openGeneralSettings()
        }
    }

    // 💬 WhatsApp
    private fun openWhatsApp() {
        val phone = "5547988818203"
        val uri = Uri.parse("https://wa.me/$phone")
        startActivity(Intent(Intent.ACTION_VIEW, uri))
    }

    // 🔄 Retorno das permissões
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        updateStatus()
    }
}
  
