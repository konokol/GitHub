package com.ivan.github.app.homepage.ui;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.ivan.github.common.util.DateFormatUtils;

import java.util.Date;

public class FeedBindings {

    /**
     * setImageUrl for imageView
     * @param imageView imageView
     * @param imageUrl url to be set
     */
    @BindingAdapter(value = {"imageUrl", "error", "placeholder"}, requireAll = false)
    public static void setImageUrl(ImageView imageView, String imageUrl, Drawable drawable, Drawable placeholder) {
        Glide.with(imageView)
                .load(imageUrl)
                .error(drawable)
                .placeholder(placeholder)
                .into(imageView);
    }

    @BindingAdapter({"date"})
    public static void setDate(TextView textView, Date date) {
        if (date == null) {
            return;
        }
        textView.setText(DateFormatUtils.getTimeSpan(date));
    }
}
