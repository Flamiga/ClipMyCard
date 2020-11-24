package com.ebookfrenzy.clipmycard.Database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ebookfrenzy.clipmycard.DAO.CardDao
import com.ebookfrenzy.clipmycard.models.Card


@Database(entities = [Card::class], version = 2)
abstract class MyDataBase : RoomDatabase() {
    abstract fun cardDao(): CardDao
}