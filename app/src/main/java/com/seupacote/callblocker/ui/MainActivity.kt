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
            requestBasePermissions()
        }

        findViewById<Button>(R.id.btnCallFilter).setOnClickListener {
            openCallScreeningSettings()
        }

        findViewById<Button>(R.id.btnBattery).setOnClickListener {
            openBatterySettings()
        }

        findViewById<Button>(R.id.btnWhatsapp).setOnClickListener {
            openWhatsApp()
        }

        updateStatus()
    }

    private fun requestBasePermissions() {
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
            ActivityCompat.requestPermissions(this, permissions.toTypedArray(), 200)
        } else {
            Toast.makeText(this, "Permissões já concedidas", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * 🔴 MÉTODO CORRETO PARA ATIVAR CALL SCREENING
     */
    private fun openCallScreeningSettings() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val intent = Intent(TelecomManager.ACTION_CHANGE_CALL_SCREENING_APP)
                intent.putExtra(
                    TelecomManager.EXTRA_CHANGE_CALL_SCREENING_APP_PACKAGE_NAME,
                    packageName
                )
                startActivity(intent)
            } else {
                Toast.makeText(
                    this,
                    "Ative o filtro de chamadas nas configurações do sistema",
                    Toast.LENGTH_LONG
                ).show()
                startActivity(Intent(Settings.ACTION_SETTINGS))
            }
        } catch (e: Exception) {
            Toast.makeText(
                this,
                "Este dispositivo não suporta filtro de chamadas",
                Toast.LENGTH_LONG
            ).show()
        }
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
        val contactsGranted =
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) ==
                    PackageManager.PERMISSION_GRANTED

        val phoneGranted =
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) ==
                    PackageManager.PERMISSION_GRANTED

        txtStatus.text = when {
            !contactsGranted || !phoneGranted ->
                "Status: permissões pendentes"
            else ->
                "Status: ative o filtro de chamadas"
        }
    }

    override fun onResume() {
        super.onResume()
        updateStatus()
    }
}
