package com.github.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;

import com.github.log.Logan;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * secure sharedPreference
 *
 * @author  Ivan on 2018-12-06 23:24.
 * @version v0.1
 * @since   v0.1.0
 */
public class SecureSharedPreference implements SharedPreferences {

    private static final String TAG = SecureSharedPreference.class.getSimpleName();

    private SharedPreferences sharedPreferences;
    private String secretKey;

    public SecureSharedPreference(Context context, String name, int mode) {
        this.sharedPreferences = context.getSharedPreferences(name, mode);
        this.secretKey = DeviceUtils.getDeviceId();
    }

    public SecureSharedPreference(Context context, String name, int mode, String key) {
        this.sharedPreferences = context.getSharedPreferences(name, mode);
        this.secretKey = key;
    }

    @SuppressLint("HardwareIds")
    @Deprecated
    public static String getDeviceSerialNumber(Context context) {
        // We're using the Reflection API because Build.SERIAL is only available
        // since API Level 9 (Gingerbread, Android 2.3).
        try {
            String deviceSerial = (String) Build.class.getField("SERIAL").get(null);
            if (TextUtils.isEmpty(deviceSerial)) {
                return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            } else {
                return deviceSerial;
            }
        } catch (Exception ignored) {
            return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        }
    }

    @Override
    public Map<String, String> getAll() {
        final Map<String, ?> encryptedMap = sharedPreferences.getAll();
        final Map<String, String> decryptedMap = new HashMap<>(encryptedMap.size());
        for (Map.Entry<String, ?> entry : encryptedMap.entrySet()) {
            try {
                Object cipherText = entry.getValue();
                //don't include the key
                if (cipherText != null && !cipherText.equals(secretKey.toString())) {
                    //the prefs should all be strings
                    decryptedMap.put(entry.getKey(), decrypt(cipherText.toString()));
                }
            } catch (Exception e) {
                L.w(TAG, "error during getAll", e);
                // Ignore issues that unencrypted values and use instead raw cipher text string
                decryptedMap.put(entry.getKey(), entry.getValue().toString());
            }
        }
        return decryptedMap;
    }

    @Override
    public String getString(String key, String defValue) {
        String value = sharedPreferences.getString(encrypt(key), defValue);
        if (value == null) return defValue;
        return decrypt(value);
    }

    @Override
    public Set<String> getStringSet(String key, Set<String> defValues) {
        Set<String> encryptedStringSet = sharedPreferences.getStringSet(encrypt(key), defValues);
        if (encryptedStringSet == null) return defValues;
        Set<String> decryptedStringSet = new HashSet<>();
        for (String s: encryptedStringSet) {
            decryptedStringSet.add(decrypt(s));
        }
        return decryptedStringSet;
    }

    @Override
    public int getInt(String key, int defValue) {
        String encryptedInteger = sharedPreferences.getString(encrypt(key), null);
        if (encryptedInteger == null) return defValue;
        try {
          return Integer.parseInt(decrypt(encryptedInteger));
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    @Override
    public long getLong(String key, long defValue) {
        String encryptedLong = sharedPreferences.getString(encrypt(key), null);
        if (encryptedLong == null) return defValue;
        try {
            return Long.parseLong(decrypt(encryptedLong));
        } catch (NumberFormatException e) {
            return 0L;
        }
    }

    @Override
    public float getFloat(String key, float defValue) {
        String encryptedFloat = sharedPreferences.getString(encrypt(key), null);
        if (encryptedFloat == null) return defValue;
        try {
            return Float.parseFloat(decrypt(encryptedFloat));
        } catch (NumberFormatException e) {
            return 0F;
        }
    }

    @Override
    public boolean getBoolean(String key, boolean defValue) {
        String encryptedBoolean = sharedPreferences.getString(encrypt(key), null);
        if (encryptedBoolean == null) return defValue;
        return Boolean.parseBoolean(decrypt(encryptedBoolean));
    }

    @Override
    public boolean contains(String key) {
        return sharedPreferences.contains(encrypt(key));
    }

    @Override
    public Editor edit() {
        return new SecureEditor();
    }

    @Override
    public void registerOnSharedPreferenceChangeListener(final OnSharedPreferenceChangeListener listener) {
        if (listener == null) return;
        sharedPreferences.registerOnSharedPreferenceChangeListener((sharedPreferences, key) -> listener.onSharedPreferenceChanged(SecureSharedPreference.this, key));
    }

    @Override
    public void unregisterOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {
        if (listener == null) return;
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener);
    }

    public class SecureEditor implements Editor {

        private Editor editor;

        @SuppressLint("CommitPrefEdits")
        public SecureEditor() {
            editor = sharedPreferences.edit();
        }

        @Override
        public Editor putString(String key, String value) {
            return editor.putString(encrypt(key), encrypt(value));
        }

        @Override
        public Editor putStringSet(String key, Set<String> values) {
            if (values == null) {
                editor.putStringSet(encrypt(key), null);
                return this;
            }
            Set<String> encryptedSet = new HashSet<>(values.size());
            for (String v : values) {
                encryptedSet.add(encrypt(v));
            }
            editor.putStringSet(encrypt(key), encryptedSet);
            return this;
        }

        @Override
        public Editor putInt(String key, int value) {
            editor.putString(encrypt(key), encrypt(Integer.toString(value)));
            return this;
        }

        @Override
        public Editor putLong(String key, long value) {
            editor.putString(encrypt(key), encrypt(Long.toString(value)));
            return this;
        }

        @Override
        public Editor putFloat(String key, float value) {
            editor.putString(encrypt(key), encrypt(Float.toString(value)));
            return this;
        }

        @Override
        public Editor putBoolean(String key, boolean value) {
            editor.putString(encrypt(key), encrypt(Boolean.toString(value)));
            return this;
        }

        @Override
        public Editor remove(String key) {
            editor.remove(encrypt(key));
            return this;
        }

        @Override
        public Editor clear() {
            editor.clear();
            return this;
        }

        @Override
        public boolean commit() {
            return editor.commit();
        }

        @Override
        public void apply() {
            editor.apply();
        }
    }

    private String encrypt(String content) {
        return CryptoUtils.AESEncrypt(this.secretKey, content);
    }

    private String decrypt(String value) {
        return CryptoUtils.AESDecrypt(this.secretKey, value);
    }
}