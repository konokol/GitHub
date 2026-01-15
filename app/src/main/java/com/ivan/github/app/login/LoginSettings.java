package com.ivan.github.app.login;

import com.github.utils.CollectionUtils;
import com.ivan.github.BuildConfig;
import com.ivan.github.GitHub;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * login setting saved on SharedPreference
 *
 * @author Ivan on 2018-11-28 23:02.
 * @version v0.1
 * @since v0.1.0
 */
public class LoginSettings {

    private static final String PREFIX = BuildConfig.APPLICATION_ID;

    private static final String KEY_RECENT_ACCOUNTS = PREFIX + ".recent_accounts";
    private static final String KEY_PASSWORD = PREFIX + ".password#";

    public static void saveUser(String email) {
        Set<String> recentAccounts = getRecentUsers();
        recentAccounts.remove(email);
        recentAccounts.add(email);
        GitHub.appComponent().preference().edit().putStringSet(KEY_RECENT_ACCOUNTS, recentAccounts).apply();
    }

    static void saveUser(String email, String password) {
        Set<String> recentAccounts = getRecentUsers();
        recentAccounts.remove(email);
        recentAccounts.add(email);
        GitHub.appComponent().preference().edit().putStringSet(KEY_RECENT_ACCOUNTS, recentAccounts).apply();
        GitHub.appComponent().secureSharedPreference().edit().putString(KEY_PASSWORD + email, password).apply();
    }

    static String getSavedPassword(String email) {
        return GitHub.appComponent().secureSharedPreference().getString(KEY_PASSWORD + email, "");
    }

    static Set<String> getRecentUsers() {
        Set<String> recentAccounts = GitHub.appComponent().preference().getStringSet(KEY_RECENT_ACCOUNTS, Collections.emptySet());
        if (CollectionUtils.isEmpty(recentAccounts)) {
            recentAccounts = new LinkedHashSet<>();
        }
        return recentAccounts;
    }
}
