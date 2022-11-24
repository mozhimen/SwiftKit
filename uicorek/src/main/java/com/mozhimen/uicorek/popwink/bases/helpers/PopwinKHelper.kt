package com.mozhimen.uicorek.popwink.bases.helpers

/**
 * @ClassName PopwinKHelper
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/11/17 14:16
 * @Version 1.0
 */
/*
class PopwinKHelper {
    var mPopupWindow: BasePopupWindow? = null

    var eventObserverMap: WeakHashMap<Any, BasePopupEvent.EventObserver>? = null

    var mFlagCacheMap: MutableMap<Int, Boolean>? = null

    val DEFAULT_KEYBOARD_SHOW_DELAY: Long = 350
    val DEFAULT_OVERLAY_STATUS_BAR_MODE: Int = OVERLAY_MASK or OVERLAY_CONTENT
    val DEFAULT_OVERLAY_NAVIGATION_BAR_MODE: Int = OVERLAY_MASK
    private val CONTENT_VIEW_ID: Int = R.id.base_popup_content_root

    val STATUS_START_SHOWING = 0x1
    val STATUS_START_DISMISS = 0x2
    var showFlag = 0
    var priority: EPriority = EPriority.NORMAL

    var mShowMode = EShowMode.SCREEN

    var contentRootId = CONTENT_VIEW_ID

    var flag: Int = IDLE

    //animate
    var mShowAnimation: Animation? = null
    var mShowAnimator: Animator? = null
    var mDismissAnimation: Animation? = null
    var mDismissAnimator: Animator? = null
    var preventInitShowAnimation = false
    var preventInitDismissAnimation = false

    var mMaskViewShowAnimation: Animation? = null
    var mMaskViewDismissAnimation: Animation? = null

    var isDefaultMaskViewShowAnimation = false
    var isDefaultMaskViewDismissAnimation = false
    var overlayMask = false

    var showDuration: Long = 0
    var dismissDuration: Long = 0
    var showKeybaordDelay = DEFAULT_KEYBOARD_SHOW_DELAY

    var animationStyleRes = 0

    //callback
    var mOnDismissListener: BasePopupWindow.OnDismissListener? = null
    var mOnBeforeShowCallback: BasePopupWindow.OnBeforeShowCallback? = null
    var mOnPopupWindowShowListener: BasePopupWindow.OnPopupWindowShowListener? = null

    //option
    var horizontalGravityMode: BasePopupWindow.GravityMode = BasePopupWindow.GravityMode.RELATIVE_TO_ANCHOR
    var verticalGravityMode: BasePopupWindow.GravityMode = BasePopupWindow.GravityMode.RELATIVE_TO_ANCHOR

    var popupGravity = Gravity.NO_GRAVITY
    var offsetX = 0
    var offsetY = 0
    var maskOffsetX = 0
    var maskOffsetY = 0
    var preMeasureWidth = 0
    var preMeasureHeight = 0

    var keyboardGravity = Gravity.BOTTOM
    var keyboardOffsetX = 0
    var keyboardOffsetY = 0

    var popupViewWidth = 0
    var popupViewHeight = 0
    var layoutDirection = LayoutDirection.LTR

    //锚点view的location
    var mAnchorViewBound: Rect? = null

    //模糊option(为空的话则不模糊）
    var mBlurOption: PopupBlurOption? = null

    //背景颜色
    var mBackgroundDrawable: Drawable? = ColorDrawable(BasePopupWindow.DEFAULT_BACKGROUND_COLOR)

    //背景对齐方向
    var alignBackgroundGravity = Gravity.TOP

    //背景View
    var mBackgroundView: View? = null

    var mAutoShowInputEdittext: EditText? = null

    var mKeyboardStateChangeListener: KeyboardUtils.OnKeyboardChangeListener? = null
    var mUserKeyboardStateChangeListener: KeyboardUtils.OnKeyboardChangeListener? = null
    var mKeyEventListener: BasePopupWindow.KeyEventListener? = null

    var mSoftInputMode = WindowManager.LayoutParams.SOFT_INPUT_STATE_UNCHANGED
    var layoutParams: MarginLayoutParams? = null

    var maxWidth = 0, var maxHeight:Int = 0, var minWidth:Int = 0, var minHeight:Int = 0

    var keybaordAlignViewId = 0
    var keybaordAlignView: View? = null

    var mShowInfo: InnerShowInfo? = null

    var mGlobalLayoutListener: OnGlobalLayoutListener? = null
    var mLinkedViewLayoutChangeListenerWrapper: LinkedViewLayoutChangeListenerWrapper? = null

    var mLinkedTarget: View? = null

    var navigationBarBounds: Rect? = null
    var cutoutSafeRect: Rect? = null

    var lastOverLayStatusBarMode = 0, var overlayStatusBarMode:Int = DEFAULT_OVERLAY_STATUS_BAR_MODE
    var lastOverlayNavigationBarMode = 0, var overlayNavigationBarMode:Int = DEFAULT_OVERLAY_NAVIGATION_BAR_MODE

    var hideKeyboardOnDismiss = true

    //unsafe
    var mOnFitWindowManagerLayoutParamsCallback: BasePopupUnsafe.OnFitWindowManagerLayoutParamsCallback? = null

    constructor(popwinK: PopwinK) {
        mFlagCacheMap = HashMap()
        mAnchorViewBound = Rect()
        navigationBarBounds = Rect()
        cutoutSafeRect = Rect()
        mPopupWindow = popwinK
        eventObserverMap = WeakHashMap<Any, BasePopupEvent.EventObserver>()
        mMaskViewShowAnimation = AlphaAnimation(0f, 1f)
        mMaskViewDismissAnimation = AlphaAnimation(1f, 0f)
        mMaskViewShowAnimation.setFillAfter(true)
        mMaskViewShowAnimation.setInterpolator(DecelerateInterpolator())
        mMaskViewShowAnimation.setDuration(
            Resources.getSystem()
                .getInteger(R.integer.config_shortAnimTime).toLong()
        )
        isDefaultMaskViewShowAnimation = true
        mMaskViewDismissAnimation.setFillAfter(true)
        mMaskViewDismissAnimation.setInterpolator(DecelerateInterpolator())
        mMaskViewDismissAnimation.setDuration(
            Resources.getSystem()
                .getInteger(R.integer.config_shortAnimTime).toLong()
        )
        isDefaultMaskViewDismissAnimation = true
    }

    fun cacheFlag(flag: Int, onceCache: Boolean) {
        if (onceCache && mFlagCacheMap!!.containsKey(flag)) return
        mFlagCacheMap!![flag] = this.flag and flag != 0
    }

    fun restoreFlag(flag: Int, defaultValue: Boolean): Boolean {
        return if (mFlagCacheMap!!.containsKey(flag)) {
            mFlagCacheMap!!.remove(flag)!!
        } else defaultValue
    }

    fun observerEvent(who: Any, observer: BasePopupEvent.EventObserver) {
        eventObserverMap!![who] = observer
    }

    fun removeEventObserver(who: Any) {
        eventObserverMap!!.remove(who)
    }

    fun sendEvent(msg: Message?) {
        if (msg == null) return
        if (msg.what < 0) return
        for ((_, value): Map.Entry<Any?, BasePopupEvent.EventObserver?> in eventObserverMap!!) {
            if (value != null) {
                value.onEvent(msg)
            }
        }
    }

    fun inflate(context: Context, layoutId: Int): View? {
        try {
            var frameLayout: FrameLayout? = FrameLayout(context)
            val view = LayoutInflater.from(context).inflate(layoutId, frameLayout, false)
            val params = view.layoutParams
            if (params != null) {
                checkAndSetGravity(params)
                layoutParams = if (params is MarginLayoutParams) {
                    MarginLayoutParams(params)
                } else {
                    MarginLayoutParams(params)
                }
                if (popupViewWidth != 0 && layoutParams!!.width != popupViewWidth) {
                    layoutParams!!.width = popupViewWidth
                }
                if (popupViewHeight != 0 && layoutParams!!.height != popupViewHeight) {
                    layoutParams!!.height = popupViewHeight
                }
                frameLayout = null
                return view
            }
            return view
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun preMeasurePopupView(mContentView: View?, w: Int, h: Int) {
        if (mContentView != null) {
            val measureWidth = View.MeasureSpec.makeMeasureSpec(
                Math.max(w, 0),
                if (w == ViewGroup.LayoutParams.WRAP_CONTENT) View.MeasureSpec.UNSPECIFIED else View.MeasureSpec.EXACTLY
            )
            val measureHeight = View.MeasureSpec.makeMeasureSpec(
                Math.max(w, h),
                if (h == ViewGroup.LayoutParams.WRAP_CONTENT) View.MeasureSpec.UNSPECIFIED else View.MeasureSpec.EXACTLY
            )
            mContentView.measure(measureWidth, measureHeight)
            preMeasureWidth = mContentView.measuredWidth
            preMeasureHeight = mContentView.measuredHeight
            mContentView.isFocusableInTouchMode = true
        }
    }

    private fun checkAndSetGravity(params: ViewGroup.LayoutParams) {
        //如果设置过gravity，则采取设置的gravity，顶替掉xml设置的（针对lazypopup）
        //https://github.com/razerdp/BasePopup/issues/310
        if (popupGravity != Gravity.NO_GRAVITY) return
        if (params is LinearLayout.LayoutParams) {
            popupGravity = params.gravity
        } else if (params is FrameLayout.LayoutParams) {
            popupGravity = params.gravity
        }
    }

    //region Animation

    //region Animation
    fun startShowAnimate(width: Int, height: Int) {
        if (!preventInitShowAnimation) {
            if (initShowAnimation(width, height) == null) {
                initShowAnimator(width, height)
            }
        }
        //动画只初始化一次，后续请自行通过setAnimation/setAnimator实现
        preventInitShowAnimation = true
        //通知蒙层动画，此时duration已经计算完毕
        val msg = Message.obtain()
        msg.what = BasePopupEvent.EVENT_SHOW
        sendEvent(msg)
        if (mShowAnimation != null) {
            mShowAnimation!!.cancel()
            mPopupWindow.mDisplayAnimateView.startAnimation(mShowAnimation)
        } else if (mShowAnimator != null) {
            mShowAnimator!!.setTarget(mPopupWindow.getDisplayAnimateView())
            mShowAnimator!!.cancel()
            mShowAnimator!!.start()
        }
    }

    fun startDismissAnimate(width: Int, height: Int) {
        if (!preventInitDismissAnimation) {
            if (initDismissAnimation(width, height) == null) {
                initDismissAnimator(width, height)
            }
        }
        //动画只初始化一次，后续请自行通过setAnimation/setAnimator实现
        preventInitDismissAnimation = true
        if (mDismissAnimation != null) {
            mDismissAnimation!!.cancel()
            mPopupWindow.mDisplayAnimateView.startAnimation(mDismissAnimation)
            if (mOnDismissListener != null) {
                mOnDismissListener.onDismissAnimationStart()
            }
            setFlag(CUSTOM_ON_ANIMATE_DISMISS, true)
        } else if (mDismissAnimator != null) {
            mDismissAnimator!!.setTarget(mPopupWindow.getDisplayAnimateView())
            mDismissAnimator!!.cancel()
            mDismissAnimator!!.start()
            if (mOnDismissListener != null) {
                mOnDismissListener.onDismissAnimationStart()
            }
            setFlag(CUSTOM_ON_ANIMATE_DISMISS, true)
        }
    }

    fun initShowAnimation(width: Int, height: Int): Animation? {
        if (mShowAnimation == null) {
            mShowAnimation = mPopupWindow.onCreateShowAnimation(width, height)
            if (mShowAnimation != null) {
                showDuration = PopupUtils.getAnimationDuration(mShowAnimation, 0)
                setToBlur(mBlurOption)
            }
        }
        return mShowAnimation
    }

    fun initShowAnimator(width: Int, height: Int): Animator? {
        if (mShowAnimator == null) {
            mShowAnimator = mPopupWindow.onCreateShowAnimator(width, height)
            if (mShowAnimator != null) {
                showDuration = PopupUtils.getAnimatorDuration(mShowAnimator, 0)
                setToBlur(mBlurOption)
            }
        }
        return mShowAnimator
    }

    fun initDismissAnimation(width: Int, height: Int): Animation? {
        if (mDismissAnimation == null) {
            mDismissAnimation = mPopupWindow.onCreateDismissAnimation(width, height)
            if (mDismissAnimation != null) {
                dismissDuration = PopupUtils.getAnimationDuration(mDismissAnimation, 0)
                setToBlur(mBlurOption)
            }
        }
        return mDismissAnimation
    }

    fun initDismissAnimator(width: Int, height: Int): Animator? {
        if (mDismissAnimator == null) {
            mDismissAnimator = mPopupWindow.onCreateDismissAnimator(width, height)
            if (mDismissAnimator != null) {
                dismissDuration = PopupUtils.getAnimatorDuration(mDismissAnimator, 0)
                setToBlur(mBlurOption)
            }
        }
        return mDismissAnimator
    }


    fun setToBlur(option: PopupBlurOption?) {
        mBlurOption = option
        if (option != null) {
            if (option.getBlurInDuration() <= 0) {
                if (showDuration > 0) {
                    option.setBlurInDuration(showDuration)
                }
            }
            if (option.getBlurOutDuration() <= 0) {
                if (dismissDuration > 0) {
                    option.setBlurOutDuration(dismissDuration)
                }
            }
        }
    }


    fun setShowAnimation(showAnimation: Animation) {
        if (mShowAnimation === showAnimation) return
        if (mShowAnimation != null) {
            mShowAnimation!!.cancel()
        }
        mShowAnimation = showAnimation
        showDuration = PopupUtils.getAnimationDuration(mShowAnimation, 0)
        setToBlur(mBlurOption)
    }

    */
