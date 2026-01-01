package com.seupacote.callblocker.ui

import android.Manifest
import android.app.role.RoleManager
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.seupacote.callblocker.R

class MainActivity : AppCompatActivity() {

    // 🔐 Launcher para permissões
    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (!granted) {
                Toast.makeText(
                    this,
                    "Permissão necessária para o funcionamento do app",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btnPermissions).setOnClickListener {
            requestPermissionsFlow()
        }

        findViewById<Button>(R.id.btnCallFilter).setOnClickListener {
            openCallScreeningRole()
        }

        findViewById<Button>(R.id.btnAutostart).setOnClickListener {
            openAutoStartSettings()
        }

        findViewById<Button>(R.id.btnBattery).setOnClickListener {
            openBatterySettings()
        }
    }

    // 🔑 FLUXO DE PERMISSÕES (POP-UPS NATIVOS)
    private fun requestPermissionsFlow() {

        // 1️⃣ CONTATOS
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissionLauncher.launch(Manifest.permission.READ_CONTACTS)
            return
        }

        // 2️⃣ NOTIFICAÇÕES (Android 13+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                return
            }
        }

        Toast.makeText(this, "Permissões concedidas com sucesso ✅", Toast.LENGTH_SHORT).show()
    }

    // 📞 DEFINIR COMO APP PADRÃO DE BLOQUEIO
    private fun openCallScreeningRole() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val roleManager = getSystemService(RoleManager::class.java)

            if (roleManager.isRoleAvailable(RoleManager.ROLE_CALL_SCREENING)) {
                val intent = roleManager.createRequestRoleIntent(
                    RoleManager.ROLE_CALL_SCREENING
                )
                startActivity(intent)
            } else {
                Toast.makeText(
                    this,
                    "Recurso não disponível neste dispositivo",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    // 🔋 Ignorar otimização de bateria
    private fun openBatterySettings() {
        startActivity(
            Intent(
                android.provider.Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS
            )
        )
    }

    // 🚀 Inicialização automática (genérico)
    private fun openAutoStartSettings() {
        startActivity(
            Intent(
                android.provider.Settings.ACTION_SETTINGS
            )
        )
    }
}
