package com.task.task.javacode;

import com.task.task.javacode.model.User;
import com.task.task.javacode.model.UsersModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("users")
    Call<List<UsersModel>> getAllUsers();
     @GET("search/users?")
    Call<User> getUser(@Query("q") String query);
}
