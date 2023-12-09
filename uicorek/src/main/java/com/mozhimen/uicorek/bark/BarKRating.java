package com.mozhimen.uicorek.bark;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RatingBar;

import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion;
import com.mozhimen.uicorek.R;
import com.mozhimen.uicorek.drawablek.BaseDrawable;
import com.mozhimen.uicorek.drawablek.StarDrawable;

/**
 * Custom RatingBar
 *
 * @author shiju.wang
 * @date 2020-01-09
 * 参考 https://github.com/giswangsj/AndRatingBar
 */

@SuppressLint("AppCompatCustomView")
public class BarKRating extends RatingBar {

    /**
     * color of rating star
     */
    private ColorStateList mStarColor;

    /**
     * color of secondary rating star
     */
    private ColorStateList mSubStarColor;

    /**
     * background color of all star
     */
    private ColorStateList mBgColor;

    /**
     * customize star drawable
     */
    private int mStarDrawable;

    /**
     * customize background drawable
     */
    private int mBgDrawable;

    /**
     * if keep the origin color of star drawable
     */
    private boolean mKeepOriginColor;

    /**
     * the scale factor of ratingbar that can change the spacing of star
     */
    private float scaleFactor;

    /**
     * additional the spacing of the star
     */
    private float starSpacing;

    /**
     * right to left
     */
    private boolean right2Left;

    private StarDrawable mDrawable;

    /**
     * event listener
     */
    private OnRatingChangeListener mOnRatingChangeListener;

    private float mTempRating;

    public BarKRating(Context context) {
        this(context, null);
    }

    public BarKRating(Context context, AttributeSet attrs) {
        // notice:can't use this(context, attrs, 0); because ratingbar has it's own defStyleAttr
        super(context, attrs);
        init(context, attrs, 0);
    }

    public BarKRating(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
//        TintTypedArray typedArray = TintTypedArray.obtainStyledAttributes(context, attrs,
//                R.styleable.AndRatingBar, defStyleAttr, 0);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BarKRating, defStyleAttr, 0);
        right2Left = typedArray.getBoolean(R.styleable.BarKRating_barKRating_right2Left, false);

        if (typedArray.hasValue(R.styleable.BarKRating_barKRating_starColor)) {
            if (right2Left) {
                mBgColor = typedArray.getColorStateList(
                        R.styleable.BarKRating_barKRating_starColor);
            } else {
                mStarColor = typedArray.getColorStateList(
                        R.styleable.BarKRating_barKRating_starColor);
            }
        }

        if (typedArray.hasValue(R.styleable.BarKRating_barKRating_subStarColor)) {
            if (!right2Left) {
                mSubStarColor = typedArray.getColorStateList(
                        R.styleable.BarKRating_barKRating_subStarColor);
            }
        }

        if (typedArray.hasValue(R.styleable.BarKRating_barKRating_bgColor)) {
            if (right2Left) {
                mStarColor = typedArray.getColorStateList(
                        R.styleable.BarKRating_barKRating_bgColor);
            } else {
                mBgColor = typedArray.getColorStateList(
                        R.styleable.BarKRating_barKRating_bgColor);
            }
        }

        mKeepOriginColor = typedArray.getBoolean(R.styleable.BarKRating_barKRating_keepOriginColor, false);
        scaleFactor = typedArray.getFloat(R.styleable.BarKRating_barKRating_scaleFactor, 1);
        starSpacing = typedArray.getDimension(R.styleable.BarKRating_barKRating_starSpacing, 0);

        // get customize drawable
        mStarDrawable = typedArray.getResourceId(R.styleable.BarKRating_barKRating_starDrawable, R.drawable.bark_rating_star);
        if (typedArray.hasValue(R.styleable.BarKRating_barKRating_bgDrawable)) {
            mBgDrawable = typedArray.getResourceId(R.styleable.BarKRating_barKRating_bgDrawable, R.drawable.bark_rating_star);
        } else {
            mBgDrawable = mStarDrawable;
        }

        typedArray.recycle();

        mDrawable = new StarDrawable(context, mStarDrawable, mBgDrawable, mKeepOriginColor);
        mDrawable.setStarCount(getNumStars());
        setProgressDrawable(mDrawable);

        if (right2Left) {
            setRating(getNumStars() - getRating());
        }
    }

    @Override
    public void setNumStars(int numStars) {
        super.setNumStars(numStars);
        if (mDrawable != null) {
            mDrawable.setStarCount(numStars);
        }
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int height = getMeasuredHeight();
        int width = Math.round(height * mDrawable.getTileRatio() * getNumStars() * scaleFactor) + (int) ((getNumStars() - 1) * starSpacing);
        setMeasuredDimension(View.resolveSizeAndState(width, widthMeasureSpec, 0), height);
    }

