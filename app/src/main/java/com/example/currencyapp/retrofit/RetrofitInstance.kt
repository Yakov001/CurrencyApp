package com.example.currencyapp.retrofit

import com.example.currencyapp.model.Currency
import com.example.currencyapp.model.Valute
import com.example.currencyapp.utils.Consts.BASE_URL
import com.google.gson.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type

object RetrofitInstance {

    private val gson by lazy {
        GsonBuilder().
        registerTypeAdapter(
            Valute::class.java, object : JsonDeserializer<Valute> {
                override fun deserialize(
                    json: JsonElement?,
                    typeOfT: Type?,
                    context: JsonDeserializationContext?
                ): Valute {
                    val valuteObj = json!!.asJsonObject
                    val list = mutableListOf<Currency>()
                    for (i in valuteObj.keySet()) {
                        val cur = Gson().fromJson(valuteObj[i], Currency::class.java)
                        cur.abbreviation = i
                        list.add(cur)
                    }
                    return Valute(list)
                }

            }
        ).create()
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    val api: StringAPI by lazy {
        retrofit.create(StringAPI::class.java)
    }
}