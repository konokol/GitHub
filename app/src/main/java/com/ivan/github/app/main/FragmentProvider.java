package com.ivan.github.app.main;

import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;

import com.ivan.github.R;
import com.ivan.github.app.homepage.ui.FeedFragment;
import com.ivan.github.app.notification.NotificationFragment;
import com.ivan.github.app.settings.SettingsFragment;

/**
 * com.ivan.github.app.main
 * <p>
 *
 * @author lijun on 2021-12-23 00:29
 * @since v1.0
 */
public class FragmentProvider {

    static Class<? extends Fragment> provideFragment(@IdRes int id) {
        Class<? extends Fragment> fragment = null;
        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        } else if (id == R.id.nav_home) {
            fragment = FeedFragment.class;
        } else if (id == R.id.nav_notification) {
            fragment = NotificationFragment.class;
        } else if (id == R.id.nav_settings) {
            fragment = SettingsFragment.class;
        }
        return fragment;
    }
}