/**
     * animation优先级更高
     *//*

    fun setShowAnimator(showAnimator: Animator) {
        if (mShowAnimation != null || mShowAnimator === showAnimator) return
        if (mShowAnimator != null) {
            mShowAnimator!!.cancel()
        }
        mShowAnimator = showAnimator
        showDuration = PopupUtils.getAnimatorDuration(mShowAnimator, 0)
        setToBlur(mBlurOption)
    }

    fun setDismissAnimation(dismissAnimation: Animation) {
        if (mDismissAnimation === dismissAnimation) return
        if (mDismissAnimation != null) {
            mDismissAnimation!!.cancel()
        }
        mDismissAnimation = dismissAnimation
        dismissDuration = PopupUtils.getAnimationDuration(mDismissAnimation, 0)
        setToBlur(mBlurOption)
    }

    fun setDismissAnimator(dismissAnimator: Animator) {
        if (mDismissAnimation != null || mDismissAnimator === dismissAnimator) return
        if (mDismissAnimator != null) {
            mDismissAnimator!!.cancel()
        }
        mDismissAnimator = dismissAnimator
        dismissDuration = PopupUtils.getAnimatorDuration(mDismissAnimator, 0)
        setToBlur(mBlurOption)
    }

    //endregion

    //endregion
    fun setPopupViewWidth(popupViewWidth: Int): razerdp.basepopup.BasePopupHelper? {
        if (popupViewWidth != 0) {
            getLayoutParams().width = popupViewWidth
        }
        return this
    }

    fun setPopupViewHeight(popupViewHeight: Int): razerdp.basepopup.BasePopupHelper? {
        if (popupViewHeight != 0) {
            getLayoutParams().height = popupViewHeight
        }
        return this
    }

    fun getPreMeasureWidth(): Int {
        return preMeasureWidth
    }


    fun getPreMeasureHeight(): Int {
        return preMeasureHeight
    }

    fun isPopupFadeEnable(): Boolean {
        return flag and FADE_ENABLE !== 0
    }

    fun isWithAnchor(): Boolean {
        return flag and WITH_ANCHOR !== 0
    }

    fun isFitsizable(): Boolean {
        return flag and FITSIZE !== 0
    }

    fun withAnchor(showAsDropDown: Boolean): razerdp.basepopup.BasePopupHelper? {
        setFlag(WITH_ANCHOR, showAsDropDown)
        if (showAsDropDown && (popupGravity == Gravity.NO_GRAVITY || popupGravity == -1)) {
            popupGravity = Gravity.BOTTOM
        }
        return this
    }

    fun setShowLocation(x: Int, y: Int): razerdp.basepopup.BasePopupHelper? {
        mAnchorViewBound!![x, y, x + 1] = y + 1
        return this
    }


    fun getPopupGravity(): Int {
        return Gravity.getAbsoluteGravity(popupGravity, layoutDirection)
    }

    fun setLayoutDirection(layoutDirection: Int): razerdp.basepopup.BasePopupHelper? {
        this.layoutDirection = layoutDirection
        return this
    }

    fun setPopupGravity(mode: BasePopupWindow.GravityMode, popupGravity: Int): razerdp.basepopup.BasePopupHelper? {
        setPopupGravityMode(mode, mode)
        this.popupGravity = popupGravity
        return this
    }

    fun setPopupGravityMode(
        horizontalGravityMode: BasePopupWindow.GravityMode,
        verticalGravityMode: BasePopupWindow.GravityMode
    ): razerdp.basepopup.BasePopupHelper? {
        this.horizontalGravityMode = horizontalGravityMode
        this.verticalGravityMode = verticalGravityMode
        return this
    }

    fun getOffsetX(): Int {
        return offsetX
    }

    fun getOffsetY(): Int {
        return offsetY
    }


    fun isAutoShowInputMethod(): Boolean {
        return flag and AUTO_INPUT_METHOD !== 0
    }

    fun isAutoMirror(): Boolean {
        return flag and AUTO_MIRROR !== 0
    }

    fun isOutSideDismiss(): Boolean {
        return flag and OUT_SIDE_DISMISS !== 0
    }

    fun isOutSideTouchable(): Boolean {
        return flag and OUT_SIDE_TOUCHABLE !== 0
    }

    fun getAnchorLocation(v: View?): razerdp.basepopup.BasePopupHelper? {
        if (v == null) {
            if (mShowMode != EShowMode.POSITION) {
                mAnchorViewBound!!.setEmpty()
            }
            return this
        }
        val location = IntArray(2)
        v.getLocationOnScreen(location)
        mAnchorViewBound!![location[0], location[1], location[0] + v.width] = location[1] + v.height
        return this
    }

    fun getAnchorViewBound(): Rect? {
        return mAnchorViewBound
    }

    fun isBackPressEnable(): Boolean {
        return flag and BACKPRESS_ENABLE !== 0
    }

    fun isOverlayStatusbar(): Boolean {
        return flag and OVERLAY_STATUS_BAR !== 0
    }

    fun isOverlayNavigationBar(): Boolean {
        return flag and OVERLAY_NAVIGATION_BAR !== 0
    }

    fun refreshNavigationBarBounds() {
        PopupUiUtils.getNavigationBarBounds(navigationBarBounds, mPopupWindow.getContext())
    }

    fun getNavigationBarSize(): Int {
        return Math.min(navigationBarBounds!!.width(), navigationBarBounds!!.height())
    }

    fun getNavigationBarGravity(): Int {
        return PopupUiUtils.getNavigationBarGravity(navigationBarBounds)
    }

    fun getCutoutGravity(): Int {
        getSafeInsetBounds(cutoutSafeRect)
        if (cutoutSafeRect!!.left > 0) {
            return Gravity.LEFT
        }
        if (cutoutSafeRect!!.top > 0) {
            return Gravity.TOP
        }
        if (cutoutSafeRect!!.right > 0) {
            return Gravity.RIGHT
        }
        return if (cutoutSafeRect!!.bottom > 0) {
            Gravity.BOTTOM
        } else Gravity.NO_GRAVITY
    }

    fun getSafeInsetBounds(r: Rect?) {
        if (r == null) return
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
            r.setEmpty()
            return
        }
        try {
            val cutout: DisplayCutout = mPopupWindow.getContext()
                .getWindow()
                .getDecorView()
                .getRootWindowInsets()
                .getDisplayCutout()
            if (cutout == null) {
                r.setEmpty()
                return
            }
            r[cutout.safeInsetLeft, cutout.safeInsetTop, cutout.safeInsetRight] = cutout.safeInsetBottom
        } catch (e: Exception) {
            PopupLog.e(e)
        }
    }

    fun overlayStatusbar(overlay: Boolean): razerdp.basepopup.BasePopupHelper? {
        var overlay = overlay
        if (!overlay && PopupUiUtils.isActivityFullScreen(mPopupWindow.getContext())) {
            Log.e(BasePopupWindow.TAG, "setOverlayStatusbar: 全屏Activity下没有StatusBar，此处不能设置为false")
            overlay = true
        }
        setFlag(OVERLAY_STATUS_BAR, overlay)
        if (!overlay) {
            lastOverLayStatusBarMode = overlayStatusBarMode
            overlayStatusBarMode = 0
        } else {
            overlayStatusBarMode = lastOverLayStatusBarMode
        }
        return this
    }

    fun setOverlayStatusbarMode(mode: Int): razerdp.basepopup.BasePopupHelper? {
        if (!isOverlayStatusbar()) {
            lastOverLayStatusBarMode = mode
        } else {
            overlayStatusBarMode = mode
            lastOverLayStatusBarMode = overlayStatusBarMode
        }
        return this
    }

    fun overlayNavigationBar(overlay: Boolean): razerdp.basepopup.BasePopupHelper? {
        setFlag(OVERLAY_NAVIGATION_BAR, overlay)
        if (!overlay) {
            lastOverlayNavigationBarMode = overlayNavigationBarMode
            overlayNavigationBarMode = 0
        } else {
            overlayNavigationBarMode = lastOverlayNavigationBarMode
        }
        return this
    }

    fun setOverlayNavigationBarMode(mode: Int): razerdp.basepopup.BasePopupHelper? {
        if (!isOverlayNavigationBar()) {
            lastOverlayNavigationBarMode = mode
        } else {
            overlayNavigationBarMode = mode
            lastOverlayNavigationBarMode = overlayNavigationBarMode
        }
        return this
    }

    fun getBlurOption(): PopupBlurOption? {
        return mBlurOption
    }


    fun getPopupBackground(): Drawable? {
        return mBackgroundDrawable
    }

    fun setPopupBackground(background: Drawable?): razerdp.basepopup.BasePopupHelper? {
        mBackgroundDrawable = background
        overlayMask = true
        return this
    }

    fun isAlignBackground(): Boolean {
        return flag and ALIGN_BACKGROUND !== 0
    }

    fun setAlignBackgound(mAlignBackground: Boolean): razerdp.basepopup.BasePopupHelper? {
        setFlag(ALIGN_BACKGROUND, mAlignBackground)
        if (!mAlignBackground) {
            setAlignBackgroundGravity(Gravity.NO_GRAVITY)
        }
        return this
    }

    fun getAlignBackgroundGravity(): Int {
        if (isAlignBackground() && alignBackgroundGravity == Gravity.NO_GRAVITY) {
            alignBackgroundGravity = Gravity.TOP
        }
        return alignBackgroundGravity
    }

    fun setAlignBackgroundGravity(gravity: Int): razerdp.basepopup.BasePopupHelper? {
        alignBackgroundGravity = gravity
        return this
    }

    fun setForceAdjustKeyboard(adjust: Boolean): razerdp.basepopup.BasePopupHelper? {
        setFlag(KEYBOARD_FORCE_ADJUST, adjust)
        return this
    }

    fun isAllowToBlur(): Boolean {
        return mBlurOption != null && mBlurOption.isAllowToBlur()
    }


    fun isClipChildren(): Boolean {
        return flag and CLIP_CHILDREN !== 0
    }

    */
