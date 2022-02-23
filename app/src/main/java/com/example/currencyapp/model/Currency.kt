package com.example.currencyapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currencies")
data class Currency(
    var abbreviation: String = "",
    val CharCode: String,
    @PrimaryKey val ID: String,
    val Name: String,
    val Nominal: Int,
    val NumCode: String,
    val Previous: Double,
    val Value: Double
)
