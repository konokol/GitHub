package com.github.design.widget;

import android.content.Context;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.github.design.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * RecyclerView with pull to refresh, load more, header and footer layout.
 *
 * @author  Ivan on 2019-04-01 00:11.
 * @version v0.1
 * @since   v1.0
 */
public class SwipeRefreshRecyclerView extends SwipeRefreshLayout {

    public static final String TAG = "SwipeRefreshRecyclerView";
    private static final int VISIBLE_THRESHOLD = 1;
    public static final int STATUS_NORMAL = 1;
    public static final int STATUS_LOADING = 2;
    public static final int STATUS_NO_MORE = 3;
    public static final int STATUS_ERROR = 4;

    protected RecyclerView mRecyclerView;
    protected HeaderFooterAdapter<?> mAdapter;
    private boolean mCanLoadMore = true;
    @LoadMoreStatus
    private int mLoadMoreStatus = STATUS_NORMAL;
    private int mPreloadNum = VISIBLE_THRESHOLD;
    private final Set<OnRefreshListener> mRefreshListeners = new LinkedHashSet<>();
    private final Set<OnLoadMoreListener> mLoadMoreListeners = new LinkedHashSet<>();

    @IntDef({STATUS_NORMAL, STATUS_LOADING, STATUS_NO_MORE, STATUS_ERROR})
    @Retention(RetentionPolicy.SOURCE)
    public @interface LoadMoreStatus{}

