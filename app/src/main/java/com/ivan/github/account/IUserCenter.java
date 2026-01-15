package com.ivan.github.account;

import com.ivan.github.account.model.User;

public interface IUserCenter {

    int STATUS_LOGIN = 1;

    int STSTUS_LOGOUT = 0;

    void registerLoginStatusChangedListener(ILoginStatusChangedListener listener);

    void unregisterLoginStatusChangedListener(ILoginStatusChangedListener listener);

    boolean isLogin();

    User getUser();

    String getUsername();

    String getLogin();

    void logout();

    String getAuthorization();

}
