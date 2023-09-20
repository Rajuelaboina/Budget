package com.phycae.recyclerviewitemex

import retrofit2.Call
import retrofit2.http.GET

interface ApiRequest {
    @GET("ActivitiesMasterData")
    fun getAllMovies(): Call<List<GroupActivitiesItem>>
}