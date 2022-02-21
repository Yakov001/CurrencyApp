package com.example.currencyapp.model

data class Currency(
    var abbreviation: String = "",
    val CharCode: String,
    val ID: String,
    val Name: String,
    val Nominal: Int,
    val NumCode: String,
    val Previous: Double,
    val Value: Double
)
