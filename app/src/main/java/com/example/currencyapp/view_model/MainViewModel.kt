package com.example.currencyapp.view_model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyapp.model.BankRequest
import com.example.currencyapp.retrofit.Repo
import com.example.currencyapp.utils.Resource
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    var forecastLiveData: MutableLiveData<Resource<BankRequest>> = MutableLiveData()

    private val repo = Repo()

    fun updateData() {
        viewModelScope.launch {
            val response = try {
                repo.getWeather()
            } catch (e: Exception) {
                Log.e("retrofit request", e.javaClass.canonicalName)
                forecastLiveData.value = Resource.Error(null, "No Internet")
                return@launch
            }
            forecastLiveData.value = Resource.Success(response.body()!!)
        }
    }
}