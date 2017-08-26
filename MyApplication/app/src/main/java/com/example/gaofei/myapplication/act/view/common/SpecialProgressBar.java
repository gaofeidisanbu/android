package com.example.gaofei.myapplication.act.view.common;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import com.example.gaofei.myapplication.R;
import com.example.gaofei.myapplication.utils.CommonUtils;


/**
 * Created by gaofei on 2017/8/15.
 */

public class SpecialProgressBar extends View {

    private static final int ANIMATION_DELAY = 400; // ms
    private static final int ANIMATION_DURATION = 600; // ms

    private int mForeColor = Color.BLUE;
    private int mBackColor = Color.GRAY;
    private int mDelimiterColor = Color.RED;
    private float mDelimiterWidth = 1f;
    private float mCornerRadius = 0f;
    private int mMax = 100;
    private int mProgress = 0;

    private Paint mPaint;
    private Canvas mCanvas;
    private RectF mRectF;
    private Bitmap mBitmap;

    private boolean mShowAnimation;
    private boolean mShowDelimiter;
    private ValueAnimator mAnimator;
    private float mAnimatedValue;
    private int mTotalNum = 4;
    private int mFinishNum = 1;

    private PorterDuffXfermode mXfermodeSrcOver = new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER);
    private PorterDuffXfermode mXfermodeSrcAtop = new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP);


    public interface Listener {
        void onAnimationEnd();
    }

    public SpecialProgressBar(Context context) {
        super(context);
        init(null, 0);
    }

    public SpecialProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(null, 0);
    }

    public SpecialProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(null, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SpecialProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(null, 0);
    }


    private void init(AttributeSet attrs, int defStyle) {
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ProgressBar, defStyle, 0);
        mForeColor = a.getColor(R.styleable.ProgressBar_foreColor, mForeColor);
        mBackColor = a.getColor(R.styleable.ProgressBar_backColor, mBackColor);
        mDelimiterColor = a.getColor(R.styleable.ProgressBar_delimiterColor, mDelimiterColor);
        mDelimiterWidth = a.getDimension(R.styleable.ProgressBar_delimiterWidth, mDelimiterWidth);
        mCornerRadius = a.getDimension(R.styleable.ProgressBar_cornerRadius, mCornerRadius);
        mCornerRadius = CommonUtils.dip2px(getContext(), 4);
        // TODO: 2016/8/31 change
        mMax = (int) a.getFloat(R.styleable.ProgressBar_max, mMax);
        mProgress = (int) a.getFloat(R.styleable.ProgressBar_progress, mProgress);
        mShowAnimation = a.getBoolean(R.styleable.ProgressBar_showAnimation, true);
        mShowDelimiter = a.getBoolean(R.styleable.ProgressBar_showDelimiter, false);
        a.recycle();

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCanvas = new Canvas();
        mRectF = new RectF();

        setMax(mMax);
        setProgress(mProgress);

        if (isInEditMode()) {
            mAnimatedValue = 1f;
        }
    }

    public void setForeColor(int foreColor) {
        if (mForeColor != foreColor) {
            mForeColor = foreColor;
            invalidate();
        }
    }

    public void setBackColor(int backColor) {
        if (mBackColor != backColor) {
            mBackColor = backColor;
            invalidate();
        }
    }

    public void setCornerRadius(int cornerRadius) {
        if (mCornerRadius != cornerRadius) {
            mCornerRadius = cornerRadius;
            invalidate();
        }
    }

    public void setMax(int max) {
        if (mMax != max) {
            mMax = max;
            invalidate();
        }
    }

    public void setProgress(int progress) {
        setProgress(progress, false);
    }

    public void setProgress(int progress, boolean forceAnimation) {
        setProgress(0, progress, forceAnimation);
    }

    public void setProgress(int begin, int end, boolean forceAnimation) {
        if (forceAnimation) {
            if (mAnimator != null) {
                mAnimator.cancel();
                mAnimator = null;
            }

            mAnimatedValue = begin / (end + 0f);
        }

        if (mProgress != end) {
            mProgress = end;
            invalidate();
        }
    }

    public void setShowAnimation(boolean showAnimation) {
        if (mShowAnimation != showAnimation) {
            if (mAnimator != null) {
                mAnimator.cancel();
                mAnimator = null;
            }

            mAnimatedValue = showAnimation ? 0f : 1f;
            mShowAnimation = showAnimation;
            invalidate();
        }
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        // or java.lang.IllegalArgumentException: width and height must be > 0
        if (w <= 0 || h <= 0) {
            return;
        }

        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas.setBitmap(mBitmap);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mShowAnimation && mProgress > 0 && mAnimator == null) {
            mAnimator = ValueAnimator.ofFloat(mAnimatedValue, 1f);
            mAnimator.setDuration(ANIMATION_DURATION);
            mAnimator.setStartDelay(ANIMATION_DELAY);
            mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mAnimatedValue = (float) animation.getAnimatedValue();
                    invalidate();
                }
            });

            mAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                }

                @Override
                public void onAnimationEnd(Animator animator) {
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });

            mAnimator.start();
        }

        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int contentWidth = getWidth() - paddingLeft - paddingRight;
        int contentHeight = getHeight() - paddingTop - paddingBottom;
        mRectF.set(paddingLeft, paddingTop, paddingLeft + contentWidth, paddingTop + contentHeight);

        mCanvas.save();
        mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

        float animatedValue = mAnimator == null ? 1f : mAnimatedValue;
        float ratio = mMax == 0 ? 1f : (float) mProgress / mMax * animatedValue;
        ratio = Math.min(1f, Math.max(0f, ratio));
        if (ratio < 1f) {
            mPaint.setXfermode(mXfermodeSrcOver);
            mPaint.setColor(mBackColor);
            mCanvas.drawRoundRect(mRectF, mCornerRadius, mCornerRadius, mPaint);
        }

        mPaint.setXfermode(ratio == 1f ? mXfermodeSrcOver : mXfermodeSrcAtop);
        mPaint.setColor(mForeColor);
        mCanvas.translate(contentWidth * (ratio - 1), 0);
        mCanvas.drawRoundRect(mRectF, mCornerRadius, mCornerRadius, mPaint);
        mCanvas.restore();
        if (mTotalNum > 1) {
            int dividerWidth = CommonUtils.dip2px(getContext(), 4);
            int dividerCount = (mTotalNum - 1);
            RectF dividerRect = new RectF();
            int everyWidth = (contentWidth - dividerCount * dividerWidth) / mTotalNum;
            for (int i = 0; i < dividerCount; i++) {
                mPaint.setXfermode(mXfermodeSrcAtop);
                mPaint.setColor(Color.WHITE);
                int dividerLeft = everyWidth * (i + 1) + dividerWidth * i;
                int dividerRight = dividerLeft + dividerWidth;
                int dividerTop = paddingTop;
                int dividerBottom = paddingTop + contentHeight;
                dividerRect.set(dividerLeft, dividerTop, dividerRight, dividerBottom);
                mCanvas.drawRect(dividerRect, mPaint);
            }

        }


        if (mShowDelimiter) {
            mPaint.setColor(mDelimiterColor);
            mPaint.setStrokeWidth(mDelimiterWidth);

            final int M = mMax;
            final int N = 3;
            float gap = contentWidth / (M + 0f);
            for (int i = 1; i < M; i++) {
                int h = (int) (contentHeight * (i % N == 0 ? 0.6f : 0.3f));
                mCanvas.drawLine(i * gap + paddingLeft, contentHeight + paddingTop - h, i * gap + paddingLeft, contentHeight + paddingTop, mPaint);
            }
        }

        canvas.drawBitmap(mBitmap, 0, 0, null);
    }
}
