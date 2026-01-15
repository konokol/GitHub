package com.ivan.github.web.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.webkit.CookieManager;
import android.webkit.RenderProcessGoneDetail;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.github.design.widget.EmptyView;
import com.github.log.Logan;
import com.ivan.github.R;
import com.ivan.github.web.BaseWebChromeClient;
import com.ivan.github.web.BaseWebViewClient;

import java.util.Map;

/**
 * com.ivan.github.webkit.widget.BifrostWebView
 *
 * @author  Ivan J. Lee on 2021-07-31 23:25
 * @version v0.1
 * @since   v1.0
 **/
public class BifrostWebView extends FrameLayout implements View.OnClickListener {

    private static final String TAG = "BifrostWebView";

//    private static final String PAGE_BLANK = "about:blank";

    private WebView mWebView;
    private EmptyView mEmptyView;
    private ViewStub mViewStub;
    private View mSslErrorView;
    private TextView mTVErrorMsg;
    private TextView mTVErrorCodeDetail;
    private Button mBtnSslProcess;
    private Button mBtnSslCancel;

    private View.OnClickListener mEmptyViewClickListener;
    private OnPageLoadListener mPageLoadListener;

    public BifrostWebView(@NonNull Context context) {
        this(context, null);
    }

    public BifrostWebView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BifrostWebView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public BifrostWebView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.bifrost_webview, this, true);
        mEmptyView = findViewById(R.id.empty_view);
        mViewStub = findViewById(R.id.ssl_error_view);
        mWebView = new WebView(context);
        addView(mWebView, 0);
        WebSettings settings = mWebView.getSettings();
        if (settings != null) {
            settings.setSupportZoom(false);
            settings.setBuiltInZoomControls(true);
            settings.setDisplayZoomControls(false);
            settings.setUseWideViewPort(true);
            settings.setLoadWithOverviewMode(true);
            settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
            settings.setCacheMode(WebSettings.LOAD_DEFAULT);
            settings.setJavaScriptEnabled(true);
        }
        mEmptyView.setOnClickListener(this);
        mWebView.setWebViewClient(new InnerWebViewClient());
        mWebView.setWebChromeClient(new InnerWebChromeClient());
    }

    private void showEmptyView() {
        mEmptyView.setVisibility(View.VISIBLE);
        mWebView.setVisibility(View.GONE);
    }

    private void hideEmptyView() {
        mEmptyView.setVisibility(View.GONE);
        mWebView.setVisibility(View.VISIBLE);
    }

    public void loadUrl(String url) {
        mEmptyView.setVisibility(View.INVISIBLE);
        mWebView.loadUrl(url);
    }

    public void loadUrl(String url, Map<String, String> additionalHttpHeaders) {
        mEmptyView.setVisibility(View.INVISIBLE);
        mWebView.loadUrl(url, additionalHttpHeaders);
    }

    public boolean canGoBack() {
        return mWebView.canGoBack();
    }

    public boolean canGoForward() {
        return mWebView.canGoForward();
    }

    public void reload() {
        mEmptyView.setVisibility(View.INVISIBLE);
        mWebView.reload();
    }

    public void goForward() {
        mWebView.goForward();
    }

    public void goBack() {
        mWebView.goBack();
    }

    public void showError(String errMsg) {
        mEmptyView.setMessage(errMsg);
        showEmptyView();
    }

    public void setPageLoadListener(OnPageLoadListener pageLoadListener) {
        this.mPageLoadListener = pageLoadListener;
    }

    public void setEmptyViewClickListener(View.OnClickListener listener) {
        this.mEmptyViewClickListener = listener;
    }

    public void onResume() {
        mWebView.onResume();
    }

    public void onPause() {
        mWebView.onPause();
    }

    public void destroy() {
        removeView(mWebView);
        mWebView.stopLoading();
        mWebView.removeAllViews();
        mWebView.destroyDrawingCache();
        mWebView.destroy();
    }

    public void clear() {
        mWebView.clearCache(true);
        CookieManager.getInstance().removeAllCookies(null);
        CookieManager.getInstance().flush();
        WebStorage.getInstance().deleteAllData();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == mEmptyView.getId()) {
            onClickEmptyView(v);
        }
    }

    private void onClickEmptyView(View view) {
        if (mEmptyViewClickListener != null) {
            mEmptyViewClickListener.onClick(view);
        }
    }

    private class InnerWebViewClient extends BaseWebViewClient {

        private String message = null;

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            if (mPageLoadListener != null) {
                mPageLoadListener.onPageStarted(BifrostWebView.this, url, favicon);
            }
            message = null;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            Logan.d(TAG, "onPageFinished");
            if (mPageLoadListener != null) {
                mPageLoadListener.onPageFinished(BifrostWebView.this, url);
            }
            if (TextUtils.isEmpty(message)) {
                hideEmptyView();
            }
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            this.onReceivedError(errorCode,
                    getContext().getString(R.string.webview_page_error_title),
                    description);
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            this.onReceivedError(error.getErrorCode(),
                    getContext().getString(R.string.webview_page_error_title),
                    error.getDescription());
        }

        private void onReceivedError(int code, CharSequence title, CharSequence desc) {
            message = getContext().getString(R.string.webview_page_error_msg, code, desc);
            Logan.w(TAG, "onReceivedError: " + message);
            mEmptyView.setTitle(title);
            mEmptyView.setMessage(message);
            showEmptyView();
        }

        @Override
        public void onReceivedHttpError(WebView view, WebResourceRequest request,
                                        WebResourceResponse errorResponse) {
            message = getContext().getString(R.string.webview_http_error_msg,
                    errorResponse.getStatusCode(), errorResponse.getReasonPhrase());
            mEmptyView.setMessage(message);
            mEmptyView.setTitle(R.string.webview_http_error_title);
            Logan.w(TAG, "onReceivedHttpError: " + message);
            showEmptyView();
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            Logan.v(TAG, "onReceivedSslError: " + error.toString());
            if (mSslErrorView == null) {
                mSslErrorView = mViewStub.inflate();
                mTVErrorMsg = mSslErrorView.findViewById(R.id.ssl_error_msg);
                mTVErrorCodeDetail = mSslErrorView.findViewById(R.id.ssl_error_code_detail);
                mBtnSslProcess = mSslErrorView.findViewById(R.id.btn_process);
                mBtnSslCancel = mSslErrorView.findViewById(R.id.btn_cancel);
            }

            mBtnSslProcess.setOnClickListener(v -> {
                handler.proceed();
                hideEmptyView();
            });
            mBtnSslCancel.setOnClickListener(v -> {
                handler.cancel();
            });
            message = getContext().getString(R.string.webview_ssl_error_code,
                    error.getPrimaryError(), error.getCertificate().toString());
            mTVErrorMsg.setText(getContext().getString(R.string.webview_ssl_error_desc, error.getUrl()));
            mTVErrorCodeDetail.setText(message);
            mSslErrorView.setVisibility(View.VISIBLE);
        }

        @Override
        public boolean onRenderProcessGone(WebView view, RenderProcessGoneDetail detail) {
            mEmptyView.setMessage(R.string.webview_render_progress_gone);
            showEmptyView();
            return true;
        }
    }

    private class InnerWebChromeClient extends BaseWebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (mPageLoadListener != null) {
                mPageLoadListener.onProgressChanged(BifrostWebView.this, newProgress);
            }
        }
    }

}
