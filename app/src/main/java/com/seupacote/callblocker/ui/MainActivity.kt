package com.seupacote.callblocker.ui

import android.app.role.RoleManager
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.seupacote.callblocker.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 🔐 Conceder permissões do app (contatos, telefone etc.)
        findViewById<Button>(R.id.btnPermissions).setOnClickListener {
            openAppSettings()
        }

        // 📞 Ativar filtro de chamadas (TELA QUE VOCÊ QUER)
        findViewById<Button>(R.id.btnCallFilter).setOnClickListener {
            openCallScreeningSelector()
        }

        // 🚀 Inicialização automática (configuração do sistema)
        findViewById<Button>(R.id.btnAutostart).setOnClickListener {
            openGeneralSettings()
        }

        // 🔋 Ignorar otimização de bateria
        findViewById<Button>(R.id.btnBattery).setOnClickListener {
            openBatterySettings()
        }
    }

    // 🔐 Abre diretamente a tela do app para permissões
    private fun openAppSettings() {
        try {
            val intent = Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.parse("package:$packageName")
            )
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(
                this,
                "Não foi possível abrir as permissões do app",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    // 📞 Abre a TELA OFICIAL de seleção de filtro de chamadas (RoleManager)
    private fun openCallScreeningSelector() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

            val roleManager = getSystemService(RoleManager::class.java)

            if (roleManager.isRoleAvailable(RoleManager.ROLE_CALL_SCREENING)) {

                if (!roleManager.isRoleHeld(RoleManager.ROLE_CALL_SCREENING)) {

                    val intent = roleManager.createRequestRoleIntent(
                        RoleManager.ROLE_CALL_SCREENING
                    )
                    startActivity(intent)

                } else {
                    Toast.makeText(
                        this,
                        "Call Blocker já está definido como filtro padrão",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            } else {
                Toast.makeText(
                    this,
                    "Seu dispositivo não suporta filtro de chamadas",
                    Toast.LENGTH_LONG
                ).show()
            }

        } else {
            Toast.makeText(
                this,
                "Recurso disponível apenas no Android 10 ou superior",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    // ⚙️ Configurações gerais do sistema
    private fun openGeneralSettings() {
        startActivity(Intent(Settings.ACTION_SETTINGS))
    }

    // 🔋 Tela de otimização de bateria
    private fun openBatterySettings() {
        startActivity(
            Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS)
        )
    }
}
