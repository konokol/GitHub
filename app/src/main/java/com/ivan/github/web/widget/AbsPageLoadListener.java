package com.ivan.github.web.widget;

import android.graphics.Bitmap;

/**
 * com.ivan.github.webkit.widget.AbsPageLoadListener
 *
 * @author Ivan J. Lee on 2021-08-02 23:16
 * @version v0.1
 * @since v1.0
 **/
public class AbsPageLoadListener implements OnPageLoadListener {

    @Override
    public void onPageStarted(BifrostWebView webView, String url, Bitmap favicon) {
    }

    @Override
    public void onPageFinished(BifrostWebView webView, String url) {
    }

    @Override
    public void onProgressChanged(BifrostWebView view, int newProgress) {
    }
}
