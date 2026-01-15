package com.ivan.github.account;

import android.content.SharedPreferences;
import android.text.TextUtils;

import com.ivan.github.GitHub;
import com.ivan.github.account.model.User;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * UserCenter implementation
 */
public class UserCenterImpl implements IUserCenter, SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String USER_DETAIL = "user_detail";
    private static final String AUTH_KEY = "auth_key";

    private static volatile UserCenterImpl mUserCenter;

    private User mUser;
    private String mAuthorization;
    private final Set<ILoginStatusChangedListener> mStatusChangedListeners;
    private final SharedPreferences mPreference;


    private UserCenterImpl() {
        mStatusChangedListeners = new CopyOnWriteArraySet<>();
        mPreference = GitHub.appComponent().secureSharedPreference();
        mPreference.registerOnSharedPreferenceChangeListener(this);
    }

    public static UserCenterImpl getInstance() {
        if (mUserCenter == null) {
            synchronized (UserCenterImpl.class) {
                if (mUserCenter == null) {
                    mUserCenter = new UserCenterImpl();
                }
            }
        }
        return mUserCenter;
    }

    public void init(User user, String auth) {
        this.mUser = user;
        this.mAuthorization = auth;
        GitHub.appComponent().secureSharedPreference().edit().putString(AUTH_KEY, auth).apply();
    }

    public void saveUser(User user) {
        mUser = user;
        if (mUser != null) {
            String userStr = GitHub.appComponent().gson().toJson(mUser);
            mPreference.edit()
                    .putString(USER_DETAIL, userStr)
                    .apply();
        } else {
            mPreference.edit()
                    .putString(USER_DETAIL, null)
                    .apply();
        }
    }

    public void loadUser() {
        String user = mPreference.getString(USER_DETAIL, null);
        if (!TextUtils.isEmpty(user)) {
            mAuthorization = mPreference.getString(AUTH_KEY, "");
            mUser = GitHub.appComponent().gson().fromJson(user, User.class);
        }
    }

    @Override
    public void logout() {
        mUser = null;
        mAuthorization = null;
        mPreference.edit().putString(USER_DETAIL, null).putString(AUTH_KEY, null).apply();
    }

    @Override
    public String getAuthorization() {
        if (TextUtils.isEmpty(mAuthorization)) {
            mAuthorization = mPreference.getString(AUTH_KEY, "");
        }
        return mAuthorization;
    }

    @Override
    public void registerLoginStatusChangedListener(ILoginStatusChangedListener listener) {
        if (listener != null) {
            mStatusChangedListeners.add(listener);
        }
    }

    @Override
    public void unregisterLoginStatusChangedListener(ILoginStatusChangedListener listener) {
        if (listener != null) {
            mStatusChangedListeners.remove(listener);
        }
    }

    private void notifyLogin() {
        for (ILoginStatusChangedListener listener : mStatusChangedListeners) {
            listener.onStatusChanged(mUser, STATUS_LOGIN);
        }
    }

    private void notifyLogout() {
        for (ILoginStatusChangedListener listener : mStatusChangedListeners) {
            listener.onStatusChanged(null, STSTUS_LOGOUT);
        }
    }

    @Override
    public boolean isLogin() {
        return mUser != null && mAuthorization != null;
    }

    @Override
    public User getUser() {
        return mUser;
    }

    @Override
    public String getUsername() {
        if (mUser == null) {
            return "";
        } else {
            return mUser.login;
        }
    }

    @Override
    public String getLogin() {
        if (mUser == null) {
            return "";
        } else {
            return mUser.login;
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (USER_DETAIL.equals(key)) {
            if (mUser == null) {
                notifyLogout();
            } else {
                notifyLogin();
            }
        }
    }
}
