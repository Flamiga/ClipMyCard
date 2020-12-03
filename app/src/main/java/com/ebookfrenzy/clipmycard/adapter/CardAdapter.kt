package com.ebookfrenzy.clipmycard.adapter

import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Filterable
import android.widget.Filter
import android.widget.Toast
import androidx.core.view.ViewPropertyAnimatorUpdateListener
import androidx.core.view.marginBottom
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ebookfrenzy.clipmycard.Fragment.YesOrNoFragment
import com.ebookfrenzy.clipmycard.R
import com.ebookfrenzy.clipmycard.models.Card
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.list_element.view.*
import java.util.*
import kotlin.collections.ArrayList
import android.content.Context as context

class CardAdapter(
    var cardList: List<Card>,
    val cardClicked: (Card) -> Unit
   // var updateListener: UpdateCard,
    //private var activated: MutableSet<String> = HashSet()


) : RecyclerView.Adapter<CardAdapter.ViewHolder>(), Filterable {

    private lateinit var context: context

    var cardFilterList = ArrayList<Card>()

    init {
        cardFilterList = cardList as ArrayList<Card>
    }


    //this method is returning the view for each item in the list
    //also something you must override
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_element, parent, false)
        context = parent.context

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // holder.bindItems(cardList[position])
        holder.bindItems(cardFilterList[position], cardClicked, context)
    }

    //this method is giving the size of the list - this MUST be implemented as this
    //also overrides something from the RecyclerView Class
    override fun getItemCount(): Int {
        return cardFilterList.size
        //return cardList.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun bindItems(
            card: Card,
            cardClicked: (Card) -> Unit,
            context: context
         //   activated: MutableSet<String>,
           // updateListener: UpdateCard

        ) {
            itemView.list_element_title.text = card.title
            itemView.list_element_price.text = card.price.toString() + " "+"kr."


            itemView.checkBox1.isChecked = false
            itemView.checkBox2.isChecked = false
            itemView.checkBox3.isChecked = false
            itemView.checkBox4.isChecked = false
            itemView.checkBox5.isChecked = false
            itemView.checkBox6.isChecked = false
            itemView.checkBox7.isChecked = false
            itemView.checkBox8.isChecked = false
            itemView.checkBox9.isChecked = false
            itemView.checkBox10.isChecked = false


            if (card.activated) {
                /*Insert gradient on activated card
                https://stackoverflow.com/questions/6115715/how-do-i-programmatically-set-the-background-color-gradient-on-a-custom-title-ba */
                var cardColor = GradientDrawable(
                  GradientDrawable.Orientation.BR_TL,
                    intArrayOf(0xFFe6b800.toInt(), 0xfffff5cc.toInt())
                )
                cardColor.setCornerRadius(50f)
                itemView.list_element_card.setBackgroundDrawable(cardColor)
               // itemView.list_element_card(cardColor)
                //itemView.list_element_card.setCardBackgroundColor(Color.rgb(231, 219, 40))

            //if statement if card = 10 grey or then yellow
                if(card.checked == 10){

                    itemView.checkBox1.isClickable = false
                    itemView.checkBox2.isClickable = false
                    itemView.checkBox3.isClickable = false
                    itemView.checkBox4.isClickable = false
                    itemView.checkBox5.isClickable = false
                    itemView.checkBox6.isClickable = false
                    itemView.checkBox7.isClickable = false
                    itemView.checkBox8.isClickable = false
                    itemView.checkBox9.isClickable = false
                    itemView.checkBox10.isClickable = false
                    card.activated === false

                    var cardColor = GradientDrawable(
                        GradientDrawable.Orientation.BR_TL,
                        intArrayOf(0xFF8c8c8c.toInt(), 0xffd9d9d9.toInt())
                    )
                    cardColor.setCornerRadius(50f)
                    itemView.list_element_card.setBackgroundDrawable(cardColor)
                    Toast.makeText(context,"Sorry you have used this card", Toast.LENGTH_LONG).show()
                }else{
                    var cardColor = GradientDrawable(
                        GradientDrawable.Orientation.BR_TL,
                        intArrayOf(0xFFe6b800.toInt(), 0xfffff5cc.toInt())
                    )
                    cardColor.setCornerRadius(50f)
                    itemView.list_element_card.setBackgroundDrawable(cardColor)

                }


                itemView.checkBox1.setOnClickListener {
                    if (itemView.checkBox1.isChecked) {
                        card.checked++
                    } else {
                        itemView.checkBox1.isChecked = true
                    }
                }

                itemView.checkBox2.setOnClickListener {
                    if (itemView.checkBox2.isChecked) {
                        card.checked++
                    } else {
                        itemView.checkBox2.isChecked = true
                    }
                }
                itemView.checkBox3.setOnClickListener {
                    if (itemView.checkBox3.isChecked) {
                        card.checked++
                    } else {
                        itemView.checkBox3.isChecked = true
                    }
                }
                itemView.checkBox4.setOnClickListener {
                    if (itemView.checkBox4.isChecked) {
                        card.checked++
                    } else {
                        itemView.checkBox4.isChecked = true
                    }
                }
                itemView.checkBox5.setOnClickListener {
                    if (itemView.checkBox5.isChecked) {
                        card.checked++
                    } else {
                        itemView.checkBox5.isChecked = true
                    }
                }
                itemView.checkBox6.setOnClickListener {
                    if (itemView.checkBox6.isChecked) {
                        card.checked++
                    } else {
                        itemView.checkBox6.isChecked = true
                    }
                }
                itemView.checkBox7.setOnClickListener {
                    if (itemView.checkBox7.isChecked) {
                        card.checked++
                    } else {
                        itemView.checkBox7.isChecked = true
                    }
                }
                itemView.checkBox8.setOnClickListener {
                    if (itemView.checkBox8.isChecked) {
                        card.checked++
                    } else {
                        itemView.checkBox8.isChecked = true
                    }
                }
                itemView.checkBox9.setOnClickListener {
                    if (itemView.checkBox9.isChecked) {
                        card.checked++
                    } else {
                        itemView.checkBox9.isChecked = true
                    }
                }

                // sætter den sidste kryds til at skifte farve for at vise at kortet er brugt op.
                itemView.checkBox10.setOnClickListener {
                    if (itemView.checkBox10.isChecked) {
                        card.checked++
                        /*initiliase the grey color*/
                        var cardColor = GradientDrawable(
                            GradientDrawable.Orientation.BR_TL,
                            intArrayOf(0xFF8c8c8c.toInt(), 0xffd9d9d9.toInt())
                        )
                        cardColor.setCornerRadius(50f)
                        itemView.list_element_card.setBackgroundDrawable(cardColor)
                        itemView.list_element_card.setCardBackgroundColor(Color.GRAY)
                    } else {
                        itemView.checkBox10.isChecked = true
                    }
                }

                /*????*/
                if (card.checked >= 1)
                    itemView.checkBox1.isChecked = true
                if (card.checked >= 2)
                    itemView.checkBox2.isChecked = true
                if (card.checked >= 3)
                    itemView.checkBox3.isChecked = true
                if (card.checked >= 4)
                    itemView.checkBox4.isChecked = true
                if (card.checked >= 5)
                    itemView.checkBox5.isChecked = true
                if (card.checked >= 6)
                    itemView.checkBox6.isChecked = true
                if (card.checked >= 7)
                    itemView.checkBox7.isChecked = true
                if (card.checked >= 8)
                    itemView.checkBox8.isChecked = true
                if (card.checked >= 9)
                    itemView.checkBox9.isChecked = true
                if (card.checked >= 10)
                    itemView.checkBox10.isChecked = true

            } else {

                itemView.checkBox1.isClickable = false
                itemView.checkBox2.isClickable = false
                itemView.checkBox3.isClickable = false
                itemView.checkBox4.isClickable = false
                itemView.checkBox5.isClickable = false
                itemView.checkBox6.isClickable = false
                itemView.checkBox7.isClickable = false
                itemView.checkBox8.isClickable = false
                itemView.checkBox9.isClickable = false
                itemView.checkBox10.isClickable = false

                /*initiliase the grey color*/
                var cardColor = GradientDrawable(
                    GradientDrawable.Orientation.BR_TL,
                    intArrayOf(0xFF8c8c8c.toInt(), 0xffd9d9d9.toInt())
                )
                cardColor.setCornerRadius(50f)
                itemView.list_element_card.setBackgroundDrawable(cardColor)
                itemView.list_element_card.setCardBackgroundColor(Color.GRAY)

                itemView.list_element_card.setOnClickListener { cardClicked(card) }

            }

        }

    }




    //implement the filterable
// Reference: https://www.tutorialsbuzz.com/2020/09/android-recyclerView-data-list-filterable-kotlin.html
    override fun getFilter(): Filter? {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterString = constraint.toString()
                if (filterString.isEmpty()) {
                    cardFilterList = cardList as ArrayList<Card>
                } else {
                    val resultList = ArrayList<Card>()
                    for (row in cardList) {
                        if (row.title.toLowerCase(Locale.ROOT).contains(
                                constraint.toString().toLowerCase(
                                    Locale.ROOT
                                )
                            )
                        ) {

                            resultList.add(row)

                        }
                    }
                    cardFilterList = resultList

                }
                val filterResults = FilterResults()
                filterResults.values = cardFilterList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                cardFilterList = results?.values as ArrayList<Card>
                //fortæller adapter at der er ændret nogle ting
                notifyDataSetChanged()
            }
        }
    }


}