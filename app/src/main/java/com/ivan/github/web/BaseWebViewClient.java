package com.ivan.github.web;

import android.net.Uri;
import android.net.http.SslError;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.github.log.Logan;

/**
 * WebViewClient for the WebViews
 *
 * @author  Ivan J. Lee on 2017/10/5 20:35.
 * @version v0.1
 * @since   v1.0
 */

public class BaseWebViewClient extends WebViewClient {

    private static final String TAG = "BaseWebViewClient";

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        Logan.v(TAG, request.getUrl().toString());
        return shouldOverrideUrlLoading(view, request.getUrl());
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Uri uri = Uri.parse(url);
        return shouldOverrideUrlLoading(view, uri);
    }

    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        super.onReceivedError(view, request, error);
    }

    @Override
    public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
        super.onReceivedHttpError(view, request, errorResponse);
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        super.onReceivedSslError(view, handler, error);
    }

    public boolean shouldOverrideUrlLoading(WebView view, Uri uri) {
        return false;
    }
}
