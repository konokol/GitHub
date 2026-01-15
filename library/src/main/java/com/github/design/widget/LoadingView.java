package com.github.design.widget;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import androidx.appcompat.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.github.design.R;

/**
 * com.github.design.widget.LoadingView
 *
 * @author Ivan J. Lee on 2020-01-12 18:00
 * @version v0.1
 * @since v1.0
 **/
public class LoadingView extends AppCompatImageView {

    AnimationDrawable mAnimationDrawable;

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        Drawable drawable = getDrawable();
        if (drawable instanceof AnimationDrawable) {
            mAnimationDrawable = (AnimationDrawable) drawable;
        } else {
            setImageResource(R.drawable.ic_loading);
            mAnimationDrawable = (AnimationDrawable) getDrawable();
        }
    }

    public void start() {
        init();
        mAnimationDrawable.stop();
        mAnimationDrawable.start();
    }

    public void stop() {
        init();
        mAnimationDrawable.stop();
    }

}
