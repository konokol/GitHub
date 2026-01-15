package com.ivan.github.widget;

import android.content.Context;
import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.IntRange;
import androidx.core.view.ActionProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ivan.github.R;

/**
 * Menu with a bridge
 *
 * @author  Ivan on 2018-12-26 23:24.
 * @version v0.1
 * @since   v0.1.0
 */
public class BridgeActionProvider extends ActionProvider {

    private View mItemView;
    private ImageView mIcon;
    private TextView mBridge;

    public BridgeActionProvider(Context context) {
        super(context);
    }

    @Override
    public View onCreateActionView() {
        int size = getContext().getResources().getDimensionPixelSize(R.dimen.action_bar_default_height_material);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_bridget_action_provider, null);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(size, size);
        view.setLayoutParams(layoutParams);
        this.mIcon = view.findViewById(R.id.iv_icon);
        this.mBridge = view.findViewById(R.id.tv_bridge_num);
        this.mItemView = view;
        return view;
    }

    public void setIcon(@DrawableRes int id) {
        mIcon.setImageResource(id);
    }

    public void setBridgeRes(@DrawableRes int id) {
        mBridge.setBackgroundResource(id);
    }

    public void setBridgeColor(@ColorInt int id) {
        mBridge.setBackgroundColor(id);
    }

    public void setBridgeTextColor(@ColorInt int color) {
        mBridge.setTextColor(color);
    }

    public void setBridgeNum(@IntRange(from = 0, to = 100) int num) {
        num = Math.max(0, num);
        String bridgeText = num > 99 ? "99+" : String.valueOf(num);
        mBridge.setText(bridgeText);
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        mItemView.setOnClickListener(onClickListener);
    }
}
