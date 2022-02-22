package com.example.calculator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.example.calculator.databinding.ActivityMainBinding
import kotlin.collections.HashMap

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val calculatorVM by viewModels<CalculatorViewModel>()
    private val onClickMap = getButtonsMap()
    private val hotKeysManager = HotKeysManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        hotKeysManager.addHotKey(HotKey(
            R.id.button_equals,
            R.id.button_equals,
            R.id.button_equals
        ) {
            startActivity(Intent(this, SettingsActivity::class.java))
        })

        calculatorVM.expression.observe(this, { string: String ->
            binding.calcText.text = string
        })
    }

    override fun onResume() {
        super.onResume()
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        when (sharedPreferences.getBoolean("nightThemeOn", false)) {
            true -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            false -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    private fun getButtonsMap(): HashMap<Int, () -> Unit> {
        return HashMap<Int, () -> Unit>().apply {
            put(R.id.button_clear) { calculatorVM.clearAll() }
            put(R.id.button_clear_one) { calculatorVM.clearOne() }
            put(R.id.button_equals) { calculatorVM.calculate() }
        }
    }

    // Called from style "DigitButton"
    fun onCalcButtonsClick(view: View) {
        hotKeysManager.addClickedButton(view.id)
        onClickMap[view.id]?.invoke() ?:
            calculatorVM.tryAddChar((view as Button).text.toString())
    }
}