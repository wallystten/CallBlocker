package com.seupacote.callblocker.ui

import android.content.Intent
import android.net.Uri
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

        findViewById<Button>(R.id.btnPermissions).setOnClickListener {
            openAppSettings()
        }

        findViewById<Button>(R.id.btnCallFilter).setOnClickListener {
            openCallScreeningSettings()
        }

        findViewById<Button>(R.id.btnAutostart).setOnClickListener {
            startActivity(Intent(Settings.ACTION_SETTINGS))
        }

        findViewById<Button>(R.id.btnBattery).setOnClickListener {
            startActivity(Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS))
        }
    }

    private fun openAppSettings() {
        try {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            intent.data = Uri.parse("package:$packageName")
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(this, "Não foi possível abrir as permissões", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openCallScreeningSettings() {
        try {
            // Intent OFICIAL para triagem de chamadas
            startActivity(Intent(Settings.ACTION_CALL_SCREENING_SETTINGS))
        } catch (e: Exception) {
            Toast.makeText(
                this,
                "Abra manualmente: Configurações > Apps > Apps padrão > Triagem de chamadas",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}
