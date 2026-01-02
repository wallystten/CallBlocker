package com.seupacote.callblocker.ui

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.seupacote.callblocker.R
import com.seupacote.callblocker.util.PremiumManager

class PremiumActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_premium)

        val btnActivate = findViewById<Button>(R.id.btnActivatePremium)

        btnActivate.setOnClickListener {
            PremiumManager.activatePremium(this)
            Toast.makeText(this, "Premium ativado com sucesso 💎", Toast.LENGTH_LONG).show()
            finish()
        }
    }
}
