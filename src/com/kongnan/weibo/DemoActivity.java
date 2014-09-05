package com.kongnan.weibo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.animation.Animator;
import android.animation.Animator.AnimatorPauseListener;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import android.widget.VideoView;

import com.kongnan.weibo.MyAnimatorSet.MyAnimatorListener;

public class DemoActivity extends Activity implements MyViewPager.OnPageChangeListener {

    private static final String TAG = DemoActivity.class.getSimpleName();

    // PageView 1
    private View mPageView1;
    private View mP1RootView;
    boolean isChronometerRunnig = false;
    int stoppedMilliseconds = 0;
    private Chronometer mP1Time;
    private VideoView mP1VideoView;// VideoView 无法旋转缩放，但是可以左右移动。
    private Uri mP1VVUriPath;

    private MyVideoImageView mVideoImageView;

    // PageView 2
    private boolean isP2Start = false;
    private boolean isP2Reverse = false;

    private MyAnimatorSet mOpenP2AnimatorSet;
    private MyAnimatorSet mCloseP2AnimatorSet;
    private View mPageView2;
    private View mP2RootView;

    private TextView mP2TimeTV;

    private ImageView mP2BBIV;
    private ImageView mP2BSIV;

    private ImageView mP2SWIV;
    private ImageView mP2BWIV;
    // PageView 3
    private boolean isP3Start = false;
    private boolean isP3Reverse = false;

    private MyAnimatorSet mOpenP3AnimatorSet;
    private MyAnimatorSet mCloseP3AnimatorSet;
    private View mPageView3;
    private View mP3RootView;
    private ImageView mP3SWIV;

    private ImageView mP3CBIV;
    private ImageView mP3CSIV;

    private ImageView mP3CLIV;
    private ImageView mP3CRIV;

    // PageView 4
    private boolean isP4Start = false;
    private boolean isP4Reverse = false;

    private MyAnimatorSet mOpenP4AnimatorSet;
    private MyAnimatorSet mCloseP4AnimatorSet;
    private View mPageView4;
    private MyAnimatorSet mP4AnimatorSet;
    private MyAnimatorSet mP4AnimatorSetAllInOne;
    private ImageView mP4EarthIV;
    private ImageView mP4EarthCloudIV;
    private ImageView mP4ManIV;
    private ImageView mP4DBIV;
    private ImageView mP4DBCIV;
    private TextView mP4DCTV;
    private ObjectAnimator mP4MantyBouncer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().hide();

        MyViewPager contentView = new MyViewPager(this);
        contentView.setBackgroundColor(0xFFc5f2fe);
        setContentView(contentView);

        contentView.setOnPageChangeListener(this);

        mPageView1 = LayoutInflater.from(this).inflate(R.layout.layout_tutorial_1, null);

        mP1RootView = mPageView1.findViewById(R.id.p1RootView);
        mP1VideoView = (VideoView) mPageView1.findViewById(R.id.p1VideoView);
        mP1VVUriPath = Uri.parse("android.resource://" + getPackageName() + "/"
                + R.raw.guide_a_motion);

        mP1Time = (Chronometer) mPageView1.findViewById(R.id.p1Chronometer);

        contentView.addView(mPageView1);

        mPageView2 = LayoutInflater.from(this).inflate(R.layout.layout_tutorial_2, null);

        mP2RootView = mPageView2.findViewById(R.id.p2RootView);

        mP2TimeTV = (TextView) mPageView2.findViewById(R.id.p2time);

        mP2BBIV = (ImageView) mPageView2.findViewById(R.id.p2bbIV);
        mP2BSIV = (ImageView) mPageView2.findViewById(R.id.p2bsIV);

        mP2SWIV = (ImageView) mPageView2.findViewById(R.id.p2swIV);
        mP2BWIV = (ImageView) mPageView2.findViewById(R.id.p2bwIV);
        // mPageView2.setVisibility(View.GONE);
        mPageView2.setAlpha(0f);
        contentView.addView(mPageView2);

        mPageView3 = LayoutInflater.from(this).inflate(R.layout.layout_tutorial_3, null);

        mP3RootView = mPageView3.findViewById(R.id.p3RootView);
        mP3SWIV = (ImageView) mPageView3.findViewById(R.id.p3swIV);

        mP3CBIV = (ImageView) mPageView3.findViewById(R.id.p3cbIV);
        mP3CSIV = (ImageView) mPageView3.findViewById(R.id.p3csIV);

        mP3CLIV = (ImageView) mPageView3.findViewById(R.id.p3clIV);
        mP3CRIV = (ImageView) mPageView3.findViewById(R.id.p3crIV);

        mPageView3.setAlpha(0f);
        contentView.addView(mPageView3);

        mPageView4 = LayoutInflater.from(this).inflate(R.layout.layout_tutorial_4, null);
        mP4EarthIV = (ImageView) mPageView4.findViewById(R.id.p4EarthIV);
        mP4EarthCloudIV = (ImageView) mPageView4.findViewById(R.id.p4EarthCloudIV);
        mP4ManIV = (ImageView) mPageView4.findViewById(R.id.p4ManIV);
        mP4DBIV = (ImageView) mPageView4.findViewById(R.id.p4dbIV);
        mP4DBCIV = (ImageView) mPageView4.findViewById(R.id.p4dbcIV);
        mP4DCTV = (TextView) mPageView4.findViewById(R.id.p4dcTV);

        mPageView4.setAlpha(0f);
        // mPageView4.setVisibility(View.GONE);
        contentView.addView(mPageView4);

