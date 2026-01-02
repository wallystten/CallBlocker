package com.seupacote.callblocker.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
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
            openAppSettingsForCallFilter()
        }

        findViewById<Button>(R.id.btnBattery).setOnClickListener {
            openBatterySettings()
        }

        findViewById<Button>(R.id.btnWhatsapp).setOnClickListener {
            openWhatsApp()
        }

        updateStatus()
    }

    /* 🔐 Fluxo de permissões */
    private fun requestPermissionsFlow() {
        val permissions = arrayOf(
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.READ_PHONE_STATE
        )

        val notGranted = permissions.any {
            ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }

        if (notGranted) {
            ActivityCompat.requestPermissions(this, permissions, 100)
        } else {
            Toast.makeText(
                this,
                "Permissões já concedidas",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    /* 📞 Ativar filtro de chamadas (limite do Android) */
    private fun openAppSettingsForCallFilter() {
        Toast.makeText(
            this,
            "Ative o Call Blocker como filtro de chamadas nesta tela",
            Toast.LENGTH_LONG
        ).show()

        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.data = Uri.parse("package:$packageName")
        startActivity(intent)
    }

    /* 🔋 Bateria */
    private fun openBatterySettings() {
        startActivity(
            Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS)
        )
    }

    /* 💬 WhatsApp */
    private fun openWhatsApp() {
        val phone = "5547988818203"
        val uri = Uri.parse("https://wa.me/$phone")
        startActivity(Intent(Intent.ACTION_VIEW, uri))
    }

    /* 📊 Status */
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
                "Status: Proteção ativa"
            } else {
                "Status: Permissões pendentes"
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
