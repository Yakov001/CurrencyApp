package com.example.currencyapp.retrofit

import com.example.currencyapp.model.BankRequest
import com.example.currencyapp.utils.Consts.ADDITION_URL
import retrofit2.Response
import retrofit2.http.GET

interface StringAPI {
    @GET(ADDITION_URL)
    suspend fun getCurrencies() : Response<BankRequest>
}