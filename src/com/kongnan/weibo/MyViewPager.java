package com.kongnan.weibo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;

public class MyViewPager extends FrameLayout {

    private static final String TAG = MyViewPager.class.getSimpleName();
    /**
     * 认为是用户滑动的最小距离
     */
    private int mSlop;
    /**
     * 滑动的最小速度
     */
    private int mMinFlingVelocity;
    /**
     * 滑动的最大速度
     */
    private int mMaxFlingVelocity;
    /**
     * 执行动画的时间
     */
    protected long mAnimationTime = 150;
    /**
     * 用来标记用户是否正在滑动中
     */
    private boolean mSwiping;
    /**
     * 滑动速度检测类
     */
    private VelocityTracker mVelocityTracker;
    /**
     * 手指按下的position
     */
    private int mDownPosition;
    /**
     * 按下的item对应的View
     */
    private View mDownView;
    private float mDownX;
    private float mDownY;

    private int mWidth;

    private int mFlaggingWidth;

    public MyViewPager(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyViewPager(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        ViewConfiguration vc = ViewConfiguration.get(context);
        mSlop = vc.getScaledTouchSlop();
        mMinFlingVelocity = vc.getScaledMinimumFlingVelocity() * 8; // 获取滑动的最小速度
        mMaxFlingVelocity = vc.getScaledMaximumFlingVelocity(); // 获取滑动的最大速度

        // 获取分辨率
        DisplayMetrics dm = context.getApplicationContext().getResources().getDisplayMetrics();
        mWidth = dm.widthPixels;
        mFlaggingWidth = mWidth / 4;
    }

    private OnPageChangeListener onPageChangeListener;

    public void setOnPageChangeListener(OnPageChangeListener onPageChangeListener) {
        this.onPageChangeListener = onPageChangeListener;
    }

    public static float clamp(float value, float max, float min) {
        return Math.max(Math.min(value, min), max);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        // Log.d(TAG, "onTouchEvent" + ev.getAction());
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                handleActionDown(ev);
                break;
            case MotionEvent.ACTION_MOVE:
                handleActionMove(ev);
                return super.onTouchEvent(ev);
            case MotionEvent.ACTION_UP:
                handleActionUp(ev);
                break;
        }
        // return super.onTouchEvent(ev);
        return true;
    }

    /**
     * 按下事件处理
     * 
     * @param ev
     * @return
     */
    private void handleActionDown(MotionEvent ev) {
        mSwiping = false;
        mIsStartRight = false;
        mIsStart = false;
        mDownX = ev.getX();
        mDownY = ev.getY();

        // mDownView = getChildAt(0);

        // 加入速度检测
        mVelocityTracker = VelocityTracker.obtain();
        mVelocityTracker.addMovement(ev);
    }

    private boolean mIsStart = false;
    private boolean mIsStartRight = false;

    /**
     * 处理手指滑动的方法
     * 
     * @param ev
     * @return
     */
    private void handleActionMove(MotionEvent ev) {

        float deltaX = ev.getX() - mDownX;
        float deltaY = ev.getY() - mDownY;

        boolean dismissRight = deltaX > 0;// 是否往右边
        if (deltaX != 0 && !mIsStart) {
            mIsStart = true;
            mIsStartRight = dismissRight;
        }
        mSwiping = true;

        if (dismissRight) {
            float ratio = clamp(deltaX / (float) mWidth, 0.0f, 1.0f);
            onPageChangeListener.onRight(mDownPosition, ratio);
        } else {
            float ratio = clamp(-deltaX / (float) mWidth, 0.0f, 1.0f);
            onPageChangeListener.onLeft(mDownPosition, ratio);
        }
    }

    /**
     * 手指抬起的事件处理
     * 
     * @param ev
     */
    private void handleActionUp(MotionEvent ev) {
        if (mVelocityTracker == null || !mSwiping) {
            return;
        }

        float deltaX = ev.getX() - mDownX;

        // 通过滑动的距离计算出X,Y方向的速度
        mVelocityTracker.computeCurrentVelocity(1000);
        float velocityX = Math.abs(mVelocityTracker.getXVelocity());
        float velocityY = Math.abs(mVelocityTracker.getYVelocity());

        boolean dismiss = false; // item是否要滑出屏幕
        boolean dismissRight = deltaX > 0;// 是否往右边
        // 当拖动item的距离大于item的 4/1，item滑出屏幕
        if (Math.abs(deltaX) > mFlaggingWidth) {
            dismiss = true;
            // 手指在屏幕滑动的速度在某个范围内，也使得item滑出屏幕
        } else if (mMinFlingVelocity <= velocityX && velocityX <= mMaxFlingVelocity
                && velocityY < velocityX) {
            dismiss = true;
        }

        // 动画
        if (mIsStartRight) {
            if (mDownPosition != 0) {
                float ratio = clamp(deltaX / (float) mWidth, 0.0f, 1.0f);
                onPageChangeListener.onRightEnd(mDownPosition, ratio, dismiss);
            }
        } else {
            float ratio = clamp(-deltaX / (float) mWidth, 0.0f, 1.0f);
            onPageChangeListener.onLeftEnd(mDownPosition, ratio, dismiss);
        }
        // 切换
        if (dismiss) {
            if (mIsStartRight) {
                mDownPosition = Math.max(mDownPosition - 1, 0);
            } else {
                mDownPosition = Math.min(mDownPosition + 1, getChildCount() - 2);
            }
        }

        // 移除速度检测
        if (mVelocityTracker != null) {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
        mSwiping = false;
        mIsStartRight = false;
        mIsStart = false;
    }

    public interface OnPageChangeListener {
        public void onLeft(int position, float ratio);

        public void onLeftEnd(int position, float ratio, boolean dismiss);

        public void onRight(int position, float ratio);

        public void onRightEnd(int position, float ratio, boolean dismiss);
    }
}
