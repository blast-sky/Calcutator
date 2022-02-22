package com.example.calculator

import androidx.core.text.isDigitsOnly
import com.notkamui.keval.KevalException
import com.notkamui.keval.keval

private fun Char.isBrace() : Boolean {
    return this == ')' || this == '('
}

class CalculatorModel {
    fun tryAddCharToString(string: String, char: String) : String {
        return if(char.isDigitsOnly() ||
            char[0].isLetter() ||
            char[0].isBrace() ||
            canInsertOperationChar(string)
        )
            string + char
        else
            string
    }

    fun calculate(string: String) : String {
        return try {
            val res = string.keval()
            if(res == res.toInt().toDouble())
                res.toInt().toString()
            else
                res.toString()
        } catch (exception: KevalException) {
            "Invalid"
        }
    }

    private fun canInsertOperationChar(string: String) : Boolean {
        if(string.isEmpty())
            return false
        val lastChar = string.last()
        return lastChar.isDigit() || lastChar.isLetter() || lastChar.isBrace()
    }
}