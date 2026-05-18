package com.example.multiviewproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        
        // Handle window insets for edge-to-edge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val editNum1 = findViewById<EditText>(R.id.edit_num1)
        val editNum2 = findViewById<EditText>(R.id.edit_num2)
        val buttonLcm = findViewById<Button>(R.id.button_lcm)
        val textResult = findViewById<TextView>(R.id.text_result_lcm)

        // Navigation
        findViewById<Button>(R.id.nav_to_power).setOnClickListener {
            startActivity(Intent(this, second_activity::class.java))
        }
        findViewById<Button>(R.id.nav_to_calc).setOnClickListener {
            startActivity(Intent(this, third_activity::class.java))
        }

        buttonLcm.setOnClickListener {
            val num1Str = editNum1.text.toString()
            val num2Str = editNum2.text.toString()

            if (num1Str.isNotEmpty() && num2Str.isNotEmpty()) {
                val n1 = num1Str.toLongOrNull() ?: 0L
                val n2 = num2Str.toLongOrNull() ?: 0L
                val result = findLcm(n1, n2)
                textResult.text = "Result: $result"
            } else {
                textResult.text = "Please enter both numbers"
            }
        }
    }

    private fun findLcm(n1: Long, n2: Long): Long {
        if (n1 == 0L || n2 == 0L) return 0
        val absN1 = Math.abs(n1)
        val absN2 = Math.abs(n2)
        val gcd = findGcd(absN1, absN2)
        // Divide first to reduce chance of overflow
        return (absN1 / gcd) * absN2
    }

    private fun findGcd(a: Long, b: Long): Long {
        var x = a
        var y = b
        while (y != 0L) {
            val temp = y
            y = x % y
            x = temp
        }
        return x
    }
}
