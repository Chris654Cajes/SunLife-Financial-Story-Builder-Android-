package com.example.financialstorybuilder

class FinancialCalculator(
    private val age: Int,
    private val annualIncome: Double,
    private val dependents: Int
) {

    companion object {
        private const val RETIREMENT_AGE = 65
        private const val LIFE_EXPECTANCY = 85
        private const val ANNUAL_EDUCATION_COST = 8000.0 // Average annual education cost
        private const val EDUCATION_YEARS = 4
        private const val EXPENSE_MULTIPLE = 0.70 // 70% of income for living expenses
        private const val INFLATION_RATE = 0.03 // 3% annual inflation
        private const val YEARS_TO_COVER_EDUCATION = 4
    }

    fun calculateIncomeReplacement(): Double {
        // Calculate how much income needs to be replaced until retirement
        val yearsToRetirement = RETIREMENT_AGE - age
        
        // Annual living expenses (70% of current income)
        val annualExpenses = annualIncome * EXPENSE_MULTIPLE
        
        // Total income replacement needed (adjusted for inflation)
        var totalReplacement = 0.0
        for (year in 1..yearsToRetirement) {
            val inflatedExpense = annualExpenses * Math.pow(1 + INFLATION_RATE, (year - 1).toDouble())
            totalReplacement += inflatedExpense
        }
        
        return totalReplacement
    }

    fun calculateEducationFund(): Double {
        // Calculate education fund needed for dependents
        if (dependents == 0) return 0.0
        
        val yearsToRetirement = RETIREMENT_AGE - age
        var educationFund = 0.0
        
        // Assume each dependent needs education funding
        for (dependent in 1..dependents) {
            val yearsUntilEducation = yearsToRetirement + (dependent * 4)
            
            // Annual education cost
            var totalEducationCost = 0.0
            for (year in 1..EDUCATION_YEARS) {
                val inflatedCost = ANNUAL_EDUCATION_COST * Math.pow(1 + INFLATION_RATE, yearsUntilEducation.toDouble() + year)
                totalEducationCost += inflatedCost
            }
            
            educationFund += totalEducationCost
        }
        
        return educationFund
    }

    fun calculateFinancialGap(): Double {
        // Total financial gap = Income Replacement + Education Fund + Emergency Fund
        val incomeReplacement = calculateIncomeReplacement()
        val educationFund = calculateEducationFund()
        
        // Emergency fund = 6 months of expenses
        val emergencyFund = (annualIncome * EXPENSE_MULTIPLE) * 0.5
        
        // Additional buffer for dependents (20% of total for unexpected costs)
        val dependentBuffer = dependents * 50000.0
        
        val totalGap = incomeReplacement + educationFund + emergencyFund + dependentBuffer
        
        return totalGap
    }

    fun getLifeExpectancy(): Int {
        return LIFE_EXPECTANCY
    }

    fun getRetirementAge(): Int {
        return RETIREMENT_AGE
    }

    fun getYearsToRetirement(): Int {
        return RETIREMENT_AGE - age
    }
}
