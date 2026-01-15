package com.ivan.github.common;

import android.net.Uri;

/**
 * com.ivan.github.common
 * tools to build uri
 * <p>
 *
 * @author Iavn  on 2021-12-23 01:04
 * @since v1.0
 */
public class UriBuilder {

    public static Uri.Builder with(String path){
        return new Uri.Builder()
                .scheme("github")
                .authority("www.github.com")
                .path(path);
    }
}
