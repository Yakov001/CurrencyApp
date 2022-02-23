package com.example.currencyapp.view_model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyapp.model.Currency
import com.example.currencyapp.retrofit.Repo
import com.example.currencyapp.room.AppDatabase
import com.example.currencyapp.utils.Resource
import kotlinx.coroutines.launch

class MainViewModel(private val db: AppDatabase) : ViewModel() {

    var forecastLiveData: MutableLiveData<Resource<List<Currency>>> = MutableLiveData()

    var needAPiRequest = true

    private val repo = Repo()

    fun updateData() {
        viewModelScope.launch {
            val response = try {
                repo.getWeather()
            } catch (e: Exception) {
                Log.e("retrofit request", e.javaClass.canonicalName)
                if (db.getCurrencyDao().getAll().isNotEmpty()) {
                    forecastLiveData.value = Resource.Error(
                        db.getCurrencyDao().getAll(),
                        "No Internet. Using old data from database"
                    )
                    return@launch
                } else if (!forecastLiveData.value?.data.isNullOrEmpty()) {
                    forecastLiveData.value = Resource.Error(
                        forecastLiveData.value!!.data,
                        "No Internet. Using old data."
                    )
                    return@launch
                }
                forecastLiveData.value = Resource.Error(null, "No Internet")
                return@launch
            }
            forecastLiveData.value = Resource.Success(response.body()!!.Valute.currencies)
            db.getCurrencyDao().insertAll(response.body()!!.Valute.currencies)
        }
    }

    override fun onCleared() {
        super.onCleared()
        db.close()
    }
}