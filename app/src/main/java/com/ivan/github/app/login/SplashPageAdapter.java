package com.ivan.github.app.login;

import android.content.Context;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ivan.github.R;
import com.ivan.github.app.login.model.SplashData;

import java.util.List;

/**
 * Adapter for Splash
 *
 * @author  Ivan at 2016-12-08  22:09
 * @version v1.0
 * @since   v0.1.0
 */

public class SplashPageAdapter extends PagerAdapter {

    private Context mContext;
    private List<SplashData> mData;

    SplashPageAdapter(Context context, List<SplashData> data) {
        this.mData = data;
        this.mContext = context;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        SplashData data = mData.get(position);
        View view = LayoutInflater.from(mContext).inflate(R.layout.splash_item, container, false);
        ViewHolder holder = new ViewHolder(view);
        holder.mImageView.setImageURI(null);
        holder.mImageView.setImageURI(Uri.parse(data.uri));
        holder.mTVTitle.setText(data.title);
        holder.mTVDescription.setText(data.description);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return mData != null ? mData.size() : 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    private static class ViewHolder {
        ImageView mImageView;
        TextView mTVTitle;
        TextView mTVDescription;

        ViewHolder(View itemView) {
            mImageView = itemView.findViewById(R.id.image);
            mTVTitle = itemView.findViewById(R.id.tv_title);
            mTVDescription = itemView.findViewById(R.id.tv_desc);
        }
    }

}