    public SwipeRefreshRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public SwipeRefreshRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_plrecycler_view, this, true);
        super.setOnRefreshListener(this::notifyOnRefresh);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.addOnScrollListener(new LoadMoreScrollListener());
    }

    private class LoadMoreScrollListener extends RecyclerView.OnScrollListener {

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
            if (layoutManager == null) {
                return;
            }
            if (!(layoutManager instanceof LinearLayoutManager)) {
                return;
            }
            if (!mCanLoadMore) {
                return;
            }
            if (mLoadMoreStatus == STATUS_NO_MORE || mLoadMoreStatus == STATUS_LOADING) {
                return;
            }
            if (dy >= 0) {
                int totalItemCount = layoutManager.getItemCount();
                int lastVisibleItem = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                if (lastVisibleItem + mPreloadNum >= totalItemCount) {
                    triggerLoadMore();
                }
            }
        }
    }

    public void setLayoutManager(LinearLayoutManager layoutManager) {
        mRecyclerView.setLayoutManager(layoutManager);
    }

    @SuppressWarnings("unchecked")
    public void setAdapter(RecyclerView.Adapter<?> adapter) {
        //noinspection rawtypes
        mAdapter = new HeaderFooterAdapter(adapter);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Nullable
    public RecyclerView.Adapter<?> getAdapter() {
        return mAdapter == null ? null : mAdapter.mInnerAdapter;
    }

    public void setCanLoadMore(boolean mCanLoadMore) {
        this.mCanLoadMore = mCanLoadMore;
    }

    public void setNormal() {
        this.mLoadMoreStatus = STATUS_NORMAL;
        mAdapter.removeFooter();
    }

    public void setLoadingMore() {
        this.mLoadMoreStatus = STATUS_LOADING;
        View loadingView = LayoutInflater.from(getContext())
                .inflate(R.layout.loading_footer_loading, this, false);
        LoadingTextView loadingTextView = loadingView.findViewById(R.id.tv_loading_text);
        mAdapter.setFooter(loadingView);
        loadingTextView.startLoading();
    }

    public void setNoMore() {
        this.mLoadMoreStatus = STATUS_NO_MORE;
        View view = LayoutInflater.from(getContext()).inflate(R.layout.loading_footer_none, this, false);
        mAdapter.setFooter(view);
    }

    public void setLoadMoreError() {
        this.mLoadMoreStatus = STATUS_ERROR;
        View view = LayoutInflater.from(getContext()).inflate(R.layout.loading_footer_error, this, false);
        view.setOnClickListener(v -> triggerLoadMore());
        mAdapter.setFooter(view);
    }

    private void triggerLoadMore() {
        setLoadingMore();
        notifyOnLoadMore();
    }

    public void setItemAnimator(@Nullable RecyclerView.ItemAnimator animator) {
        mRecyclerView.setItemAnimator(animator);
    }

    public void setHasFixedSize(boolean hasFixedSize) {
        mRecyclerView.setHasFixedSize(hasFixedSize);
    }

    public void setRecycledViewPool(@Nullable RecyclerView.RecycledViewPool pool) {
        mRecyclerView.setRecycledViewPool(pool);
    }

    public void setScrollingTouchSlop(int slopConstant) {
        mRecyclerView.setScrollingTouchSlop(slopConstant);
    }

    public void setOnFlingListener(@Nullable RecyclerView.OnFlingListener onFlingListener) {
        mRecyclerView.setOnFlingListener(onFlingListener);
    }

    public void setViewCacheExtension(@Nullable RecyclerView.ViewCacheExtension extension) {
        mRecyclerView.setViewCacheExtension(extension);
    }

    public void setItemViewCacheSize(int size) {
        mRecyclerView.setItemViewCacheSize(size);
    }

    public void addOnItemTouchListener(@NonNull RecyclerView.OnItemTouchListener listener) {
        mRecyclerView.addOnItemTouchListener(listener);
    }

    public void addItemDecoration(@NonNull RecyclerView.ItemDecoration decor) {
        mRecyclerView.addItemDecoration(decor);
    }

    public void addItemDecoration(@NonNull RecyclerView.ItemDecoration decor, int index) {
        mRecyclerView.addItemDecoration(decor, index);
    }

    public void removeItemDecorationAt(int index) {
        mRecyclerView.removeItemDecorationAt(index);
    }

    public void removeItemDecoration(@NonNull RecyclerView.ItemDecoration decor) {
        mRecyclerView.removeItemDecoration(decor);
    }

    public void removeOnScrollListener(@NonNull RecyclerView.OnScrollListener listener) {
        mRecyclerView.removeOnScrollListener(listener);
    }

    public void removeOnItemTouchListener(@NonNull RecyclerView.OnItemTouchListener listener) {
        mRecyclerView.removeOnItemTouchListener(listener);
    }

    private void notifyOnRefresh() {
        for (OnRefreshListener listener : mRefreshListeners) {
            listener.onRefresh(this);
        }
    }

    private void notifyOnLoadMore() {
        for (OnLoadMoreListener listener : mLoadMoreListeners) {
            listener.onLoadMore(this);
        }
    }

    /**
     * Set the listener to be notified when a refresh is triggered via the swipe
     *  gesture.
     * @deprecated use {@link #addRefreshListener(OnRefreshListener)}
     */
    @Override
    @Deprecated
    public void setOnRefreshListener(@Nullable SwipeRefreshLayout.OnRefreshListener listener) {
        this.addRefreshListener((view) -> {
            if (listener != null)
                listener.onRefresh();
        });
    }

    public void addRefreshListener(@Nullable OnRefreshListener listener) {
        if (listener != null) {
            mRefreshListeners.add(listener);
        }
    }

    public boolean removeRefreshListener(@Nullable OnRefreshListener listener) {
        return mRefreshListeners.remove(listener);
    }

    public void addLoadMoreListener(@Nullable OnLoadMoreListener listener) {
        if (listener != null) {
            mLoadMoreListeners.add(listener);
        }
    }

    public void removeLoadMoreListener(@Nullable OnLoadMoreListener listener) {
        mLoadMoreListeners.remove(listener);
    }

    public interface OnRefreshListener {

        /**
         * Called when a swipe gesture triggers a refresh.
         */
        void onRefresh(SwipeRefreshRecyclerView recyclerView);
    }

    public interface OnLoadMoreListener {
        /**
         * Called when a pull gesture triggers a refresh
         */
        void onLoadMore(SwipeRefreshRecyclerView recyclerView);
    }

    public void setPreloadNum(int preloadNum) {
        this.mPreloadNum = Math.max(preloadNum, VISIBLE_THRESHOLD);
    }
}
