package com.ivan.github.core.net;

import com.ivan.github.GitHub;

import java.util.HashMap;
import java.util.Map;

/**
 * tools to make requests
 *
 * @author  Iavn J. Lee on 2018-11-27 22:53.
 * @version v0.1
 * @since   v1.0
 */
public class HttpClient {

    private static final Map<Class<?>, Object> sRetrofits = new HashMap<>();

    @SuppressWarnings("unchecked")
    public static <S> S service(Class<S> clazz) {
        Object retrofit = sRetrofits.getOrDefault(clazz, null);
        if (retrofit == null) {
            retrofit = GitHub.appComponent().retrofit().create(clazz);
            sRetrofits.put(clazz, retrofit);
        }
        return (S) retrofit;
    }
}
