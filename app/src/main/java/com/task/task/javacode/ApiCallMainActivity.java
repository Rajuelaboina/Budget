package com.task.task.javacode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.task.task.BR;
import com.task.task.R;
import com.task.task.databinding.ActivityApiCallMainBinding;
import com.task.task.javacode.model.User;
import com.task.task.javacode.model.UsersModel;

import java.util.List;

public class ApiCallMainActivity extends AppCompatActivity implements OnItemClickedListener {
    private UsersViewModel viewModel ;
     ActivityApiCallMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_api_call_main);
        viewModel = new ViewModelProvider(this).get(UsersViewModel.class);
        binding = ActivityApiCallMainBinding.inflate(LayoutInflater.from(getApplicationContext()));
        setContentView(binding.getRoot());
        binding.setVariable(BR.viewModel,viewModel);
        binding.executePendingBindings();


        viewModel.getAllUsers();
        viewModel.list.observe(this, new Observer<List<UsersModel>>() {
            @SuppressLint("SuspiciousIndentation")
            @Override
            public void onChanged(List<UsersModel> list) {
                Log.e("MainActivity","ApiCallMainActivity");
                if (list!=null)
                viewModel.setAdapter(list);
               // binding.recyclerView3.setAdapter(new UsersAdapter(list,getApplicationContext()));
              //  binding.recyclerView3.addItemDecoration(new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.VERTICAL));
            }
        });
        UsersAdapter.setonItemClicked(this);
    }

    @Override
    public void onItemClick(UsersModel usersModel, int position) {
        Toast.makeText(getApplicationContext(), "value: "+usersModel.getLogin(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu,menu);
        MenuItem menuItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("search user");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {


                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                viewModel.getSearchUser(newText);
                viewModel.userMutableLiveData.observe(ApiCallMainActivity.this, new Observer<User>() {
                    @Override
                    public void onChanged(User user) {
                      // Log.e("ssss<<<><><>","<><><>: "+ user.getItems().size());
                        binding.recyclerView3.setAdapter(new UserAdapter(user.getItems()));
                    }
                });
                return true;
            }
        });
        searchView.findViewById(R.id.app_bar_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.getAllUsers();
                viewModel.list.observe(ApiCallMainActivity.this, new Observer<List<UsersModel>>() {
                    @SuppressLint("SuspiciousIndentation")
                    @Override
                    public void onChanged(List<UsersModel> list) {
                        Log.e("MainActivity","ApiCallMainActivity");
                        if (list!=null)
                            viewModel.setAdapter(list);
                        // binding.recyclerView3.setAdapter(new UsersAdapter(list,getApplicationContext()));
                        //  binding.recyclerView3.addItemDecoration(new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.VERTICAL));
                    }
                });
            }
        });
      /*  ImageView clearButton = searchView.findViewById(androidx.appcompat.R.id.search_close_btn);
        clearButton.setOnClickListener(v -> {

        });*/

        return true;
    }

}