package com.github.app.homepage.ui.viewholder;

import androidx.viewbinding.ViewBinding;

import com.github.R;
import com.github.app.homepage.model.entity.event.Event;
import com.github.app.homepage.model.entity.event.payload.CreateEventPayload;
import com.github.app.homepage.ui.FeedDataBindingViewHolder;
import com.github.app.homepage.ui.IViewBindingViewHolder;
import com.github.app.homepage.ui.Layout;
import com.github.databinding.FeedEventCreateBinding;

/**
 * Copyright © 2026 Pancoku. All rights reserved.
 * <p>
 *
 * CreateEventViewHolder
 * <p>
 *
 * @author lijun
 * @version 1.0
 * @since 2026-2026/1/22 01:00
 *
 */
@Layout(layoutId = R.layout.feed_event_create)
public class CreateEventViewHolder extends FeedDataBindingViewHolder implements IViewBindingViewHolder {

    private final FeedEventCreateBinding viewBinding;

    public CreateEventViewHolder(ViewBinding viewBinding) {
        super(viewBinding);
        this.viewBinding = (FeedEventCreateBinding) viewBinding;
    }

    @Override
    public void bindView(Event event) {
        CreateEventPayload payload = event.parsePayload(CreateEventPayload.class);
        viewBinding.setActor(event.getActor());
        viewBinding.setRepo(event.getRepo());
        viewBinding.setPayload(payload);
        viewBinding.setEvent(event);
    }
}
