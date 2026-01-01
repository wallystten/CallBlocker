package com.seupacote.callblocker.package com.seupacote.callblocker.ui

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

        val btnEnable = findViewById<Button>(R.id.btnEnableCallScreening)

        btnEnable.setOnClickListener {
            openCallSettings()
        }
    }

    private fun openCallSettings() {
        // Intent compatível com TODOS os Androids
        val intent = Intent(Settings.ACTION_SETTINGS)
        startActivity(intent)
    }
}
