package com.seupacote.callblocker.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.seupacote.callblocker.R

class PremiumActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_premium)

        // Botão assinar Premium (por enquanto direciona ao WhatsApp)
        findViewById<Button>(R.id.btnAssinarPremium).setOnClickListener {
            abrirWhatsApp()
        }

        // Botão voltar
        findViewById<Button>(R.id.btnVoltar).setOnClickListener {
            finish()
        }
    }

    private fun abrirWhatsApp() {
        val phone = "5547988818203"
        val uri = Uri.parse("https://wa.me/$phone?text=Quero%20assinar%20o%20Premium%20do%20Call%20Blocker")
        startActivity(Intent(Intent.ACTION_VIEW, uri))
    }
}
