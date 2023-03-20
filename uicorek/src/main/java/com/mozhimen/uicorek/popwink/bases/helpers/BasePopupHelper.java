package com.mozhimen.uicorek.popwink.bases.helpers;

import android.animation.Animator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Message;
import android.util.LayoutDirection;
import android.util.Log;
import android.view.DisplayCutout;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.mozhimen.basick.elemk.cons.CVersionCode;
import com.mozhimen.basick.stackk.StackK;
import com.mozhimen.basick.utilk.res.UtilKRes;
import com.mozhimen.basick.utilk.view.display.UtilKScreen;
import com.mozhimen.basick.utilk.content.activity.UtilKActivity;
import com.mozhimen.basick.utilk.anim.UtilKAnim;
import com.mozhimen.basick.utilk.anim.UtilKAnimation;
import com.mozhimen.basick.utilk.anim.UtilKAnimator;
import com.mozhimen.basick.utilk.log.UtilKLog;
import com.mozhimen.basick.utilk.view.keyboard.UtilKInputManager;
import com.mozhimen.basick.utilk.view.bar.UtilKNavigationBar;
import com.mozhimen.basick.utilk.graphics.bitmap.blur.mos.UtilKBitmapBluConfig;
import com.mozhimen.basick.utilk.view.keyboard.UtilKKeyboardChange;
import com.mozhimen.basick.utilk.log.UtilKLogPro;
import com.mozhimen.basick.utilk.view.UtilKView;
import com.mozhimen.uicorek.R;
import com.mozhimen.uicorek.popwink.bases.BasePopwinK;
import com.mozhimen.uicorek.popwink.bases.commons.IClearMemoryListener;
import com.mozhimen.uicorek.popwink.bases.commons.IEventObserver;
import com.mozhimen.uicorek.popwink.bases.cons.CEvent;
import com.mozhimen.uicorek.popwink.bases.cons.CFlag;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * Created by 大灯泡 on 2017/12/12.
 * <p>
 * PopupHelper，这货与Popup强引用哦~
 */
@SuppressWarnings("all")
public final class BasePopupHelper implements UtilKKeyboardChange.IUtilKKeyboardChangeListener, IClearMemoryListener {

    private static final String TAG = "BasePopupHelper>>>>>";
    BasePopwinK mPopupWindow;

    WeakHashMap<Object, IEventObserver> eventObserverMap;

    Map<Integer, Boolean> mFlagCacheMap;


    enum ShowMode {
        RELATIVE_TO_ANCHOR,
        SCREEN,
        POSITION
    }

    static final long DEFAULT_KEYBOARD_SHOW_DELAY = 350;
    static final int DEFAULT_OVERLAY_STATUS_BAR_MODE = CFlag.OVERLAY_MASK | CFlag.OVERLAY_CONTENT;
    static final int DEFAULT_OVERLAY_NAVIGATION_BAR_MODE = CFlag.OVERLAY_MASK;
    private static final int CONTENT_VIEW_ID = R.id.base_popwink_content_root;

    public static final int STATUS_START_SHOWING = 0x1;
    static final int STATUS_START_DISMISS = 0x2;
    public int showFlag = 0;
    public BasePopwinK.Priority priority = BasePopwinK.Priority.NORMAL;

    ShowMode mShowMode = ShowMode.SCREEN;

    int contentRootId = CONTENT_VIEW_ID;

    public int flag = CFlag.IDLE;

    //animate
    public Animation mShowAnimation;
    public Animator mShowAnimator;
    public Animation mDismissAnimation;
    public Animator mDismissAnimator;
    boolean preventInitShowAnimation;
    boolean preventInitDismissAnimation;

    public Animation mMaskViewShowAnimation;
    public Animation mMaskViewDismissAnimation;

    public boolean isDefaultMaskViewShowAnimation;
    public boolean isDefaultMaskViewDismissAnimation;
    public boolean overlayMask = false;

    long showDuration;
    long dismissDuration;
    public long showKeybaordDelay = DEFAULT_KEYBOARD_SHOW_DELAY;

    public int animationStyleRes;

    //callback
    public BasePopwinK.OnDismissListener mOnDismissListener;
    public BasePopwinK.OnBeforeShowCallback mOnBeforeShowCallback;
    public BasePopwinK.OnPopupWindowShowListener mOnPopupWindowShowListener;

    //option
    BasePopwinK.GravityMode horizontalGravityMode = BasePopwinK.GravityMode.RELATIVE_TO_ANCHOR;
    BasePopwinK.GravityMode verticalGravityMode = BasePopwinK.GravityMode.RELATIVE_TO_ANCHOR;

    public int popupGravity = Gravity.NO_GRAVITY;
    public int offsetX;
    public int offsetY;
    public int maskOffsetX;
    public int maskOffsetY;
    public int preMeasureWidth;
    public int preMeasureHeight;

    public int keyboardGravity = Gravity.BOTTOM;
    public int keyboardOffsetX;
    public int keyboardOffsetY;

    int popupViewWidth = 0;
    int popupViewHeight = 0;
    public int layoutDirection = LayoutDirection.LTR;
    //锚点view的location
    Rect mAnchorViewBound;

    //模糊option(为空的话则不模糊）
    UtilKBitmapBluConfig mBlurOption;
    //背景颜色
    Drawable mBackgroundDrawable = new ColorDrawable(BasePopwinK.DEFAULT_BACKGROUND_COLOR);
    //背景对齐方向
    int alignBackgroundGravity = Gravity.TOP;
    //背景View
    View mBackgroundView;

    public EditText mAutoShowInputEdittext;

