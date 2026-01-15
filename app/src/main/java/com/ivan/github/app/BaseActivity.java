package com.ivan.github.app;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.AnimRes;
import androidx.annotation.ColorRes;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.log.Logan;
import com.google.android.material.appbar.AppBarLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.ivan.github.R;
import com.ivan.github.common.Router;
import com.ivan.github.common.UriBuilder;

import java.util.Map;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

/**
 * BaseActivity
 *
 * @author Ivan
 */
public class BaseActivity extends AppCompatActivity {

    protected static final String TAG = BaseActivity.class.getName();

    private CompositeDisposable mDisposables;

    protected void start(String path, @AnimRes int enterAnim, @AnimRes int exitAnim) {
        Intent intent = new Intent(Intent.ACTION_VIEW, UriBuilder.with(path).build());
        intent.setPackage(getPackageName());
        start(intent, enterAnim, exitAnim);
    }

    protected void start(@NonNull Intent intent, @AnimRes int enterAnim, @AnimRes int exitAnim) {
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeCustomAnimation(this, enterAnim, exitAnim);
        try {
            ActivityCompat.startActivity(this, intent, optionsCompat.toBundle());
        } catch (ActivityNotFoundException exception) {
            Logan.e(TAG, "failed to start Activity", exception);
        }
    }

    protected void start(String path) {
        start(path, null);
    }

    protected void start(String path, Map<String, String> params) {
        Router.start(this, path, params);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onPreCreateView();
        setContentViewInternal();
        onPostCreateView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearDisposables();
    }

    protected void onPreCreateView() {}

    protected void onPostCreateView() {
    }

    private void setContentViewInternal() {
        int contentId = getContentView();
        if (contentId != View.NO_ID) {
            this.setContentView(contentId);
        }
    }

    protected @LayoutRes int getContentView() {
        return View.NO_ID;
    }

    protected void initToolbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        AppBarLayout appBarLayout = findViewById(R.id.appbar_layout);
        if (appBarLayout != null) {
            appBarLayout.setStateListAnimator(null);
            ViewCompat.setElevation(appBarLayout, 8);
        }
    }

    protected void setNavigationBarColor(@ColorRes int color) {
        getWindow().setNavigationBarColor(getResources().getColor(color));
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
    }

    protected void initToolbar(@IdRes int id) {
        Toolbar toolbar = findViewById(id);
        initToolbar(toolbar);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                onBackPressed();
                break;
            case KeyEvent.KEYCODE_MENU:
                onMenuPressed();
                break;
            case KeyEvent.KEYCODE_HOME:
                onHomePressed();
                break;
            default:
        }
        return super.onKeyDown(keyCode, event);
    }

    public void onMenuPressed() {}

    public void onHomePressed() {}

    protected void addDisposable(Disposable disposable) {
        if (mDisposables == null) {
            mDisposables = new CompositeDisposable();
        }
        mDisposables.add(disposable);
    }

    private void clearDisposables() {
        if (mDisposables != null) {
            mDisposables.clear();
        }
    }

    protected void showToast(String msg) {
        if (msg != null) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
    }
}
