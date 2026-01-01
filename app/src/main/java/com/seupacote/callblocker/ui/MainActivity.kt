package com.seupacote.callblocker.ui

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.seupacote.callblocker.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnEnable = findViewById<Button>(R.id.btnEnable)

        btnEnable.setOnClickListener {
            openCallScreeningSettings()
        }
    }

    private fun openCallScreeningSettings() {
        try {
            // Tela correta para apps de bloqueio de chamadas
            val intent = Intent(Settings.ACTION_CALL_SCREENING_SETTINGS)
            startActivity(intent)
        } catch (e: Exception) {
            // Fallback caso o Android não suporte
            startActivity(Intent(Settings.ACTION_SETTINGS))
        }
    }
}
