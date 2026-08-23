package com.github.account

import com.github.account.model.User

interface IUserCenter {

    fun registerLoginStatusChangedListener(listener: ILoginStatusChangedListener?)

    fun unregisterLoginStatusChangedListener(listener: ILoginStatusChangedListener?)

    fun isLogin(): Boolean

    fun getUser(): User?

    fun getUsername(): String

    fun getLogin(): String

    fun logout()

    fun getAuthorization(): String?

    fun init(user: User?, auth: String?)

    fun saveUser(user: User?)

    companion object {
        const val STATUS_LOGIN = 1
        const val STSTUS_LOGOUT = 0
    }
}
