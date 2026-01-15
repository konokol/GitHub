package com.ivan.github.web.widget;

import android.graphics.Bitmap;
import android.webkit.WebView;

/**
 * com.ivan.github.webkit.widget.OnPageLoadListener
 *
 * @author Ivan J. Lee on 2021-08-02 22:58
 * @version v0.1
 * @since v1.0
 **/
public interface OnPageLoadListener {

    /**
     * @see android.webkit.WebViewClient#onPageStarted(WebView, String, Bitmap)
     *
     * @param webView The WebView that is initiating the callback.
     * @param url The url to be loaded.
     * @param favicon The favicon for this page if it already exists in the
     *            database.
     */
    void onPageStarted(BifrostWebView webView, String url, Bitmap favicon);

    /**
     * @see android.webkit.WebViewClient#onPageStarted(WebView, String, Bitmap)
     *
     * @param webView The WebView that is initiating the callback.
     * @param url The url of the page.
     */
    void onPageFinished(BifrostWebView webView, String url);

    /**
     * Tell the host application the current progress of loading a page.
     * @param view The WebView that initiated the callback.
     * @param newProgress Current page loading progress, represented by
     *                    an integer between 0 and 100.
     */
    void onProgressChanged(BifrostWebView view, int newProgress);
}
