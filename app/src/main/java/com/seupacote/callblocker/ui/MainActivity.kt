
package com.seupacote.callblocker.ui

import android.Manifest
import android.app.role.RoleManager
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

class MainActivity : AppCompatActivity() {

    private lateinit var txtStatus: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtStatus = findViewById(R.id.txtStatus)

        findViewById<Button>(R.id.btnPermissions).setOnClickListener {
            requestPermissionsFlow()
        }

        findViewById<Button>(R.id.btnActivateFilter).setOnClickListener {
            activateCallScreening()
        }

        findViewById<Button>(R.id.btnBattery).setOnClickListener {
            startActivity(Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS))
        }

        findViewById<Button>(R.id.btnWhatsapp).setOnClickListener {
            val uri = Uri.parse("https://wa.me/5547988818203")
            startActivity(Intent(Intent.ACTION_VIEW, uri))
        }

        updateStatus()
    }

    private fun requestPermissionsFlow() {
        val permissions = arrayOf(
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.READ_PHONE_STATE
        )

        val notGranted = permissions.filter {
            ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }

        if (notGranted.isNotEmpty()) {
            ActivityCompat.requestPermissions(this, notGranted.toTypedArray(), 100)
        } else {
            Toast.makeText(this, "Permissões já concedidas", Toast.LENGTH_SHORT).show()
        }
    }

    private fun activateCallScreening() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val roleManager = getSystemService(RoleManager::class.java)
            if (roleManager.isRoleAvailable(RoleManager.ROLE_CALL_SCREENING)) {
                val intent = roleManager.createRequestRoleIntent(
                    RoleManager.ROLE_CALL_SCREENING
                )
                startActivity(intent)
            } else {
                openSystemSettings()
            }
        } else {
            openSystemSettings()
        }
    }

    private fun openSystemSettings() {
        Toast.makeText(
            this,
            "Ative o filtro de chamadas nas configurações do sistema",
            Toast.LENGTH_LONG
        ).show()
        startActivity(Intent(Settings.ACTION_SETTINGS))
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
                "Status: permissões concedidas"
            } else {
                "Status: permissões pendentes"
            }
    }
}
