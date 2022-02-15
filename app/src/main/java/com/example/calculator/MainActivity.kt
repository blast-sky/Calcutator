package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.viewModels
import com.example.calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val calculatorVM by viewModels<CalculatorViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        calculatorVM.expression.observe(this, { string: String ->
            binding.calcText.text = string
        })
        setContentView(binding.root)
    }

    fun onCalcButtonClick(view: View) {
        calculatorVM.tryAddChar((view as Button).text.toString())
    }

    fun onClearAll(view: View) {
        calculatorVM.clearAll()
    }

    fun onClearOne(view: View) {
        calculatorVM.clearOne()
    }

    fun onEqual(view: View) {
        calculatorVM.calculate()
    }
}