package com.github.core.net

import com.github.GitHub
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

/**
 * Handle 401 Unauthorized responses to clear user session.
 */
class TokenAuthenticator : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        // If we get a 401, it means our token is no longer valid.
        if (response.code == 401) {
            // Logout the user to clear the invalid token and force re-login.
            GitHub.appComponent().userCenter().logout()
        }
        // Return null to stop retrying.
        return null
    }
}
