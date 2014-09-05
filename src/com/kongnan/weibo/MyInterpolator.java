package com.kongnan.weibo;

import android.view.animation.Interpolator;

public class MyInterpolator implements Interpolator {

    @Override
    public float getInterpolation(float t) {
        t = t * 1.43f;
        if (t == 1.0f) {
            return 1.0f;
        }
        return t;
    }

}
