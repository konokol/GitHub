package com.ivan.github.app.login;

import android.net.Uri;

/**
 * com.ivan.github.app.login.AbsUriHandler
 *
 * @author Ivan J. Lee on 2020-10-24 23:54
 * @version v0.1
 * @since v1.0
 **/
public abstract class AbsUriHandler implements UriHandler {

    private AbsUriHandler next;

    public AbsUriHandler next(AbsUriHandler handler) {
        this.next = handler;
        return next;
    }

    @Override
    public boolean shouldHandle(Uri uri) {
        return "github".equals(uri.getScheme());
    }

    @Override
    public boolean handleInternal(Uri uri) {
        if (shouldHandle(uri)) {
            onHandle(uri);
            return true;
        } else if (next != null) {
            return next.handleInternal(uri);
        } else {
            return false;
        }
    }

    protected abstract void onHandle(Uri uri);
}
