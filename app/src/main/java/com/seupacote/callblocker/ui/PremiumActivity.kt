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

        val edtCode = findViewById<EditText>(R.id.edtCode)
        val btnActivate = findViewById<Button>(R.id.btnActivate)

        btnActivate.setOnClickListener {
            val code = edtCode.text.toString().trim()

            if (code.isEmpty()) {
                Toast.makeText(this, "Digite o código", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val success = PremiumManager.activateWithCode(this, code)

            if (success) {
                Toast.makeText(this, "✅ Premium ativado com sucesso!", Toast.LENGTH_LONG).show()
                finish()
            } else {
                Toast.makeText(this, "❌ Código inválido", Toast.LENGTH_LONG).show()
            }
        }
    }
}
