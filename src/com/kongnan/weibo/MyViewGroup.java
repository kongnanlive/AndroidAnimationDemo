package com.kongnan.weibo;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class MyViewGroup extends ViewGroup {

    public MyViewGroup(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MyViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyViewGroup(Context context) {
        super(context);

        // AbsoluteLayout
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int count = getChildCount();

        int maxHeight = 0;
        int maxWidth = 0;

        // Find out how big everyone wants to be
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        // Find rightmost and bottom-most child
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                int childRight;
                int childBottom;

                // ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) child.getLayoutParams();

                childRight = child.getMeasuredWidth();
                childBottom = child.getMeasuredHeight();

                // Rect mCachedBounds = new Rect();
                // Drawable drawable = child.getBackground();
                // if (i == 0 && drawable != null) {
                //
                // drawable.getPadding(mCachedBounds);
                // int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
                // int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
                //
                // // child.measure(
                // // MeasureSpec.makeMeasureSpec(widthSpecSize + mCachedBounds.left
                // // + mCachedBounds.right, MeasureSpec.EXACTLY),
                // // MeasureSpec.makeMeasureSpec(heightSpecSize + mCachedBounds.top
                // // + mCachedBounds.bottom, MeasureSpec.EXACTLY));
                // }

                maxWidth = Math.max(maxWidth, childRight);
                maxHeight = Math.max(maxHeight, childBottom);
            }
        }

        // Account for padding too
        maxWidth += getPaddingLeft() + getPaddingRight();
        maxHeight += getPaddingTop() + getPaddingBottom();

        // Check against minimum height and width
        maxHeight = Math.max(maxHeight, getSuggestedMinimumHeight());
        maxWidth = Math.max(maxWidth, getSuggestedMinimumWidth());

        setMeasuredDimension(resolveSizeAndState(maxWidth, widthMeasureSpec, 0),
                resolveSizeAndState(maxHeight, heightMeasureSpec, 0));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();

        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != GONE) {

                Rect mCachedBounds = new Rect();
                Drawable drawable = child.getBackground();
                if (drawable != null) {
                    // child.measure(0, 0);
                    drawable.getPadding(mCachedBounds);
                }

                int childLeft = -mCachedBounds.left;
                int childTop = -mCachedBounds.top;

                child.layout(childLeft, childTop,
                        child.getMeasuredWidth() + mCachedBounds.right,
                        child.getMeasuredHeight() + mCachedBounds.bottom);

                // child.layout(0, 0, child.getMeasuredWidth(), child.getMeasuredHeight());
            }
        }
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    @Override
    public boolean shouldDelayChildPressedState() {
        return false;
    }
}
