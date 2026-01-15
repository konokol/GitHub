package com.ivan.github.common.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

/**
 * com.ivan.github.common.util
 * Gson tools
 * <p>
 *
 * @author lijun on 2022-01-07 01:30
 * @since v1.0
 */
public class GsonUtils {

    private static final Gson sGson = new GsonBuilder().create();

    /**
     * convert JsonObject to Object
     *
     * @param jsonObject jsonObject
     * @param tClass java class
     * @param <T> generic
     * @return Object
     */
    public static <T> T from(JsonObject jsonObject, Class<T> tClass) {
        try {
            return sGson.fromJson(jsonObject, tClass);
        } catch (JsonSyntaxException e) {
            return null;
        }
    }
}
