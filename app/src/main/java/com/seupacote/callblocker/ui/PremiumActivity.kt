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

        val txtTitle = findViewById<TextView>(R.id.txtPremiumTitle)
        val txtPrice = findViewById<TextView>(R.id.txtPremiumPrice)

        val btnSubscribe = findViewById<Button>(R.id.btnSubscribe)
        val btnWhatsapp = findViewById<Button>(R.id.btnWhatsappPremium)
        val btnBack = findViewById<Button>(R.id.btnBack)

        txtTitle.text = "Premium Call Blocker"
        txtPrice.text = "R$ 9,90 / mês\nBloqueio total de chamadas desconhecidas"

        btnSubscribe.setOnClickListener {
            // Futuramente aqui entra o pagamento
            btnSubscribe.text = "Pagamento em breve"
        }

        btnWhatsapp.setOnClickListener {
            val uri = Uri.parse("https://wa.me/5547988818203")
            startActivity(Intent(Intent.ACTION_VIEW, uri))
        }

        btnBack.setOnClickListener {
            finish()
        }
    }
}
 
