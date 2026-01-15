package com.ivan.github;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.Test;

import java.util.Arrays;

/**
 * test json format
 *
 * @author  Ivan on 2018-11-22 23:56.
 * @version v0.1
 * @since   v0.1.0
 */
public class TestJson {

    private static final Gson gson = new GsonBuilder().create();

    @Test
    public void testJson() {
        Resp resp = new Resp();
        resp.data = Arrays.asList("Mon", "Tue", "Wen", "Thu", "Fri", "Sat", "Sun");
        String json = gson.toJson(resp);
        System.out.println(json);
    }

    @Test
    public void testJsonError() {
        Resp error = new Resp();
        error.message = "Requires authentication";
        error.documentation_url = "https://developer.github.com/v3";
        String json = gson.toJson(error);
        System.out.println(json);
    }
}
