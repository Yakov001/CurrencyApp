package com.example.currencyapp.model

data class BankRequest(
    val Date: String,
    val PreviousDate: String,
    val PreviousURL: String,
    val Timestamp: String,
    val Valute: Valute
)