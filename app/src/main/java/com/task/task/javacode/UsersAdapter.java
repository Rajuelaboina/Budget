package com.task.task.javacode;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.task.task.R;
import com.task.task.databinding.RowitemBinding;
import com.task.task.javacode.model.UsersModel;

import java.util.ArrayList;
import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder> {
    List<UsersModel> list =new ArrayList<>();
    Context mContext;
    public static OnItemClickedListener onItemClickedListener;
    public UsersAdapter(List<UsersModel> list, Context context) {
        this.list = list;
        this.mContext = context;
    }
    public void updateList(List<UsersModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public UsersAdapter() {

    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       // return new UserViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rowitem,parent,false));
        return new UserViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.rowitem,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
          /*  holder.textView.setText(list.get(position).getLogin());
        Log.e("AvatarUrl","AvatarUrl: "+list.get(position).getId());
        Glide.with(mContext).load(list.get(position).getAvatarUrl()).apply(RequestOptions.circleCropTransform())
                .into(holder.imageView);*/
         holder.bind(list.get(position));
         holder.binding.linearLayoutRow.setOnClickListener(v -> {
             onItemClickedListener.onItemClick(list.get(position),position);
         });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public static void setonItemClicked (OnItemClickedListener clickedListener){
        onItemClickedListener = clickedListener;
    }



    public class UserViewHolder extends RecyclerView.ViewHolder{
        RowitemBinding binding;
        ImageView imageView;
        TextView textView;
        public UserViewHolder(@NonNull RowitemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            /*imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textView7);*/
        }

        public void bind(UsersModel usersModel) {
            binding.setModel(usersModel);
        }
    }
}