        mVideoImageView = new MyVideoImageView(this);
        mVideoImageView.setScaleType(ScaleType.FIT_XY);
        contentView.addView(mVideoImageView);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
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
            }
        }
    }

    @Override
    public void onLeft(int position, float ratio) {
        if (ratio == 0)
            return;
        // Log.d(TAG, "onLeft" + position + "/" + ratio);
        switch (position) {
            case 0: {
                createOpenAnimatorSetP2();
                mOpenP2AnimatorSet.setCurrentPlayTimeAtRatio(ratio);
            }
                break;
            case 1: {
                createOpenAnimatorSetP3();
                mOpenP3AnimatorSet.setCurrentPlayTimeAtRatio(ratio);
            }
                break;
            case 2: {
                createOpenAnimatorSetP4();
                mOpenP4AnimatorSet.setCurrentPlayTimeAtRatio(ratio);
                // ArrayList<Animator> animators = mOpenP4AnimatorSet.getChildAnimations();
                // for (Animator animator : animators) {
                // ValueAnimator valueAnimator = (ValueAnimator) animator;
                // if (valueAnimator.getRepeatCount() != -1) {
                // long cpt = (long) (animator.getDuration() * ratio);
                // valueAnimator.setCurrentPlayTime(cpt);
                // } else if (!valueAnimator.isRunning()) {
                // valueAnimator.start();
                // }
                // }
            }
                break;

            default:
                break;
        }
    }

    @Override
    public void onRight(int position, float ratio) {
        if (ratio == 0)
            return;
        // Log.d(TAG, "onRight" + position + "/" + ratio);
        switch (position) {
            case 1: {
                createCloseAnimatorSetP2();
                mCloseP2AnimatorSet.setCurrentPlayTimeAtRatio(1 - ratio);
            }
                break;
            case 2: {
                createCloseAnimatorSetP3();
                mCloseP3AnimatorSet.setCurrentPlayTimeAtRatio(1 - ratio);
            }
                break;
            case 3: {
                createCloseAnimatorSetP4();
                mCloseP4AnimatorSet.setCurrentPlayTimeAtRatio(1 - ratio);
            }
                break;
            default:
                break;
        }
    }

    @Override
    public void onLeftEnd(int position, float ratio, boolean dismiss) {
        Log.d(TAG, "onLeftEnd" + position + "/" + dismiss);
        switch (position) {
            case 0: {
                createOpenAnimatorSetP2();
                if (dismiss) {
                    mOpenP2AnimatorSet.start();
                    mOpenP2AnimatorSet.pause();
                    mOpenP2AnimatorSet.setCurrentPlayTimeAtRatio(ratio);
                } else {
                    mOpenP2AnimatorSet.reverse();
                    mOpenP2AnimatorSet.pause();
                    mOpenP2AnimatorSet.setCurrentPlayTimeAtRatio(1 - ratio);
                }
                mOpenP2AnimatorSet.resume();
            }
                break;
            case 1: {
                createOpenAnimatorSetP3();
                if (dismiss) {
                    mOpenP3AnimatorSet.start();
                    mOpenP3AnimatorSet.pause();
                    mOpenP3AnimatorSet.setCurrentPlayTimeAtRatio(ratio);
                } else {
                    mOpenP3AnimatorSet.reverse();
                    mOpenP3AnimatorSet.pause();
                    mOpenP3AnimatorSet.setCurrentPlayTimeAtRatio(1 - ratio);
                }
                mOpenP3AnimatorSet.resume();
            }
                break;
            case 2: {
                createOpenAnimatorSetP4();
                if (dismiss) {
                    mOpenP4AnimatorSet.start();
                    mOpenP4AnimatorSet.pause();
                    mOpenP4AnimatorSet.setCurrentPlayTimeAtRatio(ratio);
                } else {
                    mOpenP4AnimatorSet.reverse();
                    mOpenP4AnimatorSet.pause();
                    mOpenP4AnimatorSet.setCurrentPlayTimeAtRatio(1 - ratio);
                }
                mOpenP4AnimatorSet.resume();
                // ArrayList<Animator> animators = mOpenP4AnimatorSet.getChildAnimations();
                // for (Animator animator : animators) {
                // ValueAnimator valueAnimator = (ValueAnimator) animator;
                // if (valueAnimator.getRepeatCount() != -1) {
                // // valueAnimator.setStartDelay(1000);
                // if (dismiss) {
                // valueAnimator.start();
                // valueAnimator.pause();
                // long cpt = (long) (animator.getDuration() * ratio);
                // valueAnimator.setCurrentPlayTime(cpt);
                // } else {
                // valueAnimator.reverse();
                // valueAnimator.pause();
                // long cpt = animator.getDuration()
                // - (long) (animator.getDuration() * ratio);
                // valueAnimator.setCurrentPlayTime(cpt);
                // }
                //
                // valueAnimator.resume();
                // }
                // }
            }
                break;

            default:
                break;
        }

    }

    @Override
    public void onRightEnd(int position, float ratio, boolean dismiss) {
        Log.d(TAG, "onRightEnd" + position + "/" + dismiss);
        switch (position) {
            case 1: {
                createCloseAnimatorSetP2();
                if (!dismiss) {
                    mCloseP2AnimatorSet.start();
                    mCloseP2AnimatorSet.pause();
                    mCloseP2AnimatorSet.setCurrentPlayTimeAtRatio(1 - ratio);
                } else {
                    mCloseP2AnimatorSet.reverse();
                    mCloseP2AnimatorSet.pause();
                    mCloseP2AnimatorSet.setCurrentPlayTimeAtRatio(ratio);
                }
                mCloseP2AnimatorSet.resume();
            }
                break;
            case 2: {
                createCloseAnimatorSetP3();
                if (!dismiss) {
                    mCloseP3AnimatorSet.start();
                    mCloseP3AnimatorSet.pause();
                    mCloseP3AnimatorSet.setCurrentPlayTimeAtRatio(1 - ratio);
                } else {
                    mCloseP3AnimatorSet.reverse();
                    mCloseP3AnimatorSet.pause();
                    mCloseP3AnimatorSet.setCurrentPlayTimeAtRatio(ratio);
                }
                mCloseP3AnimatorSet.resume();
            }
                break;
            case 3: {
                createCloseAnimatorSetP4();
                if (!dismiss) {
                    mCloseP4AnimatorSet.start();
                    mCloseP4AnimatorSet.pause();
                    mCloseP4AnimatorSet.setCurrentPlayTimeAtRatio(1 - ratio);
                } else {
                    mCloseP4AnimatorSet.reverse();
                    mCloseP4AnimatorSet.pause();
                    mCloseP4AnimatorSet.setCurrentPlayTimeAtRatio(ratio);
                }
                mCloseP4AnimatorSet.resume();
                // ArrayList<Animator> animators = mCloseP4AnimatorSet.getChildAnimations();
                // for (Animator animator : animators) {
                // ValueAnimator valueAnimator = (ValueAnimator) animator;
                // if (dismiss) {
                // valueAnimator.reverse();
                // valueAnimator.pause();
                // long cpt = animator.getDuration()
                // - (long) (animator.getDuration() * ratio);
                // valueAnimator.setCurrentPlayTime(cpt);
                // } else {
                // valueAnimator.start();
                // valueAnimator.pause();
                // long cpt = animator.getDuration()
                // - (long) (animator.getDuration() * ratio);
                // valueAnimator.setCurrentPlayTime(cpt);
                // }
                // valueAnimator.resume();
                // }
            }
                break;
            default:
                break;
        }
    }

    private void createCloseAnimatorSetP2() {
        if (mCloseP2AnimatorSet == null) {
            // mCloseP2AnimatorSet = mOpenP2AnimatorSet.clone();
            mCloseP2AnimatorSet = new MyAnimatorSet();
            mCloseP2AnimatorSet.playTogether(mOpenP2AnimatorSet.getChildAnimationsClone());
            mCloseP2AnimatorSet.addPauseListener(new AnimatorPauseListener() {

                @Override
                public void onAnimationResume(Animator animation) {
                    Log.d(TAG, "mCloseP2AnimatorSet:onAnimationResume");
                }

                @Override
                public void onAnimationPause(Animator animation) {
                    Log.d(TAG, "mCloseP2AnimatorSet:onAnimationPause");
                }
            });
            mCloseP2AnimatorSet.addMyListener(new MyAnimatorListener() {

                @Override
                public void onAnimationReverse(Animator animation) {
                    Log.d(TAG, "mCloseP2AnimatorSet:onAnimationReverse");
                    isP2Reverse = true;
                }

                @Override
                public void onAnimationStart(Animator animation) {
                    // Log.d(TAG, "mCloseP2AnimatorSet:onAnimationStart");
                    // if (!isP2Start) {
                    // isP2Start = true;
                    // mPageView1.setAlpha(0f);
                    // mPageView2.setAlpha(1f);
                    // mVideoImageView.setAlpha(1f);
                    // }
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                    Log.d(TAG, "mCloseP2AnimatorSet:onAnimationRepeat");
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    Log.d(TAG, "mCloseP2AnimatorSet:onAnimationEnd");
                    if (isP2Reverse) {
                        mPageView1.setAlpha(1f);
                        mPageView2.setAlpha(0f);
                        mVideoImageView.setAlpha(0f);
                        mP1VideoView.requestFocus();
                        // 重启视频
                        mP1VideoView.start();
                        // 重启 计时器
                        mP1Time.setBase(SystemClock.elapsedRealtime() - stoppedMilliseconds);
                        mP1Time.start();
                    } else {

                    }
                    isP2Start = false;
                    isP2Reverse = false;
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    Log.d(TAG, "mCloseP2AnimatorSet:onAnimationCancel");
                }
            });
        }
    }

    private void createCloseAnimatorSetP3() {
        if (mCloseP3AnimatorSet == null) {
            // mCloseP3AnimatorSet = mOpenP2AnimatorSet.clone();
            mCloseP3AnimatorSet = new MyAnimatorSet();
            mCloseP3AnimatorSet.playTogether(mOpenP3AnimatorSet.getChildAnimationsClone());
            mCloseP3AnimatorSet.addPauseListener(new AnimatorPauseListener() {

                @Override
                public void onAnimationResume(Animator animation) {
                    Log.d(TAG, "mCloseP3AnimatorSet:onAnimationResume");
                }

                @Override
                public void onAnimationPause(Animator animation) {
                    Log.d(TAG, "mCloseP3AnimatorSet:onAnimationPause");
                }
            });
            mCloseP3AnimatorSet.addMyListener(new MyAnimatorListener() {

                @Override
                public void onAnimationReverse(Animator animation) {
                    Log.d(TAG, "mCloseP3AnimatorSet:onAnimationReverse");
                    isP3Reverse = true;
                }

                @Override
                public void onAnimationStart(Animator animation) {
                    if (!isP3Start) {
                        Log.d(TAG, "mCloseP3AnimatorSet:onAnimationStart");
                        isP3Start = true;
                    }
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                    Log.d(TAG, "mCloseP3AnimatorSet:onAnimationRepeat");
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    Log.d(TAG, "mCloseP3AnimatorSet:onAnimationEnd");
                    if (isP3Reverse) {
                    } else {

                    }
                    isP3Start = false;
                    isP3Reverse = false;
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    Log.d(TAG, "mCloseP3AnimatorSet:onAnimationCancel");
                }
            });
        }
    }

    private void createCloseAnimatorSetP4() {
        if (mCloseP4AnimatorSet == null) {
            // mCloseP4AnimatorSet = mOpenP4AnimatorSet.clone();
            mCloseP4AnimatorSet = new MyAnimatorSet();
            mCloseP4AnimatorSet.playTogether(mOpenP4AnimatorSet.getChildAnimationsClone());
            mCloseP4AnimatorSet.addPauseListener(new AnimatorPauseListener() {

                @Override
                public void onAnimationResume(Animator animation) {
                    Log.d(TAG, "mCloseP4AnimatorSet:onAnimationResume");
                }

                @Override
                public void onAnimationPause(Animator animation) {
                    Log.d(TAG, "mCloseP4AnimatorSet:onAnimationPause");
                }
            });
            mCloseP4AnimatorSet.addMyListener(new MyAnimatorListener() {

                @Override
                public void onAnimationReverse(Animator animation) {
                    Log.d(TAG, "mCloseP4AnimatorSet:onAnimationReverse");
                    isP4Reverse = true;
                }

                @Override
                public void onAnimationStart(Animator animation) {
                    if (!isP4Start) {
                        Log.d(TAG, "mCloseP4AnimatorSet:onAnimationStart");
                        isP4Start = true;
                        if (mP4AnimatorSetAllInOne != null) {
                            mP4AnimatorSetAllInOne.end();
                            mP4AnimatorSetAllInOne = null;
                        }
                    }
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                    Log.d(TAG, "mCloseP4AnimatorSet:onAnimationRepeat");
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    Log.d(TAG, "mCloseP4AnimatorSet:onAnimationEnd" + isP4Reverse);
                    if (isP4Reverse) {
                        mP3SWIV.setAlpha(1f);
                    } else {
                        // mP4AnimatorSetAllInOne = new MyAnimatorSet();
                        // mP4AnimatorSetAllInOne.play(mP4AnimatorSet);
                        mP4AnimatorSetAllInOne = mP4AnimatorSet.clone();
                        mP4AnimatorSetAllInOne.start();
                    }
                    isP4Start = false;
                    isP4Reverse = false;
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    Log.d(TAG, "mCloseP4AnimatorSet:onAnimationCancel");
                }
            });
        }
    }

    private void createOpenAnimatorSetP2() {

        if (mOpenP2AnimatorSet == null) {

            // ObjectAnimator alPV1IV = ObjectAnimator.ofFloat(mPageView1, "alpha", 0f, 0f);
            // ObjectAnimator alPV2IV = ObjectAnimator.ofFloat(mPageView2, "alpha", 1f, 1f);
            // ObjectAnimator alVIVIV = ObjectAnimator.ofFloat(mVideoImageView, "alpha", 1f, 1f);

            PropertyValuesHolder pvsX = PropertyValuesHolder.ofFloat("scaleX", 1f, 0.7f);
            PropertyValuesHolder pvsY = PropertyValuesHolder.ofFloat("scaleY", 1f, 0.7f);
            PropertyValuesHolder pvrXY = PropertyValuesHolder.ofFloat("rotation", 0f, -5f);
            PropertyValuesHolder tlRVX = PropertyValuesHolder.ofFloat("translationX", 0f,
                    dip2px(this, 20f));
            PropertyValuesHolder tlRVY = PropertyValuesHolder.ofFloat("translationY", 0f,
                    dip2px(this, 100f));
            ObjectAnimator whxyBouncer = ObjectAnimator.ofPropertyValuesHolder(mP2RootView,
                    pvsX, pvsY, pvrXY, tlRVX, tlRVY).setDuration(500);
            whxyBouncer.setRepeatCount(0);
            whxyBouncer.setRepeatMode(ValueAnimator.INFINITE);

            ObjectAnimator ivxyBouncer = ObjectAnimator.ofPropertyValuesHolder(mVideoImageView,
                    pvsX, pvsY, pvrXY, tlRVX, tlRVY).setDuration(500);

            PropertyValuesHolder tlSWY = PropertyValuesHolder.ofFloat("translationY", -50f, 0f);
            PropertyValuesHolder apSWY = PropertyValuesHolder.ofFloat("alpha", 0.25f, 1f);
            PropertyValuesHolder roSWXY = PropertyValuesHolder.ofFloat("rotation", -25f, 0f);
            mP2SWIV.setPivotX(-50);
            mP2SWIV.setPivotY(-50);
            ObjectAnimator swBouncer = ObjectAnimator.ofPropertyValuesHolder(mP2SWIV, tlSWY,
                    roSWXY, apSWY).setDuration(500);

            PropertyValuesHolder tlBWY = PropertyValuesHolder.ofFloat("translationY", -100f, 0f);
            PropertyValuesHolder tlBWX = PropertyValuesHolder.ofFloat("translationX", 50f, 0f);
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
                    roBBXY).setDuration(500);

            PropertyValuesHolder scBSX = PropertyValuesHolder.ofFloat("scaleX", 1.5f, 1.1f);
            PropertyValuesHolder scBSY = PropertyValuesHolder.ofFloat("scaleY", 1.5f, 1.1f);
            PropertyValuesHolder apBSY = PropertyValuesHolder.ofFloat("alpha", 0f, 1f);
            PropertyValuesHolder roBSXY = PropertyValuesHolder.ofFloat("rotation", -45f, 0f);
            // mP2BSIV.setPivotX(mP2BBIV.getWidth() / 2);
            // mP2BSIV.setPivotY(mP2BBIV.getWidth() / 2);
            ObjectAnimator bsBouncer = ObjectAnimator.ofPropertyValuesHolder(mP2BSIV, scBSX,
                    scBSY, apBSY, roBSXY).setDuration(500);

            mOpenP2AnimatorSet = new MyAnimatorSet();
            mOpenP2AnimatorSet.addPauseListener(new AnimatorPauseListener() {

                @Override
                public void onAnimationResume(Animator animation) {
                    Log.d(TAG, "mOpenP2AnimatorSet:onAnimationResume");
                }

                @Override
                public void onAnimationPause(Animator animation) {
                    Log.d(TAG, "mOpenP2AnimatorSet:onAnimationPause");
                }
            });
            mOpenP2AnimatorSet.addMyListener(new MyAnimatorListener() {

                @Override
                public void onAnimationReverse(Animator animation) {
                    Log.d(TAG, "mOpenP2AnimatorSet:onAnimationReverse");
                    isP2Reverse = true;
                }

                @Override
                public void onAnimationStart(Animator animation) {
                    if (!isP2Start) {
                        Log.d(TAG, "mOpenP2AnimatorSet:onAnimationStart");
                        isP2Start = true;
                        mPageView1.setAlpha(0f);
                        mPageView2.setAlpha(1f);
                        mVideoImageView.setAlpha(1f);

                        mP2TimeTV.setText(mP1Time.getText());
                        // mP1VideoView.stopPlayback();
                        // mP1VideoView.clearFocus();
                        mP1VideoView.pause();
                        mVideoImageView.setTimeText(mP1Time.getText().toString());

                        // 暂停计时器
                        mP1Time.stop();
                        String chronoText = mP1Time.getText().toString();
                        String array[] = chronoText.split(":");
                        if (array.length == 2) {
                            stoppedMilliseconds = Integer.parseInt(array[0]) * 60 * 1000
                                    + Integer.parseInt(array[1]) * 1000;
                        } else if (array.length == 3) {
                            stoppedMilliseconds = Integer.parseInt(array[0]) * 60 * 60 * 1000
                                    + Integer.parseInt(array[1]) * 60 * 1000
                                    + Integer.parseInt(array[2]) * 1000;
                        }

                        // 尽力了，不知道 为什么 截取的图 为什么不是 暂停的画面

                        MediaMetadataRetriever rev = new MediaMetadataRetriever();
                        rev.setDataSource(DemoActivity.this, mP1VVUriPath); // 这里第一个参数需要Context，传this指针
                        Bitmap bitmap = rev.getFrameAtTime(
                                mP1VideoView.getCurrentPosition() * 1000,
                                MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
                        mVideoImageView.setImageBitmap(bitmap);
                    }
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                    Log.d(TAG, "mOpenP2AnimatorSet:onAnimationRepeat");
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    Log.d(TAG, "mOpenP2AnimatorSet:onAnimationEnd");
                    if (isP2Reverse) {
                        mPageView1.setAlpha(1f);
                        mPageView2.setAlpha(0f);
                        mVideoImageView.setAlpha(0f);
                        mP1VideoView.requestFocus();
                        // 重启视频
                        mP1VideoView.start();
                        // 重启 计时器
                        mP1Time.setBase(SystemClock.elapsedRealtime() - stoppedMilliseconds);
                        mP1Time.start();
                    } else {
                        mPageView1.setAlpha(0f);
                        mPageView2.setAlpha(1f);
                        mVideoImageView.setAlpha(1f);
                    }
                    isP2Start = false;
                    isP2Reverse = false;
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    Log.d(TAG, "mOpenP2AnimatorSet:onAnimationCancel");
                }
            });
            // mOpenP2AnimatorSet.cancel();
            mOpenP2AnimatorSet.playTogether(ivxyBouncer, swBouncer, bbBouncer, bsBouncer,
                    bwBouncer, whxyBouncer);
            // mOpenP2AnimatorSet.setStartDelay(1000);
            mOpenP2AnimatorSet.setDuration(500);
            // mOpenP2AnimatorSet.start();
        }
    }

    private void createOpenAnimatorSetP3() {

        if (mOpenP3AnimatorSet == null) {

            PropertyValuesHolder scP2RVX = PropertyValuesHolder.ofFloat("scaleX", 1.0f, 0f);
            PropertyValuesHolder scP2RVY = PropertyValuesHolder.ofFloat("scaleY", 1.0f, 0f);
            PropertyValuesHolder apP2RVY = PropertyValuesHolder.ofFloat("alpha", 1f, 0f);
            ObjectAnimator rootP2Bouncer = ObjectAnimator.ofPropertyValuesHolder(mPageView2,
                    scP2RVX, scP2RVY, apP2RVY).setDuration(400);
            rootP2Bouncer.setRepeatCount(0);

            Keyframe kf0 = Keyframe.ofFloat(0, 1f);
            // Keyframe kf1 = Keyframe.ofFloat(0.25f, 1f);
            Keyframe kf2 = Keyframe.ofFloat(0.5f, 1f);
            Keyframe kf4 = Keyframe.ofFloat(0.9999f, 1f);
            Keyframe kf3 = Keyframe.ofFloat(1f, 0f);
            PropertyValuesHolder apP2IVY = PropertyValuesHolder.ofKeyframe("alpha", kf0, kf2,
                    kf4, kf3);
            PropertyValuesHolder scP2IVX = PropertyValuesHolder.ofFloat("scaleX", 0.7f, 0.8f,
                    0.6f);
            PropertyValuesHolder scP2IVY = PropertyValuesHolder.ofFloat("scaleY", 0.7f, 0.8f,
                    0.6f);
            PropertyValuesHolder roP2IVXY = PropertyValuesHolder
                    .ofFloat("rotation", -5f, 0f, 0f);
            PropertyValuesHolder tlP2IVX = PropertyValuesHolder.ofFloat("translationX",
                    dip2px(this, 20f), 0f, 0f);
            PropertyValuesHolder tlP2IVY = PropertyValuesHolder.ofFloat("translationY",
                    dip2px(this, 100f), 0f, 0f);

            // 没效果
            // PropertyValuesHolder heightP2IVX1 = PropertyValuesHolder.ofInt("height",
            // mVideoImageView.getHeight(), 0);

            PropertyValuesHolder heightP2IVX1 = PropertyValuesHolder.ofFloat("translationHight",
                    1f, 0f);

            ObjectAnimator roxyBouncerP2IV = ObjectAnimator.ofPropertyValuesHolder(
                    mVideoImageView, apP2IVY, scP2IVX, scP2IVY, roP2IVXY, tlP2IVX, tlP2IVY,
                    heightP2IVX1).setDuration(500);
            roxyBouncerP2IV.setInterpolator(new AccelerateInterpolator());
            roxyBouncerP2IV.setRepeatCount(0);

            // PropertyValuesHolder apP2IVY = PropertyValuesHolder.ofFloat("alpha", 0f, 1f);
            PropertyValuesHolder scP2IVX1 = PropertyValuesHolder.ofFloat("scaleX", 1.3f, 0.7f);
            PropertyValuesHolder scP2IVY1 = PropertyValuesHolder.ofFloat("scaleY", 1.3f, 0.7f);

            // ObjectAnimator roxyP2IV = ObjectAnimator.ofFloat(mP2ImageView, "rotation",
            // -5f, 0f).setDuration(400);

            // ObjectAnimator roxyBouncerEOne = ObjectAnimator
            // .ofFloat(mP2RootView, "alpha", 0f, 0f);
            // roxyBouncerEOne.setDuration(400);
            // roxyBouncerEOne.setRepeatCount(0);

            // mP3RootView.setPivotX(mP3RootView.getWidth() / 4 * 3);
            // int height = mP3RootView.getHeight();
            // mP3RootView.setPivotY((float) (height - height * 0.15));

            PropertyValuesHolder apRVY = PropertyValuesHolder.ofFloat("alpha", 0f, 1f);
            PropertyValuesHolder pvsX = PropertyValuesHolder.ofFloat("scaleX", 1.3f, 0.7f);
            PropertyValuesHolder pvsY = PropertyValuesHolder.ofFloat("scaleY", 1.3f, 0.7f);
            PropertyValuesHolder pvrXY = PropertyValuesHolder.ofFloat("rotation", -6f, 0f);
            PropertyValuesHolder tlRVY = PropertyValuesHolder.ofFloat("translationY", 0f,
                    dip2px(this, 100f));
            ObjectAnimator whxyBouncer = ObjectAnimator.ofPropertyValuesHolder(mP3RootView,
                    apRVY, pvsX, pvsY, pvrXY, tlRVY).setDuration(500);
            whxyBouncer.setRepeatCount(0);

            PropertyValuesHolder scCLX = PropertyValuesHolder.ofFloat("scaleX", 1.5f, 1f);
            PropertyValuesHolder scCLY = PropertyValuesHolder.ofFloat("scaleY", 1.5f, 1f);
            PropertyValuesHolder tlCLY = PropertyValuesHolder.ofFloat("translationY", -50f, 0f);
            PropertyValuesHolder apCLY = PropertyValuesHolder.ofFloat("alpha", 0f, 1f);
            PropertyValuesHolder roCLXY = PropertyValuesHolder.ofFloat("rotation", -5f, 0f);
            ObjectAnimator swBouncer = ObjectAnimator.ofPropertyValuesHolder(mP3CLIV, scCLX,
                    scCLY, tlCLY, roCLXY, apCLY).setDuration(400);
            swBouncer.setInterpolator(new DecelerateInterpolator());

            PropertyValuesHolder scCRX = PropertyValuesHolder.ofFloat("scaleX", 1.5f, 1f);
            PropertyValuesHolder scCRY = PropertyValuesHolder.ofFloat("scaleY", 1.5f, 1f);
            PropertyValuesHolder tlCRY = PropertyValuesHolder.ofFloat("translationY", -50f, 0f);
            PropertyValuesHolder tlCRX = PropertyValuesHolder.ofFloat("translationX", 50f, 0f);
            PropertyValuesHolder apCRY = PropertyValuesHolder.ofFloat("alpha", 0f, 1f);
            PropertyValuesHolder roCRXY = PropertyValuesHolder.ofFloat("rotation", 5f, 0f);
            ObjectAnimator bwBouncer = ObjectAnimator.ofPropertyValuesHolder(mP3CRIV, scCRX,
                    scCRY, tlCRY, tlCRX, roCRXY, apCRY).setDuration(400);
            bwBouncer.setInterpolator(new AccelerateInterpolator());

            PropertyValuesHolder scCBX = PropertyValuesHolder.ofFloat("scaleX", 1.5f, 1f);
            PropertyValuesHolder scCBY = PropertyValuesHolder.ofFloat("scaleY", 1.5f, 1f);
            PropertyValuesHolder apCBY = PropertyValuesHolder.ofFloat("alpha", 0f, 1f);
            PropertyValuesHolder roCBXY = PropertyValuesHolder.ofFloat("rotation", -15f, 0f);
            mP3CBIV.setPivotX(mP3CBIV.getWidth() / 2);
            mP3CBIV.setPivotY(-100);
            ObjectAnimator cbBouncer = ObjectAnimator.ofPropertyValuesHolder(mP3CBIV, scCBX,
                    scCBY, apCBY, roCBXY).setDuration(500);

            PropertyValuesHolder scBSX = PropertyValuesHolder.ofFloat("scaleX", 1.5f, 1f);
            PropertyValuesHolder scBSY = PropertyValuesHolder.ofFloat("scaleY", 1.5f, 1f);
            PropertyValuesHolder apBSY = PropertyValuesHolder.ofFloat("alpha", 0f, 1f);
            ObjectAnimator csBouncer = ObjectAnimator.ofPropertyValuesHolder(mP3CSIV, scBSX,
                    scBSY, apBSY).setDuration(500);

            mOpenP3AnimatorSet = new MyAnimatorSet();
            mOpenP3AnimatorSet.addPauseListener(new AnimatorPauseListener() {

                @Override
                public void onAnimationResume(Animator animation) {
                    Log.d(TAG, "mOpenP3AnimatorSet:onAnimationResume");
                }

                @Override
                public void onAnimationPause(Animator animation) {
                    Log.d(TAG, "mOpenP3AnimatorSet:onAnimationPause");
                }
            });
            mOpenP3AnimatorSet.addMyListener(new MyAnimatorListener() {

                @Override
                public void onAnimationReverse(Animator animation) {
                    Log.d(TAG, "mOpenP3AnimatorSet:onAnimationReverse");
                    isP3Reverse = true;
                }

                @Override
                public void onAnimationStart(Animator animation) {
                    if (!isP3Start) {
                        Log.d(TAG, "mOpenP3AnimatorSet:onAnimationStart");
                        isP3Start = true;
                        mPageView3.setAlpha(1f);
                    }
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                    Log.d(TAG, "mOpenP3AnimatorSet:onAnimationRepeat");
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    Log.d(TAG, "mOpenP3AnimatorSet:onAnimationEnd" + isP3Reverse);
                    if (isP3Reverse) {
                    } else {
                    }
                    isP3Start = false;
                    isP3Reverse = false;
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    Log.d(TAG, "mOpenP3AnimatorSet:onAnimationCancel");
                }
            });
            // mOpenP3AnimatorSet.cancel();
            mOpenP3AnimatorSet.playTogether(rootP2Bouncer, roxyBouncerP2IV, whxyBouncer,
                    swBouncer, bwBouncer, cbBouncer, csBouncer);
            // mOpenP3AnimatorSet.setStartDelay(1000);
            // mOpenP3AnimatorSet.setDuration(500);
            // mOpenP3AnimatorSet.start();

        }
    }

    private void createOpenAnimatorSetP4() {

        if (mOpenP4AnimatorSet == null) {

            PropertyValuesHolder scCLX = PropertyValuesHolder.ofFloat("scaleX", 1f, 0f);
            PropertyValuesHolder scCLY = PropertyValuesHolder.ofFloat("scaleY", 1f, 0f);
            PropertyValuesHolder tlCLY = PropertyValuesHolder.ofFloat("translationY", 0f,
                    dip2px(this, 180f));
            PropertyValuesHolder tlCLX = PropertyValuesHolder.ofFloat("translationX", 0f,
                    dip2px(this, 50f));
            PropertyValuesHolder apCLY = PropertyValuesHolder.ofFloat("alpha", 1f, 0f);
            ObjectAnimator p3CLBouncer = ObjectAnimator.ofPropertyValuesHolder(mP3CLIV, scCLX,
                    scCLY, tlCLX, tlCLY, apCLY).setDuration(400);
            p3CLBouncer.setInterpolator(new AccelerateInterpolator());

            PropertyValuesHolder scCRX = PropertyValuesHolder.ofFloat("scaleX", 1f, 0f);
            PropertyValuesHolder scCRY = PropertyValuesHolder.ofFloat("scaleY", 1f, 0f);
            PropertyValuesHolder tlCRY = PropertyValuesHolder.ofFloat("translationY", 0f,
                    dip2px(this, 100f));
            PropertyValuesHolder tlCRX = PropertyValuesHolder.ofFloat("translationX", 0f,
                    dip2px(this, 60f));
            PropertyValuesHolder apCRY = PropertyValuesHolder.ofFloat("alpha", 1f, 0f);
            ObjectAnimator p3CRBouncer = ObjectAnimator.ofPropertyValuesHolder(mP3CRIV, scCRX,
                    scCRY, tlCRY, tlCRX, apCRY).setDuration(400);
            // p3CRBouncer.setInterpolator(new DecelerateInterpolator());

            PropertyValuesHolder scCBX = PropertyValuesHolder.ofFloat("scaleX", 1f, 0f);
            PropertyValuesHolder scCBY = PropertyValuesHolder.ofFloat("scaleY", 1f, 0f);
            PropertyValuesHolder apCBY = PropertyValuesHolder.ofFloat("alpha", 1f, 0f);
            // mP3CBIV.setPivotX(mP3CBIV.getWidth() / 2);
            // mP3CBIV.setPivotY(mP3CBIV.getWidth() / 2);
            PropertyValuesHolder tlCBY = PropertyValuesHolder.ofFloat("translationY", 0f,
                    dip2px(this, 250f));
            PropertyValuesHolder tlCBX = PropertyValuesHolder.ofFloat("translationX", 0f,
                    dip2px(this, 60f));
            ObjectAnimator p3CBBouncer = ObjectAnimator.ofPropertyValuesHolder(mP3CBIV, scCBX,
                    scCBY, apCBY, tlCBX, tlCBY).setDuration(600);

            PropertyValuesHolder scCSX = PropertyValuesHolder.ofFloat("scaleX", 1f, 0f);
            PropertyValuesHolder scCSY = PropertyValuesHolder.ofFloat("scaleY", 1f, 0f);
            PropertyValuesHolder apCSY = PropertyValuesHolder.ofFloat("alpha", 1f, 0f);
            ObjectAnimator p3CSBouncer = ObjectAnimator.ofPropertyValuesHolder(mP3CSIV, scCSX,
                    scCSY, apCSY).setDuration(600);

            PropertyValuesHolder scP3X = PropertyValuesHolder.ofFloat("scaleX", 0.7f, 0.01f);
            PropertyValuesHolder scP3Y = PropertyValuesHolder.ofFloat("scaleY", 0.7f, 0.01f);
            PropertyValuesHolder apP3Y = PropertyValuesHolder.ofFloat("alpha", 1f, 0f);
            PropertyValuesHolder roP3XY = PropertyValuesHolder.ofFloat("rotation", 0f, -28f);
            PropertyValuesHolder tlP3RVY = PropertyValuesHolder.ofFloat("translationY",
                    dip2px(this, 100f), 0f);
            PropertyValuesHolder tlP3RVX = PropertyValuesHolder.ofFloat("translationX", 0f,
                    dip2px(this, -75f));
            ObjectAnimator p3Bouncer = ObjectAnimator.ofPropertyValuesHolder(mP3RootView, scP3X,
                    scP3Y, apP3Y, roP3XY, tlP3RVX, tlP3RVY);
            p3Bouncer.setInterpolator(new MyInterpolator());
            p3Bouncer.setDuration(500);
            p3Bouncer.setRepeatCount(0);

            ObjectAnimator rootp4BouncerOne = ObjectAnimator
                    .ofFloat(mPageView4, "alpha", 0f, 1f);
            // rootp4BouncerOne.setInterpolator(new DecelerateInterpolator());
            rootp4BouncerOne.setDuration(400);
            rootp4BouncerOne.setRepeatCount(0);

            Keyframe kfm0 = Keyframe.ofFloat(0, 2.0f);
            Keyframe kfm1 = Keyframe.ofFloat(0.7f, 1.0f);
            Keyframe kfm2 = Keyframe.ofFloat(1f, 1.0f);
            PropertyValuesHolder scMIX = PropertyValuesHolder.ofKeyframe("scaleX", kfm0, kfm1,
                    kfm2);
            PropertyValuesHolder scMIY = PropertyValuesHolder.ofKeyframe("scaleY", kfm0, kfm1,
                    kfm2);
            Keyframe kfms0 = Keyframe.ofFloat(0, 200f);
            Keyframe kfms1 = Keyframe.ofFloat(0.7f, 0f);
            Keyframe kfms2 = Keyframe.ofFloat(1f, 0f);
            PropertyValuesHolder tlBWX = PropertyValuesHolder.ofKeyframe("translationX", kfms0,
                    kfms1, kfms2);

            // PropertyValuesHolder scMIX = PropertyValuesHolder.ofFloat("scaleX", 2.0f, 1.0f);
            // PropertyValuesHolder scMIY = PropertyValuesHolder.ofFloat("scaleY", 2.0f, 1.0f);
            // PropertyValuesHolder tlBWX = PropertyValuesHolder.ofFloat("translationX", 200f, 0f);
            ObjectAnimator bsBouncer = ObjectAnimator.ofPropertyValuesHolder(mP4ManIV, scMIX,
                    scMIY, tlBWX).setDuration(500);

            Keyframe kf0 = Keyframe.ofFloat(0, 0f);
            Keyframe kf1 = Keyframe.ofFloat(0.25f, 0f);
            Keyframe kf2 = Keyframe.ofFloat(0.5f, 0.25f);
            Keyframe kf4 = Keyframe.ofFloat(0.75f, 0.6f);
            Keyframe kf3 = Keyframe.ofFloat(1f, 1f);
            PropertyValuesHolder pvhRotation = PropertyValuesHolder.ofKeyframe("alpha", kf0,
                    kf1, kf2, kf4, kf3);
            PropertyValuesHolder scDBX = PropertyValuesHolder.ofFloat("scaleX", 2.5f, 1.0f);
            PropertyValuesHolder scDBY = PropertyValuesHolder.ofFloat("scaleY", 2.5f, 1.0f);
            PropertyValuesHolder roDBXY = PropertyValuesHolder.ofFloat("rotation", -15f, 0f);
            PropertyValuesHolder tlDBY = PropertyValuesHolder.ofFloat("translationY", 250f, 0f);
            PropertyValuesHolder tlDBX = PropertyValuesHolder.ofFloat("translationX", -150f, 0f);
            mP4DBIV.setPivotX(0);
            mP4DBIV.setPivotY(200);
            ObjectAnimator dbBouncer = ObjectAnimator.ofPropertyValuesHolder(mP4DBIV,
                    pvhRotation, scDBX, tlDBX, tlDBY, scDBY, roDBXY).setDuration(800);

            Keyframe kfdbc0 = Keyframe.ofFloat(0, 2.0f);
            Keyframe kfdbc4 = Keyframe.ofFloat(0.9f, 1.0f);
            Keyframe kfdbc3 = Keyframe.ofFloat(1f, 1.0f);
            PropertyValuesHolder scDBCX = PropertyValuesHolder.ofKeyframe("scaleX", kfdbc0,
                    kfdbc4, kfdbc3);
            PropertyValuesHolder scDBCY = PropertyValuesHolder.ofKeyframe("scaleY", kfdbc0,
                    kfdbc4, kfdbc3);
            // PropertyValuesHolder scDBCX = PropertyValuesHolder.ofFloat("scaleX", 2.0f, 1.0f);
            // PropertyValuesHolder scDBCY = PropertyValuesHolder.ofFloat("scaleY", 2.0f, 1.0f);
            ObjectAnimator dbcBouncer = ObjectAnimator.ofPropertyValuesHolder(mP4DBCIV, scDBCX,
                    scDBCY).setDuration(600);

            PropertyValuesHolder scDCX = PropertyValuesHolder.ofFloat("scaleX", 2.0f, 1.0f);
            PropertyValuesHolder scDCY = PropertyValuesHolder.ofFloat("scaleY", 2.0f, 1.0f);
            PropertyValuesHolder tlDCY = PropertyValuesHolder.ofFloat("translationY", -150f, 0f);
            PropertyValuesHolder tlDCX = PropertyValuesHolder.ofFloat("translationX", 150f, 0f);
            PropertyValuesHolder roDCXY = PropertyValuesHolder.ofFloat("rotation", -10f, 0f);
            mP4DCTV.setPivotX(0);
            mP4DCTV.setPivotY(0);
            ObjectAnimator dcBouncer = ObjectAnimator.ofPropertyValuesHolder(mP4DCTV, scDCX,
                    tlDCY, tlDCX, scDCY, roDCXY).setDuration(600);

            PropertyValuesHolder scEX = PropertyValuesHolder.ofFloat("scaleX", 2.5f, 1.5f);
            PropertyValuesHolder scEY = PropertyValuesHolder.ofFloat("scaleY", 2.5f, 1.5f);
            PropertyValuesHolder tlEY = PropertyValuesHolder.ofFloat("translationY",
                    dip2px(this, 600f), dip2px(this, 360f));
            PropertyValuesHolder tlEX = PropertyValuesHolder.ofFloat("translationX",
                    dip2px(this, -80f), 0f);
            PropertyValuesHolder roEXY = PropertyValuesHolder.ofFloat("rotation", -359f, 0f);
            // mP4EarthIV.setPivotX(600);
            // mP4EarthIV.setPivotY(0);
            ObjectAnimator eBouncer = ObjectAnimator.ofPropertyValuesHolder(mP4EarthIV, scEX,
                    scEY, roEXY, tlEY, tlEX).setDuration(600);

            ObjectAnimator ecBouncer = ObjectAnimator.ofPropertyValuesHolder(mP4EarthCloudIV,
                    scEX, scEY, roEXY, tlEY, tlEX).setDuration(600);

            mP4MantyBouncer = ObjectAnimator.ofFloat(mP4ManIV, "translationY", 0f, 40f, 0f);
            // CycleInterpolator interpolator = new CycleInterpolator(3.0f);
            // roxyBouncerE.setInterpolator(lin);
            // roxyBouncer.setStartDelay(500);
            mP4MantyBouncer.setDuration(2000);
            mP4MantyBouncer.setRepeatCount(Animation.INFINITE);// Animation.INFINITE

            ObjectAnimator roxyBouncerE = ObjectAnimator.ofFloat(mP4EarthIV, "rotation", 0f,
                    359f);
            // CycleInterpolator interpolator = new CycleInterpolator(3.0f);
            LinearInterpolator lin = new LinearInterpolator();
            // roxyBouncerE.setInterpolator(lin);
            // roxyBouncer.setStartDelay(500);
            roxyBouncerE.setDuration(40000);
            roxyBouncerE.setRepeatCount(Animation.INFINITE);// Animation.INFINITE

            ObjectAnimator roxyBouncerEC = ObjectAnimator.ofFloat(mP4EarthCloudIV, "rotation",
                    0f, 359f);
            // roxyBouncerEC.setInterpolator(lin);
            roxyBouncerEC.setDuration(60000);
            roxyBouncerEC.setRepeatCount(Animation.INFINITE);

            // 效果不对
            // PropertyValuesHolder roEECX = PropertyValuesHolder.ofFloat("rotation", 360f, 0f);
            //
            // ObjectAnimator roxyBouncerEOne = ObjectAnimator.ofPropertyValuesHolder(mP4EarthIV,
            // roEECX).setDuration(400);
            // roxyBouncerEOne.setRepeatCount(0);
            //
            // ObjectAnimator roxyBouncerECOne = ObjectAnimator.ofPropertyValuesHolder(
            // mP4EarthCloudIV, roEECX).setDuration(400);
            // roxyBouncerECOne.setRepeatCount(0);
            // MyAnimatorSet mas1 = new MyAnimatorSet();
            // mas1.playTogether(roxyBouncerEOne, roxyBouncerECOne);
            //
            // MyAnimatorSet mas2 = new MyAnimatorSet();
            // mas2.playTogether(roxyBouncerE, roxyBouncerEC);

            mP4AnimatorSet = new MyAnimatorSet();
            // mP4AnimatorSet.setDuration(50000);
            mP4AnimatorSet.setInterpolator(lin);
            mP4AnimatorSet.playTogether(roxyBouncerE, roxyBouncerEC);
            // mP4AnimatorSet.play(roxyBouncerEOne).with(roxyBouncerECOne).after(roxyBouncerE)
            // .with(roxyBouncerEC);
            // mP4AnimatorSet.playSequentially(mas1, mas2);
            // mP4AnimatorSet.start();

            mOpenP4AnimatorSet = new MyAnimatorSet();
            mOpenP4AnimatorSet.addPauseListener(new AnimatorPauseListener() {

                @Override
                public void onAnimationResume(Animator animation) {
                    Log.d(TAG, "mOpenP4AnimatorSet:onAnimationResume");
                }

                @Override
                public void onAnimationPause(Animator animation) {
                    Log.d(TAG, "mOpenP4AnimatorSet:onAnimationPause");
                }
            });
            mOpenP4AnimatorSet.addMyListener(new MyAnimatorListener() {

                @Override
                public void onAnimationReverse(Animator animation) {
                    Log.d(TAG, "mOpenP4AnimatorSet:onAnimationReverse");
                    isP3Reverse = true;
                }

                @Override
                public void onAnimationStart(Animator animation) {
                    if (!isP4Start) {
                        Log.d(TAG, "mOpenP4AnimatorSet:onAnimationStart");
                        isP4Start = true;
                        if (mP4AnimatorSetAllInOne != null) {
                            mP4AnimatorSetAllInOne.end();
                        }
                        if (!mP4MantyBouncer.isStarted()) {
                            mP4MantyBouncer.start();
                        }
                    }
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                    Log.d(TAG, "mOpenP4AnimatorSet:onAnimationRepeat");
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    Log.d(TAG, "mOpenP4AnimatorSet:onAnimationEnd" + isP4Reverse);

                    // mP4AnimatorSetAllInOne = new MyAnimatorSet();
                    // mP4AnimatorSetAllInOne.play(mP4AnimatorSet);
                    mP4AnimatorSetAllInOne = mP4AnimatorSet.clone();
                    mP4AnimatorSetAllInOne.start();

                    if (isP4Reverse) {
                        mP3SWIV.setAlpha(1f);
                    } else {
                        mP3SWIV.setAlpha(0f);
                    }
                    isP4Start = false;
                    isP4Reverse = false;
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    Log.d(TAG, "mOpenP4AnimatorSet:onAnimationCancel");
                }
            });
            // mOpenP4AnimatorSet.setDuration(400);
            // mOpenP4AnimatorSet.setInterpolator(lin);
            mOpenP4AnimatorSet.playTogether(p3CLBouncer, p3CRBouncer, p3CBBouncer, p3CSBouncer,
                    p3Bouncer, rootp4BouncerOne, bsBouncer, dbBouncer, eBouncer, ecBouncer,
                    dcBouncer, dbcBouncer);
            // mOpenP4AnimatorSet.start();
        }

        // mP4AnimatorSetAllInOne = new MyAnimatorSet();
        // mP4AnimatorSetAllInOne.playSequentially(mOpenP4AnimatorSet, mP4AnimatorSet);
        // mP4AnimatorSetAllInOne.start();
    }

    public static float density;

    public static float dip2px(Context context, float dipValue) {
        if (density == 0) {
            density = context.getApplicationContext().getResources().getDisplayMetrics().density;
        }
        return (dipValue * density + 0.5f);
    }
}
