package com.ebookfrenzy.clipmycard

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Bitmap.createBitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Display
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ebookfrenzy.clipmycard.adapter.CardAdapter
import com.ebookfrenzy.clipmycard.models.Card
import com.ebookfrenzy.clipmycard.repositories.CardRepository
import com.ebookfrenzy.clipmycard.repositories.CardRepository.initRepository
import kotlinx.android.synthetic.main.activity_main.*
import androidx.lifecycle.Observer
import com.ebookfrenzy.clipmycard.Fragment.ScanbarFragment
import com.ebookfrenzy.clipmycard.Fragment.YesOrNoFragment
import com.google.zxing.BarcodeFormat
import com.google.zxing.oned.Code128Writer
import kotlinx.android.synthetic.main.list_element.*
import kotlinx.android.synthetic.main.scanbar_layout.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.w3c.dom.Text
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    private val bars =
        arrayOf("All", "Heidi's bar, Aarhus", "Guldhornerne, Aarhus", "The Australian Bar, Aarhus")

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

        /*referere til min være i min strings.xml*/
        numberOfCardTextView.text = resources.getString(R.string.numberofcard, 0)


/*what is this?, and how can i use the add and update?*/
        GlobalScope.launch {
            val card = Card(6, "Hornsleth", false, 200, 0)
            CardRepository.addCard(card)

            val card1 = Card(5, "The Australian Bar", true, 350, 4)
            CardRepository.updateCard(card1)
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, bars)
        updateUI()
        //The spinner is defined in our xml file

        //we use a predefined simple spinner drop down,
        //you could define your own layout, so that for instance
        //there was pictures in the drop down list.
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

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
                    "You have selected: " + bars[position],
                    Toast.LENGTH_SHORT
                ).show()
                //filter bar i dropdown
                val selectBar = bars[position]
                if (selectBar == "All") {
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
            "Yes button clicked", Toast.LENGTH_LONG
        )
        card.activated = true
        val numberofactivated = numberOfActivatedCard()
        numberOfCardTextView.text = resources.getString(R.string.numberofcard, numberofactivated)
        cardAdapter.notifyDataSetChanged()
        showCustomDialog(card)
        toast.show()
    }

    
    fun numberOfActivatedCard(): Int {
        //for loop der løber igennem cardFilterList
        var counter = 0
        var discounter = -1
        cardList = CardRepository.getData()
        for (card in cardList) {
            if (card.activated)
                counter++
           if(card.activated === false)
                discounter--
        }




        return counter
    }








//callback function from yes/no dialog - for no choice
fun noClick() {
    //Here we override the method and can now do something
    val toast = Toast.makeText(
        this,
        "No button clicked", Toast.LENGTH_LONG
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

fun showCustomDialog(card: Card) {
    val dialog = ScanbarFragment(card, applicationContext)
    dialog.show(supportFragmentManager, "customFragment")
}


private fun updateUI() {
    cardList = CardRepository.getData()

    card_recyclerView.layoutManager =
        LinearLayoutManager(this, RecyclerView.VERTICAL, false)

    cardAdapter = CardAdapter(cardList, ::cardClicked)

    card_recyclerView.adapter = cardAdapter
}

}