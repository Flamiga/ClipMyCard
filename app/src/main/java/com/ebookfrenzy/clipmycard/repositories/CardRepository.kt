package com.ebookfrenzy.clipmycard.repositories

import com.ebookfrenzy.clipmycard.models.Card

object CardRepository {
    private var cards: MutableList<Card> = mutableListOf()

    fun getData(): MutableList<Card> {
        if (cards.size == 0)
            retrieveData()
        return cards
    }

    private fun retrieveData() {
        cards.add(
            Card(
                title = "Heidi's bar, Aarhus",
                price = 150, activated = false
            )
        )
        cards.add(
            Card(
                title = "The Australian Bar, Aarhus",
                price = 200, activated = true,
                checked = 4
            )
        )
        cards.add(Card(title = "Guldhornerne, Aarhus", price = 250, checked = 1, activated = true))

        cards.add(Card(title = "Guldhornerne, Aarhus", price = 250, activated = false))

        cards.add(
            Card(
                title = "The Australian Bar, Aarhus",
                price = 350, activated = false
            )
        )
    }

}
