package com.example.financialstorybuilder

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate

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
        val financialData = viewModel.getFinancialData()

        scenarioText.text = financialData.scenario
        gapText.text = "Financial Gap: $${String.format("%.2f", financialData.financialGap)}\n" +
                "Income Replacement Needed: $${String.format("%.2f", financialData.incomeReplacement)}\n" +
                "Education Fund Needed: $${String.format("%.2f", financialData.educationFund)}"

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
            Color.parseColor("#FFD700"),
            Color.parseColor("#FFC107")
        )
        dataSet.valueTextSize = 12f
        dataSet.valueTextColor = Color.BLACK

        val barData = BarData(dataSet)
        barData.barWidth = 0.4f

        barChart.apply {
            data = barData
            setFitBars(true)
            description.isEnabled = false
            legend.isEnabled = true

            val xAxis = xAxis
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.setDrawGridLines(false)
            xAxis.valueFormatter = IndexAxisValueFormatter(arrayOf("With Insurance", "Without Insurance"))

            axisLeft.axisMinimum = 0f
            axisRight.isEnabled = false

            animateY(1000)
            invalidate()
        }
    }
}
