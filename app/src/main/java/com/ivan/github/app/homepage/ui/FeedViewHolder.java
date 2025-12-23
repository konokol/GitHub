package com.ivan.github.app.homepage.ui;

import android.content.Context;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ivan.github.app.homepage.model.entity.event.Event;

/**
 * com.ivan.github.app.events.FeedViewHolder
 *
 * @author  Ivan on 2019-12-24
 * @version v0.1
 * @since   v1.0
 **/
public abstract class FeedViewHolder extends RecyclerView.ViewHolder {

    public FeedViewHolder(Context context, ViewGroup parent, @LayoutRes int id) {
        this(LayoutInflater.from(context).inflate(id, parent, false));
    }

    public Context getContext() {
        return itemView.getContext();
    }

    protected FeedViewHolder(@NonNull View itemView) {
        super(itemView);
        initView(itemView);
    }

    public abstract void initView(View itemView);

    public abstract void bindView(Event event);
}