    UtilKKeyboardChange.IUtilKKeyboardChangeListener mKeyboardStateChangeListener;
    public UtilKKeyboardChange.IUtilKKeyboardChangeListener mUserKeyboardStateChangeListener;
    public BasePopwinK.KeyEventListener mKeyEventListener;

    public int mSoftInputMode = WindowManager.LayoutParams.SOFT_INPUT_STATE_UNCHANGED;
    ViewGroup.MarginLayoutParams layoutParams;

    public int maxWidth;
    public int maxHeight;
    public int minWidth;
    public int minHeight;

    public int keybaordAlignViewId;
    public View keybaordAlignView;

    InnerShowInfo mShowInfo;

    ViewTreeObserver.OnGlobalLayoutListener mGlobalLayoutListener;
    LinkedViewLayoutChangeListenerWrapper mLinkedViewLayoutChangeListenerWrapper;

    View mLinkedTarget;

    Rect navigationBarBounds;
    Rect cutoutSafeRect;

    int lastOverLayStatusBarMode, overlayStatusBarMode = DEFAULT_OVERLAY_STATUS_BAR_MODE;
    int lastOverlayNavigationBarMode, overlayNavigationBarMode = DEFAULT_OVERLAY_NAVIGATION_BAR_MODE;

    public boolean hideKeyboardOnDismiss = true;

    //unsafe
    BasePopupUnsafe.OnFitWindowManagerLayoutParamsCallback mOnFitWindowManagerLayoutParamsCallback;

    public BasePopupHelper(BasePopwinK popupWindow) {
        mFlagCacheMap = new HashMap<>();
        mAnchorViewBound = new Rect();
        navigationBarBounds = new Rect();
        cutoutSafeRect = new Rect();
        this.mPopupWindow = popupWindow;
        this.eventObserverMap = new WeakHashMap<>();
        this.mMaskViewShowAnimation = new AlphaAnimation(0f, 1f);
        this.mMaskViewDismissAnimation = new AlphaAnimation(1f, 0f);
        this.mMaskViewShowAnimation.setFillAfter(true);
        this.mMaskViewShowAnimation.setInterpolator(new DecelerateInterpolator());
        this.mMaskViewShowAnimation.setDuration(UtilKRes.getSystemResource()
                .getInteger(android.R.integer.config_shortAnimTime));
        isDefaultMaskViewShowAnimation = true;
        this.mMaskViewDismissAnimation.setFillAfter(true);
        this.mMaskViewDismissAnimation.setInterpolator(new DecelerateInterpolator());
        this.mMaskViewDismissAnimation.setDuration(UtilKRes.getSystemResource()
                .getInteger(android.R.integer.config_shortAnimTime));
        isDefaultMaskViewDismissAnimation = true;
    }

    public void cacheFlag(int flag, boolean onceCache) {
        if (onceCache && mFlagCacheMap.containsKey(flag)) return;
        mFlagCacheMap.put(flag, (this.flag & flag) != 0);
    }

    public boolean restoreFlag(int flag, boolean defaultValue) {
        if (mFlagCacheMap.containsKey(flag)) {
            return mFlagCacheMap.remove(flag);
        }
        return defaultValue;
    }

    void observerEvent(Object who, IEventObserver observer) {
        eventObserverMap.put(who, observer);
    }

    void removeEventObserver(Object who) {
        eventObserverMap.remove(who);
    }

    public void sendEvent(Message msg) {
        if (msg == null) return;
        if (msg.what < 0) return;
        for (Map.Entry<Object, IEventObserver> entry : eventObserverMap.entrySet()) {
            if (entry.getValue() != null) {
                entry.getValue().onEvent(msg);
            }
        }
    }

