package com.ivan.github.app.homepage.ui.viewholder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;
import com.ivan.github.R;
import com.ivan.github.app.homepage.model.entity.event.Event;
import com.ivan.github.app.homepage.ui.FeedViewHolder;
import com.ivan.github.app.homepage.ui.Layout;
import com.ivan.github.common.util.DateFormatUtils;

/**
 * com.ivan.github.app.feed.ui.viewholder
 * DefaultViewHolder
 * <p>
 *
 * @author Ivan on 2022-01-07 01:43
 * @since v1.0
 */
@SuppressLint("NonConstantResourceId")
@Layout(layoutId = R.layout.layout_feed_list_item_delete)
public class DefaultViewHolder extends FeedViewHolder {

    private ImageView mIvAvatar;
    private TextView mTvUsername;
    private TextView mTvAction;
    private TextView mTvTime;
    private TextView mTvRef;
    private TextView mTvRepository;

    public static DefaultViewHolder newInstance(Context context, ViewGroup parent, int id) {
        return new DefaultViewHolder(context, parent, id);
    }

    DefaultViewHolder(Context context, ViewGroup parent, int id) {
        super(context, parent, id);
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
        Glide.with(getContext())
                .load(event.getActor().avatarUrl)
                .apply(RequestOptions.circleCropTransform()
                        .placeholder(R.drawable.ic_avatar_default)
                        .error(R.drawable.ic_avatar_default))
                .transition(DrawableTransitionOptions.with(new DrawableCrossFadeFactory.Builder(300)
                        .setCrossFadeEnabled(true)
                        .build()))
                .into(mIvAvatar);
        mTvUsername.setText(event.getActor().getDisplayLogin());
        mTvAction.setText(R.string.feed_action_default);
        mTvTime.setText(DateFormatUtils.getTimeSpan(event.getCreatedAt()));
        mTvRepository.setText(event.getRepo().getName());
    }
}
