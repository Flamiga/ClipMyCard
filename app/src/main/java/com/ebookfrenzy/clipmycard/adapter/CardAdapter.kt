package com.ebookfrenzy.clipmycard.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filterable
import android.widget.Filter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ebookfrenzy.clipmycard.R
import com.ebookfrenzy.clipmycard.models.Card
import kotlinx.android.synthetic.main.list_element.view.*
import java.util.*
import kotlin.collections.ArrayList

class CardAdapter(
    var cardList: List<Card>


) : RecyclerView.Adapter<CardAdapter.ViewHolder>(), Filterable {

    private lateinit var context: Context

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
        //Tjek linje 26 med reference listen
        holder.bindItems(cardFilterList[position])
    }

    //this method is giving the size of the list - this MUST be implemented as this
    //also overrides something from the RecyclerView Class
    override fun getItemCount(): Int {
        // kan man det her??
        return cardFilterList.size
        //return cardList.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(
            card: Card
        ) {
            //tjek linje 38-39 i referencen bør jeg gør det samme?
            itemView.list_element_title.text = card.title
            itemView.list_element_price.text = card.price.toString()
            //itemView.list_element_card.
            if (card.activated) {
                itemView.list_element_card.setCardBackgroundColor(Color.rgb(231, 219, 40))
            }

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

        }


    }
    //implement the filterable
    // Reference: https://www.tutorialsbuzz.com/2020/09/android-recyclerView-data-list-filterable-kotlin.html
    override fun getFilter(): Filter? {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterString = constraint.toString()
                if(filterString.isEmpty()) {
                    cardFilterList = cardList as ArrayList<Card>
                } else {
                    val resultList = ArrayList<Card>()
                    for (row in cardList) {
                        if (row.title.toLowerCase(Locale.ROOT).contains(constraint.toString().toLowerCase(
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