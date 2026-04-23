package com.example.financialstorybuilder

import androidx.lifecycle.ViewModel
import java.text.NumberFormat
import java.util.Locale

data class FinancialData(
    val age: Int,
    val income: Double,
    val dependents: Int,
    val incomeReplacement: Double,
    val educationFund: Double,
    val financialGap: Double,
    val scenario: String
)

class FinancialViewModel : ViewModel() {

    companion object {
        private val philippineCurrencyFormatter: NumberFormat =
            NumberFormat.getCurrencyInstance(Locale("en", "PH"))

        fun formatCurrency(amount: Double): String = philippineCurrencyFormatter.format(amount)
    }

    private var age: Int = 0
    private var income: Double = 0.0
    private var dependents: Int = 0

    fun setUserData(age: Int, income: Double, dependents: Int) {
        this.age = age
        this.income = income
        this.dependents = dependents
    }

    fun generateFinancialStory() {
        // Calculation is done when data is retrieved.
    }

    fun getFinancialData(): FinancialData {
        val calculator = FinancialCalculator(age, income, dependents)
        val incomeReplacement = calculator.calculateIncomeReplacement()
        val educationFund = calculator.calculateEducationFund()
        val financialGap = calculator.calculateFinancialGap()
        val scenario = generateScenario(financialGap, educationFund)

        return FinancialData(
            age = age,
            income = income,
            dependents = dependents,
            incomeReplacement = incomeReplacement,
            educationFund = educationFund,
            financialGap = financialGap,
            scenario = scenario
        )
    }

    private fun generateScenario(financialGap: Double, educationFund: Double): String {
        val retirementAge = 65
        val yearsToRetirement = retirementAge - age
        val lifeExpectancy = 85
        val yearsInRetirement = lifeExpectancy - retirementAge

        return buildString {
            appendLine("If something happened to you today, your family's plan would need to replace your income and cover future obligations.")
            appendLine()
            appendLine("Age: $age")
            appendLine("Annual income: ${formatCurrency(income)}")
            appendLine("Dependents: $dependents")
            appendLine()
            appendLine("Analysis")
            appendLine("- Years until retirement: $yearsToRetirement")
            appendLine("- Expected retirement duration: $yearsInRetirement")
            appendLine("- Estimated education funding: ${formatCurrency(educationFund)}")
            appendLine("- Total protection need: ${formatCurrency(financialGap)}")
            appendLine()
            appendLine("Key insight")
            appendLine("Your family may need about ${formatCurrency(financialGap)} to maintain financial stability if your income stops.")
            appendLine("This estimate includes income replacement, education support, and emergency reserves.")
        }
    }
}
