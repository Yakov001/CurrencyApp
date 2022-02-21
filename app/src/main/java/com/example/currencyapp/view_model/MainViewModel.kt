package com.example.currencyapp.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyapp.model.BankRequest
import com.example.currencyapp.retrofit.Repo
import com.example.currencyapp.utils.Resource
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    var forecastLiveData: MutableLiveData<BankRequest> = MutableLiveData()

    private val repo = Repo()

    fun updateData() {
        viewModelScope.launch {
            forecastLiveData.value = repo.getWeather().body()
        }
    }
}