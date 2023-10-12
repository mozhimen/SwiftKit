package com.mozhimen.uicorek.textk;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;

import androidx.annotation.ColorInt;
import androidx.appcompat.widget.AppCompatTextView;

import com.mozhimen.uicorek.R;

import java.util.ArrayList;

/**
 * @author : Android-张康
 * created on: 2020/11/1 16:00
 * description:
 */
public class TextKProgressDownload extends AppCompatTextView {
    //背景画笔
    private Paint mBackgroundPaint;
    //按钮文字画笔
    private volatile Paint mTextPaint;

    //背景颜色
    private int mBackgroundColor;
    private int mNormalBackgroundColor;
    //下载中后半部分后面背景颜色
    private int mBackgroundSecondColor;
    //文字颜色
    private int mTextColor;
    private int mNormalTextColor;
    //覆盖后颜色
    private int mTextCoverColor;

    private float mButtonRadius;
    //边框宽度
    private float mBorderWidth;
    //边框颜色
    private int mBorderColor;
    //点动画样式
    private int mBallStyle = STYLE_BALL_JUMP;

    private float mProgress = -1;
    private float mToProgress;
    private int mMaxProgress;
    private int mMinProgress;
    private float mProgressPercent;

    private float mTextRightBorder;
    private float mTextBottomBorder;
    //点的间隙
    private float mBallSpacing = 4;
    //点的半径
    private float mBallRadius = 6;
    private RectF mBackgroundBounds = new RectF();
    ;
    private LinearGradient mProgressTextGradient;

    private boolean mEnableAnimation = false;
    /**
     * 下载平滑动画
     */
    private ValueAnimator mProgressAnimation;

    /**
     * 记录当前文字
     */
    private CharSequence mCurrentText;
    /**
     * 开始下载
     */
    public static final int STATE_NORMAL = 0;
    /**
     * 下载之中
     */
    public static final int STATE_DOWNLOADING = 1;
    /**
     * 暂停下载
     */
    public static final int STATE_PAUSE = 2;
    /**
     * 下载完成
     */
    public static final int STATE_FINISH = 3;

    public static final int STYLE_BALL_PULSE = 1;
    public static final int STYLE_BALL_JUMP = 2;

    private int mState;
    /**
     * 点运动动画
     */
    private ArrayList<ValueAnimator> mAnimators;

    public static final float SCALE = 1.0f;

    private float[] scaleFloats = new float[]{SCALE,
            SCALE,
            SCALE};

    private float[] translateYFloats = new float[3];

    public TextKProgressDownload(Context context) {
        this(context, null);
    }

    public TextKProgressDownload(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextKProgressDownload(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
        init();
        setupAnimations();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawing(canvas);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TextKProgressDownload);
        try {
            mBackgroundColor = a.getColor(R.styleable.TextKProgressDownload_textKProgressDownload_backgroundColor, Color.parseColor("#BFE8D9"));
            mNormalBackgroundColor = a.getColor(R.styleable.TextKProgressDownload_textKProgressDownload_backgroundColorNormal, Color.parseColor("#00A667"));
            mBackgroundSecondColor = a.getColor(R.styleable.TextKProgressDownload_textKProgressDownload_backgroundColorSecond, Color.WHITE);
            mButtonRadius = a.getDimension(R.styleable.TextKProgressDownload_textKProgressDownload_radius, 0);
            mTextColor = a.getColor(R.styleable.TextKProgressDownload_textKProgressDownload_textColor, Color.parseColor("#00A667"));
            mNormalTextColor = a.getColor(R.styleable.TextKProgressDownload_textKProgressDownload_backgroundColorNormal, Color.WHITE);
            mTextCoverColor = a.getColor(R.styleable.TextKProgressDownload_textKProgressDownload_textColorCover, Color.parseColor("#00A667"));
            mBorderWidth = a.getDimension(R.styleable.TextKProgressDownload_textKProgressDownload_borderWidth, dp2px(2));
            mBorderColor = a.getColor(R.styleable.TextKProgressDownload_textKProgressDownload_borderColor, Color.parseColor("#BFE8D9"));
            mBallStyle = a.getInt(R.styleable.TextKProgressDownload_textKProgressDownload_ballStyle, STYLE_BALL_JUMP);
        } finally {
            a.recycle();
        }
    }

