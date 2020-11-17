package com.ebookfrenzy.clipmycard.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cards")
data class Card
    (
    @PrimaryKey var id: Int,
    var title: String,
    var activated: Boolean,
    var price: Int,
    var checked: Int = 0
)