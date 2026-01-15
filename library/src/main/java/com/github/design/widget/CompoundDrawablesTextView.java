package com.github.design.widget;

import android.content.Context;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import com.github.utils.L;

/**
 * TextView whose drawables can be clicked
 *
 * @author  Ivan on 2017/10/8 15:33.
 * @version v0.1
 * @since   v1.0
 */

public class CompoundDrawablesTextView extends AppCompatTextView {

    public interface OnDrawableLeftClickListener {
        void onDrawableLeftClick(View view);
    }

    public interface OnDrawableTopClickListener {
        void onDrawableTopClick(View view);
    }

    public interface OnDrawableRightClickListener {
        void onDrawableRightClick(View view);
    }

    public interface OnDrawableBottomClickListener {
        void onDrawableBottomClick(View view);
    }

    public static abstract class OnDrawableClickListerner
            implements OnDrawableLeftClickListener, OnDrawableRightClickListener,
            OnDrawableTopClickListener, OnDrawableBottomClickListener {

    }

    private OnDrawableLeftClickListener mDrawableLeftClickListener;
    private OnDrawableRightClickListener mDrawableRightClickListener;
    private OnDrawableTopClickListener mDrawableTopClickListener;
    private OnDrawableBottomClickListener mDrawableBottomClickListener;

    public CompoundDrawablesTextView(Context context) {
        super(context);
    }

    public CompoundDrawablesTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CompoundDrawablesTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setDrawableLeftClickListener(OnDrawableLeftClickListener drawableLeftClickListener) {
        this.mDrawableLeftClickListener = drawableLeftClickListener;
    }

    public void setDrawableRightClickListener(OnDrawableRightClickListener drawableRightClickListener) {
        this.mDrawableRightClickListener = drawableRightClickListener;
    }

    public void setDrawableTopClickListener(OnDrawableTopClickListener drawableTopClickListener) {
        this.mDrawableTopClickListener = drawableTopClickListener;
    }

    public void setDrawableBottomClickListener(OnDrawableBottomClickListener drawableBottomClickListener) {
        this.mDrawableBottomClickListener = drawableBottomClickListener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        L.v("====", "action: " + event.getAction());
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (touchDrawableLeft(event) || touchDrawableTop(event) || touchDrawableRight(event) || touchDrawableBottom(event)) {
                return true;
            }
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            if (mDrawableLeftClickListener != null && touchDrawableLeft(event)) {
                mDrawableLeftClickListener.onDrawableLeftClick(this);
            } else if (mDrawableTopClickListener != null && touchDrawableTop(event)) {
                mDrawableTopClickListener.onDrawableTopClick(this);
            } else if (mDrawableRightClickListener != null && touchDrawableRight(event)) {
                mDrawableRightClickListener.onDrawableRightClick(this);
            }else if (mDrawableBottomClickListener != null && touchDrawableBottom(event)) {
                mDrawableBottomClickListener.onDrawableBottomClick(this);
            }
        }
        return super.onTouchEvent(event);
    }

    private boolean touchDrawableLeft(MotionEvent event) {
        float touchX = event.getX(), touchY = event.getY();
        int touchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        Drawable drawableLeft = getCompoundDrawables()[0];
        if (drawableLeft != null) {
            int w = drawableLeft.getIntrinsicWidth();
            int h = drawableLeft.getIntrinsicHeight();
            RectF rectF = new RectF(getPaddingLeft() - touchSlop,
                    (getMeasuredHeight() - h)  / 2f - touchSlop,
                    getPaddingLeft() + w + touchSlop,
                    (getMeasuredHeight() + h) / 2f + touchSlop);
            if (rectF.contains(touchX, touchY) && mDrawableLeftClickListener != null) {
                return true;
            }
        }
        return false;
    }

    private boolean touchDrawableTop(MotionEvent event) {
        float touchX = event.getX(), touchY = event.getY();
        int touchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        Drawable drawableTop = getCompoundDrawables()[1];
        if (drawableTop != null) {
            int w = drawableTop.getIntrinsicWidth();
            int h = drawableTop.getIntrinsicHeight();
            RectF rectF = new RectF((getMeasuredWidth() - w) / 2f - touchSlop,
                    getPaddingTop() - touchSlop,
                    (getMeasuredWidth() + w) / 2f + touchSlop,
                    getPaddingTop() + h + touchSlop);
            if (rectF.contains(touchX, touchY) && mDrawableTopClickListener != null) {
                return true;
            }
        }
        return false;
    }

    private boolean touchDrawableRight(MotionEvent event) {
        float touchX = event.getX(), touchY = event.getY();
        int touchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        Drawable drawableRight = getCompoundDrawables()[2];
        if (drawableRight != null) {
            int w = drawableRight.getIntrinsicWidth();
            int h = drawableRight.getIntrinsicHeight();
            RectF rectF = new RectF(getMeasuredWidth() - getPaddingRight() - w - touchSlop,
                    (getMeasuredHeight() - h) / 2f - touchSlop,
                    getMeasuredWidth() - getPaddingRight() + touchSlop,
                    (getMeasuredHeight() + h) / 2f + touchSlop);
            if (rectF.contains(touchX, touchY) && mDrawableRightClickListener != null) {
                return true;
            }
        }
        return false;
    }

    private boolean touchDrawableBottom(MotionEvent event) {
        float touchX = event.getX(), touchY = event.getY();
        int touchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        Drawable drawableBottom = getCompoundDrawables()[3];
        if (drawableBottom != null) {
            int w = drawableBottom.getIntrinsicWidth();
            int h = drawableBottom.getIntrinsicHeight();
            RectF rectF = new RectF((getMeasuredWidth() - w) / 2f - touchSlop,
                    getMeasuredHeight() - getPaddingBottom() - h - touchSlop,
                    (getMeasuredWidth() + w) / 2f + touchSlop,
                    getMeasuredHeight() - getPaddingBottom() + touchSlop);
            if (rectF.contains(touchX, touchY) && mDrawableBottomClickListener != null) {
                return true;
            }
        }
        return false;
    }

}
