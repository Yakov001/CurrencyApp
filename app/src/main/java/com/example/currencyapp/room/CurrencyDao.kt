package com.example.currencyapp.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.currencyapp.model.Currency

@Dao
interface CurrencyDao {

    @Query("SELECT * FROM currencies")
    fun getAll(): LiveData<List<Currency>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<Currency>)

}