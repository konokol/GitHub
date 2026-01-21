package com.github.app.homepage.ui;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.viewbinding.ViewBinding;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.github.log.Logan;
import com.github.R;
import com.github.app.homepage.model.entity.event.Event;
import com.github.app.homepage.model.entity.event.EventType;
import com.github.app.homepage.ui.viewholder.DefaultViewHolder;
import com.github.app.homepage.ui.viewholder.DeleteEventViewHolder;
import com.github.app.homepage.ui.viewholder.PushEventViewHolder;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * com.github.app.events.FeedViewHolderFactory
 *
 * @author Ivan on 2019-12-24
 * @version v0.1
 * @since v1.0
 **/
public class FeedViewHolderFactory {

    private static final String TAG = "FeedViewHolderFactory";

    private static final Map<String, Class<? extends FeedViewHolder>> classMap = new HashMap<>() {
        {
            put(EventType.DELETE_EVENT, DeleteEventViewHolder.class);
            put(EventType.PUSH_EVENT, PushEventViewHolder.class);
        }
    };

    public static @NonNull FeedViewHolder create(Context context, LayoutInflater inflater, ViewGroup parent, Event event) {
        Class<? extends FeedViewHolder> clazz = classMap.get(event.getType());
        if (clazz == null) {
            return DefaultViewHolder.newInstance(inflater, parent, R.layout.feed_event_default);
        }

        Layout annotation = clazz.getAnnotation(Layout.class);
        if (annotation == null) {
            return DefaultViewHolder.newInstance(inflater, parent, R.layout.feed_event_default);
        }

        int layoutId = annotation.layoutId();
        try {
            Class<?>[] interfaces = clazz.getInterfaces();
            Class<?> viewBindingInterface = null;
            for (Class<?> i : interfaces) {
                if (IViewBindingViewHolder.class.equals(i)) {
                    viewBindingInterface = i;
                    break;
                }
            }
            if (viewBindingInterface == null) {
                Constructor<? extends FeedViewHolder> constructor = clazz.getConstructor(LayoutInflater.class, ViewGroup.class, int.class);
                return constructor.newInstance(inflater, parent, layoutId);
            } else {
                Constructor<? extends FeedViewHolder> constructor = clazz.getConstructor(ViewBinding.class);
                ViewBinding viewBinding = DataBindingUtil.inflate(inflater, layoutId, parent, false);
                return constructor.newInstance(viewBinding);
            }
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException |
                 InvocationTargetException e) {
            Logan.e(TAG, "failed to Instantiation class " + clazz, e);
        }
        return DefaultViewHolder.newInstance(inflater, parent, R.layout.feed_event_default);
    }

}
