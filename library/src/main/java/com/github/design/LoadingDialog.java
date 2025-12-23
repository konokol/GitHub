package com.github.design;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.github.design.widget.LoadingView;

/**
 * com.github.LoadingDialog
 *
 * @author Ivan J. Lee on 2020-01-12 20:27
 * @version v0.1
 * @since v1.0
 **/
public class LoadingDialog extends Dialog {

    private TextView mTvText;
    private LoadingView mLoadingView;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    public LoadingDialog(@NonNull Context context) {
        this(context, R.style.dialogStyle);
    }

    public LoadingDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.loading_view, new FrameLayout(getContext()), false);
        mTvText = view.findViewById(R.id.tv_loading_text);
        mLoadingView = view.findViewById(R.id.lv_loading);
        setContentView(view);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        Window window = getWindow();
        if (window != null) {
            window.setWindowAnimations(R.style.dialogStyle);
        }
        mLoadingView.start();
    }

    @Override
    public void show() {
        if (Looper.myLooper() == mHandler.getLooper()) {
            super.show();
        } else {
            mHandler.post(super::show);
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (mLoadingView == null) {
            return;
        }
        if (Looper.myLooper() == mHandler.getLooper()) {
            mLoadingView.stop();
        } else {
            mHandler.post(() -> mLoadingView.stop());
        }
    }

    public void setText(CharSequence text) {
        mTvText.setText(text);
    }

    public void setLoadingImage(@DrawableRes int resId) {
        mLoadingView.setImageResource(resId);
    }
}
