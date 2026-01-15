package com.ivan.github.app.login;

import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.github.design.widget.LoadingView;
import com.github.log.Logan;
import com.ivan.github.R;
import com.ivan.github.account.UserCenterImpl;
import com.ivan.github.api.OAuthService;
import com.ivan.github.app.ToolbarActivity;
import com.ivan.github.app.login.model.OAuthReq;
import com.ivan.github.app.login.model.OAuthResp;
import com.ivan.github.core.net.HttpClient;
import com.ivan.github.core.net.TransformerHelper;
import com.ivan.github.web.widget.AbsPageLoadListener;
import com.ivan.github.web.widget.BifrostWebView;

import java.util.UUID;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import okhttp3.HttpUrl;

/**
 * Adapter for Splash
 *
 * @author  Ivan at 2020-10-12  22:09
 * @version v1.0
 * @since   v0.1.0
 */
public class OAuthActivity extends ToolbarActivity implements View.OnClickListener {

    private static final String TAG = "OAuthActivity";

    private LoadingView mLoadingView;
    private BifrostWebView mWebView;

    private String mUrl;
    private String mTarget;

    private static final String clientId = "08d9cad09d2e1745edb4";
    private static final String clientSecret = "3943633750719013ae8208f6f001951566328218";

    private static final String sOAuthPath = "/oauth/redirect";

    @Override
    protected int getContentView() {
        return R.layout.activity_oauth;
    }

    @Override
    protected void onPreCreateView() {
        super.onPreCreateView();
        setNavigationBarColor(R.color.colorPrimaryDark);
    }

    private void resolveParams() {
        Uri uri = getIntent().getData();
        if (uri != null) {
            mTarget = uri.getQueryParameter("target");
        } else {
            mTarget = getIntent().getStringExtra("target");
        }
        if (TextUtils.isEmpty(mTarget)) {
            mTarget = "/homepage";
        }
    }

    @Override
    protected void onPostCreateView() {
        super.onPostCreateView();
        resolveParams();
        mLoadingView = findViewById(R.id.lv_loading);
        mWebView = findViewById(R.id.web_view);
        mWebView.setEmptyViewClickListener(this);
        mWebView.setPageLoadListener(new OAuthPageLoadListener(new OAuthRedirectHandler()));
        mUrl = new HttpUrl.Builder()
                .scheme("https")
                .host("github.com")
                .addPathSegments("login/oauth/authorize")
                .addQueryParameter("client_id", clientId)
                .addQueryParameter("scope", "user,repo,workflow")
                .build()
                .toString();
        mWebView.loadUrl(mUrl);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_oauth_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.app_bar_refresh) {
            showLoading();
            mWebView.loadUrl(mUrl);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mLoadingView.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mWebView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mWebView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mLoadingView.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebView.destroy();
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
            Logan.i(TAG, "goBack");
        } else {
            clearWebViewCache();
            super.onBackPressed();
        }
    }

    public void clearWebViewCache() {
        mWebView.clear();
    }

    private void showLoading() {
        if (mLoadingView != null) {
            mLoadingView.setVisibility(View.VISIBLE);
            mLoadingView.start();
        }
    }

    private void dismissLoading() {
        if (mLoadingView != null) {
            mLoadingView.setVisibility(View.GONE);
            mLoadingView.stop();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.empty_view) {
            showLoading();
            mWebView.reload();
        }
    }

    private class OAuthPageLoadListener extends AbsPageLoadListener {

        private final AbsUriHandler handler;

        public OAuthPageLoadListener(AbsUriHandler handler) {
            this.handler = handler;
        }

        @Override
        public void onPageStarted(BifrostWebView webView, String url, Bitmap favicon) {
            mLoadingView.start();
        }

        @Override
        public void onPageFinished(BifrostWebView webView, String url) {
            mLoadingView.setVisibility(View.GONE);
            mLoadingView.stop();
            if (handler != null) {
                handler.handleInternal(Uri.parse(url));
            }
        }
    }

    private class OAuthRedirectHandler extends AbsUriHandler {

        public OAuthRedirectHandler() {
            next(new ErrorHandler());
        }

        @Override
        public boolean shouldHandle(Uri uri) {
            return super.shouldHandle(uri) &&
                    sOAuthPath.equals(uri.getPath());
        }

        @Override
        protected void onHandle(Uri uri) {
            String code = uri.getQueryParameter("code");
            String error = uri.getQueryParameter("error");
            if (!TextUtils.isEmpty(error)) {
                Toast.makeText(OAuthActivity.this, error, Toast.LENGTH_SHORT).show();
                Logan.e(TAG, "login failed: " + error);
            } else {
                final OAuthReq req = new OAuthReq();
                req.client_id = clientId;
                req.client_secret = clientSecret;
                req.code = code;
                req.state = UUID.randomUUID().toString();
                final OAuthResp[] auth = new OAuthResp[1];
                Disposable d = HttpClient.service(OAuthService.class)
                        .oauth(req)
                        .compose(TransformerHelper.result())
                        .flatMap(oAuthResp -> {
                            auth[0] = oAuthResp;
                            if (oAuthResp == null || TextUtils.isEmpty(oAuthResp.access_token)) {
                                return Observable.error(
                                        new IllegalArgumentException("log in failed: #0001"));
                            } else {
                                return HttpClient.service(OAuthService.class)
                                        .getUser(oAuthResp.access_token);
                            }
                        })
                        .compose(TransformerHelper.schedulers())
                        .compose(TransformerHelper.result())
                        .doOnSubscribe(disposable -> showLoading())
                        .doOnComplete(OAuthActivity.this::dismissLoading)
                        .doOnDispose(OAuthActivity.this::dismissLoading)
                        .doOnError(throwable -> dismissLoading())
                        .subscribe(user -> {
                            UserCenterImpl.getInstance().init(user, auth[0].access_token);
                            UserCenterImpl.getInstance().saveUser(user);
                            start(mTarget);
                            setResult(LoginConst.RESULT_CODE_OAUTH_SUCCESS);
                            finish();
                        }, throwable -> {
                            dismissLoading();
                            showToast(throwable.getLocalizedMessage());
                            mWebView.showError(String.format("%s\n%s",
                                    throwable.getLocalizedMessage(),
                                    getString(R.string.tap_to_retry)));
                        });
                addDisposable(d);
            }
        }
    }

    private class ErrorHandler extends AbsUriHandler {

        @Override
        protected void onHandle(Uri uri) {
            String err = uri.getQueryParameter("error");
            if (TextUtils.isEmpty(err)) {
                err = "login failed";
            }
            Toast.makeText(OAuthActivity.this, err, Toast.LENGTH_LONG).show();
        }
    }

}