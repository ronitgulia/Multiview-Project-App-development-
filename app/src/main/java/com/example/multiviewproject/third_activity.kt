package com.example.multiviewproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class third_activity : AppCompatActivity() {

    private lateinit var tvExpression: TextView
    private lateinit var tvResult: TextView
    private var lastNumeric: Boolean = false
    private var stateError: Boolean = false
    private var lastDot: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.third_activity)

        tvExpression = findViewById(R.id.tv_expression)
        tvResult = findViewById(R.id.tv_result)

        setNumericOnClickListeners()
        setOperatorOnClickListeners()

        // Navigation
        findViewById<Button>(R.id.nav_to_lcm).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        findViewById<Button>(R.id.nav_to_power).setOnClickListener {
            startActivity(Intent(this, second_activity::class.java))
        }
    }

    private fun setNumericOnClickListeners() {
        val buttons = listOf(
            R.id.btn_0, R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4,
            R.id.btn_5, R.id.btn_6, R.id.btn_7, R.id.btn_8, R.id.btn_9, R.id.btn_dot
        )

        val listener = android.view.View.OnClickListener { v ->
            val button = v as Button
            if (stateError) {
                tvResult.text = button.text
                stateError = false
            } else {
                if (button.id == R.id.btn_dot) {
                    if (lastNumeric && !lastDot) {
                        tvResult.append(".")
                        lastNumeric = false
                        lastDot = true
                    }
                } else {
                    if (tvResult.text.toString() == "0") {
                        tvResult.text = button.text
                    } else {
                        tvResult.append(button.text)
                    }
                    lastNumeric = true
                }
            }
        }

        for (id in buttons) {
            findViewById<Button>(id).setOnClickListener(listener)
        }
    }

    private fun setOperatorOnClickListeners() {
        findViewById<Button>(R.id.btn_add).setOnClickListener { onOperator("+") }
        findViewById<Button>(R.id.btn_subtract).setOnClickListener { onOperator("-") }
        findViewById<Button>(R.id.btn_multiply).setOnClickListener { onOperator("×") }
        findViewById<Button>(R.id.btn_divide).setOnClickListener { onOperator("÷") }

        findViewById<Button>(R.id.btn_clear).setOnClickListener {
            tvResult.text = "0"
            tvExpression.text = ""
            lastNumeric = false
            stateError = false
            lastDot = false
        }

        findViewById<Button>(R.id.btn_backspace).setOnClickListener {
            val currentText = tvResult.text.toString()
            if (currentText.length > 1) {
                tvResult.text = currentText.substring(0, currentText.length - 1)
            } else {
                tvResult.text = "0"
            }
        }

        findViewById<Button>(R.id.btn_equal).setOnClickListener {
            onEqual()
        }
    }

    private fun onOperator(operator: String) {
        if (lastNumeric && !stateError) {
            tvExpression.text = "${tvResult.text} $operator"
            tvResult.text = "0"
            lastNumeric = false
            lastDot = false
        }
    }

    private fun onEqual() {
        if (lastNumeric && !stateError) {
            val expression = tvExpression.text.toString()
            if (expression.isEmpty()) return

            val parts = expression.split(" ")
            if (parts.size < 2) return

            val operand1 = parts[0].toDoubleOrNull() ?: 0.0
            val operator = parts[1]
            val operand2 = tvResult.text.toString().toDoubleOrNull() ?: 0.0

            var result = 0.0
            when (operator) {
                "+" -> result = operand1 + operand2
                "-" -> result = operand1 - operand2
                "×" -> result = operand1 * operand2
                "÷" -> {
                    if (operand2 != 0.0) {
                        result = operand1 / operand2
                    } else {
                        tvResult.text = "Error"
                        stateError = true
                        lastNumeric = false
                        return
                    }
                }
            }
            tvResult.text = if (result % 1 == 0.0) result.toInt().toString() else result.toString()
            tvExpression.text = ""
            lastNumeric = true
            lastDot = tvResult.text.contains(".")
        }
    }
}
