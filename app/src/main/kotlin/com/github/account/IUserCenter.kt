package com.github.account

import com.github.account.model.User
import kotlinx.coroutines.flow.Flow

/**
 * User center interface for managing user login status and information.
 */
interface IUserCenter {

    companion object {
        const val STATUS_LOGIN = 1
        const val STATUS_LOGOUT = 0
    }

    /**
     * Check if user is logged in.
     */
    fun isLogin(): Boolean

    /**
     * Reactive login status.
     */
    fun isLoginFlow(): Flow<Boolean>

    /**
     * Get current logged-in user.
     */
    fun getUser(): User?

    /**
     * Get username of current user.
     */
    fun getUsername(): String

    /**
     * Get login name of current user.
     */
    fun getLogin(): String

    /**
     * Get authorization token.
     */
    fun getAuthorization(): String?

    /**
     * Logout current user and clear data.
     */
    fun logout()

    /**
     * Initialize user center with user info and auth token.
     */
    fun init(user: User?, auth: String?)

    /**
     * Save/Update user info.
     */
    fun saveUser(user: User?)

    /**
     * Register login status listener.
     */
    fun registerLoginStatusChangedListener(listener: ILoginStatusChangedListener?)

    /**
     * Unregister login status listener.
     */
    fun unregisterLoginStatusChangedListener(listener: ILoginStatusChangedListener?)
}
