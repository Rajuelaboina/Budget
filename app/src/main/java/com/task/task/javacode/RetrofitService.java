package com.task.task.javacode;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
  public static Retrofit retrofit;
   public static String BASE_URL = "https://api.github.com/";

  public static Retrofit getInstance(){
        if (retrofit ==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
  }


}
