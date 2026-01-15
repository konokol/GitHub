package com.github.design.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import java.text.MessageFormat;

/**
 * com.github.design.widget.LoadingTextView
 * <p>
 *
 * @author Ivan J. Lee on 2023-02-20 23:24
 * @since v1.0
 */
public class LoadingTextView extends AppCompatTextView implements Runnable {

    private final static int DELAY = 200;
    private final String[] mDots = new String[]{".", "..", "..."};
    private int mLoopCount = 0;
    private boolean isLoading;
    private CharSequence mText;

    public LoadingTextView(Context context) {
        this(context, null);
    }

    public LoadingTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mText = getText();
    }

    public void startLoading() {
        isLoading = true;
        this.postDelayed(this, DELAY);
    }

    public void stopLoading() {
        isLoading = false;
        this.removeCallbacks(this);
    }

    @Override
    public void run() {
        mLoopCount %= mDots.length;
        this.setText(MessageFormat.format("{0}{1}", mText, mDots[mLoopCount]));
        if (isLoading) {
            mLoopCount++;
            this.postDelayed(this, DELAY);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        isLoading = false;
        stopLoading();
    }
}
