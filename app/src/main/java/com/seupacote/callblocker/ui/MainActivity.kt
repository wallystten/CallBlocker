package com.seupacote.callblocker.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.telecom.TelecomManager
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

        // 🔐 Permissões
        findViewById<Button>(R.id.btnPermissions).setOnClickListener {
            handlePermissionsFlow()
        }

        // ⚙️ Configurações do sistema
        findViewById<Button>(R.id.btnAutostart).setOnClickListener {
            openGeneralSettings()
        }

        // 🔋 Bateria
        findViewById<Button>(R.id.btnBattery).setOnClickListener {
            openBatterySettings()
        }

        // 💬 WhatsApp
        findViewById<Button>(R.id.btnWhatsapp).setOnClickListener {
            openWhatsApp()
        }

        updateStatus()
    }

    private fun handlePermissionsFlow() {
        // 1️⃣ Contatos
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

        // 2️⃣ Telefone
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

        // 3️⃣ Filtro de chamadas
        openCallScreeningSettings()
    }

    private fun openCallScreeningSettings() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val intent = Intent(TelecomManager.ACTION_CHANGE_DEFAULT_DIALER)
                intent.putExtra(
                    TelecomManager.EXTRA_CHANGE_DEFAULT_DIALER_PACKAGE_NAME,
                    packageName
                )
                startActivity(intent)
            } else {
                startActivity(Intent(Settings.ACTION_SETTINGS))
            }
        } catch (e: Exception) {
            Toast.makeText(
                this,
                "Não foi possível abrir as configurações de chamadas",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun openGeneralSettings() {
        startActivity(Intent(Settings.ACTION_SETTINGS))
    }

    private fun openBatterySettings() {
        startActivity(Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS))
    }

    private fun openWhatsApp() {
        val phone = "5547988818203"
        val uri = Uri.parse("https://wa.me/$phone")
        startActivity(Intent(Intent.ACTION_VIEW, uri))
    }

    private fun updateStatus() {
        val premiumActive = PremiumManager.isPremiumActive(this)
        val trialActive = TrialManager.isTrialActive(this)
        val daysLeft = TrialManager.getDaysLeft(this)

        txtStatus.text = when {
            premiumActive ->
                "Premium ativo\nBloqueio total ativo"

            trialActive ->
                "Trial ativo\n$daysLeft dia(s) restantes\nBloqueio ativo"

            else ->
                "Bloqueio desativado\nAtive o Premium"
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        updateStatus()
    }
}
