package com.github.account

import android.text.TextUtils
import com.github.GitHub
import com.github.account.model.User
import com.github.app.GHStorage
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import java.util.concurrent.CopyOnWriteArraySet
import javax.inject.Inject
import javax.inject.Singleton

/**
 * UserCenter implementation using Vault (via GHStorage)
 */
@Singleton
class UserCenterImpl @Inject constructor(
    private val ghStorage: GHStorage
) : IUserCenter {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val storage by lazy { ghStorage.getSecureStorage() }

    private var mUser: User? = null
    private var mAuthorization: String? = null
    private val mStatusChangedListeners = CopyOnWriteArraySet<ILoginStatusChangedListener>()

    init {
        // Load initial state and watch for changes
        scope.launch {
            loadUserInternal()
            
            // Watch for external changes
            storage.watch(USER_DETAIL, User::class.java).collectLatest { user ->
                if (user == null && mUser != null) {
                    mUser = null
                    mAuthorization = null
                    notifyLogout()
                } else if (user != null && mUser == null) {
                    mUser = user
                    notifyLogin()
                }
            }
        }
    }

    override fun registerLoginStatusChangedListener(listener: ILoginStatusChangedListener?) {
        listener?.let { mStatusChangedListeners.add(it) }
    }

    override fun unregisterLoginStatusChangedListener(listener: ILoginStatusChangedListener?) {
        listener?.let { mStatusChangedListeners.remove(it) }
    }

    override fun isLogin(): Boolean = mUser != null && !mAuthorization.isNullOrEmpty()

    override fun isLoginFlow(): Flow<Boolean> = storage.watch(USER_DETAIL, User::class.java)
        .map { it != null }

    override fun getUser(): User? = mUser

    override fun getUsername(): String = mUser?.login ?: ""

    override fun getLogin(): String = mUser?.login ?: ""

    override fun logout() {
        mUser = null
        mAuthorization = null
        scope.launch {
            storage.remove(USER_DETAIL)
            storage.remove(AUTH_KEY)
        }
        notifyLogout()
    }

    override fun getAuthorization(): String? {
        return mAuthorization
    }

    override fun init(user: User?, auth: String?) {
        this.mUser = user
        this.mAuthorization = auth
        scope.launch {
            storage.put(AUTH_KEY, auth ?: "")
            if (user != null) {
                storage.put(USER_DETAIL, user)
            }
        }
    }

    override fun saveUser(user: User?) {
        this.mUser = user
        scope.launch {
            if (user != null) {
                storage.put(USER_DETAIL, user)
            } else {
                storage.remove(USER_DETAIL)
            }
        }
    }

    /**
     * Called by UserCenterInit to load user data on startup.
     */
    fun loadUser() {
        scope.launch {
            loadUserInternal()
        }
    }

    private suspend fun loadUserInternal() {
        val userResult = storage.get(USER_DETAIL, User::class.java)
        mUser = userResult.getOrNull()
        
        val authResult = storage.get(AUTH_KEY, String::class.java)
        mAuthorization = authResult.getOrNull()
    }

    private fun notifyLogin() {
        mStatusChangedListeners.forEach { it.onStatusChanged(mUser, IUserCenter.STATUS_LOGIN) }
    }

    private fun notifyLogout() {
        mStatusChangedListeners.forEach { it.onStatusChanged(null, IUserCenter.STATUS_LOGOUT) }
    }

    companion object {
        private const val USER_DETAIL = "user_detail"
        private const val AUTH_KEY = "auth_key"

        @JvmStatic
        fun getInstance(): UserCenterImpl {
            // For legacy Java access
            return GitHub.appComponent().userCenter() as UserCenterImpl
        }
    }
}
