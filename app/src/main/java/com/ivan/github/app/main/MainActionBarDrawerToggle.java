package com.ivan.github.app.main;

import android.animation.ArgbEvaluator;
import android.app.Activity;
import android.view.View;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.ivan.github.R;
import com.ivan.github.common.util.StatusBarUtils;

/**
 * com.ivan.github.app.main
 * <p>
 * MainActionBarDrawerToggle
 *
 * @author Ivan J. Lee on 2023-02-02 22:39
 * @since v1.0
 */
public class MainActionBarDrawerToggle extends ActionBarDrawerToggle {

    private final Activity activity;

    public MainActionBarDrawerToggle(Activity activity, DrawerLayout drawerLayout, Toolbar toolbar) {
        super(activity, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        this.activity = activity;
    }

    @Override
    public void onDrawerOpened(View drawerView) {
        super.onDrawerOpened(drawerView);
        StatusBarUtils.setStatusBarColorRes(activity, R.color.transparent);
    }

    @Override
    public void onDrawerClosed(View drawerView) {
        super.onDrawerClosed(drawerView);
        StatusBarUtils.setStatusBarColorRes(activity, R.color.colorPrimaryDark);
    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {
        super.onDrawerSlide(drawerView, slideOffset);
        int startColor = activity.getResources().getColor(R.color.colorPrimaryDark);
        int endColor = activity.getResources().getColor(R.color.transparent);
        int color = (int) new ArgbEvaluator().evaluate(slideOffset, startColor, endColor);
        StatusBarUtils.setStatusBarColor(activity, color);
    }
}
