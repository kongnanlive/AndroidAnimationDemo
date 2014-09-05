package com.kongnan.weibo;

import java.lang.reflect.Field;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;

public class MyImageView extends ImageView {

    private static final String TAG = MyImageView.class.getSimpleName();

    public MyImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public MyImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyImageView(Context context) {
        super(context);
        init(context);
    }

    private int width, height = 0;

    private void init(Context context) {
        // 获取分辨率
        DisplayMetrics dm = context.getApplicationContext().getResources().getDisplayMetrics();
        width = dm.widthPixels;
        // 高度不对
        // 应该去掉 状态栏
        height = dm.heightPixels;
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, sbar = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            sbar = getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        height = height - sbar;
        Log.d(TAG, "width" + width + "height" + height);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(width, height);
        // Log.d(TAG, "onMeasure :" + widthMeasureSpec + "/" + heightMeasureSpec);
    }

    @Override
    public void layout(int l, int t, int r, int b) {
        super.layout(l, t, r, b);

        Log.d(TAG, "layout :" + l + t + r + b);
    }
}
