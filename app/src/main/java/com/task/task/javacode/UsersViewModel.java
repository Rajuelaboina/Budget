package com.task.task.javacode;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.task.task.javacode.model.User;
import com.task.task.javacode.model.UsersModel;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersViewModel extends ViewModel {
    MutableLiveData<List<UsersModel>> list = new MutableLiveData<>();
    UsersAdapter adapter = new UsersAdapter();
    MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();

    void getAllUsers(){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {


                ApiService  apiService = RetrofitService.getInstance().create(ApiService.class);
                Call<List<UsersModel>> call = apiService.getAllUsers();
                call.enqueue(new Callback<List<UsersModel>>() {
                    @Override
                    public void onResponse(Call<List<UsersModel>> call, Response<List<UsersModel>> response) {
                        Log.e("Response","Response: "+ response.body());
                        if (response.isSuccessful()){
                            list.postValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<UsersModel>> call, Throwable t) {

                    }
                });
            }
        });
    }
    public void setAdapter(List<UsersModel> list){
          adapter.updateList(list);
    }
    public UsersAdapter getAdapter(){
        return adapter;
    }

    public void getSearchUser(String query) {
        ApiService  apiService = RetrofitService.getInstance().create(ApiService.class);
        Call<User> call = apiService.getUser(query);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                userMutableLiveData.postValue(response.body());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }
}
