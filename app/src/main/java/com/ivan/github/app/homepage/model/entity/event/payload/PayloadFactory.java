package com.ivan.github.app.homepage.model.entity.event.payload;

import androidx.annotation.Nullable;

import com.github.utils.L;

/**
 * com.ivan.github.app.events.model.payload.PayloadFactory
 *
 * @author Ivan on 2019-12-29
 * @version v0.1
 * @since v1.0
 **/
public class PayloadFactory {

    public static @Nullable Class<?> getClass(String eventType) {
        if (eventType == null || eventType.isEmpty()) {
            return null;
        }
        Package pkg = EventPayload.class.getPackage();
        if (pkg == null) {
            return null;
        }
        String name = pkg.getName() + "." + eventType + "Payload";
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException e) {
            L.e(e);
            return null;
        }
    }
}
