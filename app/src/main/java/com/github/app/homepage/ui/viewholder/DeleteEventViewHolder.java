package com.github.app.homepage.ui.viewholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.R;
import com.github.app.homepage.model.entity.event.Repository;
import com.github.app.homepage.ui.FeedViewHolder;
import com.github.app.homepage.model.entity.event.Event;
import com.github.app.homepage.model.entity.event.payload.DeleteEventPayload;
import com.github.app.homepage.ui.Layout;
import com.github.common.util.DateFormatUtils;
import com.github.common.util.GsonUtils;

/**
 * com.github.app.events.viewholder.DeleteEventViewHolder
 *
 * @author Ivan J. Lee on 2020-01-01 23:15
 * @version v0.1
 * @since v1.0
 **/
@Layout(layoutId = R.layout.feed_event_delete)
public class DeleteEventViewHolder extends FeedViewHolder {

    private ImageView mIvAvatar;
    private TextView mTvUsername;
    private TextView mTvAction;
    private TextView mTvTime;
    private TextView mTvRef;
    private TextView mTvRepository;

    public DeleteEventViewHolder(Context context, LayoutInflater inflater, ViewGroup parent, int id) {
        super(inflater, parent, id);
    }

    @Override
    public void initView(View itemView) {
        mIvAvatar = itemView.findViewById(R.id.iv_avatar);
        mTvUsername = itemView.findViewById(R.id.tv_username);
        mTvAction = itemView.findViewById(R.id.tv_action);
        mTvTime = itemView.findViewById(R.id.tv_time);
        mTvRef = itemView.findViewById(R.id.tv_ref);
        mTvRepository = itemView.findViewById(R.id.tv_repository);
    }

    @Override
    public void bindView(Event event) {
        DeleteEventPayload payload = GsonUtils.from(event.getPayload(), DeleteEventPayload.class);
        Glide.with(getContext())
                .load(event.getActor().avatarUrl)
                .into(mIvAvatar);
        mTvUsername.setText(event.getActor().getName());
        if (DeleteEventPayload.REF_TYPE_BRANCH.equals(payload.getRefType())) {
            mTvAction.setText(R.string.feed_delete_branch);
        } else if (DeleteEventPayload.REF_TYPE_TAG.equals(payload.getRefType())) {
            mTvAction.setText(R.string.feed_delete_tag);
        } else {
            mTvAction.setText(R.string.feed_delete);
        }
        mTvTime.setText(DateFormatUtils.getTimeSpan(event.getCreatedAt()));
        mTvRef.setText(payload.getRef());
        if (payload.getRepository() != null) {
            Repository repository = payload.getRepository();
            mTvRepository.setText(repository.getFullName());
        }
    }
}
