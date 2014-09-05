package com.kongnan.weibo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.view.ViewPager.PageTransformer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

public class MainActivity extends Activity implements OnPageChangeListener {

    private ViewPager mViewPager;

    private FragementPagerAdapter mAdapter;

    private List<View> mPagerViews = new ArrayList<View>();

    // PageView 1
    private View mP1RootView;
    boolean isChronometerRunnig = false;
    private Chronometer mP1Time;
    private VideoView mP1VideoView;// VideoView 无法旋转缩放，但是可以左右移动。
    private Uri mP1VVUriPath;

    // PageView 2
    AnimatorSet mP2AnimatorSet = new AnimatorSet();
    private View mP2RootView;

    private TextView mP2TimeTV;
    private ImageView mP2ImageView;

    private ImageView mP2BBIV;
    private ImageView mP2BSIV;

    private ImageView mP2SWIV;
    private ImageView mP2BWIV;
    // PageView 4
    AnimatorSet mP4AnimatorSetOne = new AnimatorSet();
    AnimatorSet mP4AnimatorSet = new AnimatorSet();
    AnimatorSet mP4AnimatorSetAllInOne = new AnimatorSet();
    private ImageView mP4EarthIV;
    private ImageView mP4EarthCloudIV;
    private ImageView mP4ManIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getActionBar().hide();

        mViewPager = (ViewPager) findViewById(R.id.viewPager);

        View pageView1 = LayoutInflater.from(this).inflate(R.layout.layout_tutorial_1, null);

        mP1RootView = pageView1.findViewById(R.id.p1RootView);
        mP1VideoView = (VideoView) pageView1.findViewById(R.id.p1VideoView);
        mP1VVUriPath = Uri.parse("android.resource://" + getPackageName() + "/"
                + R.raw.guide_a_motion);

        mP1Time = (Chronometer) pageView1.findViewById(R.id.p1Chronometer);

        mPagerViews.add(pageView1);

        View pageView2 = LayoutInflater.from(this).inflate(R.layout.layout_tutorial_2, null);

        mP2RootView = pageView2.findViewById(R.id.p2RootView);

        mP2TimeTV = (TextView) pageView2.findViewById(R.id.p2time);

        // mP2ImageView = (ImageView) pageView2.findViewById(R.id.p2iv);
        mP2BBIV = (ImageView) pageView2.findViewById(R.id.p2bbIV);
        mP2BSIV = (ImageView) pageView2.findViewById(R.id.p2bsIV);

        mP2SWIV = (ImageView) pageView2.findViewById(R.id.p2swIV);
        mP2BWIV = (ImageView) pageView2.findViewById(R.id.p2bwIV);

        mPagerViews.add(pageView2);

        View pageView4 = LayoutInflater.from(this).inflate(R.layout.layout_tutorial_4, null);
        mP4EarthIV = (ImageView) pageView4.findViewById(R.id.p4EarthIV);
        mP4EarthCloudIV = (ImageView) pageView4.findViewById(R.id.p4EarthCloudIV);
        mP4ManIV = (ImageView) pageView4.findViewById(R.id.p4ManIV);
        mPagerViews.add(pageView4);

        mAdapter = new FragementPagerAdapter();
        mViewPager.setPageTransformer(true, new PageTransformer() {

            @Override
            public void transformPage(View page, float position) {

            }
        });
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOnPageChangeListener(this);

