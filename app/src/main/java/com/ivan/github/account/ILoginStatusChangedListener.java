package com.ivan.github.account;

import com.ivan.github.account.model.User;

/**
 * notify user status changed
 */
public interface ILoginStatusChangedListener {

    /**
     * Login status changed callback
     * @param user user
     * @param status 0 logout, 1 login
     */
    void onStatusChanged(User user, int status);
}
