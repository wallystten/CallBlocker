package com.seupacote.callblocker.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.seupacote.callblocker.R
import com.seupacote.callblocker.util.PremiumManager

class PremiumActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_premium)

        val inputCode = findViewById<EditText>(R.id.edtCode)
        val btnActivate = findViewById<Button>(R.id.btnActivate)

        btnActivate.setOnClickListener {
            val code = inputCode.text.toString().trim()
            val success = PremiumManager.activatePremium(this, code)

            if (success) {
                Toast.makeText(this, "Premium ativado com sucesso!", Toast.LENGTH_LONG).show()
                finish()
            } else {
                Toast.makeText(this, "Código inválido!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
