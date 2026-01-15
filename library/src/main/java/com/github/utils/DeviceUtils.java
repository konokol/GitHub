package com.github.utils;

import android.os.Build;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * com.github.utils
 * DeviceUtils
 * <p>
 *
 * @author Ivan J. Lee on 2022-01-05 23:30
 * @since v1.0
 */
public class DeviceUtils {

    private static String md5(String message) {
        if (message == null) {
            return "";
        }
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(message.getBytes());
            StringBuilder result = new StringBuilder();
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result.append(temp);
            }
            return result.toString().toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getDeviceId() {
        String deviceInfo = Build.BOARD +
                Build.BRAND +
                Build.DEVICE +
                Build.DISPLAY +
                Build.HOST +
                Build.ID +
                Build.MANUFACTURER +
                Build.MODEL +
                Build.PRODUCT +
                Build.TAGS +
                Build.TYPE +
                Build.USER;
        String md5 = md5(deviceInfo);
        return md5;
    }
}
