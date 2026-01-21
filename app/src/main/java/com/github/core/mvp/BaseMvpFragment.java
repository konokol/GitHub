package com.github.core.mvp;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.os.Looper;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.github.design.LoadingDialog;
import com.github.design.widget.EmptyView;
import com.github.log.Logan;
import com.github.R;
import com.github.app.BaseFragment;

/**
 * com.github.core.mvp
 *
 * @author  Ivan J. Lee on 2019-11-19
 * @version v0.1
 * @since   v1.0
 **/
public abstract class BaseMvpFragment<P extends IPresenter<?>> extends BaseFragment implements IBaseStateView<P> {

    private static final String TAG = "BaseMvpFragment";

    protected static final int STATE_NORMAL = 1;
    protected static final int STATE_EMPTY = 2;
    protected static final int STATE_ERROR = 3;

    protected View mRootView;
    protected LoadingDialog mLoadingDialog;
    protected EmptyView mEmptyView;
    protected EmptyView mErrorView;

    protected SparseArray<View> mStateViews = new SparseArray<>(3);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = LayoutInflater.from(getContext()).inflate(R.layout.layout_base_fragment, container, false);
        if (getContext() != null) {
            mLoadingDialog = new LoadingDialog(getContext());
        }
        initState();
        onViewCreated(mRootView);
        return mRootView;
    }

    protected void initState() {
        FrameLayout frameLayout = ((FrameLayout) mRootView);
        mEmptyView = (EmptyView) LayoutInflater.from(getContext()).inflate(R.layout.layout_empty_view, frameLayout, false);
        mErrorView = (EmptyView) LayoutInflater.from(getContext()).inflate(R.layout.layout_error_view, frameLayout, false);
        View normalView = LayoutInflater.from(getContext()).inflate(getLayoutId(), frameLayout, false);
        mStateViews.put(STATE_EMPTY, mEmptyView);
        mStateViews.put(STATE_ERROR, mErrorView);
        mStateViews.put(STATE_NORMAL, normalView);
        mEmptyView.setVisibility(View.GONE);
        mErrorView.setVisibility(View.GONE);
        normalView.setVisibility(View.VISIBLE);
        frameLayout.addView(mEmptyView);
        frameLayout.addView(mErrorView);
        frameLayout.addView(normalView);
    }

    protected void onViewCreated(View rootView) {
    }

    abstract protected @LayoutRes int getLayoutId();

    public void addState(int state, View view) {
        mStateViews.put(state, view);
        view.setVisibility(View.GONE);
        ((FrameLayout) mRootView).addView(view);
    }

    protected void showEmptyView() {
        showStateView(STATE_EMPTY);
    }

    protected void showEmptyView(CharSequence msg) {
        mEmptyView.setMessage(msg);
        showStateView(STATE_EMPTY);
    }

    protected void showErrorView() {
        showStateView(STATE_ERROR);
    }

    protected void showErrorView(CharSequence title, CharSequence msg){
        mErrorView.setTitle(title);
        mErrorView.setMessage(msg);
        showStateView(STATE_ERROR);
    }

    protected void showNormalView() {
        showStateView(STATE_NORMAL);
    }

    protected void showStateView(int state) {
        for (int i = 0; i < mStateViews.size(); i++) {
            int key = mStateViews.keyAt(i);
            if (key == state) {
                mStateViews.valueAt(i).setVisibility(View.VISIBLE);
            } else {
                mStateViews.valueAt(i).setVisibility(View.GONE);
            }
        }
    }
    @Override
    public boolean isAlive() {
        return isAdded() && !isDetached() && getActivity() != null;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        attach(getPresenter());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        detach(getPresenter());
    }

    @Override
    public void attach(P p) {
        p.start();
    }

    @Override
    public void detach(P p) {
        dismissLoading();
        p.stop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        dismissLoading();
    }

    @Override
    public void showLoading() {
        if (mLoadingDialog != null) {
            mLoadingDialog.show();
        }
    }

    @Override
    public void dismissLoading() {
        Logan.d(TAG, "dismissLoading");
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
    }

    @Override
    public void showToast(String msg) {
        if (isAlive()) {
            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
        }
    }
}
