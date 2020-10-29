package com.ebookfrenzy.clipmycard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val bars =
        arrayOf("Heidi's bar, Aarhus", "Guldhorn, Aarhus", "The Australian Bar, Aarhus")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, bars)

        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // this code is called whenever the spinner is clicked
                Toast.makeText(this@MainActivity, "Bar selected: " + bars[position], Toast.LENGTH_SHORT).show()
            }
        }
        val position = spinner.selectedItemPosition

        val bar = spinner.selectedItem
    }
}