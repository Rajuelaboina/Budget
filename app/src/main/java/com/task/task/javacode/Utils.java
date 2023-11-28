package com.task.task.javacode;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class Utils {

    @BindingAdapter("app:imageurl")
    public static void loadImageUrl(ImageView imageView,String url){
        Glide.with(imageView.getContext()).load(url).apply(RequestOptions.circleCropTransform())
                .into(imageView);
    }
    @BindingAdapter("app:textDisplay")
    public static void loadText(TextView textView,String str){
        textView.setText(str);
    }
}
