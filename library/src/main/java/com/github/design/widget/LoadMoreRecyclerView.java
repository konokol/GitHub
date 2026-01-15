package com.github.design.widget;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * LoadMore RecyclerView
 *
 * @author  Ivan on 2019-04-21 12:49.
 * @version v0.1
 * @since   v1.0
 */
public class LoadMoreRecyclerView extends RecyclerView {

    private static final int STATE_NONE = 0x00;
    private static final int STATE_LOADING = 0x01;
    private static final int STATE_FAILURE = 0x02;
    private static final int STATE_COMPLETE = 0x03;

    private static final int VISIBLE_THRESHOLD = 1;

    private int state;
    private boolean loadMoreEnabled = true;
    private LinearLayoutManager layoutManager;

    public LoadMoreRecyclerView(Context context) {
        this(context, null);
    }

    public LoadMoreRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadMoreRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

}
