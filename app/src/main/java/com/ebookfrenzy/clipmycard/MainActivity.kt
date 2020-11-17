package com.ebookfrenzy.clipmycard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ebookfrenzy.clipmycard.adapter.CardAdapter
import com.ebookfrenzy.clipmycard.models.Card
import com.ebookfrenzy.clipmycard.repositories.CardRepository
import com.ebookfrenzy.clipmycard.repositories.CardRepository.initRepository
import kotlinx.android.synthetic.main.activity_main.*
import androidx.lifecycle.Observer
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val bars =
        arrayOf("Alle", "Heidi's bar, Aarhus", "Guldhornerne, Aarhus", "The Australian Bar, Aarhus")

    private lateinit var cardList: MutableList<Card>
    private lateinit var cardAdapter: CardAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initRepository(this)

        CardRepository.getAllCards().observe(this, Observer {
            if (it.size > 0) {
                cardList = it as MutableList<Card>
                //update UI
                Log.d("ReceivedData", "Books from database")
                for (book in it)
                    Log.d("Book:", cardList.toString())
            }
        })
        val String = getString(R.string.numberofcard)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, bars)
        updateUI()

        GlobalScope.launch {
           val card = Card(6, "Hornsleth", false, 200, 0)
            CardRepository.addCard(card)

            val card1 = Card(5, "The Australian Bar", true, 350, 4)
            CardRepository.updateCard(card1)
        }


        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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
                Toast.makeText(
                    this@MainActivity,
                    "Du har valgt: " + bars[position],
                    Toast.LENGTH_SHORT
                ).show()
                //filter bar i dropdown
                val selectBar = bars[position]
                if (selectBar == "Alle") {
                    cardAdapter.filter?.filter("")
                } else {
                    cardAdapter.filter?.filter(selectBar)

                }
            }
        }
        //val position = spinner.selectedItemPosition

        //val bar = spinner.selectedItem


    }

    private fun updateUI() {
        cardList = CardRepository.getData()

        card_recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        cardAdapter = CardAdapter(cardList)

        card_recyclerView.adapter = cardAdapter
    }
}