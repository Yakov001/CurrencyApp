package com.example.currencyapp.repository

import com.example.currencyapp.model.Currency
import com.example.currencyapp.room.AppDatabase

class Repository(private val db: AppDatabase){

    fun getSavedCurrencies() = db.getCurrencyDao().getAll()

    suspend fun saveCurrencies(list: List<Currency>) = db.getCurrencyDao().insertAll(list)

}