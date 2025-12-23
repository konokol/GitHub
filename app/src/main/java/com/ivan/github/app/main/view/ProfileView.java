package com.ivan.github.app.main.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.github.log.Logan;
import com.ivan.github.GitHub;
import com.ivan.github.R;
import com.ivan.github.account.model.User;
import com.ivan.github.common.Router;
import com.ivan.github.common.util.BitmapUtils;

/**
 * com.ivan.github.app.main.view
 * ProfileView
 * <p>
 *
 * @author lijun on 2023-02-02 22:50
 * @since v1.0
 */
public class ProfileView extends LinearLayout {

    public static final String TAG = "ProfileView";

    private ViewGroup mProfileBackground;
    private ImageView mIVAvatar;
    private TextView mTVUsername;
    private TextView mTVLogin;
    private TextView mTVLocation;
    private TextView mTVEmail;
    private TextView mTVAnnouncement;

    public ProfileView(Context context) {
        this(context, null);
    }

    public ProfileView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProfileView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        inflate(context, R.layout.profile_view, this);
        mProfileBackground = findViewById(R.id.ll_profile_background);
        mIVAvatar = findViewById(R.id.iv_avatar);
        mTVUsername = findViewById(R.id.tv_username);
        mTVLogin = findViewById(R.id.tv_login);
        mTVLocation = findViewById(R.id.tv_location);
        mTVEmail = findViewById(R.id.tv_email);
        mTVAnnouncement = findViewById(R.id.tv_announcement);
        OnLoginClickListener listener = new OnLoginClickListener();
        mIVAvatar.setOnClickListener(listener);
        mTVUsername.setOnClickListener(listener);
    }

    public void update() {
        updateProfileInfo();
    }

    private class OnLoginClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            boolean isLogin = GitHub.appComponent().userCenter().isLogin();
            if (v.getId() == R.id.iv_avatar) {
                if (!isLogin) {
                    Router.start(getContext(), "/auth");
                } else {
                    // TODO: 2021/12/26 start MediaPreview
                }
            } else if (v.getId() == R.id.tv_username && !isLogin) {
                Router.start(getContext(), "/auth");
            }
        }
    }


    private void updateProfileInfo() {
        User user = GitHub.appComponent().userCenter().getUser();
        if (user == null) {
            mTVUsername.setText(R.string.sign_in);
            mTVLogin.setVisibility(View.GONE);
            mTVEmail.setVisibility(View.GONE);
            mTVLocation.setVisibility(View.GONE);
            mTVAnnouncement.setVisibility(View.VISIBLE);
            mIVAvatar.setImageResource(com.github.design.R.drawable.ic_github);
            mProfileBackground.setBackgroundResource(R.drawable.side_nav_bar);
        } else {
            mTVLogin.setVisibility(View.VISIBLE);
            mTVEmail.setVisibility(View.VISIBLE);
            mTVLocation.setVisibility(View.VISIBLE);
            mTVAnnouncement.setVisibility(View.GONE);
            RequestBuilder<Bitmap> requestBuilder = Glide.with(this).asBitmap().load(user.getAvatarUrl());
            requestBuilder.into(new CustomViewTarget<View, Bitmap>(mProfileBackground) {
                @Override
                public void onLoadFailed(@Nullable Drawable errorDrawable) {
                }

                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                    Bitmap bitmap;
                    try {
                        bitmap = BitmapUtils.renderScriptBlur(getContext(), resource, 5, 1 / 64f);
                    } catch (Exception exception) {
                        Logan.e(TAG, "failed to blur image", exception);
                        return;
                    }
                    mProfileBackground.setBackground(new BitmapDrawable(getResources(), bitmap));
                }

                @Override
                protected void onResourceCleared(@Nullable Drawable placeholder) {
                }
            });
            requestBuilder.apply(RequestOptions.circleCropTransform()).into(mIVAvatar);
            mTVUsername.setText(user.getName());
            mTVLogin.setText(user.getLogin());
            mTVLocation.setText(user.getLocation());
            mTVEmail.setText(user.getEmail());
        }
    }
}
