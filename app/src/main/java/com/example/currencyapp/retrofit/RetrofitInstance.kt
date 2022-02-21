package com.example.currencyapp.retrofit

import com.example.currencyapp.utils.Consts.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: StringAPI by lazy {
        retrofit.create(StringAPI::class.java)
    }
}