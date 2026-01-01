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

        val btnPermissions = findViewById<Button>(R.id.btnPermissions)
        val btnCallFilter = findViewById<Button>(R.id.btnCallFilter)
        val btnAutostart = findViewById<Button>(R.id.btnAutostart)
        val btnBattery = findViewById<Button>(R.id.btnBattery)

        btnPermissions.setOnClickListener {
            startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS))
        }

        btnCallFilter.setOnClickListener {
            startActivity(Intent(Settings.ACTION_CALL_SCREENING_SETTINGS))
        }

        btnAutostart.setOnClickListener {
            startActivity(Intent(Settings.ACTION_SETTINGS))
        }

        btnBattery.setOnClickListener {
            startActivity(Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS))
        }
    }
}
