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

class MainActivity : AppCompatActivity() {

    private lateinit var txtStatus: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtStatus = findViewById(R.id.txtStatus)

        findViewById<Button>(R.id.btnPermissions).setOnClickListener {
            requestPermissions()
        }

        findViewById<Button>(R.id.btnAutostart).setOnClickListener {
            openSystemSettings()
        }

        findViewById<Button>(R.id.btnBattery).setOnClickListener {
            openBatterySettings()
        }

        findViewById<Button>(R.id.btnWhatsapp).setOnClickListener {
            openWhatsApp()
        }

        updateStatus()
    }

    // 🔐 Permissões
    private fun requestPermissions() {
        val permissions = mutableListOf<String>()

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
            != PackageManager.PERMISSION_GRANTED
        ) {
            permissions.add(Manifest.permission.READ_CONTACTS)
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            permissions.add(Manifest.permission.READ_PHONE_STATE)
        }

        if (permissions.isNotEmpty()) {
            ActivityCompat.requestPermissions(this, permissions.toTypedArray(), 100)
        } else {
            openCallScreeningSettings()
        }
    }

    // 📞 TELA CORRETA DO FILTRO DE CHAMADAS
    private fun openCallScreeningSettings() {
        try {
            val intent = Intent(TelecomManager.ACTION_CHANGE_DEFAULT_DIALER)
            intent.putExtra(
                TelecomManager.EXTRA_CHANGE_DEFAULT_DIALER_PACKAGE_NAME,
                packageName
            )
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(
                this,
                "Ative o filtro de chamadas manualmente nas configurações",
                Toast.LENGTH_LONG
            ).show()
            startActivity(Intent(Settings.ACTION_SETTINGS))
        }
    }

    private fun openSystemSettings() {
        startActivity(Intent(Settings.ACTION_SETTINGS))
    }

    private fun openBatterySettings() {
        startActivity(Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS))
    }

    private fun openWhatsApp() {
        val uri = Uri.parse("https://wa.me/5547988818203")
        startActivity(Intent(Intent.ACTION_VIEW, uri))
    }

    private fun updateStatus() {
        val contactsGranted =
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) ==
                    PackageManager.PERMISSION_GRANTED

        val phoneGranted =
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) ==
                    PackageManager.PERMISSION_GRANTED

        txtStatus.text =
            if (contactsGranted && phoneGranted) {
                "Permissões concedidas\nAtive o filtro de chamadas"
            } else {
                "Permissões pendentes"
            }
    }
}
