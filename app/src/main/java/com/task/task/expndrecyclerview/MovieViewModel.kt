package com.phycae.recyclerviewitemex

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieViewModel : ViewModel() {
    private val list= MutableLiveData<List<GroupActivitiesItem>>()
     //var adapter:MyAdapter = MyAdapter()

    fun getAllMovies(){
        val apiCall = RetrofitRequest.getInstance().create(ApiRequest::class.java)
        val call:Call<List<GroupActivitiesItem>>  = apiCall.getAllMovies()
        call.enqueue(object : Callback<List<GroupActivitiesItem>>{
            override fun onResponse(call: Call<List<GroupActivitiesItem>>, response: Response<List<GroupActivitiesItem>>) {
                list.postValue(response.body())
            }

            override fun onFailure(call: Call<List<GroupActivitiesItem>>, t: Throwable) {
                //list.postValue(null)
            }

        })

    }
    fun getMoviesObserver(): MutableLiveData<List<GroupActivitiesItem>>{
        return list
    }

    /*fun setAdapter(list: List<Movies>) {
        adapter.updateList(list)
    }
    fun getMyAdapter():MyAdapter{
        return adapter
    }*/
}