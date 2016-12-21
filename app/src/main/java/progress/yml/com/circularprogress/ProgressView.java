package progress.yml.com.circularprogress;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

/**
 * Created by YMediaLabs
 * <p>
 * Copyright (C) 2016
 */
public class ProgressView extends View {
    private static final String TAG = "ProgressView";
    public static final float THICKNESS = 10f;
    private Paint mPaint;
    private RectF mRect;
    private float mStartAngle=0f;
    private float mSweepAngle=15f;
    private float mRotate;

    public ProgressView(Context context) {
        super(context);
        createPaint();
    }

    public ProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        createPaint();
    }

    public ProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        createPaint();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ProgressView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        createPaint();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawArc(mRect, mStartAngle, mSweepAngle, false, mPaint);

    }

    private void createPaint() {
        mPaint = new Paint();
        mPaint.setColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(THICKNESS);
        mPaint.setStrokeCap(Paint.Cap.BUTT);
        mRect = new RectF();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
       createAnimation();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int xPad = getPaddingLeft() + getPaddingRight();
        int yPad = getPaddingTop() + getPaddingBottom();
        int width = getMeasuredWidth() - xPad;
        int height = getMeasuredHeight() - yPad;
        int size = (width < height) ? width : height;
        mRect.left = THICKNESS;
        mRect.top = THICKNESS;
        mRect.right = size - getPaddingLeft() - THICKNESS;
        mRect.bottom = size - getPaddingLeft() - THICKNESS;
        setMeasuredDimension(size + xPad, size + yPad);

    }

    private void createAnimation(){

        // Extending the front of the arc
        ValueAnimator frontEndExtend = ValueAnimator.ofFloat(mSweepAngle, 360f);
        frontEndExtend.setDuration(4000);
        frontEndExtend.setInterpolator(new DecelerateInterpolator(1));
        frontEndExtend.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mSweepAngle = (Float) animation.getAnimatedValue();
                invalidate();
            }
        });
        frontEndExtend.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
              //  mSweepAngle = 15f;

            }
        });

        // Extending the front of the arc

       final  ValueAnimator BackEndShort = ValueAnimator.ofFloat(0,360f);
        BackEndShort.setDuration(2000);
        BackEndShort.setInterpolator(new DecelerateInterpolator(1));
        BackEndShort.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mStartAngle = (Float) animation.getAnimatedValue();
                mSweepAngle=360f-mStartAngle;
                Log.d(TAG, "Start Angle :" + mStartAngle);
                Log.d(TAG, "Sweep Angle :" + mSweepAngle);
                invalidate();
            }
        });
        BackEndShort.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });



        final ValueAnimator valueAnimator = ValueAnimator.ofFloat(mStartAngle, 360f);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator anim) {
               mStartAngle = (Float) anim.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
               // mStartAngle = 0;
               // createAnimation();
                BackEndShort.start();
            }
        });
        valueAnimator.setDuration(4000);


        final ValueAnimator rotate = ValueAnimator.ofFloat(0f, 360f);
        rotate.setInterpolator(new LinearInterpolator());
        rotate.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator anim) {
                mRotate = (Float) anim.getAnimatedValue();
                invalidate();
            }
        });
        rotate.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                // mStartAngle = 0;
                // createAnimation();

            }
        });
        rotate.setDuration(4000);
        rotate.setRepeatMode(ValueAnimator.RESTART);



        AnimatorSet set = new AnimatorSet();
        set.play(frontEndExtend).with(valueAnimator).with(rotate);
       // set.play(BackEndShort);
        set.start();


    }

}
