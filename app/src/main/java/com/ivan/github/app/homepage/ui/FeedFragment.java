package com.ivan.github.app.homepage.ui;

import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.View;
import android.widget.TextView;

import com.github.design.widget.SwipeRefreshRecyclerView;
import com.ivan.github.R;
import com.ivan.github.app.homepage.DaggerFeedComponent;
import com.ivan.github.app.homepage.FeedModule;
import com.ivan.github.app.homepage.model.entity.event.Event;
import com.ivan.github.app.homepage.FeedContract;
import com.ivan.github.app.homepage.presenter.FeedPresenter;
import com.ivan.github.core.mvp.BaseMvpFragment;

import java.util.List;

import javax.inject.Inject;

/**
 * FeedFragment
 *
 * @author Ivan J. Lee
 */
public class FeedFragment extends BaseMvpFragment<FeedContract.Presenter> implements FeedContract.View {

    private SwipeRefreshRecyclerView mRecyclerView;
    private FeedListAdapter mAdapter;
    private TextView mTvEmpty;

    @Inject
    FeedPresenter mPresenter;

    public FeedFragment() {
        inject();
    }

    public static FeedFragment newInstance() {
        FeedFragment fragment = new FeedFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_feed;
    }

    private void inject() {
        DaggerFeedComponent.builder()
                .feedModule(new FeedModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void onViewCreated(View rootView) {
        mRecyclerView = rootView.findViewById(R.id.pl_recycler_view);
        mAdapter = new FeedListAdapter(getContext());
        mTvEmpty = rootView.findViewById(R.id.tv_empty_view);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addLoadMoreListener(view -> mPresenter.loadMore());
        mRecyclerView.addRefreshListener(view -> mPresenter.refresh());
        mPresenter.listUserEvents(0);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mPresenter != null) {
            mPresenter.stop();
        }
    }

    @Override
    public void updateList(List<Event> list) {
        showNormalView();
        int size = mAdapter.getItemCount();
        mRecyclerView.setRefreshing(false);
        mAdapter.appendData(list);
        mAdapter.notifyItemInserted(size);
    }

    @Override
    public void showEmptyView() {
        mTvEmpty.setText(R.string.feed_empty_text);
        mTvEmpty.setVisibility(View.GONE);
    }

    @Override
    public void showErrorPage(int code, String error) {
        Snackbar.make(mRecyclerView, error, Snackbar.LENGTH_LONG).show();
        showErrorView(getString(R.string.error) + "#" + code, error);
        if (mAdapter.getItemCount() > 0) {
            Snackbar.make(mRecyclerView, error, Snackbar.LENGTH_LONG).show();
        } else {
            mTvEmpty.setText(error);
            mTvEmpty.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showEnd() {
        mRecyclerView.setRefreshing(false);
    }

    @Override
    public FeedContract.Presenter getPresenter() {
        return mPresenter;
    }
}
