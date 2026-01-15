package com.ivan.github.app.login;

import android.net.Uri;

/**
 * com.ivan.github.app.login.UriHandler
 *
 * @author Ivan J. Lee on 2020-10-24 23:53
 * @version v0.1
 * @since v1.0
 **/
public interface UriHandler {

    boolean shouldHandle(Uri uri);

    boolean handleInternal(Uri uri);
}
