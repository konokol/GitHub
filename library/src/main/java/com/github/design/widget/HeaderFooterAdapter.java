package com.github.design.widget;

import android.annotation.SuppressLint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * com.github.design.widget
 * RecyclerView support header and footer
 * <p>
 *
 * @author Ivan J. Lee on 2023-02-18 21:23
 * @since v1.0
 */
public class HeaderFooterAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final String TAG = "HeaderFooterAdapter";

    private static final int HEADER = Integer.MIN_VALUE;
    private static final int FOOTER = Integer.MIN_VALUE + 1;
    @Nullable
    protected RecyclerView.Adapter<VH> mInnerAdapter;
    protected View mHeader;
    protected View mFooter;

    public HeaderFooterAdapter(@NonNull RecyclerView.Adapter<VH> adapter) {
        this.mInnerAdapter = adapter;
        adapter.registerAdapterDataObserver(new InnerAdapterDataObserver());
    }

    private class InnerAdapterDataObserver extends RecyclerView.AdapterDataObserver {

        @SuppressLint("NotifyDataSetChanged")
        public void onChanged() {
            notifyDataSetChanged();
        }

        public void onItemRangeChanged(int positionStart, int itemCount) {
            notifyItemRangeChanged(getHeaderCount() + positionStart, itemCount);
        }

        public void onItemRangeInserted(int positionStart, int itemCount) {
            notifyItemRangeInserted(getHeaderCount() + positionStart, itemCount);
        }

        public void onItemRangeRemoved(int positionStart, int itemCount) {
            notifyItemRangeRemoved(getHeaderCount() + positionStart, itemCount);
        }

        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            notifyItemMoved(getHeaderCount() + fromPosition, getHeaderCount() + toPosition);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == HEADER) {
            return new HeaderFooterViewHolder(new FrameLayout(parent.getContext()));
        } else if (viewType == FOOTER) {
            return new HeaderFooterViewHolder(new FrameLayout(parent.getContext()));
        } else if (mInnerAdapter != null) {
            return mInnerAdapter.onCreateViewHolder(parent, viewType);
        } else {
            throw new IllegalArgumentException("mInnerAdapter is null, you should set mInnerAdapter first");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == HEADER) {
            FrameLayout container = ((HeaderFooterViewHolder) holder).mContainer;
            container.removeAllViews();
            container.addView(mHeader);
        } else if (viewType == FOOTER) {
            FrameLayout container = ((HeaderFooterViewHolder) holder).mContainer;
            container.removeAllViews();
            container.addView(mFooter);
        } else if (mInnerAdapter != null) {
            //noinspection unchecked
            mInnerAdapter.onBindViewHolder((VH) holder, position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        int header = getHeaderCount();
        int items = getInnerItemCount();
        if (0 <= position && position < header) {
            return HEADER;
        } else if (position >= header + items) {
            return FOOTER;
        } else if (mInnerAdapter != null) {
            return mInnerAdapter.getItemViewType(position - header);
        } else {
            return super.getItemViewType(position);
        }
    }

    @Override
    public int getItemCount() {
        return getInnerItemCount() +getHeaderCount() + getFooterCount();
    }

    public void setHeader(View header) {
        this.mHeader = header;
        if (this.mHeader == null) {
            this.notifyItemInserted(0);
        } else {
            this.notifyItemChanged(0);
        }
    }

    public void setFooter(View footer) {
        this.mFooter = footer;
        if (this.mFooter == null) {
            this.notifyItemInserted(getHeaderCount() + getInnerItemCount());
        } else {
            this.notifyItemChanged(getHeaderCount() + getInnerItemCount());
        }
    }

    public void removeHeader() {
        if (this.mHeader != null) {
            this.mHeader = null;
            this.notifyItemRemoved(0);
        }
    }

    public void removeFooter() {
        if (this.mFooter != null) {
            this.mFooter = null;
            this.notifyItemRemoved(getItemCount() - 1);
        }
    }

    private int getInnerItemCount() {
        return mInnerAdapter == null ? 0 : mInnerAdapter.getItemCount();
    }

    private int getHeaderCount() {
        return mHeader == null ? 0 : 1;
    }

    private int getFooterCount() {
        return mFooter == null ? 0 : 1;
    }

    protected static class HeaderFooterViewHolder extends RecyclerView.ViewHolder {

        protected FrameLayout mContainer;

        public HeaderFooterViewHolder(@NonNull FrameLayout itemView) {
            super(itemView);
            ViewGroup.LayoutParams layoutParams = itemView.getLayoutParams();
            if (layoutParams == null) {
                layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                itemView.setLayoutParams(layoutParams);
            }
            this.mContainer = itemView;
        }
    }
}
