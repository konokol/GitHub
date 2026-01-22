package com.github.app.homepage.ui;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.github.R;
import com.github.common.util.DateFormatUtils;

import java.util.Date;

public class FeedBindings {

    /**
     * setImageUrl for imageView
     * @param imageView imageView
     * @param imageUrl url to be set
     */
    @BindingAdapter(value = {"imageUrl", "error", "placeholder"}, requireAll = false)
    public static void setImageUrl(ImageView imageView, String imageUrl, Drawable errDrawable, Drawable placeholder) {
        Glide.with(imageView)
                .load(imageUrl)
                .error(errDrawable == null ?
                        ContextCompat.getDrawable(imageView.getContext(), R.drawable.ic_avatar_default) :
                        errDrawable)
                .placeholder(placeholder == null ?
                        ContextCompat.getDrawable(imageView.getContext(), R.drawable.ic_avatar_default) :
                        placeholder)
                .into(imageView);
    }

    @BindingAdapter({"date"})
    public static void setDate(TextView textView, Date date) {
        if (date == null) {
            return;
        }
        textView.setText(DateFormatUtils.getTimeSpan(date));
    }

    @BindingAdapter({"visible"})
    public static void setVisible(View view, boolean visible) {
        if (visible) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }
}
