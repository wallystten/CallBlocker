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
            requestPermissionsFlow()
        }

        findViewById<Button>(R.id.btnAutostart).setOnClickListener {
            openCallScreeningActivation()
        }

        findViewById<Button>(R.id.btnBattery).setOnClickListener {
            openBatterySettings()
        }

        findViewById<Button>(R.id.btnWhatsapp).setOnClickListener {
            openWhatsApp()
        }

        updateStatus()
    }

    // 🔐 Solicita permissões necessárias
    private fun requestPermissionsFlow() {
        val permissions = mutableListOf<String>()

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissions.add(Manifest.permission.READ_CONTACTS)
        }

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_PHONE_STATE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissions.add(Manifest.permission.READ_PHONE_STATE)
        }

        if (permissions.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                permissions.toTypedArray(),
                100
            )
        } else {
            Toast.makeText(
                this,
                "Permissões já concedidas",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    // 📞 ATIVAÇÃO DO FILTRO (FORMA CORRETA)
    private fun openCallScreeningActivation() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val telecomManager =
                    getSystemService(TELECOM_SERVICE) as TelecomManager

                val intent =
                    telecomManager.createManageBlockedNumbersIntent()

                startActivity(intent)

                Toast.makeText(
                    this,
                    "Ative o Call Blocker como app de triagem",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                startActivity(Intent(Settings.ACTION_SETTINGS))
            }
        } catch (e: Exception) {
            Toast.makeText(
                this,
                "Abra Configurações > Apps > Apps padrão > Triagem de chamadas",
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
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CONTACTS
            ) == PackageManager.PERMISSION_GRANTED

        val phoneGranted =
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_PHONE_STATE
            ) == PackageManager.PERMISSION_GRANTED

        txtStatus.text =
            if (contactsGranted && phoneGranted) {
                "Status: permissões concedidas"
            } else {
                "Status: permissões pendentes"
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
