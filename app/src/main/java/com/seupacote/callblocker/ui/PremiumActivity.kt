package com.seupacote.callblocker.ui

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.seupacote.callblocker.R

class PremiumActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_premium)

        val btnSubscribe = findViewById<Button>(R.id.btnSubscribe)
        val btnBack = findViewById<Button>(R.id.btnBack)

        btnSubscribe.setOnClickListener {
            Toast.makeText(
                this,
                "Pagamento será implementado em breve",
                Toast.LENGTH_LONG
            ).show()
        }

        btnBack.setOnClickListener {
            finish()
        }
    }
}
