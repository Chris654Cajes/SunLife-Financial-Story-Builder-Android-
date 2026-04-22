package com.example.financialstorybuilder

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

class InputActivity : AppCompatActivity() {

    private lateinit var viewModel: FinancialViewModel
    private lateinit var ageInput: EditText
    private lateinit var incomeInput: EditText
    private lateinit var dependentsInput: EditText
    private lateinit var generateButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input)

        viewModel = ViewModelProvider(this).get(FinancialViewModel::class.java)

        ageInput = findViewById(R.id.ageInput)
        incomeInput = findViewById(R.id.incomeInput)
        dependentsInput = findViewById(R.id.dependentsInput)
        generateButton = findViewById(R.id.generateButton)

        generateButton.setOnClickListener {
            validateAndProceed()
        }
    }

    private fun validateAndProceed() {
        val age = ageInput.text.toString().trim()
        val income = incomeInput.text.toString().trim()
        val dependents = dependentsInput.text.toString().trim()

        when {
            age.isEmpty() -> {
                Toast.makeText(this, "Please enter your age", Toast.LENGTH_SHORT).show()
                return
            }
            income.isEmpty() -> {
                Toast.makeText(this, "Please enter your annual income", Toast.LENGTH_SHORT).show()
                return
            }
            dependents.isEmpty() -> {
                Toast.makeText(this, "Please enter number of dependents", Toast.LENGTH_SHORT).show()
                return
            }
            age.toIntOrNull() == null || age.toInt() < 18 || age.toInt() > 100 -> {
                Toast.makeText(this, "Please enter a valid age (18-100)", Toast.LENGTH_SHORT).show()
                return
            }
            income.toDoubleOrNull() == null || income.toDouble() <= 0 -> {
                Toast.makeText(this, "Please enter a valid income", Toast.LENGTH_SHORT).show()
                return
            }
            dependents.toIntOrNull() == null || dependents.toInt() < 0 -> {
                Toast.makeText(this, "Please enter a valid number of dependents", Toast.LENGTH_SHORT).show()
                return
            }
        }

        val ageInt = age.toInt()
        val incomeDouble = income.toDouble()
        val dependentsInt = dependents.toInt()

        viewModel.setUserData(ageInt, incomeDouble, dependentsInt)
        viewModel.generateFinancialStory()

        val intent = Intent(this, ResultActivity::class.java)
        startActivity(intent)
    }
}
