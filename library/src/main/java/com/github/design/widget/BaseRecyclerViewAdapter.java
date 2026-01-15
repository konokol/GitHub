package com.github.design.widget;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * BaseRecyclerViewHolder
 *
 * @author  Ivan on 2019-04-24 22:14.
 * @version v0.1
 * @since   v1.0
 */
public abstract class BaseRecyclerViewAdapter<VH extends RecyclerView.ViewHolder, Data> extends RecyclerView.Adapter<VH> {

    protected Context mContext;
    protected List<Data> mData = new ArrayList<>();
    private ListenerInfo mListenerInfo = new ListenerInfo();
    private Map<Integer, View.OnClickListener> mItemClickListenerMap = new HashMap<>();
    private Map<Integer, View.OnLongClickListener> mItemLongClickListenerMap = new HashMap<>();

    public BaseRecyclerViewAdapter(Context context) {
        this.mContext = context;
    }

    public Context getContext() {
        return mContext;
    }

    public void setOnItemClickListener(OnItemClickListener<Data> onItemClickListener) {
        this.mListenerInfo.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener<Data> onItemLongClickListener) {
        this.mListenerInfo.onItemLongClickListener = onItemLongClickListener;
    }

    public void setData(List<Data> list) {
        mData.clear();
        if (list != null) {
            mData.addAll(list);
        }
    }

    public void appendData(List<Data> list) {
        mData.addAll(list);
    }

    @Override
    public void onBindViewHolder(@NonNull VH vh, int i) {
        Data data = mData.get(i);
        final int fi = i;
        if (mListenerInfo.onItemLongClickListener != null) {
            View.OnLongClickListener onItemLongClickListener = mItemLongClickListenerMap.get(data.hashCode());
            if (onItemLongClickListener == null) {
                onItemLongClickListener = v -> mListenerInfo.onItemLongClickListener.onItemClick(v, fi, data);
            }
            vh.itemView.setOnLongClickListener(onItemLongClickListener);
        }
        if (mListenerInfo.onItemClickListener != null) {
            View.OnClickListener onClickListener = mItemClickListenerMap.get(data.hashCode());
            if (onClickListener == null) {
                onClickListener = view -> mListenerInfo.onItemClickListener.onItemClick(view, fi, data);
            }
            vh.itemView.setOnClickListener(onClickListener);
        }
        onBindViewHolder(vh, i, data);
    }

    public abstract void onBindViewHolder(@NonNull VH vh, int i, Data data);

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public Data getData(int pos) {
        if (mData != null) {
            return mData.get(pos);
        } else {
            return null;
        }
    }

    private class ListenerInfo {
        private OnItemClickListener<Data> onItemClickListener;
        private OnItemLongClickListener<Data> onItemLongClickListener;
    }

    public interface OnItemClickListener<Data> {
        void onItemClick(View view, int pos, Data data);
    }

    public interface OnItemLongClickListener<Data> {
        boolean onItemClick(View view, int pos, Data data);
    }

}
