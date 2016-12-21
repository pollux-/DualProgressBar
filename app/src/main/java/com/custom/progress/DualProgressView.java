package com.custom.progress;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

/**
 * Create custom view shows indeterminate progress by showing to circle
 * Spinning one inside the other.
 * <p>
 * Created by SreeKumar
 * <p>
 * Copyright (C) 2016
 */
public class DualProgressView extends View {

    private static final float INDETERMINANT_MIN_SWEEP = 15f;

    /**
     * Draw outer progress
     */
    private Paint mOuterCirclePaint;
    /**
     * Draw inner progress
     */
    private Paint mInnerCirclePaint;
    /**
     * Thickness of the progress
     */
    private float mThickness;
    /**
     * Padding between the two circles
     */
    private float mInnerPadding;
    /**
     * Animation duration
     */
    private int mAnimDuration;
    /**
     * Rect for drawing outer circle
     */
    private RectF mOuterCircleRect;
    /**
     * Rect for drawing inner circle
     */
    private RectF mInnerCircleRect;

    /**
     * Outer Circle Color
     */
    @ColorInt
    private int mOuterCircleColor;
    /**
     * Inner Circle Color
     */
    @ColorInt
    private int mInnerCircleColor;
    /**
     * Number of step in the Animation
     **/
    private int mSteps;

    /**
     * Actual size of the complete circle.
     **/
    private int mSize;
    /**
     * Starting Angle to start the progress Animation.
     */
    private float mStartAngle;
    /***
     * Sweep Angle
     */
    private float mIndeterminateSweep;
    /**
     * Rotation offset
     */
    private float mIndeterminateRotateOffset;
    /**
     * Progress Animation set
     */
    private AnimatorSet mIndeterminateAnimator;


    public DualProgressView(Context context) {
        super(context);
        init(null, 0);
    }

