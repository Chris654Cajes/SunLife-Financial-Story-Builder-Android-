package com.example.financialstorybuilder

import androidx.lifecycle.ViewModel

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

    private var age: Int = 0
    private var income: Double = 0.0
    private var dependents: Int = 0

    fun setUserData(age: Int, income: Double, dependents: Int) {
        this.age = age
        this.income = income
        this.dependents = dependents
    }

    fun generateFinancialStory() {
        // Calculation is done when data is retrieved
    }

    fun getFinancialData(): FinancialData {
        val calculator = FinancialCalculator(age, income, dependents)
        val incomeReplacement = calculator.calculateIncomeReplacement()
        val educationFund = calculator.calculateEducationFund()
        val financialGap = calculator.calculateFinancialGap()
        val scenario = generateScenario()

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

    private fun generateScenario(): String {
        val retirementAge = 65
        val yearsToRetirement = retirementAge - age
        val lifeExpectancy = 85
        val yearsInRetirement = lifeExpectancy - retirementAge

        return buildString {
            appendLine("🏠 Your Financial Story\n")
            appendLine("Scenario: What happens if something happens to you today?\n")
            appendLine("Age: $age | Annual Income: $${"%.2f".format(income)} | Dependents: $dependents\n")
            appendLine("📊 Analysis:")
            appendLine("• Years until retirement: $yearsToRetirement years")
            appendLine("• Expected retirement duration: $yearsInRetirement years")
            appendLine("• Your family needs: $${"%.2f".format(calculateFinancialGap())} for financial security")
            appendLine("• Education costs for $dependents dependent(s): $${"%.2f".format(calculateEducationFund())}")
            appendLine("\n💡 Key Insight:")
            appendLine("Your family would need $${"%.2f".format(calculateFinancialGap())} to maintain their lifestyle")
            appendLine("if you were unable to earn income. This includes:")
            appendLine("• ${dependents * 4} years of education funding")
            appendLine("• Living expenses until retirement age")
            appendLine("• Emergency reserves")
            appendLine("\n✅ Solution:")
            appendLine("Proper life insurance can bridge this gap and provide peace of mind.")
        }
    }

    private fun calculateIncomeReplacement(): Double {
        val calculator = FinancialCalculator(age, income, dependents)
        return calculator.calculateIncomeReplacement()
    }

    private fun calculateEducationFund(): Double {
        val calculator = FinancialCalculator(age, income, dependents)
        return calculator.calculateEducationFund()
    }

    private fun calculateFinancialGap(): Double {
        val calculator = FinancialCalculator(age, income, dependents)
        return calculator.calculateFinancialGap()
    }
}
