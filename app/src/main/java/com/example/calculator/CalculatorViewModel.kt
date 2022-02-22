package com.example.calculator

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CalculatorViewModel : ViewModel() {
    val expression = MutableLiveData(String())
    private val calculator = CalculatorModel()
    private val maxExpressionLength = 10

    fun calculate() {
        val exp = calculator.calculate(expression.value!!)
        expression.value = if(exp.length > maxExpressionLength)
            exp.substring(0, maxExpressionLength)
        else
            exp
    }

    fun tryAddChar(char: String) {
        if(expression.value == "Invalid")
            clearAll()
        expression.value = calculator.tryAddCharToString(expression.value!!, char)
    }

    fun clearAll() {
        expression.value = ""
    }

    fun clearOne() {
        if(expression.value!!.isEmpty()) return
        expression.value = expression.value!!.dropLast(1)
    }
}