/**
     * non null
     *//*

    fun getLayoutParams(): MarginLayoutParams {
        if (layoutParams == null) {
            val w = if (popupViewWidth == 0) ViewGroup.LayoutParams.MATCH_PARENT else popupViewWidth
            val h = if (popupViewHeight == 0) ViewGroup.LayoutParams.WRAP_CONTENT else popupViewHeight
            layoutParams = MarginLayoutParams(w, h)
        }
        if (layoutParams!!.width > 0) {
            if (minWidth > 0) {
                layoutParams!!.width = Math.max(layoutParams!!.width, minWidth)
            }
            if (maxWidth > 0) {
                layoutParams!!.width = Math.min(layoutParams!!.width, maxWidth)
            }
        }
        if (layoutParams!!.height > 0) {
            if (minHeight > 0) {
                layoutParams!!.height = Math.max(layoutParams!!.height, minHeight)
            }
            if (maxHeight > 0) {
                layoutParams!!.height = Math.min(layoutParams!!.height, maxHeight)
            }
        }
        return layoutParams
    }

    fun setContentRootId(contentRoot: View?): razerdp.basepopup.BasePopupHelper? {
        if (contentRoot == null) return this
        if (contentRoot.id == View.NO_ID) {
            contentRoot.id = CONTENT_VIEW_ID
        }
        contentRootId = contentRoot.id
        return this
    }

    fun getSoftInputMode(): Int {
        return mSoftInputMode
    }

    fun getBackgroundView(): View? {
        return mBackgroundView
    }

    fun setBackgroundView(backgroundView: View?): razerdp.basepopup.BasePopupHelper? {
        mBackgroundView = backgroundView
        overlayMask = true
        return this
    }

    fun getMaxWidth(): Int {
        return maxWidth
    }

    fun getMaxHeight(): Int {
        return maxHeight
    }


    fun getShowMode(): EShowMode? {
        return mShowMode
    }

    fun setShowMode(showMode: EShowMode): razerdp.basepopup.BasePopupHelper? {
        mShowMode = showMode
        return this
    }

    fun getMinWidth(): Int {
        return minWidth
    }

    fun getMinHeight(): Int {
        return minHeight
    }

    fun linkTo(anchorView: View?): razerdp.basepopup.BasePopupHelper? {
        if (anchorView == null) {
            if (mLinkedViewLayoutChangeListenerWrapper != null) {
                mLinkedViewLayoutChangeListenerWrapper!!.detach()
                mLinkedViewLayoutChangeListenerWrapper = null
            }
            mLinkedTarget = null
            return this
        }
        mLinkedTarget = anchorView
        return this
    }

    fun isSyncMaskAnimationDuration(): Boolean {
        return flag and BasePopupFlag.SYNC_MASK_ANIMATION_DURATION !== 0
    }

    fun isAlignAnchorWidth(): Boolean {
        return if (isWithAnchor()) {
            //point mode时，由于是一像素，因此忽略
            if (mShowInfo != null && mShowInfo!!.positionMode) {
                false
            } else flag and BasePopupFlag.AS_WIDTH_AS_ANCHOR !== 0
        } else false
    }

    fun isAlignAnchorHeight(): Boolean {
        return if (isWithAnchor()) {
            //point mode时，由于是一像素，因此忽略
            if (mShowInfo != null && mShowInfo!!.positionMode) {
                false
            } else flag and BasePopupFlag.AS_HEIGHT_AS_ANCHOR !== 0
        } else false
    }

    //-----------------------------------------controller-----------------------------------------
    fun prepare(v: View?, positionMode: Boolean) {
        if (mShowInfo == null) {
            mShowInfo = InnerShowInfo(v, positionMode)
        } else {
            mShowInfo!!.mAnchorView = v
            mShowInfo!!.positionMode = positionMode
        }
        if (positionMode) {
            setShowMode(EShowMode.POSITION)
        } else {
            setShowMode(if (v == null) EShowMode.SCREEN else EShowMode.RELATIVE_TO_ANCHOR)
        }
        getAnchorLocation(v)
        applyToPopupWindow()
    }

    private fun applyToPopupWindow() {
        if (mPopupWindow == null || mPopupWindow.mPopupWindowProxy == null) return
        mPopupWindow.mPopupWindowProxy.setSoftInputMode(mSoftInputMode)
        mPopupWindow.mPopupWindowProxy.setAnimationStyle(animationStyleRes)
        mPopupWindow.mPopupWindowProxy.setTouchable(flag and TOUCHABLE !== 0)
        mPopupWindow.mPopupWindowProxy.setFocusable(flag and TOUCHABLE !== 0)
    }

    fun onDismiss() {
        if (isAutoShowInputMethod() && hideKeyboardOnDismiss) {
            KeyboardUtils.close(mPopupWindow.getContext())
        }
        if (mLinkedViewLayoutChangeListenerWrapper != null) {
            mLinkedViewLayoutChangeListenerWrapper!!.detach()
        }
    }

    fun setFlag(flag: Int, added: Boolean) {
        if (!added) {
            this.flag = this.flag and flag.inv()
        } else {
            this.flag = this.flag or flag
            if (flag == AUTO_MIRROR) {
                this.flag = this.flag or WITH_ANCHOR
            }
        }
    }

    fun onDispatchKeyEvent(event: KeyEvent?): Boolean {
        return if (mKeyEventListener != null && mKeyEventListener.onKey(event)) {
            true
        } else mPopupWindow.onDispatchKeyEvent(event)
    }

    fun onInterceptTouchEvent(event: MotionEvent?): Boolean {
        return mPopupWindow.onInterceptTouchEvent(event)
    }

    fun onTouchEvent(event: MotionEvent?): Boolean {
        return mPopupWindow.onTouchEvent(event)
    }

    fun onBackPressed(): Boolean {
        return mPopupWindow.onBackPressed()
    }

    fun onShow() {
        prepareShow()
        if (flag and CUSTOM_ON_UPDATE !== 0) return
        if (mShowAnimation == null || mShowAnimator == null) {
            mPopupWindow.mDisplayAnimateView.getViewTreeObserver()
                .addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
                    override fun onGlobalLayout() {
                        mPopupWindow.mDisplayAnimateView.getViewTreeObserver()
                            .removeOnGlobalLayoutListener(
                                this
                            )
                        startShowAnimate(
                            mPopupWindow.mDisplayAnimateView.getWidth(),
                            mPopupWindow.mDisplayAnimateView.getHeight()
                        )
                    }
                })
        } else {
            startShowAnimate(
                mPopupWindow.mDisplayAnimateView.getWidth(),
                mPopupWindow.mDisplayAnimateView.getHeight()
            )
        }
    }

    fun onAttachToWindow() {
        showFlag = showFlag and STATUS_START_SHOWING.inv()
        if (mPopupWindow != null) {
            mPopupWindow.onShowing()
        }
        if (mOnPopupWindowShowListener != null) {
            mOnPopupWindowShowListener.onShowing()
        }
    }

    fun onPopupLayout(popupRect: Rect, anchorRect: Rect) {
        if (mPopupWindow != null) {
            mPopupWindow.onPopupLayout(popupRect, anchorRect)
        }
    }

    fun onSizeChange(oldW: Int, oldH: Int, newW: Int, newH: Int) {
        if (mPopupWindow != null) {
            mPopupWindow.onSizeChange(oldW, oldH, newW, newH)
        }
    }

    private fun prepareShow() {
        showFlag = showFlag or razerdp.basepopup.BasePopupHelper.STATUS_START_SHOWING
        if (mGlobalLayoutListener == null) {
            mGlobalLayoutListener = KeyboardUtils.observerKeyboardChange(mPopupWindow.getContext(),
                object : OnKeyboardChangeListener() {
                    fun onKeyboardChange(keyboardBounds: Rect?, isVisible: Boolean) {
                        this@BasePopupHelper
                            .onKeyboardChange(
                                keyboardBounds,
                                isVisible
                            )
                        if (!mPopupWindow.isShowing()) {
                            PopupUiUtils.safeRemoveGlobalLayoutListener(
                                mPopupWindow
                                    .getContext()
                                    .getWindow()
                                    .getDecorView(),
                                mGlobalLayoutListener
                            )
                            return
                        }
                    }
                })
        }
        PopupUiUtils.safeAddGlobalLayoutListener(
            mPopupWindow.getContext()
                .getWindow()
                .getDecorView(),
            mGlobalLayoutListener
        )
        if (mLinkedTarget != null) {
            if (mLinkedViewLayoutChangeListenerWrapper == null) {
                mLinkedViewLayoutChangeListenerWrapper = LinkedViewLayoutChangeListenerWrapper(
                    mLinkedTarget
                )
            }
            if (!mLinkedViewLayoutChangeListenerWrapper!!.isAdded) {
                mLinkedViewLayoutChangeListenerWrapper!!.attach()
            }
        }
    }


    fun dismiss(animateDismiss: Boolean) {
        if (mPopupWindow == null || !mPopupWindow.onBeforeDismissInternal(mOnDismissListener)) {
            return
        }
        if (mPopupWindow.mDisplayAnimateView == null || animateDismiss && flag and CUSTOM_ON_ANIMATE_DISMISS !== 0) {
            return
        }
        showFlag = showFlag and STATUS_START_SHOWING.inv()
        showFlag = showFlag or STATUS_START_DISMISS
        val msg: Message = BasePopupEvent.getMessage(BasePopupEvent.EVENT_DISMISS)
        if (animateDismiss) {
            startDismissAnimate(
                mPopupWindow.mDisplayAnimateView.getWidth(),
                mPopupWindow.mDisplayAnimateView.getHeight()
            )
            msg.arg1 = 1
            mPopupWindow.mDisplayAnimateView.removeCallbacks(dismissAnimationDelayRunnable)
            mPopupWindow.mDisplayAnimateView.postDelayed(
                dismissAnimationDelayRunnable,
                Math.max(dismissDuration, 0)
            )
        } else {
            msg.arg1 = 0
            mPopupWindow.superDismiss()
        }
        BasePopupUnsafe.StackFetcher.remove(mPopupWindow)
        sendEvent(msg)
    }

    private var dismissAnimationDelayRunnable: Runnable? = Runnable {
        flag = flag and CUSTOM_ON_ANIMATE_DISMISS.inv()
        if (mPopupWindow != null) {
            //popup可能已经释放引用了
            mPopupWindow.superDismiss()
        }
    }

    fun forceDismiss() {
        if (mDismissAnimation != null) mDismissAnimation!!.cancel()
        if (mDismissAnimator != null) mDismissAnimator!!.cancel()
        if (mPopupWindow != null && hideKeyboardOnDismiss) {
            KeyboardUtils.close(mPopupWindow.getContext())
        }
        if (dismissAnimationDelayRunnable != null) {
            dismissAnimationDelayRunnable!!.run()
        }
    }

    fun onKeyboardChange(keyboardBounds: Rect?, isVisible: Boolean) {
        if (mKeyboardStateChangeListener != null) {
            mKeyboardStateChangeListener.onKeyboardChange(keyboardBounds, isVisible)
        }
        if (mUserKeyboardStateChangeListener != null) {
            mUserKeyboardStateChangeListener.onKeyboardChange(keyboardBounds, isVisible)
        }
    }

    fun update(v: View?, positionMode: Boolean) {
        var v = v
        if (!mPopupWindow.isShowing() || mPopupWindow.mContentView == null) return
        if (v == null && mShowInfo != null) {
            v = mShowInfo!!.mAnchorView
        }
        prepare(v, positionMode)
        mPopupWindow.mPopupWindowProxy.update()
    }

    fun onConfigurationChanged(newConfig: Configuration?) {
        update(if (mShowInfo == null) null else mShowInfo!!.mAnchorView, if (mShowInfo == null) false else mShowInfo!!.positionMode)
    }

    fun dispatchOutSideEvent(event: MotionEvent?, touchInMask: Boolean, isMaskPressed: Boolean) {
        if (mPopupWindow != null) {
            mPopupWindow.dispatchOutSideEvent(event, touchInMask, isMaskPressed)
        }
    }

    fun hasBackground(): Boolean {
        if (mBackgroundView != null) {
            return true
        }
        return if (mBackgroundDrawable is ColorDrawable) {
            (mBackgroundDrawable as ColorDrawable).color != Color.TRANSPARENT && mBackgroundDrawable
                .getAlpha() > 0
        } else mBackgroundDrawable != null
    }

    class InnerShowInfo(var mAnchorView: View?, var positionMode: Boolean)

    class LinkedViewLayoutChangeListenerWrapper(private val mTarget: View?) : ViewTreeObserver.OnPreDrawListener {
        var isAdded = false
        private var lastX = 0f
        private var lastY = 0f
        private var lastWidth = 0
        private var lastHeight = 0
        private var lastVisible = 0
        private var lastShowState = false
        private var hasChange = false
        var lastLocationRect = Rect()
        var newLocationRect = Rect()
        fun attach() {
            if (mTarget == null || isAdded) return
            mTarget.getGlobalVisibleRect(lastLocationRect)
            refreshViewParams()
            mTarget.viewTreeObserver.addOnPreDrawListener(this)
            isAdded = true
        }

        fun detach() {
            if (mTarget == null || !isAdded) return
            try {
                mTarget.viewTreeObserver.removeOnPreDrawListener(this)
            } catch (e: Exception) {
            }
            isAdded = false
        }

        fun refreshViewParams() {
            if (mTarget == null) return

            //之所以不直接用getGlobalVisibleRect，是因为getGlobalVisibleRect需要不断的找到parent然后获取位置，因此先比较自身属性，然后进行二次验证
            val curX = mTarget.x
            val curY = mTarget.y
            val curWidth = mTarget.width
            val curHeight = mTarget.height
            val curVisible = mTarget.visibility
            val isShow = mTarget.isShown
            hasChange = (curX != lastX || curY != lastY || curWidth != lastWidth || curHeight != lastHeight || curVisible != lastVisible) && isAdded
            if (!hasChange) {
                //不排除是recyclerview中那样子的情况，因此这里进行二次验证，获取view在屏幕中的位置
                mTarget.getGlobalVisibleRect(newLocationRect)
                if (newLocationRect != lastLocationRect) {
                    lastLocationRect.set(newLocationRect)
                    //处理可能的在recyclerview回收的事情
                    if (!handleShowChange(mTarget, lastShowState, isShow)) {
                        hasChange = true
                    }
                }
            }
            lastX = curX
            lastY = curY
            lastWidth = curWidth
            lastHeight = curHeight
            lastVisible = curVisible
            lastShowState = isShow
        }

        private fun handleShowChange(target: View, lastShowState: Boolean, isShow: Boolean): Boolean {
            if (lastShowState && !isShow) {
                if (mPopupWindow.isShowing()) {
                    dismiss(false)
                    return true
                }
            } else if (!lastShowState && isShow) {
                if (!mPopupWindow.isShowing()) {
                    mPopupWindow.tryToShowPopup(target, false)
                    return true
                }
            }
            return false
        }

        override fun onPreDraw(): Boolean {
            if (mTarget == null) return true
            refreshViewParams()
            if (hasChange) {
                update(mTarget, false)
            }
            return true
        }
    }

    fun findActivity(parent: Any?): Activity? {
        return findActivity(parent, true)
    }

    fun findActivity(parent: Any?, returnTopIfNull: Boolean): Activity? {
        var act: Activity? = null
        if (parent is Context) {
            act = PopupUtils.getActivity(parent as Context?)
        } else if (parent is Fragment) {
            act = parent.activity
        } else if (parent is Dialog) {
            act = PopupUtils.getActivity(parent.context)
        }
        if (act == null && returnTopIfNull) {
            act = BasePopupSDK.getInstance().getTopActivity()
        }
        return act
    }

    fun findDecorView(parent: Any?): View? {
        var decorView: View? = null
        var window: Window? = null
        if (parent is Dialog) {
            window = parent.window
        } else if (parent is DialogFragment) {
            if (parent.dialog == null) {
                decorView = parent.view
            } else {
                window = parent.dialog!!.window
            }
        } else if (parent is Fragment) {
            decorView = parent.view
        } else if (parent is Context) {
            val act: Activity = PopupUtils.getActivity(parent as Context?)
            decorView = if (act == null) null else act.findViewById(R.id.content)
        }
        return decorView ?: window?.decorView
    }

    fun isPrePopupBackgroundExists(): Boolean {
        if (mPopupWindow == null) return false
        val popupList: LinkedList<WindowManagerProxy> = WindowManagerProxy.PopupWindowQueueManager.getInstance()
            .getPopupList(mPopupWindow.getContext())
        if (popupList == null || popupList.isEmpty()) return false
        val size = popupList.size
        if (size == 1) {
            // 只有一个popup的时候需要留意这个popup是否正在dismiss
            val proxy: WindowManagerProxy = popupList[0]
            if (proxy.mPopupHelper != null && proxy.mPopupHelper.showFlag and razerdp.basepopup.BasePopupHelper.STATUS_START_DISMISS !== 0) {
                return false
            }
        }
        for (windowManagerProxy in popupList) {
            val helper: razerdp.basepopup.BasePopupHelper = windowManagerProxy.mPopupHelper
            if (helper != null && helper.hasBackground()) {
                return true
            }
        }
        return false
    }

    fun clear(destroy: Boolean) {
        if (mPopupWindow != null && mPopupWindow.mDisplayAnimateView != null) {
            //神奇的是，这个方式有可能失效，runnable根本就没有被remove掉
            mPopupWindow.mDisplayAnimateView.removeCallbacks(dismissAnimationDelayRunnable)
        }
        if (eventObserverMap != null) {
            eventObserverMap!!.clear()
        }
        PopupUiUtils.releaseAnimation(
            mShowAnimation,
            mDismissAnimation,
            mShowAnimator,
            mDismissAnimator,
            mMaskViewShowAnimation,
            mMaskViewDismissAnimation
        )
        if (mBlurOption != null) {
            mBlurOption.clear()
        }
        if (mShowInfo != null) {
            mShowInfo!!.mAnchorView = null
        }
        if (mGlobalLayoutListener != null) {
            PopupUiUtils.safeRemoveGlobalLayoutListener(
                mPopupWindow.getContext()
                    .getWindow()
                    .getDecorView(),
                mGlobalLayoutListener
            )
        }
        if (mLinkedViewLayoutChangeListenerWrapper != null) {
            mLinkedViewLayoutChangeListenerWrapper!!.detach()
        }
        showFlag = 0
        dismissAnimationDelayRunnable = null
        mShowAnimation = null
        mDismissAnimation = null
        mShowAnimator = null
        mDismissAnimator = null
        mMaskViewShowAnimation = null
        mMaskViewDismissAnimation = null
        eventObserverMap = null
        mPopupWindow = null
        mOnPopupWindowShowListener = null
        mOnDismissListener = null
        mOnBeforeShowCallback = null
        mBlurOption = null
        mBackgroundDrawable = null
        mBackgroundView = null
        mAutoShowInputEdittext = null
        mKeyboardStateChangeListener = null
        mShowInfo = null
        mLinkedViewLayoutChangeListenerWrapper = null
        mLinkedTarget = null
        mGlobalLayoutListener = null
        mUserKeyboardStateChangeListener = null
        mKeyEventListener = null
        keybaordAlignView = null
        mOnFitWindowManagerLayoutParamsCallback = null
    }
}*/
