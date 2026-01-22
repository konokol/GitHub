package com.github.app.homepage.ui;

import android.content.Context;
import androidx.annotation.NonNull;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.github.app.homepage.model.entity.event.EventType;
import com.github.design.widget.BaseRecyclerViewAdapter;
import com.github.app.homepage.model.entity.event.Event;

/**
 * FeedListAdapter
 *
 * @author  Ivan on 2019-04-24 22:02.
 * @version v0.1
 * @since   v1.0
 */
public class FeedListAdapter extends BaseRecyclerViewAdapter<FeedViewHolder, Event> {

    private LayoutInflater layoutInflater;

    public FeedListAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(getContext());
        }
        return FeedViewHolderFactory.create(getContext(), layoutInflater, viewGroup, viewType);
    }

    @Override
    public int getItemViewType(int position) {
        Event event = getData(position);
        EventType eventType = EventType.fromName(event.getType());
        return eventType == null ? 0 : eventType.type();
    }

    @Override
    public void onBindViewHolder(@NonNull FeedViewHolder feedViewHolder, int i, Event event) {
        feedViewHolder.bindView(event);
    }

}
