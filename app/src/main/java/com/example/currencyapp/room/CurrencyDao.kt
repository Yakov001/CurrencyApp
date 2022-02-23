package com.example.currencyapp.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.currencyapp.model.Currency

@Dao
interface CurrencyDao {

    @Query("SELECT * FROM currencies")
    suspend fun getAll(): List<Currency>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<Currency>)

}