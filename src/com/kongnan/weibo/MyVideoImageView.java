package com.kongnan.weibo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

public class MyVideoImageView extends ImageView {

    private static final String TAG = MyVideoImageView.class.getSimpleName();

    private Rect mDrawableRect;
    private Bitmap mBitmap;
    private BitmapShader mBitmapShader;
    private Paint mBitmapPaint;
    private Matrix mShaderMatrix;
    private int mBitmapWidth;
    private int mBitmapHeight;

    private int mTopRect;
    private int mBottomRect;

    private String mTime;

    private float mTextFontSize;
    private float mTextX;
    private float mTextY;

    public MyVideoImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public MyVideoImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyVideoImageView(Context context) {
        super(context);
        init(context);
    }

    public void setTimeText(String time) {
        mTime = time;
    }

    private boolean isReverse = false;

    public void setTranslationHight(float i) {
        Log.d(TAG, "translationHight :" + i);
        Log.d(TAG, "mTopRect :" + mTopRect + "/mBottomRect" + mBottomRect);
        // 做个保护
        if (i > 1 || i < 0)
            return;
        if (i == 1) {
            // mTopRect = 0;
            // mBottomRect = getBottom();
            invalidate();
            return;
        }
        // 动画一半，不做任何改变
        if (i > 0.5) {
            // return;
            i = 0.5f;
        }
        // 小于 10分之2 停止裁剪
        if (i < 0.2) {
            return;
        }
        // Log.d(TAG, "setTranslationHight :" + i);

        mTopRect = (int) (-getHeight() * (i - 0.5));
        mBottomRect = getHeight() - mTopRect;

        invalidate();
    }

    private void init(Context context) {
        mTextX = Util.dp2px(getContext(), 180);
        mTextY = Util.dp2px(getContext(), 40);
        mTextFontSize = Util.sp2px(getContext(), 18);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        // super.onDraw(canvas);
        if (getDrawable() == null) {
            return;
        }
        // Log.d(TAG, "onDraw()");
        // if (isStart) {

        RectF rect = new RectF(0, mTopRect, getRight(), mBottomRect);

        // RectF rect = new RectF(getPaddingLeft(), getPaddingTop(), getRight() - getLeft()
        // - getPaddingRight(), getBottom() - getTop() - getPaddingBottom());

        canvas.drawRect(rect, mBitmapPaint);
        // }

        Paint p = new Paint();
        p.setColor(Color.BLACK);
        p.setTextSize(mTextFontSize);
        canvas.drawText(mTime, mTextX, mTextY, p);// 画文本
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        mBitmap = bm;
        // mBitmap = drawableToBitmap(getDrawable());
        setup();
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        // 取 drawable 的长宽
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();

        // 取 drawable 的颜色格式
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        // 建立对应 bitmap
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        // 建立对应 bitmap 的画布
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        // 把 drawable 内容画到画布中
        drawable.draw(canvas);
        return bitmap;
    }

    private void setup() {
        if (mBitmap == null) {
            return;
        }

        mTopRect = 0;
        mBottomRect = getBottom();

        mBitmapShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        mBitmapPaint = new Paint();
        mBitmapPaint.setAntiAlias(true);
        mBitmapPaint.setShader(mBitmapShader);

        mBitmapHeight = mBitmap.getHeight();
        mBitmapWidth = mBitmap.getWidth();

        updateShaderMatrix();
        invalidate();
    }

    private void updateShaderMatrix() {

        float scaleX;
        float scaleY;

        mShaderMatrix = new Matrix();
        mShaderMatrix.set(null);

        mDrawableRect = new Rect(0, 0, getRight() - getLeft(), getBottom() - getTop());

        scaleX = mDrawableRect.width() / (float) mBitmapWidth;
        scaleY = mDrawableRect.height() / (float) mBitmapHeight;
        mShaderMatrix.setScale(scaleX, scaleY);

        mBitmapShader.setLocalMatrix(mShaderMatrix);
    }
}
