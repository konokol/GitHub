package com.ivan.github.app.main;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.github.log.Logan;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.ivan.github.R;
import com.ivan.github.app.BaseActivity;
import com.ivan.github.app.main.view.ProfileView;
import com.ivan.github.common.util.StatusBarUtils;
import com.ivan.github.widget.BridgeActionProvider;

/**
 * Homepage of the App
 *
 * @author Ivan
 */
public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";

    private BridgeActionProvider mBridgeActionProvider;
    private DrawerLayout mDrawer;
    private boolean mExitNow = false;
    private final Handler mMainHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new MainActionBarDrawerToggle(this, mDrawer, toolbar);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setPadding(0, StatusBarUtils.getStatusBarHeight(this), 0, 0);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        ProfileView profileView = headerView.findViewById(R.id.nav_profile_view);
        profileView.update();
        switchFragment(R.id.nav_home);
    }

    @Override
    public void onBackPressed() {
        Logan.d(TAG, "onBackPress");
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem menuItem = menu.findItem(R.id.action_notification);
        mBridgeActionProvider = (BridgeActionProvider) MenuItemCompat.getActionProvider(menuItem);
        return true;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        mBridgeActionProvider.setIcon(R.drawable.ic_menu_notification);
        mBridgeActionProvider.setBridgeRes(R.drawable.ic_dot_blue);
//        mBridgeActionProvider.setBridgeNum(99);
        mBridgeActionProvider.setOnClickListener(v -> Toast.makeText(MainActivity.this, "notification action clicked", Toast.LENGTH_SHORT).show());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            switchFragment(R.id.nav_settings);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switchFragment(id);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void switchFragment(@IdRes int id) {
        Class<? extends Fragment> fClass = FragmentProvider.provideFragment(id);
        if (fClass == null) {
            return;
        }
        String fName = fClass.getName();
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentByTag(fName);
        if (fragment == null) {
            fragment = fm.getFragmentFactory().instantiate(getClassLoader(), fClass.getName());
            fm.beginTransaction().replace(R.id.content_main, fragment, fName).commit();
        } else {
            fm.beginTransaction().show(fragment).commit();
        }
    }
}
