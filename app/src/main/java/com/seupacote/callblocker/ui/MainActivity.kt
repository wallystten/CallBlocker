package com.seupacote.callblocker.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.seupacote.callblocker.R
import com.seupacote.callblocker.util.TrialManager

class MainActivity : AppCompatActivity() {

    private lateinit var txtStatus: TextView
    private lateinit var txtInfo: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtStatus = findViewById(R.id.txtStatus)
        txtInfo = findViewById(R.id.txtInfo)

        findViewById<Button>(R.id.btnPermissions).setOnClickListener {
            requestPermissions()
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

    private fun requestPermissions() {
        val permissions = arrayOf(
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.READ_PHONE_STATE
        )

        val missing = permissions.filter {
            ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }

        if (missing.isNotEmpty()) {
            ActivityCompat.requestPermissions(this, missing.toTypedArray(), 100)
        }
    }

    private fun updateStatus() {
        val trialActive = TrialManager.isTrialActive(this)
        val daysLeft = TrialManager.getDaysLeft(this)

        txtStatus.text = if (trialActive) {
            "🟢 Trial ativo: $daysLeft dia(s)\nBloqueio disponível"
        } else {
            "🔴 Trial expirado\nBloqueio desativado"
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
  