        animal(0);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            mP2RootView.setPivotX(mP2RootView.getWidth() / 4 * 3);
            int height = mP2RootView.getHeight();
            mP2RootView.setPivotY((float) (height - height * 0.15));
        }
    }

    private class FragementPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mPagerViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mPagerViews.get(position));
            return mPagerViews.get(position);

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        // mP1VideoView.stopPlayback();
        // mP1VideoView.clearFocus();
    }

    @Override
    public void onPageSelected(int position) {
        animal(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        // mP1VideoView.stopPlayback();
        // mP1VideoView.clearFocus();
    }

    float BALL_SIZE = 100f;

    private void animal(int position) {

        // mP2AnimatorSet.end();
        mP4AnimatorSetAllInOne.end();

        switch (position) {
            case 0:
                // mP2AnimatorSet.cancel();
                // mP2RootView.clearAnimation();
                mP1VideoView.setVisibility(View.VISIBLE);

                if (!isChronometerRunnig) {
                    isChronometerRunnig = true;
                    // mP1VideoView.getHolder().addCallback(this);
                    // mP1VideoView.stopPlayback();
                    mP1VideoView.setVideoURI(mP1VVUriPath);
                    mP1VideoView.requestFocus();
                    mP1VideoView.start();
                    mP1VideoView.setOnPreparedListener(new OnPreparedListener() {

                        public void onPrepared(MediaPlayer mp) {
                            mp.start();
                            mp.setLooping(true);
                        }
                    });
                    // if (!isChronometerRunnig) {
                    // isChronometerRunnig = true;
                    mP1Time.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                        @Override
                        public void onChronometerTick(Chronometer cArg) {
                            long time = SystemClock.elapsedRealtime() - cArg.getBase();
                            Date date = new Date(time);
                            DateFormat formatter = new SimpleDateFormat("mm:ss");
                            String dateFormatted = formatter.format(date);
                            if (dateFormatted.equals("01:00"))
                                mP1Time.stop();
                            cArg.setText(dateFormatted);

                        }
                    });
                    mP1Time.setBase(SystemClock.elapsedRealtime());
                    mP1Time.start();
                } else {
                    // TODO 尝试N次没法解决黑屏不重新播放的问题
                    mP1VideoView.requestLayout();
                    mP1VideoView.invalidate();
                    mP1VideoView.resume();
                    mP1VideoView.pause();
                }
                break;
            case 1:
                mP2TimeTV.setText(mP1Time.getText());
                mP1VideoView.stopPlayback();
                mP1VideoView.clearFocus();
                mP1VideoView.setVisibility(View.GONE);
                // mP2RootView.requestFocus();
                // mP2RootView.requestLayout();
                PropertyValuesHolder pvsX = PropertyValuesHolder.ofFloat("scaleX", 1f, 0.7f);
                PropertyValuesHolder pvsY = PropertyValuesHolder.ofFloat("scaleY", 1f, 0.7f);
                PropertyValuesHolder pvrXY = PropertyValuesHolder.ofFloat("rotation", 0f, -5f);
                ObjectAnimator whxyBouncer = ObjectAnimator.ofPropertyValuesHolder(mP2RootView,
                        pvsX, pvsY, pvrXY).setDuration(1000);
                whxyBouncer.setRepeatCount(0);
                whxyBouncer.setRepeatMode(ValueAnimator.INFINITE);

                PropertyValuesHolder tlSWY = PropertyValuesHolder.ofFloat("translationY", -50f,
                        0f);
                PropertyValuesHolder apSWY = PropertyValuesHolder.ofFloat("alpha", 0.25f, 1f);
                PropertyValuesHolder roSWXY = PropertyValuesHolder.ofFloat("rotation", -25f, 0f);
                mP2SWIV.setPivotX(-50);
                mP2SWIV.setPivotY(-50);
                ObjectAnimator swBouncer = ObjectAnimator.ofPropertyValuesHolder(mP2SWIV, tlSWY,
                        roSWXY, apSWY).setDuration(500);

                PropertyValuesHolder tlBWY = PropertyValuesHolder.ofFloat("translationY", -100f,
                        0f);
                PropertyValuesHolder tlBWX = PropertyValuesHolder.ofFloat("translationX", 50f,
                        0f);
                PropertyValuesHolder apBWY = PropertyValuesHolder.ofFloat("alpha", 0f, 1f);
                PropertyValuesHolder roBWXY = PropertyValuesHolder.ofFloat("rotation", -60f, 0f);
                mP2BWIV.setPivotX(-100);
                mP2BWIV.setPivotY(-100);
                ObjectAnimator bwBouncer = ObjectAnimator.ofPropertyValuesHolder(mP2BWIV, tlBWY,
                        tlBWX, roBWXY, apBWY).setDuration(500);

                PropertyValuesHolder apBBY = PropertyValuesHolder.ofFloat("alpha", 0f, 1f);
                PropertyValuesHolder roBBXY = PropertyValuesHolder.ofFloat("rotation", -15f, 0f);
                mP2BBIV.setPivotX(mP2BBIV.getWidth() / 2);
                mP2BBIV.setPivotY(-100);
                ObjectAnimator bbBouncer = ObjectAnimator.ofPropertyValuesHolder(mP2BBIV, apBBY,
                        roBBXY);

                PropertyValuesHolder scBSX = PropertyValuesHolder.ofFloat("scaleX", 1.5f, 1.1f);
                PropertyValuesHolder scBSY = PropertyValuesHolder.ofFloat("scaleY", 1.5f, 1.1f);
                PropertyValuesHolder apBSY = PropertyValuesHolder.ofFloat("alpha", 0f, 1f);
                PropertyValuesHolder roBSXY = PropertyValuesHolder.ofFloat("rotation", -45f, 0f);
                // mP2BSIV.setPivotX(mP2BBIV.getWidth() / 2);
                // mP2BSIV.setPivotY(mP2BBIV.getWidth() / 2);
                ObjectAnimator bsBouncer = ObjectAnimator.ofPropertyValuesHolder(mP2BSIV, scBSX,
                        scBSY, apBSY, roBSXY);

                // mP2AnimatorSet.cancel();
                mP2AnimatorSet.playTogether(swBouncer, bbBouncer, bsBouncer, bwBouncer,
                        whxyBouncer);
                // mP2AnimatorSet.setStartDelay(1000);
                mP2AnimatorSet.setDuration(500);
                mP2AnimatorSet.start();
                break;

            case 2:

                ObjectAnimator roxyBouncerEOne = ObjectAnimator.ofFloat(mP4EarthIV, "rotation",
                        0f, 180f);
                // roxyBouncerEOne.setDuration(500);
                roxyBouncerEOne.setRepeatCount(0);

                ObjectAnimator roxyBouncerECOne = ObjectAnimator.ofFloat(mP4EarthCloudIV,
                        "rotation", 0f, 180f);
                // roxyBouncerECOne.setDuration(500);
                roxyBouncerECOne.setRepeatCount(0);

                ObjectAnimator tyBouncer = ObjectAnimator.ofFloat(mP4ManIV, "translationY", 0f,
                        60f, 0f);
                // CycleInterpolator interpolator = new CycleInterpolator(3.0f);
                // roxyBouncerE.setInterpolator(lin);
                // roxyBouncer.setStartDelay(500);
                tyBouncer.setDuration(2000);
                tyBouncer.setRepeatCount(Animation.INFINITE);// Animation.INFINITE

                ObjectAnimator roxyBouncerE = ObjectAnimator.ofFloat(mP4EarthIV, "rotation", 0f,
                        359f);
                // CycleInterpolator interpolator = new CycleInterpolator(3.0f);
                LinearInterpolator lin = new LinearInterpolator();
                // roxyBouncerE.setInterpolator(lin);
                // roxyBouncer.setStartDelay(500);
                roxyBouncerE.setDuration(40000);
                roxyBouncerE.setRepeatCount(Animation.INFINITE);// Animation.INFINITE
                // roxyBouncer.setInterpolator(interpolator);
                // mP4EarthIV.setPivotX(mP4EarthIV.getWidth() * 0.5f);
                // mP4EarthIV.setPivotY(mP4EarthIV.getHeight() * 0.5f);
                // roxyBouncer.start();

                ObjectAnimator roxyBouncerEC = ObjectAnimator.ofFloat(mP4EarthCloudIV,
                        "rotation", 0f, 359f);
                // roxyBouncerEC.setInterpolator(lin);
                roxyBouncerEC.setDuration(60000);
                roxyBouncerEC.setRepeatCount(Animation.INFINITE);

                // mP4AnimatorSet.setDuration(50000);
                mP4AnimatorSet.setInterpolator(lin);
                mP4AnimatorSet.playTogether(roxyBouncerE, roxyBouncerEC, tyBouncer);
                // mP4AnimatorSet.start();

                mP4AnimatorSetOne.setDuration(400);
                mP4AnimatorSetOne.setInterpolator(lin);
                mP4AnimatorSetOne.playTogether(roxyBouncerEOne, roxyBouncerECOne);
                // mP4AnimatorSetOne.start();

                mP4AnimatorSetAllInOne = new AnimatorSet();
                mP4AnimatorSetAllInOne.playSequentially(mP4AnimatorSetOne, mP4AnimatorSet);
                mP4AnimatorSetAllInOne.start();

                break;
        }
    }
}
