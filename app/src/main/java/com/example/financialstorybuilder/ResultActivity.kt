package com.example.financialstorybuilder

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter

class ResultActivity : AppCompatActivity() {

    private lateinit var viewModel: FinancialViewModel
    private lateinit var scenarioText: TextView
    private lateinit var gapText: TextView
    private lateinit var barChart: BarChart
    private lateinit var backButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        viewModel = ViewModelProvider(this).get(FinancialViewModel::class.java)

        scenarioText = findViewById(R.id.scenarioText)
        gapText = findViewById(R.id.gapText)
        barChart = findViewById(R.id.barChart)
        backButton = findViewById(R.id.backButton)

        backButton.setOnClickListener {
            finish()
        }

        displayResults()
    }

    private fun displayResults() {
        val age = intent.getIntExtra(InputActivity.EXTRA_AGE, -1)
        val income = intent.getDoubleExtra(InputActivity.EXTRA_INCOME, -1.0)
        val dependents = intent.getIntExtra(InputActivity.EXTRA_DEPENDENTS, -1)

        if (age < 0 || income < 0 || dependents < 0) {
            Toast.makeText(this, "Missing financial inputs. Please try again.", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        viewModel.setUserData(age, income, dependents)
        val financialData = viewModel.getFinancialData()

        scenarioText.text = financialData.scenario
        gapText.text = buildString {
            append("Financial Gap: ${FinancialViewModel.formatCurrency(financialData.financialGap)}")
            append("\nIncome Replacement Needed: ${FinancialViewModel.formatCurrency(financialData.incomeReplacement)}")
            append("\nEducation Fund Needed: ${FinancialViewModel.formatCurrency(financialData.educationFund)}")
        }

        setupBarChart(financialData)
    }

    private fun setupBarChart(financialData: FinancialData) {
        val withInsurance = financialData.incomeReplacement
        val withoutInsurance = financialData.financialGap

        val entries = arrayListOf(
            BarEntry(0f, withInsurance.toFloat()),
            BarEntry(1f, withoutInsurance.toFloat())
        )

        val dataSet = BarDataSet(entries, "Financial Gap Comparison")
        dataSet.colors = listOf(
            Color.parseColor("#4CAF50"),  // Green for "With Insurance" (what insurance provides)
            Color.parseColor("#F44336")   // Red for "Without Insurance" (the gap)
        )
        dataSet.valueTextSize = 10f
        dataSet.valueTextColor = Color.BLACK
        dataSet.valueFormatter = object : ValueFormatter() {
            override fun getBarLabel(barEntry: BarEntry?): String {
                return barEntry?.y?.toDouble()?.let(FinancialViewModel::formatCurrency).orEmpty()
            }
        }

        val barData = BarData(dataSet)
        barData.barWidth = 0.4f

        barChart.apply {
            data = barData
            setFitBars(true)
            description.isEnabled = false
            legend.isEnabled = true
            setExtraBottomOffset(12f)

            val xAxis = xAxis
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.setDrawGridLines(false)
            xAxis.granularity = 1f
            xAxis.labelCount = 2
            xAxis.labelRotationAngle = -15f
            xAxis.textSize = 10f
            xAxis.setAvoidFirstLastClipping(true)
            xAxis.valueFormatter = IndexAxisValueFormatter(arrayOf("With Coverage", "Current Gap"))

            axisLeft.axisMinimum = 0f
            axisLeft.valueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    return FinancialViewModel.formatCurrency(value.toDouble())
                }
            }
            axisRight.isEnabled = false

            animateY(1000)
            invalidate()
        }
    }
}
