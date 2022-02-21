package com.example.currencyapp.retrofit

import com.example.currencyapp.model.BankRequest
import retrofit2.Response

class Repo {

    suspend fun getWeather(): Response<BankRequest> {
        return RetrofitInstance.api.getCurrencies()
    }

}