    @Override
    public void setProgressDrawable(Drawable d) {
        super.setProgressDrawable(d);
        applyProgressTints();
    }

    private void applyProgressTints() {
        if (getProgressDrawable() == null) {
            return;
        }
        applyPrimaryProgressTint();
        applyProgressBackgroundTint();
        applySecondaryProgressTint();
    }

    private void applyPrimaryProgressTint() {
        if (mStarColor != null) {
            Drawable target = getTintTargetFromProgressDrawable(android.R.id.progress, true);
            if (target != null) {
                applyTintForDrawable(target, mStarColor);
            }
        }
    }

    private void applySecondaryProgressTint() {
        if (mSubStarColor != null) {
            Drawable target = getTintTargetFromProgressDrawable(android.R.id.secondaryProgress,
                    false);
            if (target != null) {
                applyTintForDrawable(target, mSubStarColor);
            }
        }
    }

    private void applyProgressBackgroundTint() {
        if (mBgColor != null) {
            Drawable target = getTintTargetFromProgressDrawable(android.R.id.background, false);
            if (target != null) {
                applyTintForDrawable(target, mBgColor);
            }
        }
    }

    private Drawable getTintTargetFromProgressDrawable(int layerId, boolean shouldFallback) {
        Drawable progressDrawable = getProgressDrawable();
        if (progressDrawable == null) {
            return null;
        }
        progressDrawable.mutate();
        Drawable layerDrawable = null;
        if (progressDrawable instanceof LayerDrawable) {
            layerDrawable = ((LayerDrawable) progressDrawable).findDrawableByLayerId(layerId);
        }
        if (layerDrawable == null && shouldFallback) {
            layerDrawable = progressDrawable;
        }
        return layerDrawable;
    }

    // Progress drawables in this library has already rewritten tint related methods for
    // compatibility.
    @SuppressLint("NewApi")
    private void applyTintForDrawable(Drawable drawable, ColorStateList tintList) {
        if (tintList != null) {
            if (drawable instanceof BaseDrawable) {
                drawable.setTintList(tintList);
            } else {
                if (UtilKBuildVersion.isAfterV_21_5_L()) {
                    drawable.setTintList(tintList);
                }
            }
            // The drawable (or one of its children) may not have been
            // stateful before applying the tint, so let's try again.
            if (drawable.isStateful()) {
                drawable.setState(getDrawableState());
            }
        }
    }

    /**
     * Get the listener that is listening for rating change events.
     *
     * @return The listener, may be null.
     */
    public OnRatingChangeListener getOnRatingChangeListener() {
        return mOnRatingChangeListener;
    }

    /**
     * Sets the listener to be called when the rating changes.
     *
     * @param listener The listener.
     */
    public void setOnRatingChangeListener(OnRatingChangeListener listener) {
        mOnRatingChangeListener = listener;
        if (right2Left) {
            mOnRatingChangeListener.onRatingChanged(this, getNumStars() - getRating());
        } else {
            mOnRatingChangeListener.onRatingChanged(this, getRating());
        }
    }

    @Override
    public synchronized void setSecondaryProgress(int secondaryProgress) {
        super.setSecondaryProgress(secondaryProgress);

        // HACK: Check and call our listener here because this method is always called by
        // updateSecondaryProgress() from onProgressRefresh().
        float rating = getRating();
        if (mOnRatingChangeListener != null && rating != mTempRating) {
            if (right2Left) {
                mOnRatingChangeListener.onRatingChanged(this, getNumStars() - rating);
            } else {
                mOnRatingChangeListener.onRatingChanged(this, rating);
            }
        }
        mTempRating = rating;
    }

    /**
     * set the scale factor of the ratingbar
     *
     * @param scaleFactor
     */
    public void setScaleFactor(float scaleFactor) {
        this.scaleFactor = scaleFactor;
        requestLayout();
    }

    /**
     * set the spacing of the star
     *
     * @param starSpacing
     */
    public void setStarSpacing(float starSpacing) {
        this.starSpacing = starSpacing;
        requestLayout();
    }

    /**
     * A callback that notifies clients when the rating has been changed. This includes changes that
     * were initiated by the user through a touch gesture or arrow key/trackball as well as changes
     * that were initiated programmatically. This callback <strong>will</strong> be called
     * continuously while the user is dragging, different from framework's
     * {@link OnRatingBarChangeListener}.
     */
    public interface OnRatingChangeListener {

        /**
         * Notification that the rating has changed. This <strong>will</strong> be called
         * continuously while the user is dragging, different from framework's
         * {@link OnRatingBarChangeListener}.
         *
         * @param ratingBar The RatingBar whose rating has changed.
         * @param rating    The current rating. This will be in the range 0..numStars.
         */
        void onRatingChanged(BarKRating ratingBar, float rating);
    }
}
