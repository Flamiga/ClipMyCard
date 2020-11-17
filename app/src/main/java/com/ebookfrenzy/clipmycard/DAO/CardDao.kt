package com.ebookfrenzy.clipmycard.DAO

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ebookfrenzy.clipmycard.models.Card

@Dao
interface CardDao {
    @Query("SELECT * FROM cards")
    fun getAllCards(): LiveData<List<Card>>

    //Ignore means that we will ignore if we try to insert a
    //book with the same primary key
    //Alternatives are: Replace
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCard(card: Card) : Long

    @Update
    suspend fun updateCard(card: Card) : Int


}