    public View inflate(Context context, int layoutId) {
        try {
            FrameLayout tempLayout = new FrameLayout(context);
            View result = LayoutInflater.from(context).inflate(layoutId, tempLayout, false);
            ViewGroup.LayoutParams childParams = result.getLayoutParams();
            if (childParams != null) {
                checkAndSetGravity(childParams);
                if (childParams instanceof ViewGroup.MarginLayoutParams) {
                    layoutParams = new ViewGroup.MarginLayoutParams((ViewGroup.MarginLayoutParams) childParams);
                } else {
                    layoutParams = new ViewGroup.MarginLayoutParams(childParams);
                }

                if (popupViewWidth != 0 && layoutParams.width != popupViewWidth) {
                    layoutParams.width = popupViewWidth;
                }
                if (popupViewHeight != 0 && layoutParams.height != popupViewHeight) {
                    layoutParams.height = popupViewHeight;
                }
                tempLayout = null;
                return result;
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            UtilKLog.et(TAG,e.getMessage());
        }
        return null;
    }

    public void preMeasurePopupView(View mContentView, int w, int h) {
        if (mContentView != null) {
            int measureWidth = View.MeasureSpec.makeMeasureSpec(Math.max(w, 0),
                    w == ViewGroup.LayoutParams.WRAP_CONTENT ? View.MeasureSpec.UNSPECIFIED : View.MeasureSpec.EXACTLY);
            int measureHeight = View.MeasureSpec.makeMeasureSpec(Math.max(w, h),
                    h == ViewGroup.LayoutParams.WRAP_CONTENT ? View.MeasureSpec.UNSPECIFIED : View.MeasureSpec.EXACTLY);
            mContentView.measure(measureWidth, measureHeight);
            preMeasureWidth = mContentView.getMeasuredWidth();
            preMeasureHeight = mContentView.getMeasuredHeight();
            mContentView.setFocusableInTouchMode(true);
        }
    }

    void checkAndSetGravity(ViewGroup.LayoutParams p) {
        //如果设置过gravity，则采取设置的gravity，顶替掉xml设置的（针对lazypopup）
        //https://github.com/razerdp/BasePopup/issues/310
        if (p == null || this.popupGravity != Gravity.NO_GRAVITY) return;
        if (p instanceof LinearLayout.LayoutParams) {
            this.popupGravity = ((LinearLayout.LayoutParams) p).gravity;
        } else if (p instanceof FrameLayout.LayoutParams) {
            this.popupGravity = ((FrameLayout.LayoutParams) p).gravity;
        }
    }

    //region Animation

    void startShowAnimate(int width, int height) {
        if (!preventInitShowAnimation) {
            if (initShowAnimation(width, height) == null) {
                initShowAnimator(width, height);
            }
        }
        //动画只初始化一次，后续请自行通过setAnimation/setAnimator实现
        preventInitShowAnimation = true;
        //通知蒙层动画，此时duration已经计算完毕
        Message msg = Message.obtain();
        msg.what = CEvent.EVENT_SHOW;
        sendEvent(msg);
        if (mShowAnimation != null) {
            mShowAnimation.cancel();
            mPopupWindow.mDisplayAnimateView.startAnimation(mShowAnimation);
        } else if (mShowAnimator != null) {
            mShowAnimator.setTarget(mPopupWindow.getDisplayAnimateView());
            mShowAnimator.cancel();
            mShowAnimator.start();
        }
    }

    void startDismissAnimate(int width, int height) {
        if (!preventInitDismissAnimation) {
            if (initDismissAnimation(width, height) == null) {
                initDismissAnimator(width, height);
            }
        }
        //动画只初始化一次，后续请自行通过setAnimation/setAnimator实现
        preventInitDismissAnimation = true;
        if (mDismissAnimation != null) {
            mDismissAnimation.cancel();
            mPopupWindow.mDisplayAnimateView.startAnimation(mDismissAnimation);
            if (mOnDismissListener != null) {
                mOnDismissListener.onDismissAnimationStart();
            }
            setFlag(CFlag.CUSTOM_ON_ANIMATE_DISMISS, true);
        } else if (mDismissAnimator != null) {
            mDismissAnimator.setTarget(mPopupWindow.getDisplayAnimateView());
            mDismissAnimator.cancel();
            mDismissAnimator.start();
            if (mOnDismissListener != null) {
                mOnDismissListener.onDismissAnimationStart();
            }
            setFlag(CFlag.CUSTOM_ON_ANIMATE_DISMISS, true);
        }
    }

    Animation initShowAnimation(int width, int height) {
        if (mShowAnimation == null) {
            mShowAnimation = mPopupWindow.onCreateShowAnimation(width, height);
            if (mShowAnimation != null) {
                showDuration = UtilKAnimation.getAnimationDuration(mShowAnimation);
                setToBlur(mBlurOption);
            }
        }
        return mShowAnimation;
    }

    Animator initShowAnimator(int width, int height) {
        if (mShowAnimator == null) {
            mShowAnimator = mPopupWindow.onCreateShowAnimator(width, height);
            if (mShowAnimator != null) {
                showDuration = UtilKAnimator.getAnimatorDuration(mShowAnimator);
                setToBlur(mBlurOption);
            }
        }
        return mShowAnimator;
    }

    Animation initDismissAnimation(int width, int height) {
        if (mDismissAnimation == null) {
            mDismissAnimation = mPopupWindow.onCreateDismissAnimation(width, height);
            if (mDismissAnimation != null) {
                dismissDuration = UtilKAnimation.getAnimationDuration(mDismissAnimation);
                setToBlur(mBlurOption);
            }
        }
        return mDismissAnimation;
    }

    Animator initDismissAnimator(int width, int height) {
        if (mDismissAnimator == null) {
            mDismissAnimator = mPopupWindow.onCreateDismissAnimator(width, height);
            if (mDismissAnimator != null) {
                dismissDuration = UtilKAnimator.getAnimatorDuration(mDismissAnimator);
                setToBlur(mBlurOption);
            }
        }
        return mDismissAnimator;
    }


    public void setToBlur(UtilKBitmapBluConfig option) {
        this.mBlurOption = option;
        if (option != null) {
            if (option.getBlurInDuration() <= 0) {
                if (showDuration > 0) {
                    option.setBlurInDuration(showDuration);
                }
            }
            if (option.getBlurOutDuration() <= 0) {
                if (dismissDuration > 0) {
                    option.setBlurOutDuration(dismissDuration);
                }
            }
        }
    }


    public void setShowAnimation(Animation showAnimation) {
        if (mShowAnimation == showAnimation) return;
        if (mShowAnimation != null) {
            mShowAnimation.cancel();
        }
        mShowAnimation = showAnimation;
        showDuration = UtilKAnimation.getAnimationDuration(mShowAnimation);
        setToBlur(mBlurOption);
    }

    /**
     * animation优先级更高
     */
    public void setShowAnimator(Animator showAnimator) {
        if (mShowAnimation != null || mShowAnimator == showAnimator) return;
        if (mShowAnimator != null) {
            mShowAnimator.cancel();
        }
        mShowAnimator = showAnimator;
        showDuration = UtilKAnimator.getAnimatorDuration(mShowAnimator);
        setToBlur(mBlurOption);
    }

    public void setDismissAnimation(Animation dismissAnimation) {
        if (mDismissAnimation == dismissAnimation) return;
        if (mDismissAnimation != null) {
            mDismissAnimation.cancel();
        }
        mDismissAnimation = dismissAnimation;
        dismissDuration = UtilKAnimation.getAnimationDuration(mDismissAnimation);
        setToBlur(mBlurOption);
    }

    public void setDismissAnimator(Animator dismissAnimator) {
        if (mDismissAnimation != null || mDismissAnimator == dismissAnimator) return;
        if (mDismissAnimator != null) {
            mDismissAnimator.cancel();
        }
        mDismissAnimator = dismissAnimator;
        dismissDuration = UtilKAnimator.getAnimatorDuration(mDismissAnimator);
        setToBlur(mBlurOption);
    }

    //endregion

    public BasePopupHelper setPopupViewWidth(int popupViewWidth) {
        if (popupViewWidth != 0) {
            getLayoutParams().width = popupViewWidth;
        }
        return this;
    }

    public BasePopupHelper setPopupViewHeight(int popupViewHeight) {
        if (popupViewHeight != 0) {
            getLayoutParams().height = popupViewHeight;
        }
        return this;
    }

    int getPreMeasureWidth() {
        return preMeasureWidth;
    }


    int getPreMeasureHeight() {
        return preMeasureHeight;
    }

    public boolean isPopupFadeEnable() {
        return (flag & CFlag.FADE_ENABLE) != 0;
    }

    boolean isWithAnchor() {
        return (flag & CFlag.WITH_ANCHOR) != 0;
    }

    boolean isFitsizable() {
        return (flag & CFlag.FITSIZE) != 0;
    }

    public BasePopupHelper withAnchor(boolean showAsDropDown) {
        setFlag(CFlag.WITH_ANCHOR, showAsDropDown);
        if (showAsDropDown && (popupGravity == Gravity.NO_GRAVITY || popupGravity == -1)) {
            popupGravity = Gravity.BOTTOM;
        }
        return this;
    }

    public BasePopupHelper setShowLocation(int x, int y) {
        mAnchorViewBound.set(x, y, x + 1, y + 1);
        return this;
    }


    public int getPopupGravity() {
        return Gravity.getAbsoluteGravity(popupGravity, layoutDirection);
    }

    BasePopupHelper setLayoutDirection(int layoutDirection) {
        this.layoutDirection = layoutDirection;
        return this;
    }

    public BasePopupHelper setPopupGravity(BasePopwinK.GravityMode mode, int popupGravity) {
        setPopupGravityMode(mode, mode);
        this.popupGravity = popupGravity;
        return this;
    }

    public BasePopupHelper setPopupGravityMode(BasePopwinK.GravityMode horizontalGravityMode,
                                               BasePopwinK.GravityMode verticalGravityMode) {
        this.horizontalGravityMode = horizontalGravityMode;
        this.verticalGravityMode = verticalGravityMode;
        return this;
    }

    public int getOffsetX() {
        return offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }


    boolean isAutoShowInputMethod() {
        return (flag & CFlag.AUTO_INPUT_METHOD) != 0;
    }

    public boolean isAutoMirror() {
        return (flag & CFlag.AUTO_MIRROR) != 0;
    }

    public boolean isOutSideDismiss() {
        return (flag & CFlag.OUT_SIDE_DISMISS) != 0;
    }

    public boolean isOutSideTouchable() {
        return (flag & CFlag.OUT_SIDE_TOUCHABLE) != 0;
    }

    BasePopupHelper getAnchorLocation(View v) {
        if (v == null) {
            if (mShowMode != ShowMode.POSITION) {
                mAnchorViewBound.setEmpty();
            }
            return this;
        }
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        mAnchorViewBound.set(location[0],
                location[1],
                location[0] + v.getWidth(),
                location[1] + v.getHeight());
        return this;
    }

    public Rect getAnchorViewBound() {
        return mAnchorViewBound;
    }

    public boolean isBackPressEnable() {
        return (flag & CFlag.BACKPRESS_ENABLE) != 0;
    }

    boolean isOverlayStatusbar() {
        return (flag & CFlag.OVERLAY_STATUS_BAR) != 0;
    }

    boolean isOverlayNavigationBar() {
        return (flag & CFlag.OVERLAY_NAVIGATION_BAR) != 0;
    }

    void refreshNavigationBarBounds() {
        UtilKNavigationBar.getNavigationBarBounds(navigationBarBounds, mPopupWindow.getContext());
    }

    int getNavigationBarSize() {
        return Math.min(navigationBarBounds.width(), navigationBarBounds.height());
    }

    int getNavigationBarGravity() {
        return UtilKNavigationBar.getNavigationBarGravity(navigationBarBounds);
    }

    public int getCutoutGravity() {
        getSafeInsetBounds(cutoutSafeRect);
        if (cutoutSafeRect.left > 0) {
            return Gravity.LEFT;
        }
        if (cutoutSafeRect.top > 0) {
            return Gravity.TOP;
        }
        if (cutoutSafeRect.right > 0) {
            return Gravity.RIGHT;
        }
        if (cutoutSafeRect.bottom > 0) {
            return Gravity.BOTTOM;
        }
        return Gravity.NO_GRAVITY;
    }

    void getSafeInsetBounds(Rect r) {
        if (r == null) return;
        if (Build.VERSION.SDK_INT < CVersionCode.V_28_9_P) {
            r.setEmpty();
            return;
        }
        try {
            DisplayCutout cutout = mPopupWindow.getContext()
                    .getWindow()
                    .getDecorView()
                    .getRootWindowInsets()
                    .getDisplayCutout();
            if (cutout == null) {
                r.setEmpty();
                return;
            }
            r.set(cutout.getSafeInsetLeft(), cutout.getSafeInsetTop(),
                    cutout.getSafeInsetRight(), cutout.getSafeInsetBottom());
        } catch (Exception e) {
            UtilKLogPro.e(e);
        }
    }

    public BasePopupHelper overlayStatusbar(boolean overlay) {
        if (!overlay && UtilKScreen.isFullScreen(mPopupWindow.getContext())) {
            Log.e(TAG, "setOverlayStatusbar: 全屏Activity下没有StatusBar，此处不能设置为false");
            overlay = true;
        }
        setFlag(CFlag.OVERLAY_STATUS_BAR, overlay);
        if (!overlay) {
            lastOverLayStatusBarMode = overlayStatusBarMode;
            overlayStatusBarMode = 0;
        } else {
            overlayStatusBarMode = lastOverLayStatusBarMode;
        }
        return this;
    }

    public BasePopupHelper setOverlayStatusbarMode(int mode) {
        if (!isOverlayStatusbar()) {
            lastOverLayStatusBarMode = mode;
        } else {
            lastOverLayStatusBarMode = overlayStatusBarMode = mode;
        }
        return this;
    }

    public BasePopupHelper overlayNavigationBar(boolean overlay) {
        setFlag(CFlag.OVERLAY_NAVIGATION_BAR, overlay);
        if (!overlay) {
            lastOverlayNavigationBarMode = overlayNavigationBarMode;
            overlayNavigationBarMode = 0;
        } else {
            overlayNavigationBarMode = lastOverlayNavigationBarMode;
        }
        return this;
    }

    public BasePopupHelper setOverlayNavigationBarMode(int mode) {
        if (!isOverlayNavigationBar()) {
            lastOverlayNavigationBarMode = mode;
        } else {
            lastOverlayNavigationBarMode = overlayNavigationBarMode = mode;
        }
        return this;
    }

    UtilKBitmapBluConfig getBlurOption() {
        return mBlurOption;
    }


    public Drawable getPopupBackground() {
        return mBackgroundDrawable;
    }

    public BasePopupHelper setPopupBackground(Drawable background) {
        mBackgroundDrawable = background;
        overlayMask = true;
        return this;
    }

    boolean isAlignBackground() {
        return (flag & CFlag.ALIGN_BACKGROUND) != 0;
    }

    public BasePopupHelper setAlignBackgound(boolean mAlignBackground) {
        setFlag(CFlag.ALIGN_BACKGROUND, mAlignBackground);
        if (!mAlignBackground) {
            setAlignBackgroundGravity(Gravity.NO_GRAVITY);
        }
        return this;
    }

    int getAlignBackgroundGravity() {
        if (isAlignBackground() && alignBackgroundGravity == Gravity.NO_GRAVITY) {
            alignBackgroundGravity = Gravity.TOP;
        }
        return alignBackgroundGravity;
    }

    public BasePopupHelper setAlignBackgroundGravity(int gravity) {
        this.alignBackgroundGravity = gravity;
        return this;
    }

    BasePopupHelper setForceAdjustKeyboard(boolean adjust) {
        setFlag(CFlag.KEYBOARD_FORCE_ADJUST, adjust);
        return this;
    }

    boolean isAllowToBlur() {
        return mBlurOption != null && mBlurOption.isAllowToBlur();
    }


    boolean isClipChildren() {
        return (flag & CFlag.CLIP_CHILDREN) != 0;
    }

    /**
     * non null
     */
    @NonNull
    ViewGroup.MarginLayoutParams getLayoutParams() {
        if (layoutParams == null) {
            int w = popupViewWidth == 0 ? ViewGroup.LayoutParams.MATCH_PARENT : popupViewWidth;
            int h = popupViewHeight == 0 ? ViewGroup.LayoutParams.WRAP_CONTENT : popupViewHeight;
            layoutParams = new ViewGroup.MarginLayoutParams(w, h);
        }
        if (layoutParams.width > 0) {
            if (minWidth > 0) {
                layoutParams.width = Math.max(layoutParams.width, minWidth);
            }
            if (maxWidth > 0) {
                layoutParams.width = Math.min(layoutParams.width, maxWidth);
            }
        }
        if (layoutParams.height > 0) {
            if (minHeight > 0) {
                layoutParams.height = Math.max(layoutParams.height, minHeight);
            }
            if (maxHeight > 0) {
                layoutParams.height = Math.min(layoutParams.height, maxHeight);
            }
        }
        return layoutParams;
    }

    public BasePopupHelper setContentRootId(View contentRoot) {
        if (contentRoot == null) return this;
        if (contentRoot.getId() == View.NO_ID) {
            contentRoot.setId(CONTENT_VIEW_ID);
        }
        this.contentRootId = contentRoot.getId();
        return this;
    }

    int getSoftInputMode() {
        return mSoftInputMode;
    }

    View getBackgroundView() {
        return mBackgroundView;
    }

    public BasePopupHelper setBackgroundView(View backgroundView) {
        mBackgroundView = backgroundView;
        overlayMask = true;
        return this;
    }

    int getMaxWidth() {
        return maxWidth;
    }

    int getMaxHeight() {
        return maxHeight;
    }


    ShowMode getShowMode() {
        return mShowMode;
    }

    BasePopupHelper setShowMode(ShowMode showMode) {
        mShowMode = showMode;
        return this;
    }

    int getMinWidth() {
        return minWidth;
    }

    int getMinHeight() {
        return minHeight;
    }

    public BasePopupHelper linkTo(View anchorView) {
        if (anchorView == null) {
            if (mLinkedViewLayoutChangeListenerWrapper != null) {
                mLinkedViewLayoutChangeListenerWrapper.detach();
                mLinkedViewLayoutChangeListenerWrapper = null;
            }
            mLinkedTarget = null;
            return this;
        }
        mLinkedTarget = anchorView;
        return this;
    }

    boolean isSyncMaskAnimationDuration() {
        return (flag & CFlag.SYNC_MASK_ANIMATION_DURATION) != 0;
    }

    boolean isAlignAnchorWidth() {
        if (isWithAnchor()) {
            //point mode时，由于是一像素，因此忽略
            if (mShowInfo != null && mShowInfo.positionMode) {
                return false;
            }
            return (flag & CFlag.AS_WIDTH_AS_ANCHOR) != 0;
        }
        return false;
    }

    boolean isAlignAnchorHeight() {
        if (isWithAnchor()) {
            //point mode时，由于是一像素，因此忽略
            if (mShowInfo != null && mShowInfo.positionMode) {
                return false;
            }
            return (flag & CFlag.AS_HEIGHT_AS_ANCHOR) != 0;
        }
        return false;
    }

    //-----------------------------------------controller-----------------------------------------
    public void prepare(View v, boolean positionMode) {
        if (mShowInfo == null) {
            mShowInfo = new InnerShowInfo(v, positionMode);
        } else {
            mShowInfo.mAnchorView = v;
            mShowInfo.positionMode = positionMode;
        }
        if (positionMode) {
            setShowMode(ShowMode.POSITION);
        } else {
            setShowMode(v == null ? ShowMode.SCREEN : ShowMode.RELATIVE_TO_ANCHOR);
        }
        getAnchorLocation(v);
        applyToPopupWindow();
    }

    private void applyToPopupWindow() {
        if (mPopupWindow == null || mPopupWindow.mPopupWindowProxy == null) return;
        mPopupWindow.mPopupWindowProxy.setSoftInputMode(mSoftInputMode);
        mPopupWindow.mPopupWindowProxy.setAnimationStyle(animationStyleRes);
        mPopupWindow.mPopupWindowProxy.setTouchable((flag & CFlag.TOUCHABLE) != 0);
        mPopupWindow.mPopupWindowProxy.setFocusable((flag & CFlag.TOUCHABLE) != 0);
    }

    public void onDismiss() {
        if (isAutoShowInputMethod() && hideKeyboardOnDismiss) {
            UtilKInputManager.hide(mPopupWindow.getContext());
        }

        if (mLinkedViewLayoutChangeListenerWrapper != null) {
            mLinkedViewLayoutChangeListenerWrapper.detach();
        }
    }

    public void setFlag(int flag, boolean added) {
        if (!added) {
            this.flag &= ~flag;
        } else {
            this.flag |= flag;
            if (flag == CFlag.AUTO_MIRROR) {
                this.flag |= CFlag.WITH_ANCHOR;
            }
        }
    }

    boolean onDispatchKeyEvent(KeyEvent event) {
        if (mKeyEventListener != null && mKeyEventListener.onKey(event)) {
            return true;
        }
        return mPopupWindow.onDispatchKeyEvent(event);
    }

    boolean onInterceptTouchEvent(MotionEvent event) {
        return mPopupWindow.onInterceptTouchEvent(event);
    }

    boolean onTouchEvent(MotionEvent event) {
        return mPopupWindow.onTouchEvent(event);
    }

    boolean onBackPressed() {
        return mPopupWindow.onBackPressed();
    }

    public void onShow() {
        prepareShow();
        if ((flag & CFlag.CUSTOM_ON_UPDATE) != 0) return;
        if (mShowAnimation == null || mShowAnimator == null) {
            mPopupWindow.mDisplayAnimateView.getViewTreeObserver()
                    .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                        @Override
                        public void onGlobalLayout() {
                            mPopupWindow.mDisplayAnimateView.getViewTreeObserver()
                                    .removeOnGlobalLayoutListener(
                                            this);
                            startShowAnimate(mPopupWindow.mDisplayAnimateView.getWidth(),
                                    mPopupWindow.mDisplayAnimateView.getHeight());
                        }
                    });
        } else {
            startShowAnimate(mPopupWindow.mDisplayAnimateView.getWidth(),
                    mPopupWindow.mDisplayAnimateView.getHeight());
        }
    }

    void onAttachToWindow() {
        showFlag &= ~STATUS_START_SHOWING;
        if (mPopupWindow != null) {
            mPopupWindow.onShowing();
        }
        if (mOnPopupWindowShowListener != null) {
            mOnPopupWindowShowListener.onShowing();
        }
    }

    void onPopupLayout(@NonNull Rect popupRect, @NonNull Rect anchorRect) {
        if (mPopupWindow != null) {
            mPopupWindow.onPopupLayout(popupRect, anchorRect);
        }
    }

    void onSizeChange(int oldW, int oldH, int newW, int newH) {
        if (mPopupWindow != null) {
            mPopupWindow.onSizeChange(oldW, oldH, newW, newH);
        }
    }

    private void prepareShow() {
        showFlag |= BasePopupHelper.STATUS_START_SHOWING;

        if (mGlobalLayoutListener == null && mPopupWindow.getContext() != null) {
            mGlobalLayoutListener = UtilKKeyboardChange.observerKeyboardChange(mPopupWindow.getContext(), new UtilKKeyboardChange.IUtilKKeyboardChangeListener() {
                @Override
                public void onChange(Rect keyboardBounds, boolean isVisible) {
                    BasePopupHelper.this.onChange(keyboardBounds, isVisible);
                    if (!mPopupWindow.isShowing()) {
                        UtilKView.safeRemoveGlobalLayoutListener(mPopupWindow.getContext().getWindow().getDecorView(), mGlobalLayoutListener);
                        return;
                    }
                }
            });
        }
        UtilKView.safeAddGlobalLayoutListener(mPopupWindow.getContext().getWindow().getDecorView(), mGlobalLayoutListener);
        if (mLinkedTarget != null) {
            if (mLinkedViewLayoutChangeListenerWrapper == null) {
                mLinkedViewLayoutChangeListenerWrapper = new LinkedViewLayoutChangeListenerWrapper(
                        mLinkedTarget);
            }
            if (!mLinkedViewLayoutChangeListenerWrapper.isAdded) {
                mLinkedViewLayoutChangeListenerWrapper.attach();
            }
        }
    }


    public void dismiss(boolean animateDismiss) {
        if (mPopupWindow == null || !mPopupWindow.onBeforeDismissInternal(mOnDismissListener)) {
            return;
        }
        if (mPopupWindow.mDisplayAnimateView == null || animateDismiss && (flag & CFlag.CUSTOM_ON_ANIMATE_DISMISS) != 0) {
            return;
        }
        showFlag &= ~STATUS_START_SHOWING;
        showFlag |= STATUS_START_DISMISS;
        Message msg = CEvent.getMessage(CEvent.EVENT_DISMISS);
        if (animateDismiss) {
            startDismissAnimate(mPopupWindow.mDisplayAnimateView.getWidth(),
                    mPopupWindow.mDisplayAnimateView.getHeight());
            msg.arg1 = 1;
            mPopupWindow.mDisplayAnimateView.removeCallbacks(dismissAnimationDelayRunnable);
            mPopupWindow.mDisplayAnimateView.postDelayed(dismissAnimationDelayRunnable,
                    Math.max(dismissDuration, 0));
        } else {
            msg.arg1 = 0;
            mPopupWindow.superDismiss();
        }
        BasePopupUnsafe.StackFetcher.remove(mPopupWindow);
        sendEvent(msg);
    }

    private Runnable dismissAnimationDelayRunnable = new Runnable() {
        @Override
        public void run() {
            flag &= ~CFlag.CUSTOM_ON_ANIMATE_DISMISS;
            if (mPopupWindow != null) {
                //popup可能已经释放引用了
                mPopupWindow.superDismiss();
            }
        }
    };

    public void forceDismiss() {
        if (mDismissAnimation != null) mDismissAnimation.cancel();
        if (mDismissAnimator != null) mDismissAnimator.cancel();
        if (mPopupWindow != null && hideKeyboardOnDismiss) {
            UtilKInputManager.hide(mPopupWindow.getContext());
        }
        if (dismissAnimationDelayRunnable != null) {
            dismissAnimationDelayRunnable.run();
        }
    }

    @Override
    public void onChange(Rect keyboardBounds, boolean isVisible) {
        if (mKeyboardStateChangeListener != null) {
            mKeyboardStateChangeListener.onChange(keyboardBounds, isVisible);
        }
        if (mUserKeyboardStateChangeListener != null) {
            mUserKeyboardStateChangeListener.onChange(keyboardBounds, isVisible);
        }
    }

    public void update(View v, boolean positionMode) {
        if (!mPopupWindow.isShowing() || mPopupWindow.mContentView == null) return;
        if (v == null && mShowInfo != null) {
            v = mShowInfo.mAnchorView;
        }
        prepare(v, positionMode);
        mPopupWindow.mPopupWindowProxy.update();
    }

    void onConfigurationChanged(Configuration newConfig) {
        update(mShowInfo == null ? null : mShowInfo.mAnchorView, mShowInfo == null ? false : mShowInfo.positionMode);
    }

    void dispatchOutSideEvent(MotionEvent event, boolean touchInMask, boolean isMaskPressed) {
        if (mPopupWindow != null) {
            mPopupWindow.dispatchOutSideEvent(event, touchInMask, isMaskPressed);
        }
    }

    boolean hasBackground() {
        if (mBackgroundView != null) {
            return true;
        }
        if (mBackgroundDrawable instanceof ColorDrawable) {
            return ((ColorDrawable) mBackgroundDrawable).getColor() != Color.TRANSPARENT && mBackgroundDrawable
                    .getAlpha() > 0;
        }
        return mBackgroundDrawable != null;
    }

    static class InnerShowInfo {
        View mAnchorView;
        boolean positionMode;

        InnerShowInfo(View mAnchorView, boolean positionMode) {
            this.mAnchorView = mAnchorView;
            this.positionMode = positionMode;
        }
    }

    class LinkedViewLayoutChangeListenerWrapper implements ViewTreeObserver.OnPreDrawListener {

        private View mTarget;
        private boolean isAdded;
        private float lastX, lastY;
        private int lastWidth, lastHeight, lastVisible;
        private boolean lastShowState, hasChange;
        Rect lastLocationRect = new Rect();
        Rect newLocationRect = new Rect();

        public LinkedViewLayoutChangeListenerWrapper(View target) {
            mTarget = target;
        }

        void attach() {
            if (mTarget == null || isAdded) return;
            mTarget.getGlobalVisibleRect(lastLocationRect);
            refreshViewParams();
            mTarget.getViewTreeObserver().addOnPreDrawListener(this);
            isAdded = true;
        }

        void detach() {
            if (mTarget == null || !isAdded) return;
            try {
                mTarget.getViewTreeObserver().removeOnPreDrawListener(this);
            } catch (Exception e) {
            }
            isAdded = false;
        }

        void refreshViewParams() {
            if (mTarget == null) return;

            //之所以不直接用getGlobalVisibleRect，是因为getGlobalVisibleRect需要不断的找到parent然后获取位置，因此先比较自身属性，然后进行二次验证
            float curX = mTarget.getX();
            float curY = mTarget.getY();
            int curWidth = mTarget.getWidth();
            int curHeight = mTarget.getHeight();
            int curVisible = mTarget.getVisibility();
            boolean isShow = mTarget.isShown();

            hasChange = (curX != lastX ||
                    curY != lastY ||
                    curWidth != lastWidth ||
                    curHeight != lastHeight ||
                    curVisible != lastVisible) && isAdded;
            if (!hasChange) {
                //不排除是recyclerview中那样子的情况，因此这里进行二次验证，获取view在屏幕中的位置
                mTarget.getGlobalVisibleRect(newLocationRect);
                if (!newLocationRect.equals(lastLocationRect)) {
                    lastLocationRect.set(newLocationRect);
                    //处理可能的在recyclerview回收的事情
                    if (!handleShowChange(mTarget, lastShowState, isShow)) {
                        hasChange = true;
                    }
                }
            }

            lastX = curX;
            lastY = curY;
            lastWidth = curWidth;
            lastHeight = curHeight;
            lastVisible = curVisible;
            lastShowState = isShow;
        }

        private boolean handleShowChange(View target, boolean lastShowState, boolean isShow) {
            if (lastShowState && !isShow) {
                if (mPopupWindow.isShowing()) {
                    dismiss(false);
                    return true;
                }
            } else if (!lastShowState && isShow) {
                if (!mPopupWindow.isShowing()) {
                    mPopupWindow.tryToShowPopup(target, false);
                    return true;
                }
            }
            return false;
        }


        @Override
        public boolean onPreDraw() {
            if (mTarget == null) return true;
            refreshViewParams();
            if (hasChange) {
                update(mTarget, false);
            }
            return true;
        }
    }

    @Nullable
    public static Activity findActivity(Object parent) {
        return findActivity(parent, true);
    }

    @Nullable
    static Activity findActivity(Object parent, boolean returnTopIfNull) {
        Activity act = null;
        if (parent instanceof Context) {
            act = UtilKActivity.getActivityByContext((Context) parent, true);
        } else if (parent instanceof Fragment) {
            act = ((Fragment) parent).getActivity();
        } else if (parent instanceof Dialog) {
            act = UtilKActivity.getActivityByContext(((Dialog) parent).getContext(), true);
        }
        if (act == null && returnTopIfNull) {
            act = StackK.INSTANCE.getStackTopActivity(true);
        }
        return act;
    }

    @Nullable
    public static View findDecorView(Object parent) {
        View decorView = null;
        Window window = null;
        if (parent instanceof Dialog) {
            window = ((Dialog) parent).getWindow();
        } else if (parent instanceof DialogFragment) {
            if (((DialogFragment) parent).getDialog() == null) {
                decorView = ((DialogFragment) parent).getView();
            } else {
                window = ((DialogFragment) parent).getDialog().getWindow();
            }
        } else if (parent instanceof Fragment) {
            decorView = ((Fragment) parent).getView();
        } else if (parent instanceof Context) {
            Activity act = UtilKActivity.getActivityByContext((Context) parent, true);
            decorView = act == null ? null : act.findViewById(android.R.id.content);
        }

        if (decorView != null) {
            return decorView;
        } else {
            return window == null ? null : window.getDecorView();
        }
    }

    boolean isPrePopupBackgroundExists() {
        if (mPopupWindow == null) return false;
        LinkedList<WindowManagerProxy> popupList = WindowManagerProxy.PopupWindowQueueManager.getInstance()
                .getPopupList(mPopupWindow.getContext());
        if (popupList == null || popupList.isEmpty()) return false;
        final int size = popupList.size();
        if (size == 1) {
            // 只有一个popup的时候需要留意这个popup是否正在dismiss
            WindowManagerProxy proxy = popupList.get(0);
            if (proxy.mPopupHelper != null && (proxy.mPopupHelper.showFlag & BasePopupHelper.STATUS_START_DISMISS) != 0) {
                return false;
            }
        }
        for (WindowManagerProxy windowManagerProxy : popupList) {
            BasePopupHelper helper = windowManagerProxy.mPopupHelper;
            if (helper != null && helper.hasBackground()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void clear(boolean destroy) {
        if (mPopupWindow != null && mPopupWindow.mDisplayAnimateView != null) {
            //神奇的是，这个方式有可能失效，runnable根本就没有被remove掉
            mPopupWindow.mDisplayAnimateView.removeCallbacks(dismissAnimationDelayRunnable);
        }
        if (eventObserverMap != null) {
            eventObserverMap.clear();
        }
        UtilKAnim.releaseAnim(mShowAnimation, mDismissAnimation, mShowAnimator, mDismissAnimator, mMaskViewShowAnimation, mMaskViewDismissAnimation);
        if (mBlurOption != null) {
            mBlurOption.clear();
        }
        if (mShowInfo != null) {
            mShowInfo.mAnchorView = null;
        }
        if (mGlobalLayoutListener != null) {
            UtilKView.safeRemoveGlobalLayoutListener(mPopupWindow.getContext()
                            .getWindow()
                            .getDecorView(),
                    mGlobalLayoutListener);
        }

        if (mLinkedViewLayoutChangeListenerWrapper != null) {
            mLinkedViewLayoutChangeListenerWrapper.detach();
        }
        showFlag = 0;
        dismissAnimationDelayRunnable = null;
        mShowAnimation = null;
        mDismissAnimation = null;
        mShowAnimator = null;
        mDismissAnimator = null;
        mMaskViewShowAnimation = null;
        mMaskViewDismissAnimation = null;
        eventObserverMap = null;
        mPopupWindow = null;
        mOnPopupWindowShowListener = null;
        mOnDismissListener = null;
        mOnBeforeShowCallback = null;
        mBlurOption = null;
        mBackgroundDrawable = null;
        mBackgroundView = null;
        mAutoShowInputEdittext = null;
        mKeyboardStateChangeListener = null;
        mShowInfo = null;
        mLinkedViewLayoutChangeListenerWrapper = null;
        mLinkedTarget = null;
        mGlobalLayoutListener = null;
        mUserKeyboardStateChangeListener = null;
        mKeyEventListener = null;
        keybaordAlignView = null;
        mOnFitWindowManagerLayoutParamsCallback = null;
    }
}
