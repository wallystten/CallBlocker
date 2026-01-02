package com.seupacote.callblocker.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.seupacote.callblocker.R

class PremiumActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_premium)

        val btnSubscribe = findViewById<Button>(R.id.btnSubscribe)
        val btnWhatsapp = findViewById<Button>(R.id.btnWhatsappPremium)

        btnSubscribe.setOnClickListener {
            // Aqui futuramente entra código de ativação
            openWhatsApp()
        }

        btnWhatsapp.setOnClickListener {
            openWhatsApp()
        }
    }

    private fun openWhatsApp() {
        val phone = "5547988818203"
        val uri = Uri.parse("https://wa.me/$phone")
        startActivity(Intent(Intent.ACTION_VIEW, uri))
    }
}