    private void init() {
        mMaxProgress = 100;
        mMinProgress = 0;
        mProgress = 0;
        //设置背景画笔
        mBackgroundPaint = new Paint();
        mBackgroundPaint.setAntiAlias(true);
        mBackgroundPaint.setStyle(Paint.Style.FILL);
        //设置文字画笔
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(getTextSize());
        //解决文字有时候画不出问题
        setLayerType(LAYER_TYPE_SOFTWARE, mTextPaint);
        //初始化状态设为NORMAL
        mState = STATE_NORMAL;
    }

    private void setupAnimations() {
        if (mEnableAnimation) {
            //ProgressBar的动画
            mProgressAnimation = ValueAnimator.ofFloat(0, 1).setDuration(500);
            mProgressAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float timePercent = (float) animation.getAnimatedValue();
                    mProgress = ((mToProgress - mProgress) * timePercent + mProgress);
                    invalidate();
                }
            });

            setBallStyle(mBallStyle);
        }
    }


    private void drawing(Canvas canvas) {
        drawBackground(canvas);
        drawTextAbove(canvas);
    }

    private void drawBackground(Canvas canvas) {
        switch (mState) {
            case STATE_PAUSE:
            case STATE_DOWNLOADING:
                //下载中 需要绘制边框
                mBackgroundBounds.left = mBorderWidth;
                mBackgroundBounds.top = mBorderWidth;
                mBackgroundBounds.right = getMeasuredWidth() - mBorderWidth;
                mBackgroundBounds.bottom = getMeasuredHeight() - mBorderWidth;
                mBackgroundPaint.setStyle(Paint.Style.STROKE);
                mBackgroundPaint.setColor(mBorderColor);
                mBackgroundPaint.setStrokeWidth(mBorderWidth);
                canvas.drawRoundRect(mBackgroundBounds, mButtonRadius, mButtonRadius, mBackgroundPaint);
                //计算当前的进度
                mBackgroundPaint.setStyle(Paint.Style.FILL);
                mProgressPercent = mProgress / (mMaxProgress + 0f);
                mBackgroundPaint.setColor(mBackgroundSecondColor);
                canvas.save();
                //画出dst图层
                canvas.drawRoundRect(mBackgroundBounds, mButtonRadius, mButtonRadius, mBackgroundPaint);
                //设置图层显示模式为 SRC_ATOP
                PorterDuffXfermode porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP);
                mBackgroundPaint.setColor(mBackgroundColor);
                mBackgroundPaint.setXfermode(porterDuffXfermode);
                //计算 src 矩形的右边界
                float right = mBackgroundBounds.right * mProgressPercent;
                //在dst画出src矩形
                canvas.drawRect(mBackgroundBounds.left, mBackgroundBounds.top, right, mBackgroundBounds.bottom, mBackgroundPaint);
                canvas.restore();
                mBackgroundPaint.setXfermode(null);
                break;
            case STATE_NORMAL:
            case STATE_FINISH:
            default:
                mBackgroundBounds.left = 0;
                mBackgroundBounds.top = 0;
                mBackgroundBounds.right = getMeasuredWidth();
                mBackgroundBounds.bottom = getMeasuredHeight();
                mBackgroundPaint.setStyle(Paint.Style.FILL);
                mBackgroundPaint.setColor(mNormalBackgroundColor);
                canvas.drawRoundRect(mBackgroundBounds, mButtonRadius, mButtonRadius, mBackgroundPaint);
                break;
        }
    }

    private void drawTextAbove(Canvas canvas) {
        //计算Baseline绘制的Y坐标
        final float y = canvas.getHeight() / 2 - (mTextPaint.descent() / 2 + mTextPaint.ascent() / 2);
        if (mCurrentText == null) {
            if (isInEditMode()) {
                mCurrentText = getText();
            } else {
                mCurrentText = "";
            }
        }
        final float textWidth = mTextPaint.measureText(mCurrentText.toString());
        mTextBottomBorder = y;
        mTextRightBorder = (getMeasuredWidth() + textWidth) / 2;
        //color
        switch (mState) {
            case STATE_PAUSE:
            case STATE_DOWNLOADING:
                //进度条压过距离
                float coverLength = getMeasuredWidth() * mProgressPercent;
                //开始渐变指示器
                float indicator1 = getMeasuredWidth() / 2.0F - textWidth / 2;
                //结束渐变指示器
                float indicator2 = getMeasuredWidth() / 2.0F + textWidth / 2;
                //文字变色部分的距离
                float coverTextLength = textWidth / 2 - getMeasuredWidth() / 2.0F + coverLength;
                float textProgress = coverTextLength / textWidth;
                if (coverLength <= indicator1) {
                    mTextPaint.setShader(null);
                    mTextPaint.setColor(mTextColor);
                } else if (indicator1 < coverLength && coverLength <= indicator2) {
                    //设置变色效果
                    mProgressTextGradient = new LinearGradient((getMeasuredWidth() - textWidth) / 2, 0, (getMeasuredWidth() + textWidth) / 2, 0,
                            new int[]{mTextCoverColor, mTextColor},
                            new float[]{textProgress, textProgress + 0.001f},
                            Shader.TileMode.CLAMP);
                    mTextPaint.setColor(mTextColor);
                    mTextPaint.setShader(mProgressTextGradient);
                } else {
                    mTextPaint.setShader(null);
                    mTextPaint.setColor(mTextCoverColor);
                }
                canvas.drawText(mCurrentText.toString(), (getMeasuredWidth() - textWidth) / 2, y, mTextPaint);
                break;
            case STATE_NORMAL:
            case STATE_FINISH:
            default:
                mTextPaint.setShader(null);
                mTextPaint.setColor(mNormalTextColor);
                canvas.drawText(mCurrentText.toString(), (getMeasuredWidth() - textWidth) / 2, y, mTextPaint);
                break;

        }
    }

    public void drawLoadingBall(Canvas canvas) {
        for (int i = 0; i < 3; i++) {
            canvas.save();
            float translateX = mTextRightBorder + 10 + (mBallRadius * 2) * i + mBallSpacing * i;
            canvas.translate(translateX, mTextBottomBorder);
            canvas.drawCircle(0, translateYFloats[i], mBallRadius * scaleFloats[i], mTextPaint);
            canvas.restore();
        }
    }

    private void startAnimators() {
        if (null != mAnimators) {
            for (int i = 0; i < mAnimators.size(); i++) {
                ValueAnimator animator = mAnimators.get(i);
                animator.start();
            }
        }
    }

    private void stopAnimators() {
        if (mAnimators != null) {
            for (ValueAnimator animator : mAnimators) {
                if (animator != null && animator.isStarted()) {
                    animator.end();
                }
            }
        }
    }


    public ArrayList<ValueAnimator> createBallPulseAnimators() {
        ArrayList<ValueAnimator> animators = new ArrayList<>();
        int[] delays = new int[]{120, 240, 360};
        for (int i = 0; i < 3; i++) {
            final int index = i;

            ValueAnimator scaleAnim = ValueAnimator.ofFloat(1, 0.3f, 1);

            scaleAnim.setDuration(750);
            scaleAnim.setRepeatCount(-1);
            scaleAnim.setStartDelay(delays[i]);

            scaleAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    scaleFloats[index] = (float) animation.getAnimatedValue();
                    postInvalidate();
                }
            });
            animators.add(scaleAnim);
        }
        return animators;
    }

    public ArrayList<ValueAnimator> createBallJumpAnimators() {
        ArrayList<ValueAnimator> animators = new ArrayList<>();
        int[] delays = new int[]{70, 140, 210};
        for (int i = 0; i < 3; i++) {
            final int index = i;
            ValueAnimator scaleAnim = ValueAnimator.ofFloat(mTextBottomBorder, mTextBottomBorder - mBallRadius * 2, mTextBottomBorder);
            scaleAnim.setDuration(600);
            scaleAnim.setRepeatCount(-1);
            scaleAnim.setStartDelay(delays[i]);
            scaleAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    translateYFloats[index] = (float) animation.getAnimatedValue();
                    postInvalidate();
                }
            });
            animators.add(scaleAnim);
        }
        return animators;
    }


    public int getState() {
        return mState;
    }

    public void setState(int state) {
        //状态确实有改变
        if (mState != state) {
            this.mState = state;
            invalidate();
            if (state == STATE_FINISH) {
                //开启点动画
                startAnimators();
            } else {
                stopAnimators();
            }
        }
    }

    /**
     * 设置当前按钮文字
     */
    public void setCurrentText(CharSequence charSequence) {
        mCurrentText = charSequence;
        invalidate();
    }

    /**
     * 设置带下载进度的文字
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void setProgressText(String text, int progress) {
        setProgressText(text, (float) progress);
    }

    /**
     * 设置带下载进度的文字
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void setProgressText(String text, float progress) {
        if (!TextUtils.isEmpty(text)) {
            mCurrentText = text;
        }
        if (progress >= mMinProgress && progress <= mMaxProgress) {
            if (mEnableAnimation) {
                mToProgress = progress;
                if (mProgressAnimation.isRunning()) {
                    mProgressAnimation.resume();
                }
                mProgressAnimation.start();
            } else {
                mProgress = progress;
                invalidate();
            }
        } else if (progress < mMinProgress) {
            mProgress = 0;
        } else if (progress > mMaxProgress) {
            mProgress = 100;
            invalidate();
        }
    }


    //设置点动画样式
    private void setBallStyle(int mBallStyle) {
        this.mBallStyle = mBallStyle;
        if (mBallStyle == STYLE_BALL_PULSE) {
            mAnimators = createBallPulseAnimators();
        } else {
            mAnimators = createBallJumpAnimators();
        }
    }

    public float getBorderWidth() {
        return mBorderWidth;
    }

    public void setBorderWidth(int width) {
        this.mBorderWidth = dp2px(width);
        invalidate();
    }

    public float getProgress() {
        return mProgress;
    }

    public void setProgress(float progress) {
        this.mProgress = progress;
        invalidate();
    }

    public float getButtonRadius() {
        return mButtonRadius;
    }

    public void setButtonRadius(float buttonRadius) {
        mButtonRadius = buttonRadius;
        invalidate();
    }

    public int getTextColor() {
        return mTextColor;
    }

    @Override
    public void setTextColor(int textColor) {
        super.setTextColor(textColor);
        mTextColor = textColor;
    }

    public int getTextCoverColor() {
        return mTextCoverColor;
    }

    public void setTextCoverColor(int textCoverColor) {
        mTextCoverColor = textCoverColor;
    }

    public int getMinProgress() {
        return mMinProgress;
    }

    public void setMinProgress(int minProgress) {
        mMinProgress = minProgress;
    }

    public int getMaxProgress() {
        return mMaxProgress;
    }

    public void setMaxProgress(int maxProgress) {
        mMaxProgress = maxProgress;
    }

    public void setBorderColor(@ColorInt int borderColor) {
        mBorderColor = borderColor;
        invalidate();
    }

    @Override
    public void setBackgroundColor(@ColorInt int backgroundColor) {
        mBackgroundColor = backgroundColor;
        invalidate();
    }

    public void setNormalBackgroundColor(int normalBackgroundColor) {
        mNormalBackgroundColor = normalBackgroundColor;
        invalidate();
    }

    public void setBackgroundSecondColor(@ColorInt int backgroundSecondColor) {
        mBackgroundSecondColor = backgroundSecondColor;
        invalidate();
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        TextKProgressDownload.SavedState ss = (TextKProgressDownload.SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        mState = ss.state;
        mProgress = ss.progress;
        mCurrentText = ss.currentText;
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        if (mCurrentText == null) {
            mCurrentText = "";
        }
        return new TextKProgressDownload.SavedState(superState, (int) mProgress, mState, mCurrentText.toString());
    }

    public static class SavedState extends BaseSavedState {

        private int progress;
        private int state;
        private String currentText;

        public SavedState(Parcelable parcel, int progress, int state, String currentText) {
            super(parcel);
            this.progress = progress;
            this.state = state;
            this.currentText = currentText;
        }

        private SavedState(Parcel in) {
            super(in);
            progress = in.readInt();
            state = in.readInt();
            currentText = in.readString();
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(progress);
            out.writeInt(state);
            out.writeString(currentText);
        }

        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {

            @Override
            public TextKProgressDownload.SavedState createFromParcel(Parcel in) {
                return new TextKProgressDownload.SavedState(in);
            }

            @Override
            public TextKProgressDownload.SavedState[] newArray(int size) {
                return new TextKProgressDownload.SavedState[size];
            }
        };
    }

    private int dp2px(int dp) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * density);
    }
}
