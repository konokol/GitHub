package com.github.app.homepage.ui.viewholder;

import androidx.viewbinding.ViewBinding;

import com.github.R;
import com.github.app.homepage.model.entity.event.Event;
import com.github.app.homepage.model.entity.event.payload.PushEventPayload;
import com.github.app.homepage.ui.FeedDataBindingViewHolder;
import com.github.app.homepage.ui.IViewBindingViewHolder;
import com.github.app.homepage.ui.Layout;
import com.github.databinding.FeedEventPushSimpleBinding;

@Layout(layoutId = R.layout.feed_event_push_simple)
public class PushEventViewHolder extends FeedDataBindingViewHolder implements IViewBindingViewHolder {

    private final FeedEventPushSimpleBinding viewBinding;

    public PushEventViewHolder(ViewBinding viewBinding) {
        super(viewBinding);
        this.viewBinding = (FeedEventPushSimpleBinding) viewBinding;
    }

    @Override
    public void bindView(Event event) {
        PushEventPayload payload = event.parsePayload(PushEventPayload.class);
        viewBinding.setActor(event.getActor());
        viewBinding.setRepo(event.getRepo());
        viewBinding.setPayload(payload);
        viewBinding.setEvent(event);
    }
}
