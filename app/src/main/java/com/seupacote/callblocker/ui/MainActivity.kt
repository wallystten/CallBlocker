package com.seupacote.callblocker.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.seupacote.callblocker.R
import com.seupacote.callblocker.util.TrialManager

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

    // 🔐 Permissões básicas
    private fun requestPermissionsFlow() {

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

        showCallScreeningDialog()
    }

    // 📞 UX correta para ativar Call Screening
    private fun showCallScreeningDialog() {
        AlertDialog.Builder(this)
            .setTitle("Ativar filtro de chamadas")
            .setMessage(
                "Para o bloqueio funcionar, selecione o Call Blocker como app padrão de identificação de chamadas e spam.\n\n" +
                        "Toque em \"Configurações\" e escolha Call Blocker."
            )
            .setPositiveButton("Configurações") { _, _ ->
                openCallScreeningSettings()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun openCallScreeningSettings() {
        try {
            val intent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                Intent(Settings.ACTION_CALL_SCREENING_SETTINGS)
            } else {
                Intent(Settings.ACTION_SETTINGS)
            }
            startActivity(intent)
        } catch (e: Exception) {
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
        val uri = Intent(Intent.ACTION_VIEW).apply {
            data = android.net.Uri.parse("https://wa.me/5547988818203")
        }
        startActivity(uri)
    }

    // 📊 Status (Trial / Premium)
    private fun updateStatus() {
        val trialActive = TrialManager.isTrialActive(this)
        val daysLeft = TrialManager.getDaysLeft(this)

        txtStatus.text =
            if (trialActive) {
                "🆓 Trial ativo\n$daysLeft dia(s) restantes\nBloqueio ativo"
            } else {
                "🔒 Trial expirado\nAtive o Premium"
            }
    }

    override fun onResume() {
        super.onResume()
        updateStatus()
    }
}
