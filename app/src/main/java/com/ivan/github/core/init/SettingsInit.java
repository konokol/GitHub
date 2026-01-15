package com.ivan.github.core.init;

import android.content.Context;

import androidx.annotation.NonNull;

import com.ivan.github.app.AppSettings;

/**
 * com.ivan.github.core.init
 * SettingsInit
 * <p>
 *
 * @author lijun on 2021-12-23 01:27
 * @since v1.0
 */
public class SettingsInit extends AbsInitializer<Class<AppSettings>> {

    @NonNull
    @Override
    public String getTag() {
        return "SettingsInit";
    }

    @Override
    public Class<AppSettings> onCreate(Context context) {
        return AppSettings.class;
    }
}
