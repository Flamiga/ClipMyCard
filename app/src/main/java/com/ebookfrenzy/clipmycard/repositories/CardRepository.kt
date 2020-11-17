package com.ebookfrenzy.clipmycard.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.ebookfrenzy.clipmycard.DAO.CardDao
import com.ebookfrenzy.clipmycard.Database.MyDataBase
import com.ebookfrenzy.clipmycard.models.Card
import com.ebookfrenzy.clipmycard.repositories.CardRepository.getAllCards

object CardRepository {
    private var cards: MutableList<Card> = mutableListOf()
    private lateinit var cardDao: CardDao
    private var db: MyDataBase? = null


    //This returns a LiveData object that we can observe
    //So this already happens in the background
    fun getAllCards(): LiveData<List<Card>> {
        return cardDao.getAllCards()
    }

    suspend fun addCard(card: Card): Long {
        val rowNr = cardDao.addCard(card)
        return rowNr
    }

    suspend fun updateCard(card: Card): Int {
        return cardDao.updateCard(card)
    }

    //Reference fra Lesson12
    fun initRepository(context: Context) {
        if (db == null) {
            db = Room.databaseBuilder(
                context,
                MyDataBase::class.java, "mydatabase"
            ).fallbackToDestructiveMigration().build()
            //NOTICE - When we update the version of the database, it will be cleared now
            //There is a migration system, if you don't want that
        }
        cardDao = db?.cardDao()!!

    }

    fun getData(): MutableList<Card> {
        if (cards.size == 0)
            retrieveData()
        return cards
    }


    private fun retrieveData() {
        cards.add(
            Card(
                id = 1,
                title = "Heidi's bar, Aarhus",
                price = 150, activated = false
            )
        )
        cards.add(
            Card(
                id = 2,
                title = "The Australian Bar, Aarhus",
                price = 200, activated = true,
                checked = 4
            )
        )
        cards.add(
            Card(
                id = 3,
                title = "Guldhornerne, Aarhus",
                price = 250,
                checked = 1,
                activated = true
            )
        )

        cards.add(Card(id = 4, title = "Guldhornerne, Aarhus", price = 250, activated = false))

        cards.add(
            Card(
                id = 5,
                title = "The Australian Bar, Aarhus",
                price = 350, activated = false
            )
        )
    }

}
