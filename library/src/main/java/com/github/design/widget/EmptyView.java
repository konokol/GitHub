package com.github.design.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

import com.github.design.R;

/**
 * com.github.design.widget.EmptyView
 *
 * @author Ivan J. Lee on 2020-10-25 21:25
 * @version v0.1
 * @since v1.0
 **/
public class EmptyView extends LinearLayout {

    private ImageView mIVDrawable;
    private TextView mTvTitle;
    private TextView mTvText;

    private CharSequence mTitle;
    private CharSequence mMessage;
    private Drawable mDrawable;
    private int mDrawableSize;

    public EmptyView(Context context) {
        this(context, null);
    }

    public EmptyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmptyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.EmptyView);
        mTitle = attributes.getString(R.styleable.EmptyView_title);
        mMessage = attributes.getString(R.styleable.EmptyView_message);
        mDrawable = attributes.getDrawable(R.styleable.EmptyView_drawable);
        mDrawableSize = attributes.getDimensionPixelSize(R.styleable.EmptyView_drawableSize, -1);
        LayoutInflater.from(getContext()).inflate(R.layout.empty_view, this, true);
        this.setOrientation(VERTICAL);
        this.setGravity(Gravity.CENTER);
        mTvTitle = findViewById(R.id.tv_empty_title);
        mTvText = findViewById(R.id.tv_empty_content);
        mIVDrawable = findViewById(R.id.iv_empty_icon);
        updateAttrs();
        attributes.recycle();
    }

    private void updateAttrs() {
        mTvTitle.setText(mTitle);
        if (TextUtils.isEmpty(mTitle)) {
            mTvTitle.setVisibility(View.GONE);
        } else {
            mTvTitle.setVisibility(View.VISIBLE);
        }
        mTvText.setText(mMessage);
        mIVDrawable.setImageDrawable(mDrawable);
        if (mDrawableSize > 0) {
            mIVDrawable.setMaxWidth(mDrawableSize);
        }
    }

    public void setTitle(@StringRes int resId) {
        this.setTitle(getContext().getResources().getString(resId));
    }

    public void setTitle(CharSequence title) {
        this.mTitle = title;
        mTvTitle.setText(mTitle);
        if (TextUtils.isEmpty(mTitle)) {
            mTvTitle.setVisibility(View.GONE);
        } else {
            mTvTitle.setVisibility(View.VISIBLE);
        }
    }

    public void setMessage(@StringRes int resId) {
        this.setMessage(getContext().getResources().getString(resId));
    }

    public void setMessage(CharSequence message) {
        this.mMessage = message;
        this.mTvText.setText(mMessage);
    }

    public void setDrawable(@DrawableRes int resId) {
        setDrawable(getContext().getResources().getDrawable(resId));
    }

    public void setDrawable(Drawable drawable) {
        this.mDrawable = drawable;
        this.mIVDrawable.setImageDrawable(mDrawable);
        if (mDrawableSize > 0) {
            this.mIVDrawable.setMaxWidth(mDrawableSize);
        }
    }

    public void  setDrawableSize(int dimen) {
        this.mDrawableSize = dimen;
        this.mIVDrawable.setImageDrawable(mDrawable);
        if (mDrawableSize > 0) {
            this.mIVDrawable.setMaxWidth(mDrawableSize);
        }
    }
}
