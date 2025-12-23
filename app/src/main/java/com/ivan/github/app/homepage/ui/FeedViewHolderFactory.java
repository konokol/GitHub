package com.ivan.github.app.homepage.ui;

import android.content.Context;
import androidx.annotation.NonNull;

import android.view.ViewGroup;

import com.github.log.Logan;
import com.ivan.github.R;
import com.ivan.github.app.homepage.model.entity.event.Event;
import com.ivan.github.app.homepage.model.entity.event.EventType;
import com.ivan.github.app.homepage.ui.viewholder.DefaultViewHolder;
import com.ivan.github.app.homepage.ui.viewholder.DeleteEventViewHolder;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * com.ivan.github.app.events.FeedViewHolderFactory
 *
 * @author Ivan on 2019-12-24
 * @version v0.1
 * @since v1.0
 **/
public class FeedViewHolderFactory {

    private static final String TAG = "FeedViewHolderFactory";

    private static Map<String, Class<? extends FeedViewHolder>> classMap = new HashMap<>() {
        {
            put(EventType.DELETE_EVENT, DeleteEventViewHolder.class);
        }
    };

    public static @NonNull FeedViewHolder create(Context context, ViewGroup parent, Event event) {
        Class<? extends FeedViewHolder> clazz = classMap.get(event.getType());
        if (clazz == null) {
            return DefaultViewHolder.newInstance(context, parent, R.layout.layout_feed_list_item_default);
        }

        Layout annotation = clazz.getAnnotation(Layout.class);
        if (annotation == null) {
            return DefaultViewHolder.newInstance(context, parent, R.layout.layout_feed_list_item_default);
        }

        int layoutId = annotation.layoutId();
        try {
            Constructor<? extends FeedViewHolder> constructor = clazz.getConstructor(Context.class, ViewGroup.class, int.class);
            return constructor.newInstance(context, parent, layoutId);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException |
                 InvocationTargetException e) {
            Logan.e(TAG, "failed to Instantiation class " + clazz, e);
        }
        return DefaultViewHolder.newInstance(context, parent, R.layout.layout_feed_list_item_default);
    }

}
