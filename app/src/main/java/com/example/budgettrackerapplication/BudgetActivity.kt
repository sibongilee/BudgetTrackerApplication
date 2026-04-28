package com.example.budgettrackerapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import com.example.budgettrackerapplication.DatabaseHelper
import com.example.budgettrackerapplication.R


class BudgetActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_budget)
        val db = DatabaseHelper(this)

        val seekMin = findViewById<SeekBar>(R.id.seekBarMin)
        val seekMax = findViewById<SeekBar>(R.id.seekBarMax)
        val tvMin = findViewById<TextView>(R.id.tvMinBudget)
        val tvMax = findViewById<TextView>(R.id.tvMaxBudget)
        val btnSave = findViewById<Button>(R.id.btnSaveBudget)

        seekMin.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                tvMin.text = "R$progress"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        seekMax.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                tvMax.text = "R$progress"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        btnSave.setOnClickListener {
            val min = seekMin.progress
            val max = seekMax.progress

            if (min > max) {
                Toast.makeText(this, "Minimum cannot be greater than maximum", Toast.LENGTH_SHORT)
                    .show()
            } else {
                db.saveBudget(min, max)
                Toast.makeText(this, "Budget saved", Toast.LENGTH_SHORT).show()
            }
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}