    public DualProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public DualProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }


    /**
     * Initialize all drawing parameters from the custom Attributes.
     *
     * @param attrs
     * @param defStyle
     */
    protected void init(AttributeSet attrs, int defStyle) {

        mOuterCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mInnerCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        mOuterCircleRect = new RectF();
        mInnerCircleRect = new RectF();

        Resources resources = getResources();

        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.DualProgressView, defStyle, 0);
        mThickness = a.getDimensionPixelSize(R.styleable.DualProgressView_dpv_thickness,
                resources.getDimensionPixelSize(R.dimen.default_thickness));
        mInnerPadding = a.getDimensionPixelSize(R.styleable.DualProgressView_dpv_inner_padding,
                resources.getDimensionPixelSize(R.dimen.default_inner_padding));

        mOuterCircleColor = a.getColor(R.styleable.DualProgressView_dpv_outer_color,
                ContextCompat.getColor(getContext(), R.color.colorPrimary));
        mInnerCircleColor = a.getColor(R.styleable.DualProgressView_dpv_inner_color,
                ContextCompat.getColor(getContext(), R.color.colorAccent));
        mAnimDuration = a.getInteger(R.styleable.DualProgressView_dpv_anim_duration,
                resources.getInteger(R.integer.default_anim_duration));
        mSteps = resources.getInteger(R.integer.default_anim_step);
        mStartAngle = resources.getInteger(R.integer.default_start_angle);
        a.recycle();


        setPaint();

    }

    /**
     * Set the two paint object with
     * supplied color for drawing.
     */
    private void setPaint() {

        mOuterCirclePaint.setColor(mOuterCircleColor);
        mOuterCirclePaint.setStyle(Paint.Style.STROKE);
        mOuterCirclePaint.setStrokeWidth(mThickness);
        mOuterCirclePaint.setStrokeCap(Paint.Cap.BUTT);
        mInnerCirclePaint.setColor(mInnerCircleColor);
        mInnerCirclePaint.setStyle(Paint.Style.STROKE);
        mInnerCirclePaint.setStrokeWidth(mThickness);
        mInnerCirclePaint.setStrokeCap(Paint.Cap.BUTT);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawArc(mOuterCircleRect, mStartAngle + mIndeterminateRotateOffset, mIndeterminateSweep, false, mOuterCirclePaint);
        canvas.drawArc(mInnerCircleRect, mStartAngle + mIndeterminateRotateOffset + 180f, mIndeterminateSweep, false, mInnerCirclePaint);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int xPad = getPaddingLeft() + getPaddingRight();
        int yPad = getPaddingTop() + getPaddingBottom();
        int width = getMeasuredWidth() - xPad;
        int height = getMeasuredHeight() - yPad;
        mSize = (width < height) ? width : height;
        setMeasuredDimension(mSize + xPad, mSize + yPad);
        updateRectAngleBounds();
    }

    /**
     * Set two rectangle bounds for drawing two circles.
     */
    private void updateRectAngleBounds() {
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        mOuterCircleRect.set(paddingLeft + mThickness, paddingTop + mThickness,
                mSize - paddingLeft - mThickness, mSize - paddingTop - mThickness);
        mInnerCircleRect.set(paddingLeft + mThickness + mInnerPadding,
                paddingTop + mThickness + mInnerPadding, mSize - paddingLeft - mThickness - mInnerPadding,
                mSize - paddingTop - mThickness - mInnerPadding);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mSize = (w < h) ? w : h;
        updateRectAngleBounds();
    }


    /**
     * Create the Circle Progress Animation sequence
     *
     * @param step
     * @return
     */
    private AnimatorSet createIndeterminateAnimator(float step) {

        final float maxSweep = 360f * (mSteps - 1) / mSteps + INDETERMINANT_MIN_SWEEP;
        final float start = -90f + step * (maxSweep - INDETERMINANT_MIN_SWEEP);

        // Extending the front of the arc
        ValueAnimator frontEndExtend = ValueAnimator.ofFloat(INDETERMINANT_MIN_SWEEP, maxSweep);
        frontEndExtend.setDuration(mAnimDuration / mSteps / 2);
        frontEndExtend.setInterpolator(new DecelerateInterpolator(1));
        frontEndExtend.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mIndeterminateSweep = (Float) animation.getAnimatedValue();
                invalidate();
            }
        });

        // Overall rotation
        ValueAnimator rotateAnimator1 = ValueAnimator.ofFloat(step * 720f / mSteps, (step + .5f) * 720f / mSteps);
        rotateAnimator1.setDuration(mAnimDuration / mSteps / 2);
        rotateAnimator1.setInterpolator(new LinearInterpolator());
        rotateAnimator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mIndeterminateRotateOffset = (Float) animation.getAnimatedValue();
            }
        });

        // Followed by...

        // Retracting the back end of the arc
        ValueAnimator backEndRetract = ValueAnimator.ofFloat(start, start + maxSweep - INDETERMINANT_MIN_SWEEP);
        backEndRetract.setDuration(mAnimDuration / mSteps / 2);
        backEndRetract.setInterpolator(new DecelerateInterpolator(1));
        backEndRetract.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mStartAngle = (Float) animation.getAnimatedValue();
                mIndeterminateSweep = maxSweep - mStartAngle + start;
                invalidate();
            }
        });

        // More overall rotation
        ValueAnimator rotateAnimator2 = ValueAnimator.ofFloat((step + .5f) * 720f / mSteps, (step + 1) * 720f / mSteps);
        rotateAnimator2.setDuration(mAnimDuration / mSteps / 2);
        rotateAnimator2.setInterpolator(new LinearInterpolator());
        rotateAnimator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mIndeterminateRotateOffset = (Float) animation.getAnimatedValue();
            }
        });

        AnimatorSet set = new AnimatorSet();
        set.play(frontEndExtend).with(rotateAnimator1);
        set.play(backEndRetract).with(rotateAnimator2).after(rotateAnimator1);
        return set;
    }

    public void resetAnimation() {

        if (mIndeterminateAnimator != null && mIndeterminateAnimator.isRunning())
            mIndeterminateAnimator.cancel();
        mIndeterminateSweep = INDETERMINANT_MIN_SWEEP;

        // Build the whole AnimatorSet
        mIndeterminateAnimator = new AnimatorSet();
        AnimatorSet prevSet = null, nextSet;
        for (int k = 0; k < mSteps; k++) {
            nextSet = createIndeterminateAnimator(k);
            AnimatorSet.Builder builder = mIndeterminateAnimator.play(nextSet);
            if (prevSet != null)
                builder.after(prevSet);
            prevSet = nextSet;
        }

        // Listen to end of animation so we can infinitely loop
        mIndeterminateAnimator.addListener(new AnimatorListenerAdapter() {
            boolean wasCancelled = false;

            @Override
            public void onAnimationCancel(Animator animation) {
                wasCancelled = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (!wasCancelled)
                    resetAnimation();
            }
        });
        mIndeterminateAnimator.start();
    }

    /**
     * Starts the progress bar animation.
     * (This is an alias of resetAnimation() so it does the same thing.)
     */
    public void startAnimation() {
        resetAnimation();
    }

    /**
     * Stops the animation
     */

    public void stopAnimation() {
        if (mIndeterminateAnimator != null) {
            mIndeterminateAnimator.cancel();
            mIndeterminateAnimator = null;
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startAnimation();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopAnimation();
    }

    @Override
    public void setVisibility(int visibility) {
        int currentVisibility = getVisibility();
        super.setVisibility(visibility);
        if (visibility != currentVisibility) {
            if (visibility == View.VISIBLE) {
                resetAnimation();
            } else if (visibility == View.GONE || visibility == View.INVISIBLE) {
                stopAnimation();
            }
        }
    }



}
