package com.ivan.github.app.login;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.github.design.widget.CirclePagerIndicator;
import com.ivan.github.R;
import com.ivan.github.app.AppSettings;
import com.ivan.github.app.BaseActivity;
import com.ivan.github.app.login.model.SplashData;
import com.ivan.github.common.UriBuilder;
import com.ivan.github.core.BaseConfig;
import com.ivan.github.common.UrlConst;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ivan
 */
public class LandingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        setNavigationBarColor(R.color.colorPrimaryDark);
        initView();
        AppSettings.setVersion(BaseConfig.versionCode);
    }

    private void initView() {
        ViewPager mViewPager = findViewById(R.id.view_pager);
        SplashPageAdapter adapter = new SplashPageAdapter(this, getSplashData());
        mViewPager.setAdapter(adapter);
        CirclePagerIndicator indicator = findViewById(R.id.indicator);
        indicator.setViewPager(mViewPager);
    }

    private List<SplashData> getSplashData() {
        List<SplashData> result = new ArrayList<>();

        String uriPrefix = "android.resource://"+getPackageName()+"/mipmap/";

        SplashData d1 = new SplashData();
        d1.uri = uriPrefix + getString(R.string.splash_image_01);
        d1.title = getString(R.string.splash_title_01);
        d1.description = getString(R.string.splash_dec_01);
        result.add(d1);

        SplashData d2 = new SplashData();
        d2.uri = uriPrefix + getString(R.string.splash_image_02);
        d2.title = getString(R.string.splash_title_02);
        d2.description = getString(R.string.splash_dec_02);
        result.add(d2);

        SplashData d3 = new SplashData();
        d3.uri = uriPrefix + getString(R.string.splash_image_03);
        d3.title = getString(R.string.splash_title_03);
        d3.description = getString(R.string.splash_dec_03);
        result.add(d3);

        SplashData d4 = new SplashData();
        d4.uri = uriPrefix + getString(R.string.splash_image_04);
        d4.title = getString(R.string.splash_title_04);
        d4.description = getString(R.string.splash_dec_04);
        result.add(d4);

        return result;
    }

    public void signIn(View view) {
        Intent intent = new Intent(this, OAuthActivity.class);
//        ActivityOptionsCompat activityOptions= ActivityOptionsCompat.makeSceneTransitionAnimation(this);
        startActivityForResult(intent, LoginConst.REQUEST_CODE_OAUTH);
    }

    public void signUp(View view) {
        Uri uri = UriBuilder.with("/web")
                .appendQueryParameter("url", UrlConst.GITHUB_REGISTER)
                .appendQueryParameter("title", getString(R.string.join_github))
                .build();
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }

    public void explore(View view) {
        Uri uri = UriBuilder.with("/homepage").build();
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LoginConst.REQUEST_CODE_LOGIN && resultCode == LoginConst.RESULT_CODE_FINISH) {
            this.finish();
        } else if (requestCode == LoginConst.REQUEST_CODE_OAUTH && resultCode == LoginConst.RESULT_CODE_OAUTH_SUCCESS) {
            this.finish();
        }
    }
}
