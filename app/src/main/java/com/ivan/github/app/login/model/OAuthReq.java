package com.ivan.github.app.login.model;

import androidx.annotation.Keep;


/**
 * com.ivan.github.app.login.model.LoginReq
 *
 * @author Ivan J. Lee on 2020-10-24 23:12
 * @version v0.1
 * @since v1.0
 **/
@Keep
public class OAuthReq {
    /**
     * Required. The client ID you received from GitHub for your GitHub App.
     */
    public String client_id;

    /**
     * Required. The client secret you received from GitHub for your GitHub App.
     */
    public String client_secret;

    /**
     * Required. The code you received as a response to Step 1.
     */
    public String code;

    /**
     * The URL in your application where users are sent after authorization.
     */
    public String redirect_uri;

    /**
     * The unguessable random string you provided in Step 1.
     */
    public String state;
}
