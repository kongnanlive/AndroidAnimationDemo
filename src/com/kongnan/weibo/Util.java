package com.kongnan.weibo;

import android.content.Context;
import android.util.TypedValue;

public class Util {
    public static float dp2px(Context context, int dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources()
                .getDisplayMetrics());
    }

    public static float sp2px(Context context, int sp) {
        // final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        // return (int) (spValue * fontScale + 0.5f);
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources()
                .getDisplayMetrics());
    }
}
