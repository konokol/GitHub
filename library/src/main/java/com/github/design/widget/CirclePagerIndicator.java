package com.github.design.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.view.View;

import com.github.design.R;
import com.github.utils.Utils;

/**
 * circle indicator for view pager
 *
 * @author      Ivan at 2016-12-09  15:41
 * @version     v0.1
 * @since       v0.1
 */

public class CirclePagerIndicator extends View {

    private int mDefaultCircleSize = Utils.dp2px(10);
    private float mDefaultStrokeWidth = Utils.dp2px(2f);

    private int mUnselectedColor = Color.GRAY;
    private int mSelectedColor = Color.DKGRAY;
    private float mStrokeWidth = mDefaultStrokeWidth;

    private int mCount;
    private int mPosition;
    private float mScrollPositionOffset;

    private Paint mUnselectedPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mSelectedPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private ViewPager mViewPager;

    public CirclePagerIndicator(Context context) {
        super(context);
        init();
    }

    public CirclePagerIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CirclePagerIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CirclePagerIndicator);
        mUnselectedColor = array.getColor(R.styleable.CirclePagerIndicator_unselected_color, Color.GRAY);
        mSelectedColor = array.getColor(R.styleable.CirclePagerIndicator_selected_color, Color.DKGRAY);
        mStrokeWidth = array.getDimension(R.styleable.CirclePagerIndicator_stroke_width, mDefaultStrokeWidth);
        init();
        array.recycle();
    }

    private void init() {
        mUnselectedPaint.setColor(mUnselectedColor);
        mUnselectedPaint.setStyle(Paint.Style.STROKE);
        mUnselectedPaint.setStrokeWidth(mStrokeWidth);
        mSelectedPaint.setColor(mSelectedColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width, height;

        try {
            mCount = mViewPager.getAdapter().getCount();
        } catch (Exception e) {
            return;
        }

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int defaultSize = mDefaultCircleSize * (2 * mCount -1);

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            width = Math.min(defaultSize, widthSize);
        } else {
            width = defaultSize;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            height = Math.min(mDefaultCircleSize, heightSize);
        } else {
            height = mDefaultCircleSize;
        }
        setMeasuredDimension(width, height);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        if (mViewPager == null) {
            return;
        }

        float width = getMeasuredWidth();
        float height = getMeasuredHeight();
        float r = Math.min(width / mCount, height) / 2;

        for (int i = 0; i < mCount; i++) {
            canvas.drawCircle(r + i * 4 * r, r, r - mStrokeWidth, mUnselectedPaint);
        }

        float rx = r *(4 * mPosition +1) + 4 * mScrollPositionOffset * r;
        canvas.drawCircle(rx, r, r - mStrokeWidth, mSelectedPaint);

    }

    public void setViewPager(ViewPager viewPager) {
        this.mViewPager = viewPager;
        if (mViewPager != null) {
            mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    mPosition = position;
                    mScrollPositionOffset = positionOffset;
                    invalidate();
                }

                @Override
                public void onPageSelected(int position) {
                    mPosition = position;
                    invalidate();
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }
    }

}
