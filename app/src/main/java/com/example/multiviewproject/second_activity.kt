package com.example.multiviewproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.pow

class second_activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.second_activity)

        val editBase = findViewById<EditText>(R.id.edit_base)
        val editExponent = findViewById<EditText>(R.id.edit_exponent)
        val buttonPower = findViewById<Button>(R.id.button_power)
        val textResultPower = findViewById<TextView>(R.id.text_result_power)

        // Navigation
        findViewById<Button>(R.id.nav_to_lcm).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        findViewById<Button>(R.id.nav_to_calc).setOnClickListener {
            startActivity(Intent(this, third_activity::class.java))
        }

        buttonPower.setOnClickListener {
            val baseStr = editBase.text.toString()
            val expStr = editExponent.text.toString()
            
            if (baseStr.isNotEmpty() && expStr.isNotEmpty()) {
                val base = baseStr.toDoubleOrNull() ?: 0.0
                val exponent = expStr.toDoubleOrNull() ?: 0.0
                
                val result = base.pow(exponent)
                
                // Format result to avoid unnecessary decimals
                val displayResult = if (result % 1.0 == 0.0 && result < Long.MAX_VALUE) {
                    result.toLong().toString()
                } else {
                    result.toString()
                }
                
                textResultPower.text = "Result: $displayResult"
            } else {
                textResultPower.text = "Please enter both fields"
            }
        }
    }
}
