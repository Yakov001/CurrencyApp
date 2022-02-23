package com.example.currencyapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.currencyapp.model.Currency

@Database(entities = [Currency::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getCurrencyDao(): CurrencyDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also{ instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "currency_db.db"
            ).build()
    }
}