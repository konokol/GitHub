package com.ivan.github.web;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import androidx.appcompat.widget.Toolbar;

import com.github.utils.L;
import com.google.android.material.snackbar.Snackbar;
import com.ivan.github.R;
import com.ivan.github.app.BaseActivity;

/**
 * a class used to hold some constant values
 *
 * @author  Ivan at 2017-10-05 17:38
 * @version v0.1
 * @since   v0.1.0
 */

public class WebActivity extends BaseActivity {

    private static final String EXTRA_KEY_URL = "url";
    private static final String EXTRA_KEY_TITLE = "title";

    private Toolbar mToolbar;
    private ProgressBar mProgressBar;
    private WebView mWebView;

    private String mUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        initView();
        initToolbar(mToolbar);
        initData();
    }

    protected void initView() {
        mToolbar = this.findViewById(R.id.toolbar);
        mProgressBar = this.findViewById(R.id.progress_bar);
        mWebView = this.findViewById(R.id.web_view);
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        mWebView.setWebViewClient(new GHWebViewClient());
        mWebView.setWebChromeClient(new GHWebChromeClient());
    }

    protected void initData() {
        Uri data = getIntent().getData();
        if (data == null) {
            finish();
            return;
        }
        mUrl = data.getQueryParameter(EXTRA_KEY_URL);
        String mTitle = data.getQueryParameter(EXTRA_KEY_TITLE);
        if (TextUtils.isEmpty(mTitle)) {
            mTitle = getString(R.string.webview_error);
        }
        setTitle(mTitle);
        if (!TextUtils.isEmpty(mUrl)) {
            mWebView.loadUrl(mUrl);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_web_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.app_bar_refresh:
                mWebView.loadUrl(mWebView.getUrl());
                return true;
            case R.id.app_bar_share:
                Snackbar.make(mWebView, "Not implemented yet!", Snackbar.LENGTH_SHORT).show();
                return true;
            case R.id.app_bar_open:
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mUrl));
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    L.e(TAG, e);
                    Snackbar.make(mWebView, R.string.webview_not_app_found_to_open_the_link, Snackbar.LENGTH_SHORT).show();
                }
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    private static class GHWebViewClient extends BaseWebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return false;
        }
    }

    private class GHWebChromeClient extends BaseWebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            mProgressBar.setProgress(newProgress);
            if (newProgress == 100) {
                mProgressBar.setVisibility(View.GONE);
            } else {
                mProgressBar.setVisibility(View.VISIBLE);
            }
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            if (TextUtils.isEmpty(title)) {
                setTitle(view.getUrl());
            } else {
                setTitle(title);
            }
            super.onReceivedTitle(view, title);
        }

        @Override
        public void onReceivedIcon(WebView view, Bitmap icon) {
            super.onReceivedIcon(view, icon);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mWebView.stopLoading();
    }

    @Override
    protected void onDestroy() {
        mWebView.loadUrl("");
        try {
            mWebView.removeAllViews();
            mWebView.destroy();
        } catch (Exception e) {
            L.printStackTrace(e);
        }
        super.onDestroy();
    }
}
