package com.ebookfrenzy.clipmycard

import android.graphics.Color
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
import com.ebookfrenzy.clipmycard.Fragment.YesOrNoFragment
//import com.ebookfrenzy.clipmycard.Fragment.YesOrNoFragment
import kotlinx.android.synthetic.main.list_element.*
import kotlinx.android.synthetic.main.list_element.view.*
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
                Log.d("ReceivedData", "cards from database")
                for (book in it)
                    Log.d("cards:", cardList.toString())
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
    }

    //callback function from yes/no dialog - for yes choice
    fun yesClicked(card: Card) {
        val toast = Toast.makeText(
            this,
            "yes button clicked", Toast.LENGTH_LONG
        )
        card.activated = true
        cardAdapter.notifyDataSetChanged()
        toast.show()
    }


    //callback function from yes/no dialog - for no choice
    fun noClick() {
        //Here we override the method and can now do something
        val toast = Toast.makeText(
            this,
            "no button clicked", Toast.LENGTH_LONG
        )
        toast.show()
    }

    fun cardClicked(card: Card) {
        showDialog(card)
    }

    fun showDialog(card: Card) {
        //showing our dialog.

        val dialog = YesOrNoFragment(::yesClicked, ::noClick, card)
        //Here we show the dialog
        //The tag "MyFragement" is not important for us.
        dialog.show(supportFragmentManager, "myFragment")
    }

    private fun updateUI() {
        cardList = CardRepository.getData()

        card_recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        cardAdapter = CardAdapter(cardList, ::cardClicked)

        card_recyclerView.adapter = cardAdapter
    }

}