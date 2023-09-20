package com.phycae.recyclerviewitemex

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitRequest {
    companion object{
        val BASE_URL = "http://172.27.0.189/MDTSWebServices/hhservices.asmx/"
        fun getInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}