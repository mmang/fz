package com.mangmang.fz.ui.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.mangmang.fz.R;


/**
 * Created by Administrator on 2016/7/11.
 */
public class LoadingView extends View {
    private static final Interpolator MATERIAL_INTERPOLATOR = new FastOutSlowInInterpolator();

    private static final int DEGREE_360 = 360;
    private static final int NUM_POINTS = 5;

    private static final float MIN_SWIPE_DEGREE = 0.1f;
    private static final float MAX_SWIPE_DEGREES = 0.8f * DEGREE_360;
    private static final float FULL_GROUP_ROTATION = 3.0f * DEGREE_360;
    private static final float MAX_ROTATION_INCREMENT = 0.25f * DEGREE_360;

    private static final float END_TRIM_DURATION_OFFSET = 1.0f;
    private static final float START_TRIM_DURATION_OFFSET = 0.5f;


    private Paint mPaint;

    private float mRotationCount;
    private float mGroupRotation;

    private float mEndDegrees;
    private float mStartDegrees;
    private float mSwipeDegrees;
    private float mRotationIncrement;
    private float mOriginEndDegrees;
    private float mOriginStartDegrees;
    private float mOriginRotationIncrement;


    public LoadingView(Context context) {
        super(context);
        init();
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(getResources().getColor(R.color.colorPrimaryDark));
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(getResources().getDimension(R.dimen.x5));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        setupAnimators();
    }

    public void setPaint(Paint paint) {
        this.mPaint = paint;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int save = canvas.save();

        canvas.rotate(mGroupRotation, getWidth() / 2, getHeight() / 2);

        float width = mPaint.getStrokeWidth() / 2;
        RectF rectf = new RectF(width, width, getWidth() - width, getHeight() - width);
        canvas.drawArc(rectf, mStartDegrees, mSwipeDegrees, false, mPaint);
        canvas.restoreToCount(save);
    }

    private void setupAnimators() {
        ValueAnimator mLoadAnimator = ValueAnimator.ofFloat(0f, 1.0f);
        mLoadAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mLoadAnimator.setRepeatMode(ValueAnimator.RESTART);
        mLoadAnimator.setDuration(2000);
        mLoadAnimator.setInterpolator(new LinearInterpolator());
        mLoadAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float renderProgress = (float) animation.getAnimatedValue();

                if (renderProgress <= START_TRIM_DURATION_OFFSET) {
                    float startTrimProgress = renderProgress / START_TRIM_DURATION_OFFSET;
                    mStartDegrees = mOriginStartDegrees + MAX_SWIPE_DEGREES * MATERIAL_INTERPOLATOR.getInterpolation(startTrimProgress);
                }

                if (renderProgress > START_TRIM_DURATION_OFFSET) {
                    float endTrimProgress = (renderProgress - START_TRIM_DURATION_OFFSET) / (END_TRIM_DURATION_OFFSET - START_TRIM_DURATION_OFFSET);
                    mEndDegrees = mOriginEndDegrees + MAX_SWIPE_DEGREES * MATERIAL_INTERPOLATOR.getInterpolation(endTrimProgress);
                }

                if (Math.abs(mEndDegrees - mStartDegrees) > MIN_SWIPE_DEGREE) {
                    mSwipeDegrees = mEndDegrees - mStartDegrees;
                }
                mGroupRotation = ((FULL_GROUP_ROTATION / NUM_POINTS) * renderProgress) + (FULL_GROUP_ROTATION * (mRotationCount / NUM_POINTS));
                mRotationIncrement = mOriginRotationIncrement + (MAX_ROTATION_INCREMENT * renderProgress);
                invalidate();
            }
        });
        mLoadAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
                storeOriginals();

                mStartDegrees = mEndDegrees;
                mRotationCount = (mRotationCount + 1) % (NUM_POINTS);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                mRotationCount = 0;
            }
        });
        mLoadAnimator.start();
    }

    private void storeOriginals() {
        mOriginEndDegrees = mEndDegrees;
        mOriginStartDegrees = mStartDegrees;
        mOriginRotationIncrement = mRotationIncrement;
    }
}
