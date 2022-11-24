package com.mozhimen.uicorek.popwink.bases

import android.animation.Animator
import android.app.Activity
import android.view.*
import android.widget.EditText
import android.widget.PopupWindow
import com.mozhimen.uicorek.popwink.bases.commons.ICreateBlurOptionListener

/**
 * @ClassName PopwinK
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/11/17 12:06
 * @Version 1.0
 */
/*
class PopwinK : DefaultLifecycleObserver {
    val TAG = "PopwinK>>>>>"

    var DEFAULT_BACKGROUND_COLOR = Color.parseColor("#8f000000")
    private val MAX_RETRY_SHOW_TIME = 3
    val MATCH_PARENT = ViewGroup.LayoutParams.MATCH_PARENT
    val WRAP_CONTENT = ViewGroup.LayoutParams.WRAP_CONTENT

    //region # variate
    //init
    private var _anchorLifecycleOwner: Any
    private var _popwinKHelper: PopwinKHelper
    private var _context: Activity? = null
    private var _initWidth = 0
    private var _initHeight = 0
    private var _initRunnable: Runnable? = null

    private var _contentView: View? = null//popup视图
    private var _popupWindowProxy: PopupWindowProxy? = null//元素定义

    private var mAnchorDecorView: View? = null
    private var isDestroyed = false
    var pendingPopupWindow = false
    var mDisplayAnimateView: View? = null
    //endregion

    @Volatile
    private var isExitAnimatePlaying = false

    //region # constructor
    */
/**
     *  * 最常用的构造器，传入context情况下会寻找其Activity作为依附的WindowToken，如果获取不到（如ApplicationContext）则取当前应用最顶层Activity作为依附的WindowToken
     *  * 此时PopupWindow的层级与Activity的Window一致，无法显示在其他Window上方（如弹出一个Dialog再弹出一个PopupWindow，此时PopupWindow显示在Dialog**下层**）
     *  * 该构造器构造的BasePopup可以在Dialog或DialogFragment下弹出
     *//*

    constructor(context: Context) : this(context, 0, 0)

    constructor(context: Context, width: Int, height: Int) : this(context, width, height, 0)

    */
/**
     *  * 构造器传入Fragment情况下会寻找其RootView作为依附的WindowToken，如果获取不到则取当前fragment所属Activity作为依附的WindowToken
     *  * 此时PopupWindow的层级与Fragment的Window一致，无法显示在其他Window上方（如弹出一个Dialog再弹出一个PopupWindow，此时PopupWindow显示在Dialog**下层**）
     *  * 该构造器构造的BasePopup可以在DialogFragment或者依附于Activity的Fragment下弹出，其余情况不能弹出
     *//*

    constructor(fragment: Fragment) : this(fragment, 0, 0)

    constructor(fragment: Fragment, width: Int, height: Int) : this(fragment, width, height, 0)

    */
/**
     *  * 构造器传入Dialog情况下会寻找其Window作为依附的WindowToken
     *  * 此时PopupWindow的层级与Dialog的Window一致，无法显示在其他Window上方（如弹出一个Dialog再弹出一个PopupWindow，此时PopupWindow显示在Dialog**上层**）
     *  * 该构造器构造的BasePopup只可以在Dialog显示（即Dialog的ContentView准备就绪）的时候弹出
     *//*

    constructor(dialog: Dialog) : this(dialog, 0, 0)

    constructor(dialog: Dialog, width: Int, height: Int) : this(dialog, width, height, 0)

    constructor(ownerAnchorParent: Any, width: Int, height: Int, flag: Int) {
        this._anchorLifecycleOwner = ownerAnchorParent
        checkActivity()
        _popwinKHelper = PopwinKHelper(this)
        setPriority(EPriority.NORMAL)
        _initWidth = width
        _initHeight = height
    }

    private fun checkActivity() {
        if (_context != null) {
            return
        }
        val activity: Activity = UtilKActivity.getActivityByObj(_anchorLifecycleOwner, true) ?: return
        if (_anchorLifecycleOwner is LifecycleOwner) {
            bindLifecycleOwner(_anchorLifecycleOwner as LifecycleOwner)
        } else if (activity is LifecycleOwner) {
            bindLifecycleOwner(activity as LifecycleOwner)
        } else {
            listenForLifeCycle(activity)
        }
        _context = activity
        _initRunnable?.run()
    }

    private fun bindLifecycleOwner(lifecycleOwner: LifecycleOwner): PopwinK {
        if (getContext() is LifecycleOwner) {
            (getContext() as LifecycleOwner?)!!.lifecycle.removeObserver(this)
        }
        lifecycleOwner.lifecycle.addObserver(this)
        return this
    }

    private fun listenForLifeCycle(activity: Activity) {
        activity.window.decorView.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(view: View) {}
            override fun onViewDetachedFromWindow(view: View) {
                view.removeOnAttachStateChangeListener(this)
                onDestroy()
            }
        })
    }
    //endregion

    //region # inflate view
    fun setContentView(@LayoutRes layoutId: Int) {
        setContentView(createPopupById(layoutId))
    }

    */
/**
     * 这个方法封装了LayoutInflater.from(context).inflate，方便您设置PopupWindow所用的xml
     * @param layoutId reference of layout
     * @return root View of the layout
     *//*

    private fun createPopupById(layoutId: Int): View? {
        return _popwinKHelper.inflate(getContextOrApplicationContext(), layoutId)
    }

    private fun getContextOrApplicationContext(): Context {
        return getContext() ?: UtilKGlobal.instance.getApp()!!
    }

    */
/**
     * 获取context，请留意是否为空`null`
     * @return 返回对应的context。如果为空，则返回`null`
     *//*

    private fun getContext(): Activity? {
        return _context
    }

    fun setContentView(view: View?) {
        _initRunnable = Runnable {
            _initRunnable = null
            initView(view)
        }
        if (getContext() == null) return
        _initRunnable!!.run()
    }

    private fun initView(contentView: View?) {
        _contentView = contentView
        _popwinKHelper.setContentRootId(_contentView)
        mDisplayAnimateView = onCreateAnimateView()
        if (mDisplayAnimateView == null) {
            mDisplayAnimateView = _contentView
        }
        setWidth(_initWidth)
        setHeight(_initHeight)

        //默认占满全屏
        if (_popupWindowProxy == null) {
            _popupWindowProxy = PopupWindowProxy(
                BasePopupContextWrapper(
                    getContext(),
                    _popwinKHelper
                )
            )
        }
        _popupWindowProxy.setContentView(_contentView)
        _popupWindowProxy.setOnDismissListener(this)
        setPopupAnimationStyle(0)
        if (_contentView != null) {
            onViewCreated(_contentView!!)
        }
    }

    */
/**
     * 当ContentView创建的时候回调该方法，ContentView指[.onCreateContentView]返回的值
     *//*

    fun onViewCreated(contentView: View) {}

    */
/**
     *
     *
     * 该方法决定您的PopupWindow将会以怎样的动画展示出来，可以返回为 `null`
     *
     *
     *
     * 本类提供一些简单的动画方法：
     *
     *  * [razerdp.util.animation.AnimationHelper]：快速创建动画
     *
     *
     *
     * 如果需要用到属性动画，请覆写[.onCreateShowAnimator]
     *
     * @return 返回显示PopupWindow的动画
     *//*

    protected fun onCreateShowAnimation(): Animation? {
        return null
    }

    protected fun onCreateShowAnimation(width: Int, height: Int): Animation? {
        return onCreateShowAnimation()
    }

    */
/**
     *
     *
     * 该方法决定您的PopupWindow将会以怎样的动画消失，可以返回为 `null`
     * <br></br>
     * <br></br>
     * 如果返回不为空，则在返回动画播放结束后触发[PopupWindow.dismiss]
     *
     *
     *
     * 本类提供一些简单的动画方法：
     *
     *  * [razerdp.util.animation.AnimationHelper]：快速创建动画
     *
     *
     *
     *
     * 如果需要用到属性动画，请覆写[.onCreateDismissAnimator] ()}
     *
     * @return 返回PopupWindow消失前的动画
     *//*

    protected fun onCreateDismissAnimation(): Animation? {
        return null
    }

    protected fun onCreateDismissAnimation(width: Int, height: Int): Animation? {
        return onCreateDismissAnimation()
    }

    */
/**
     *
     *
     * 该方法决定您的PopupWindow将会以怎样的动画展示出来（返回 [Animator]），可以返回为 `null`
     * <br></br>
     * <br></br>
     * 功能详情请看[.onCreateShowAnimation]
     *
     *
     * @return 返回显示PopupWindow的动画
     *//*

    protected fun onCreateShowAnimator(): Animator? {
        return null
    }

    protected fun onCreateShowAnimator(width: Int, height: Int): Animator? {
        return onCreateShowAnimator()
    }

    */
/**
     *
     *
     * 通过该方法您可以指定您的PopupWindow显示动画用于哪个View（[.onCreateShowAnimation]/[.onCreateShowAnimator]）
     * <br></br>
     * <br></br>
     * 可以返回为空 `null`
     *
     *
     * @return 返回指定播放动画的View，返回为空则默认整个PopupWindow
     *//*

    protected fun onCreateAnimateView(): View? {
        return null
    }


    */
/**
     *
     *
     * 该方法决定您的PopupWindow将会以怎样的动画消失（返回 [Animator]），可以返回为 `null`
     * <br></br>
     * <br></br>
     * 功能详情请看[.onCreateDismissAnimation] ()}
     *
     *//*

    protected fun onCreateDismissAnimator(): Animator? {
        return null
    }

    protected fun onCreateDismissAnimator(width: Int, height: Int): Animator? {
        return onCreateDismissAnimator()
    }

    */
/**
     *
     *
     * 当传入true，你的PopupWindow将会淡入显示，淡出消失。
     * <br></br>
     * 与[.onCreateShowAnimation]/[.onCreateDismissAnimation]不同的是，该方法为Window层级服务，固定Style
     * <br></br>
     *
     *  * {@style ref razerdp.library.R.anim.basepopup_fade_in}
     *  * {@style ref razerdp.library.R.anim.basepopup_fade_out}
     *
     *
     *
     * @param isPopupFadeAnimate true for apply anim style
     *//*

    fun setPopupFadeEnable(isPopupFadeAnimate: Boolean): BasePopupWindow? {
        _popwinKHelper.setFlag(FADE_ENABLE, isPopupFadeAnimate)
        return this
    }


    */
/**
     *
     *
     * 当前PopupWindow是否设置了淡入淡出效果
     *
     *//*

    fun isPopupFadeEnable(): Boolean {
        return _popwinKHelper.isPopupFadeEnable()
    }

    */
/**
     *
     *
     * 设置PopupWindow的动画style**针对PopupWindow整体的Window哦**
     * <br></br>
     * <br></br>
     * 通常情况下，请使用[.onCreateDismissAnimation] or [.onCreateShowAnimator]
     *
     *//*

    fun setPopupAnimationStyle(animationStyleRes: Int): BasePopupWindow? {
        _popwinKHelper.animationStyleRes = animationStyleRes
        return this
    }

    //------------------------------------------showPopup-----------------------------------------------

    //------------------------------------------showPopup-----------------------------------------------
    */
/**
     *
     *
     * 调用这个方法时，将会展示PopupWindow。
     * <br></br>
     * <br></br>
     * 如果[.onCreateShowAnimation] or [.onCreateShowAnimator]其中之一返回不为空，
     * 则在PopupWindow展示后为[.onCreateAnimateView] 指定的View执行动画
     *
     *
     *
     * 您可以在[Activity.onCreate]里面使用该方法，本方法在无法展示时会重试3次，如果3次都无法展示，则失败。
     *
     *//*

    fun showPopupWindow() {
        if (checkPerformShow(null)) {
            _popwinKHelper.withAnchor(false)
            tryToShowPopup(null, false)
        }
    }

    */
/**
     *
     *
     * 调用这个方法时，将会展示PopupWindow。
     * <br></br>
     * <br></br>
     * <h3>本方法在展示PopupWindow时，会跟系统一样，展示在传入的View的底部，如果位置足够，将会跟anchorView的锚点对齐。</h3>
     * <br></br>
     * <br></br>
     * 其他方法详情参考[.showPopupWindow]
     *
     *
     *
     *
     * @param anchorView 锚点View，PopupWindow将会显示在其下方
     *//*

    fun showPopupWindow(anchorView: View?) {
        if (checkPerformShow(anchorView)) {
            _popwinKHelper.withAnchor(anchorView != null)
            tryToShowPopup(anchorView, false)
        }
    }

    */
/**
     *
     *
     * 调用这个方法时，将会在指定位置弹出PopupWindow。
     * <br></br>
     * 其他方法详情参考[.showPopupWindow]
     *
     *
     *
     * @param x 坐标轴x
     * @param y 坐标轴y
     *//*

    fun showPopupWindow(x: Int, y: Int) {
        if (checkPerformShow(null)) {
            _popwinKHelper.setShowLocation(x, y)
            _popwinKHelper.withAnchor(true)
            tryToShowPopup(null, true)
        }
    }

    */
/**
     *
     *
     * 啥都不干，单纯的update，简单的说，就是更新你所设置的所有东西~
     * <br></br>
     * **WARN：非常不建议在连续update的情况下使用背景模糊，这会导致较大的性能消耗。****
     ** *//*

    fun update() {
        _popwinKHelper.update(null, false)
    }

    */
/**
     *
     *
     * 参考anchorView更新PopupWindow位置或大小等信息。
     * <br></br>
     * **该方法跟anchorView关联，即您的gravity，offset等会跟随anchorView变化而变化**
     *
     * <br></br>
     * **WARN：非常不建议在连续update的情况下使用背景模糊，这会导致较大的性能消耗。****
     *
     * @param anchorView 被参考的anchorView
     ** *//*

    fun update(anchorView: View?) {
        _popwinKHelper.update(anchorView, false)
    }

    */
/**
     *
     *
     * 在指定位置更新PopupWindow位置或大小等信息。
     *
     * <br></br>
     * **WARN：非常不建议在连续update的情况下使用背景模糊，这会导致较大的性能消耗。****
     *
     * @param x 目标位置x坐标
     * @param y 目标位置y坐标
     ** *//*

    fun update(x: Int, y: Int) {
        if (!isShowing() || getContentView() == null) return
        _popwinKHelper.setShowLocation(x, y)
        _popwinKHelper.withAnchor(true)
        _popwinKHelper.update(null, true)
    }

    */
/**
     *
     *
     * 更新PopupWindow的宽高
     * <br></br>
     * **WARN：非常不建议在连续update的情况下使用背景模糊，这会导致较大的性能消耗。****
     * <br></br>
     * **WARN：非常不建议在连续update的情况下使用背景模糊，这会导致较大的性能消耗。****
     * <br></br>
     *
     * @param width  宽度
     * @param height 高度
     **** *//*

    fun update(width: Float, height: Float) {
        if (!isShowing() || getContentView() == null) return
        setWidth(width.toInt())
            .setHeight(height.toInt())
            .update()
    }

    */
/**
     *
     *
     * 在指定位置更新PopupWindow位置或大小等信息。
     * <br></br>
     *
     * @param x      目标位置x坐标
     * @param y      目标位置y坐标
     * @param width  宽度
     * @param height 高度
     *//*

    fun update(x: Int, y: Int, width: Float, height: Float) {
        if (!isShowing() || getContentView() == null) return
        _popwinKHelper.setShowLocation(x, y)
        _popwinKHelper.withAnchor(true)
        _popwinKHelper.setPopupViewWidth(width.toInt())
        _popwinKHelper.setPopupViewHeight(height.toInt())
        _popwinKHelper.update(null, true)
    }


    //------------------------------------------Methods-----------------------------------------------

    //------------------------------------------Methods-----------------------------------------------
    */
/**
     * 感谢@xchengDroid(https://github.com/xchengDroid)在#263(https://github.com/razerdp/BasePopup/issues/263)中提出的建议
     *//*

    fun tryToShowPopup(v: View?, positionMode: Boolean) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw CalledFromWrongThreadException(PopupUtils.getString(R.string.basepopup_error_thread))
        }
        checkActivity()
        if (_context == null) {
            if (BasePopupSDK.getInstance().getTopActivity() == null) {
                waitForFirstActivityOpened(v, positionMode)
                return
            }
            onShowError(NullPointerException(PopupUtils.getString(R.string.basepopup_error_non_act_context)))
            return
        }
        if (isShowing() || _contentView == null) return
        if (isDestroyed) {
            onShowError(IllegalAccessException(PopupUtils.getString(R.string.basepopup_error_destroyed)))
            return
        }
        val decorView = getDecorView()
        if (decorView == null) {
            onShowError(
                NullPointerException(
                    PopupUtils.getString(
                        R.string.basepopup_error_decorview,
                        ownerParentLog()
                    )
                )
            )
            return
        }
        if (decorView.windowToken == null) {
            onShowError(
                IllegalStateException(
                    PopupUtils.getString(
                        R.string.basepopup_window_not_prepare,
                        ownerParentLog()
                    )
                )
            )
            pendingPopupWindow(decorView, v, positionMode)
            return
        }
        onLogInternal(PopupUtils.getString(R.string.basepopup_window_prepared, ownerParentLog()))
        if (!onBeforeShow()) return
        _popwinKHelper.prepare(v, positionMode)
        try {
            if (isShowing()) {
                onShowError(IllegalStateException(PopupUtils.getString(R.string.basepopup_has_been_shown)))
                return
            }
            _popwinKHelper.onShow()
            //这里传参没啥意义的，，反正代理类会解决
            _popupWindowProxy.showAtLocation(decorView, Gravity.NO_GRAVITY, 0, 0)
            onLogInternal(PopupUtils.getString(R.string.basepopup_shown_successful))
        } catch (e: Exception) {
            e.printStackTrace()
            superDismiss()
            onShowError(e)
        }
    }

    fun waitForFirstActivityOpened(v: View?, positionMode: Boolean) {
        BasePopupSDK.getInstance().regFirstActivityOpen(Observer<Boolean?> { tryToShowPopup(v, positionMode) })
    }

    fun onBeforeDismissInternal(cb: OnDismissCallback): Boolean {
        var result = onBeforeDismiss()
        if (cb != null) {
            result = result && cb.onBeforeDismiss()
        }
        return result
    }

    fun onBeforeShow(): Boolean {
        return true
    }

    fun onBeforeDismiss(): Boolean {
        return true
    }

    protected fun onShowError(e: Exception) {
        PopupLog.e(TAG, "onShowError: ", e)
        onLogInternal(e.message)
    }

    private fun getDecorView(): View? {
        mAnchorDecorView = BasePopupHelper.findDecorView(_anchorLifecycleOwner)
        return mAnchorDecorView
    }

    fun dispatchOutSideEvent(event: MotionEvent, touchInBackground: Boolean, isMaskPressed: Boolean) {
        val consumeEvent = onOutSideTouch(event, touchInBackground, isMaskPressed)
        if (_popwinKHelper.isOutSideTouchable()) {
            val proxy: WindowManagerProxy = _popupWindowProxy.prevWindow()
            if (proxy == null) {
                if (consumeEvent) {
                    event.action = MotionEvent.ACTION_CANCEL
                }
                if (mAnchorDecorView != null) {
                    mAnchorDecorView!!.rootView.dispatchTouchEvent(event)
                } else {
                    _context!!.window.decorView.rootView.dispatchTouchEvent(event)
                }
            } else {
                if (!consumeEvent) {
                    proxy.dispatchToDecorProxy(event)
                }
            }
        }
    }


    private fun pendingPopupWindow(decorView: View, anchorView: View?, positionMode: Boolean) {
        if (pendingPopupWindow) return
        pendingPopupWindow = true
        decorView.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(v: View) {
                pendingPopupWindow = false
                v.removeOnAttachStateChangeListener(this)
                v.post { tryToShowPopup(anchorView, positionMode) }
            }

            override fun onViewDetachedFromWindow(v: View) {
                pendingPopupWindow = false
                v.removeOnAttachStateChangeListener(this)
            }
        })
    }


    */
/**
     * PopupWindow展示时是否收起键盘，默认收起
     *
     * @param dismiss
     * @return
     *//*

    fun hideKeyboardOnShow(dismiss: Boolean): BasePopupWindow? {
        setKeyboardAdaptive(dismiss)
        return this
    }

    */
/**
     * PopupWindow消失时是否收起键盘，默认收起
     *
     * @param dismiss
     * @return
     *//*

    fun hideKeyboardOnDismiss(dismiss: Boolean): BasePopupWindow? {
        _popwinKHelper.hideKeyboardOnDismiss = dismiss
        return this
    }

    */
/**
     *
     *
     * PopupWindow是否需要自适应输入法，为输入法弹出让出区域
     *
     *
     * @param adaptive
     *  * true for "SOFT_INPUT_ADJUST_RESIZE" mode
     *  * false for "SOFT_INPUT_STATE_UNCHANGED" mode
     *
     * <br></br>
     *//*

    fun setKeyboardAdaptive(adaptive: Boolean): BasePopupWindow? {
        _popwinKHelper.mSoftInputMode = if (adaptive) WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE else WindowManager.LayoutParams.SOFT_INPUT_STATE_UNCHANGED
        return this
    }

    */
/**
     *
     *
     * 设置PopupWindow适配输入法的适配模式
     *
     *
     * @param flag
     *  * [BasePopupWindow.FLAG_KEYBOARD_ALIGN_TO_ROOT]，键盘适配对齐到整个popup content view
     *  * [BasePopupWindow.FLAG_KEYBOARD_ALIGN_TO_VIEW]，键盘适配对齐到指定的view，需要传入viewid
     *  * [BasePopupWindow.FLAG_KEYBOARD_IGNORE_OVER]，键盘适配仅作用于无法完整显示的情况
     *  * [BasePopupWindow.FLAG_KEYBOARD_ANIMATE_ALIGN]，键盘是否动画适配
     *  * [BasePopupWindow.FLAG_KEYBOARD_FORCE_ADJUST]，是否强制适配输入法
     *
     *//*

    fun setKeyboardAdaptionMode(mode: Int): BasePopupWindow? {
        return setKeyboardAdaptionMode(0, mode)
    }


    */
/**
     *
     *
     * 设置PopupWindow适配输入法的适配模式
     *
     *
     * @param viewId keyboard对齐的View id
     * @param flag
     *  * [BasePopupWindow.FLAG_KEYBOARD_ALIGN_TO_ROOT]，键盘适配对齐到整个popup content view
     *  * [BasePopupWindow.FLAG_KEYBOARD_ALIGN_TO_VIEW]，键盘适配对齐到指定的view，需要传入viewid
     *  * [BasePopupWindow.FLAG_KEYBOARD_IGNORE_OVER]，键盘适配仅作用于无法完整显示的情况
     *  * [BasePopupWindow.FLAG_KEYBOARD_ANIMATE_ALIGN]，键盘是否动画适配
     *  * [BasePopupWindow.FLAG_KEYBOARD_FORCE_ADJUST]，是否强制适配输入法
     *
     *//*

    fun setKeyboardAdaptionMode(viewId: Int, mode: Int): BasePopupWindow? {
        _popwinKHelper.keybaordAlignViewId = viewId
        _popwinKHelper.setFlag(
            CFlag.KEYBOARD_ALIGN_TO_ROOT
                    or CFlag.KEYBOARD_ALIGN_TO_VIEW
                    or CFlag.KEYBOARD_IGNORE_OVER_KEYBOARD
                    or CFlag.KEYBOARD_ANIMATE_ALIGN
                    or CFlag.KEYBOARD_FORCE_ADJUST, false
        )
        _popwinKHelper.setFlag(mode, true)
        return this
    }


    */
/**
     *
     *
     * 设置PopupWindow适配输入法的适配模式
     *
     *
     * @param alignTarget keyboard对齐的Vie
     * @param flag
     *  * [BasePopupWindow.FLAG_KEYBOARD_ALIGN_TO_ROOT]，键盘适配对齐到整个popup content view
     *  * [BasePopupWindow.FLAG_KEYBOARD_ALIGN_TO_VIEW]，键盘适配对齐到指定的view，需要传入view，传入view的时候将会优先于传入viewid
     *  * [BasePopupWindow.FLAG_KEYBOARD_IGNORE_OVER]，键盘适配仅作用于无法完整显示的情况
     *  * [BasePopupWindow.FLAG_KEYBOARD_ANIMATE_ALIGN]，键盘是否动画适配
     *  * [BasePopupWindow.FLAG_KEYBOARD_FORCE_ADJUST]，是否强制适配输入法
     *
     *//*

    fun setKeyboardAdaptionMode(alignTarget: View, mode: Int): BasePopupWindow? {
        _popwinKHelper.keybaordAlignView = alignTarget
        _popwinKHelper.setFlag(
            (CFlag.KEYBOARD_ALIGN_TO_ROOT
                    or CFlag.KEYBOARD_ALIGN_TO_VIEW
                    or CFlag.KEYBOARD_IGNORE_OVER_KEYBOARD
                    or CFlag.KEYBOARD_ANIMATE_ALIGN
                    or CFlag.KEYBOARD_FORCE_ADJUST), false
        )
        _popwinKHelper.setFlag(mode, true)
        return this
    }


    */
/**
     *
     *
     * PopupWindow在展示的时候自动打开输入法
     *
     *//*

    fun setAutoShowKeyboard(autoShow: Boolean): BasePopupWindow? {
        return setAutoShowKeyboard(null, autoShow)
    }

    */
/**
     *
     *
     * PopupWindow在展示的时候自动打开输入法，在传入参数时请务必传入[EditText]
     *
     *//*

    fun setAutoShowKeyboard(editText: EditText, autoShow: Boolean): BasePopupWindow? {
        _popwinKHelper.mAutoShowInputEdittext = editText
        _popwinKHelper.setFlag(AUTO_INPUT_METHOD, autoShow)
        return this
    }


    */
/**
     * 设置输入法弹出延时
     *
     * @param delay 单位：毫秒，如果小于0则会被置为0
     * @return
     *//*

    fun setShowKeyboardDelay(delay: Long): BasePopupWindow? {
        _popwinKHelper.showKeybaordDelay = Math.max(0, delay)
        return this
    }

    */
/**
     * 设置输入法弹出时，basepopup在剩余空间内的gravity
     *
     * @return
     *//*

    fun setKeyboardGravity(gravity: Int): BasePopupWindow? {
        _popwinKHelper.keyboardGravity = gravity
        return this
    }

    */
/**
     * 设置输入法弹出时，basepopup在剩余空间内的水平位移
     *
     * @return
     *//*

    fun setKeyboardOffsetX(offsetX: Int): BasePopupWindow? {
        _popwinKHelper.keyboardOffsetX = offsetX
        return this
    }

    */
/**
     * 设置输入法弹出时，basepopup在剩余空间内的垂直位移
     *
     * @return
     *//*

    fun setKeyboardOffsetY(offsetY: Int): BasePopupWindow? {
        _popwinKHelper.keyboardOffsetY = offsetY
        return this
    }

    */
/**
     *
     *
     * 是否允许PopupWindow响应返回键并dismiss
     *
     *
     *
     *//*

    fun setBackPressEnable(backPressEnable: Boolean): BasePopupWindow? {
        _popwinKHelper.setFlag(BACKPRESS_ENABLE, backPressEnable)
        return this
    }


    */
/**
     *
     *
     * 还在用View.findViewById么，，，不如试试这款？
     *
     *
     * @param id the ID to search for
     * @return a view inject given ID if found, or `null` otherwise
     *//*

    fun <T : View?> findViewById(id: Int): T? {
        if (_contentView != null && id != 0) {
            return _contentView!!.findViewById<View>(id) as T
        }
        Log.e(TAG, "contentView is null,please call setContentView() before findViewById()")
        return null
    }


    */
/**
     * 允许PopupWindow覆盖状态栏
     *//*

    fun setOverlayStatusbar(overlay: Boolean): BasePopupWindow? {
        _popwinKHelper.overlayStatusbar(overlay)
        return this
    }

    */
/**
     * 设置覆盖状态栏的模式
     *
     * @param mode   * 可以选择 [BasePopupFlag.OVERLAY_CONTENT]或者[BasePopupFlag.OVERLAY_MASK]
     *  * 如果只传入[BasePopupFlag.OVERLAY_CONTENT]，则内容（[.onCreateContentView]）可以覆盖导航栏，蒙层不可以
     *  * 如果只传入[BasePopupFlag.OVERLAY_MASK]，则蒙层可以覆盖导航栏，内容不可以
     *  * 如果传入[BasePopupFlag.OVERLAY_CONTENT]|[BasePopupFlag.OVERLAY_MASK]，则两者皆可
     *
     *//*

    fun setOverlayStatusbarMode(mode: Int): BasePopupWindow? {
        _popwinKHelper.setOverlayStatusbarMode(mode)
        return this
    }

    */
/**
     * 允许PopupWindow覆盖导航栏
     *//*

    fun setOverlayNavigationBar(overlay: Boolean): BasePopupWindow? {
        _popwinKHelper.overlayNavigationBar(overlay)
        return this
    }

    */
/**
     * 是否允许蒙层叠加，默认不叠加，一个页面同个background不会重复叠加，直到设置了背景为止。
     *//*

    fun setOverlayMask(overlay: Boolean): BasePopupWindow? {
        _popwinKHelper.overlayMask = overlay
        return this
    }

    */
/**
     * 设置覆盖导航栏的模式
     *
     * @param mode   * 可以选择 [BasePopupFlag.OVERLAY_CONTENT]或者[BasePopupFlag.OVERLAY_MASK]
     *  * 如果只传入[BasePopupFlag.OVERLAY_CONTENT]，则内容（[.onCreateContentView]）可以覆盖导航栏，蒙层不可以
     *  * 如果只传入[BasePopupFlag.OVERLAY_MASK]，则蒙层可以覆盖导航栏，内容不可以
     *  * 如果传入[BasePopupFlag.OVERLAY_CONTENT]|[BasePopupFlag.OVERLAY_MASK]，则两者皆可
     *
     *//*

    fun setOverlayNavigationBarMode(mode: Int): BasePopupWindow? {
        _popwinKHelper.setOverlayNavigationBarMode(mode)
        return this
    }

    */
/**
     *
     *
     * 设置PopupWindow背景颜色，默认颜色为**#8f000000**
     *
     *
     * @param color 背景颜色
     *//*

    fun setBackgroundColor(color: Int): BasePopupWindow? {
        _popwinKHelper.setPopupBackground(ColorDrawable(color))
        return this
    }

    */
/**
     *
     *
     * 设置PopupWindow背景Drawable，默认颜色为**#8f000000**
     *
     *
     * @param drawableIds 背景Drawable id
     *//*

    fun setBackground(drawableIds: Int): BasePopupWindow? {
        if (drawableIds == 0) {
            return setBackground(null)
        }
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setBackground(getContextOrApplicationContext(true)!!.getDrawable(drawableIds))
        } else {
            setBackground(getContextOrApplicationContext(true)!!.resources.getDrawable(drawableIds))
        }
    }

    */
/**
     *
     *
     * 设置PopupWindow背景Drawable，默认颜色为**#8f000000**
     *
     *
     * @param background 背景Drawable
     *//*

    fun setBackground(background: Drawable?): BasePopupWindow? {
        _popwinKHelper.setPopupBackground(background)
        return this
    }


    */
/**
     *
     *
     * 设置PopupWindow背景View，因为背景全屏的原因，该View将会被强制设为MATCH_PARENT/MATCH_PARENT
     *
     *
     * @param backgroundView 背景View
     *//*

    fun setBackgroundView(backgroundView: View?): BasePopupWindow? {
        _popwinKHelper.setBackgroundView(backgroundView)
        return this
    }

    */
/**
     *
     *
     * 获取当前PopupWindow背景
     *
     *
     * @return 背景
     *//*

    fun getPopupBackground(): Drawable? {
        return _popwinKHelper.getPopupBackground()
    }

    */
/**
     *
     *
     * 设置PopupWindow弹出时是否模糊背景。
     * <br></br>
     * <br></br>
     * 在使用模糊背景前，您可以通过[.setBlurOption]传入模糊配置。
     * <br></br>
     * <br></br>
     * **本方法默认模糊当前Activity的DecorView**
     *
     *
     * @param blurBackgroundEnable true for blur decorView
     *//*

    fun setBlurBackgroundEnable(blurBackgroundEnable: Boolean): BasePopupWindow? {
        return setBlurBackgroundEnable(blurBackgroundEnable, null)
    }

    */
/**
     *
     *
     * 设置PopupWindow弹出时是否模糊背景。
     * <br></br>
     * <br></br>
     * 在使用模糊背景前，您可以通过[.setBlurOption]传入模糊配置。
     * <br></br>
     * <br></br>
     * 本方法允许您传入一个初始化监听，您可以在[ICreateBlurOptionListener.onCreateBlurOption]中进行展示前的最后一次修改
     *
     *
     * @param blurBackgroundEnable true for blur decorView
     * @param optionInitListener   初始化回调
     *//*

    fun setBlurBackgroundEnable(blurBackgroundEnable: Boolean, optionInitListener: ICreateBlurOptionListener?): BasePopupWindow? {
        val activity = getContext()
        if (activity == null) {
            onLogInternal("无法配置默认模糊脚本，因为context不是activity")
            return this
        }
        var option: PopupBlurOption? = null
        if (blurBackgroundEnable) {
            option = PopupBlurOption()
            option.setFullScreen(true)
                .setBlurInDuration(-1)
                .setBlurOutDuration(-1)
            optionInitListener?.onCreateBlurOption(option)
            val decorView = getDecorView()
            if (decorView is ViewGroup && decorView.getId() == R.id.content) {
                option.setBlurView((activity.window.decorView as ViewGroup).getChildAt(0))
                option.setFullScreen(true)
            } else {
                option.setBlurView(decorView)
            }
        }
        return setBlurOption(option)
    }

    */
/**
     *
     *
     * 应用模糊配置，更多详情请参考[.setBlurBackgroundEnable]或者[.setBlurBackgroundEnable]
     *
     *
     * @param option 模糊配置
     *//*

    fun setBlurOption(option: PopupBlurOption?): BasePopupWindow? {
        _popwinKHelper.setToBlur(option)
        return this
    }

    */
/**
     * 这个方法用于简化您为View设置OnClickListener事件，多个View将会使用同一个点击事件
     *//*

    protected fun setViewClickListener(listener: View.OnClickListener?, vararg views: View?) {
        for (view: View? in views) {
            if (view != null && listener != null) {
                view.setOnClickListener(listener)
            }
        }
    }

    */
/**
     * PopupWindow是否处于展示状态
     *//*

    fun isShowing(): Boolean {
        return if (_popupWindowProxy == null) {
            false
        } else _popupWindowProxy.isShowing() || (_popwinKHelper.showFlag and BasePopupHelper.STATUS_START_SHOWING) !== 0
    }

    fun getOnDismissListener(): OnDismissCallback? {
        return _popwinKHelper.mOnDismissListener
    }

    */
/**
     * 设置PopupWindow显示的监听
     *//*

    fun setOnPopupWindowShowListener(onPopupWindowShowListener: IShowListener): BasePopupWindow? {
        _popwinKHelper.mOnPopupWindowShowListener = onPopupWindowShowListener
        return this
    }

    */
/**
     *
     *
     * 设置dismiss监听
     *
     *
     * @param onDismissListener 监听器
     *//*

    fun setOnDismissListener(onDismissListener: OnDismissCallback): BasePopupWindow? {
        _popwinKHelper.mOnDismissListener = onDismissListener
        return this
    }

    fun getOnBeforeShowCallback(): IBeforeShowListener {
        return _popwinKHelper.mOnBeforeShowCallback
    }

    */
/**
     *
     *
     * 当您设置了OnBeforeShowCallback监听之后，在您调用｛
     *
     *  * [.showPopupWindow]
     *  * [.showPopupWindow]
     *  * [.showPopupWindow]
     * ｝
     * <br></br>
     * 任意一个方法，在show之前回回调到该监听器。
     * <br></br>
     *
     *
     * @param mOnBeforeShowCallback
     * @return
     *//*

    fun setOnBeforeShowCallback(mOnBeforeShowCallback: IBeforeShowListener): BasePopupWindow? {
        _popwinKHelper.mOnBeforeShowCallback = mOnBeforeShowCallback
        return this
    }

    */
/**
     *
     *
     * 设置展示PopupWindow的动画，详情参考[.onCreateShowAnimation]
     *
     *
     * @param showAnimation 展示动画
     *//*

    fun setShowAnimation(showAnimation: Animation?): BasePopupWindow? {
        _popwinKHelper.setShowAnimation(showAnimation)
        return this
    }

    fun getShowAnimation(): Animation? {
        return _popwinKHelper.mShowAnimation
    }

    */
/**
     *
     *
     * 设置展示PopupWindow的动画，详情参考[.onCreateShowAnimator]
     *
     *
     * @param showAnimator 展示动画
     *//*

    fun setShowAnimator(showAnimator: Animator?): BasePopupWindow? {
        _popwinKHelper.setShowAnimator(showAnimator)
        return this
    }

    fun getShowAnimator(): Animator? {
        return _popwinKHelper.mShowAnimator
    }

    */
/**
     *
     *
     * 设置退出PopupWindow的动画，详情参考[.onCreateDismissAnimation]
     *
     *
     * @param dismissAnimation 退出动画
     *//*

    fun setDismissAnimation(dismissAnimation: Animation?): BasePopupWindow? {
        _popwinKHelper.setDismissAnimation(dismissAnimation)
        return this
    }

    fun getDismissAnimation(): Animation? {
        return _popwinKHelper.mDismissAnimation
    }

    */
/**
     *
     *
     * 设置退出PopupWindow的动画，详情参考[.onCreateDismissAnimator]
     *
     *
     * @param dismissAnimator 退出动画
     *//*

    fun setDismissAnimator(dismissAnimator: Animator?): BasePopupWindow? {
        _popwinKHelper.setDismissAnimator(dismissAnimator)
        return this
    }

    fun getDismissAnimator(): Animator? {
        return _popwinKHelper.mDismissAnimator
    }


    */
/**
     *
     *
     * 获取PopupWindow的根布局
     *
     *
     * @see .onCreateContentView
     *//*

    fun getContentView(): View? {
        return _contentView
    }

    */
/**
     *
     *
     * 获取PopupWindow执行动画的View
     *
     * <br></br>
     * 如果[.onCreateAnimateView]返回为空，则返回contentView（[.onCreateContentView]）
     *//*

    fun getDisplayAnimateView(): View? {
        return mDisplayAnimateView
    }

    */
/**
     * 获取PopupWindow实例
     *
     * @return
     *//*

    fun getPopupWindow(): PopupWindow? {
        return _popupWindowProxy
    }

    fun getOffsetX(): Int {
        return _popwinKHelper.getOffsetX()
    }

    fun getMaskOffsetX(): Int {
        return _popwinKHelper.maskOffsetX
    }

    */
/**
     * 设定x位置的偏移量(中心点在popup的左上角)
     *
     *
     *
     * @param offsetX
     *//*

    fun setOffsetX(offsetX: Int): BasePopupWindow? {
        _popwinKHelper.offsetX = offsetX
        return this
    }

    */
/**
     * 设定蒙层x位置的偏移量
     *
     *
     *
     * @param offsetX
     *//*

    fun setMaskOffsetX(offsetX: Int): BasePopupWindow? {
        _popwinKHelper.maskOffsetX = offsetX
        return this
    }

    fun getOffsetY(): Int {
        return _popwinKHelper.getOffsetY()
    }

    fun getMaskOffsetY(): Int {
        return _popwinKHelper.maskOffsetY
    }

    */
/**
     * 设定y位置的偏移量(中心点在popup的左上角)
     *
     * @param offsetY
     *//*

    fun setOffsetY(offsetY: Int): BasePopupWindow? {
        _popwinKHelper.offsetY = offsetY
        return this
    }

    */
/**
     * 设定蒙层y位置的偏移量
     *
     *
     *
     * @param offsetY
     *//*

    fun setMaskOffsetY(offsetY: Int): BasePopupWindow? {
        _popwinKHelper.maskOffsetY = offsetY
        return this
    }

    fun getPopupGravity(): Int {
        return _popwinKHelper.getPopupGravity()
    }

    */
/**
     *
     *
     * 设置参考方向  [Gravity]
     * <br></br>
     *
     *  *  不跟anchorView联系的情况下，gravity意味着在整个view中的方位[.showPopupWindow]
     *  *  如果跟anchorView联系，gravity意味着以anchorView为中心的方位[.showPopupWindow]
     *
     *
     * @param popupGravity
     *//*

    fun setPopupGravity(popupGravity: Int): BasePopupWindow? {
        _popwinKHelper.popupGravity = popupGravity
        return this
    }

    */
/**
     *
     *
     * 设置参考方向 [Gravity]
     * <br></br>
     *
     *  *  不跟anchorView联系的情况下，gravity意味着在整个view中的方位[.showPopupWindow]
     *  *  如果跟anchorView联系，gravity意味着以anchorView为中心的方位[.showPopupWindow]
     *
     *
     * @param mode  * GravityMode.RELATIVE_TO_ANCHOR：该模式将会以Anchor作为参考点，表示Popup处于该Anchor的哪个位置
     *  * GravityMode.ALIGN_TO_ANCHOR_SIDE：该模式将会以Anchor作为参考点，表示Popup对齐Anchor的哪条边
     *
     *//*

    fun setPopupGravity(mode: EGravityMode, popupGravity: Int): BasePopupWindow? {
        _popwinKHelper.setPopupGravity(mode, popupGravity)
        return this
    }

    */
/**
     * 设置参考模式 [Gravity]
     *
     * @param mode  * GravityMode.RELATIVE_TO_ANCHOR：该模式将会以Anchor作为参考点，表示Popup处于该Anchor的哪个位置
     *  * GravityMode.ALIGN_TO_ANCHOR_SIDE：该模式将会以Anchor作为参考点，表示Popup对齐Anchor的哪条边
     *
     *//*

    fun setPopupGravityMode(mode: EGravityMode?): BasePopupWindow? {
        _popwinKHelper.setPopupGravityMode(mode, mode)
        return this
    }

    */
/**
     * 分别设置垂直方向与水平方向参考模式 [Gravity]
     *
     * @param mode  * GravityMode.RELATIVE_TO_ANCHOR：该模式将会以Anchor作为参考点，表示Popup处于该Anchor的哪个位置
     *  * GravityMode.ALIGN_TO_ANCHOR_SIDE：该模式将会以Anchor作为参考点，表示Popup对齐Anchor的哪条边
     *
     *//*

    fun setPopupGravityMode(horizontalMode: EGravityMode?, verticalMode: EGravityMode?): BasePopupWindow? {
        _popwinKHelper.setPopupGravityMode(horizontalMode, verticalMode)
        return this
    }

    fun isAutoMirror(): Boolean {
        return _popwinKHelper.isAutoMirror()
    }

    */
/**
     *
     *
     * 自动镜像定位
     * <br></br>
     * 如果当前屏幕不足以完整显示您的PopupWindow，则PopupWindow会自行布置在其镜像位置。
     * <br></br>
     * <br></br>
     * <pre>
     * 比如当前PopupWindow显示在某个View的下方，而屏幕下方不够位置展示完整改PopupWindow，
     * 当本设置为true，PopupWindow将会显示在原来的View的上方以满足完整显示PopupWindow的情况。
    </pre> *
     * <br></br>
     * <br></br>
     * **如果您配置了[.setOffsetY]，则对应的偏移量也是在其适配后的位置生效**
     *
     *
     * @param enable 是否开启
     *//*

    fun setAutoMirrorEnable(enable: Boolean): BasePopupWindow? {
        _popwinKHelper.setFlag(AUTO_MIRROR, enable)
        _popwinKHelper.cacheFlag(FITSIZE, true)
        if (enable) {
            setFitSize(false)
        } else {
            setFitSize(_popwinKHelper.restoreFlag(FITSIZE, true))
        }
        return this
    }

    */
/**
     *
     *
     * 获取PoupWindow的高度，如果PopupWindow没显示，则返回0
     * <br></br>
     *
     *
     * @see .preMeasurePopupView
     *//*

    fun getHeight(): Int {
        return if (_contentView == null) 0 else _contentView!!.height
    }

    */
/**
     *
     *
     * 获取PoupWindow的宽度，如果PopupWindow没显示，则返回0
     * <br></br>
     *
     *
     * @see .preMeasurePopupView
     *//*

    fun getWidth(): Int {
        return if (_contentView == null) 0 else _contentView!!.width
    }

    */
/**
     * 预测量，测量结果未必准确~
     *//*

    fun preMeasure(width: Int, height: Int) {
        _popwinKHelper.preMeasurePopupView(_contentView, width, height)
    }

    */
/**
     * 获取预测量的宽度，需要先执行[.preMeasure]
     *//*

    fun getPreMeasureWidth(): Int {
        return _popwinKHelper.preMeasureWidth
    }

    */
/**
     * 获取预测量的高度，需要先执行[.preMeasure]
     *//*

    fun getPreMeasureHeight(): Int {
        return _popwinKHelper.preMeasureHeight
    }


    */
/**
     *
     *
     * 是否允许点击PopupWindow外部时触发dismiss
     *
     * <br></br>
     * dismiss popup when touch outside from popup
     *
     * @param outSideDismiss true for allow
     *//*

    fun setOutSideDismiss(outSideDismiss: Boolean): BasePopupWindow? {
        _popwinKHelper.setFlag(OUT_SIDE_DISMISS, outSideDismiss)
        return this
    }

    */
/**
     *
     *
     * 是否允许PopupWindow外部响应事件
     * <br></br>
     * <br></br>
     * 如果允许拦截事件，则PopupWindow外部无法响应事件。
     *
     *
     * @param touchable
     *  * ture:外部可点击
     *  * false：外部不可点击
     *
     *//*

    fun setOutSideTouchable(touchable: Boolean): BasePopupWindow? {
        _popwinKHelper.setFlag(OUT_SIDE_TOUCHABLE, touchable)
        return this
    }

    */
/**
     * 该参数决定popupWindow是否被限制在绘制边界
     *
     *
     * <br></br>
     *
     *  * true：PopupWindow将会被限制边界，其动画不可突破其边界
     *  * false：PopupWindow将不会被限制绘制边界，其动画可突破其边界
     *
     *
     * @param clipChildren 默认为true
     *//*

    fun setClipChildren(clipChildren: Boolean): BasePopupWindow? {
        _popwinKHelper.setFlag(CLIP_CHILDREN, clipChildren)
        return this
    }

    fun isAllowDismissWhenTouchOutside(): Boolean {
        return _popwinKHelper.isOutSideDismiss()
    }


    */
/**
     * 外部是否可以点击
     *//*

    fun isOutSideTouchable(): Boolean {
        return _popwinKHelper.isOutSideTouchable()
    }

    */
/**
     *
     *
     * 设置PopupWindow的背景是否对齐到PopupWindow。
     * <br></br>
     * <br></br>
     * 默认情况下，PopupWindow背景都是铺满整个屏幕的。
     * 但在某些情况下您可能在PopupWindow之上不需要展示背景，这时候您可以调用这个方法来强制Background对齐到PopupWindow的顶部。
     *
     *
     * @param isAlignBackground 是否对齐背景
     *//*

    fun setAlignBackground(isAlignBackground: Boolean): BasePopupWindow? {
        _popwinKHelper.setAlignBackgound(isAlignBackground)
        return this
    }

    */
/**
     *
     *
     * 设置PopupWindow的背景对齐PopupWindow的方式，请传入[Gravity]中的值
     * <br></br>
     *
     * @param gravity 请传入[Gravity]中的值，传入[Gravity.NO_GRAVITY]则意味着不对齐
     *//*

    fun setAlignBackgroundGravity(gravity: Int): BasePopupWindow? {
        _popwinKHelper.setAlignBackgroundGravity(gravity)
        return this
    }

    */
/**
     *
     *
     * 允许PopupWindow跟某个anchorView关联，其位置，可视性将会跟anchorView同步>
     * <br></br>
     * **WARN：非常不建议在anchorView频繁变化的情况下使用背景模糊，这会导致较大的性能消耗。**
     *//*

    fun linkTo(anchorView: View?): BasePopupWindow? {
        _popwinKHelper.linkTo(anchorView)
        return this
    }

    fun setWidth(width: Int): BasePopupWindow {
        _popwinKHelper.setPopupViewWidth(width)
        return this
    }

    fun setHeight(height: Int): BasePopupWindow? {
        _popwinKHelper.setPopupViewHeight(height)
        return this
    }

    */
/**
     * 设置BasePopup最大宽度
     *//*

    fun setMaxWidth(maxWidth: Int): BasePopupWindow? {
        _popwinKHelper.maxWidth = maxWidth
        return this
    }

    */
/**
     * 设置BasePopup最大高度
     *//*

    fun setMaxHeight(maxHeight: Int): BasePopupWindow? {
        _popwinKHelper.maxHeight = maxHeight
        return this
    }

    */
/**
     * 设置BasePopup最小宽度
     *//*

    fun setMinWidth(minWidth: Int): BasePopupWindow? {
        _popwinKHelper.minWidth = minWidth
        return this
    }

    */
/**
     * 设置BasePopup最小高度
     *//*

    fun setMinHeight(minHeight: Int): BasePopupWindow? {
        _popwinKHelper.minHeight = minHeight
        return this
    }

    */
/**
     * 是否允许BasePopup自动调整大小
     *//*

    fun setFitSize(canResize: Boolean): BasePopupWindow? {
        _popwinKHelper.setFlag(FITSIZE, canResize)
        return this
    }

    */
/**
     * 设置背景蒙层显示的动画，如果为空，则蒙层不显示动画，直接弹出
     *//*

    fun setMaskViewShowAnimation(animation: Animation): BasePopupWindow? {
        _popwinKHelper.mMaskViewShowAnimation = animation
        _popwinKHelper.isDefaultMaskViewShowAnimation = false
        return this
    }

    */
/**
     * 设置背景蒙层退出的动画，如果为空，则蒙层不显示动画，直接消失
     *//*

    fun setMaskViewDismissAnimation(animation: Animation): BasePopupWindow? {
        _popwinKHelper.mMaskViewDismissAnimation = animation
        _popwinKHelper.isDefaultMaskViewDismissAnimation = false
        return this
    }

    fun syncMaskAnimationDuration(sync: Boolean): BasePopupWindow? {
        _popwinKHelper.setFlag(BasePopupFlag.SYNC_MASK_ANIMATION_DURATION, sync)
        return this
    }

    */
/**
     * 设置键盘监听回调
     *//*

    fun setOnKeyboardChangeListener(listener: KeyboardUtils.OnKeyboardChangeListener): BasePopupWindow? {
        _popwinKHelper.mUserKeyboardStateChangeListener = listener
        return this
    }

    */
/**
     * 设置key event
     *//*

    fun setKeyEventListener(keyEventListener: IKeyEventListener): BasePopupWindow? {
        _popwinKHelper.mKeyEventListener = keyEventListener
        return this
    }

    */
/**
     * basepopup宽度跟随anchorview
     *//*

    fun setWidthAsAnchorView(widthAsAnchor: Boolean): BasePopupWindow? {
        _popwinKHelper.setFlag(BasePopupFlag.AS_WIDTH_AS_ANCHOR, widthAsAnchor)
        return this
    }

    */
/**
     * basepopup高度跟随anchorView
     *//*

    fun setHeightAsAnchorView(heightAsAnchor: Boolean): BasePopupWindow? {
        _popwinKHelper.setFlag(BasePopupFlag.AS_HEIGHT_AS_ANCHOR, heightAsAnchor)
        return this
    }

    */
/**
     * 设置layoutdirection
     *//*

    fun setLayoutDirection(layoutDirection: Int): BasePopupWindow? {
        _popwinKHelper.layoutDirection = layoutDirection
        return this
    }

    */
/**
     * 设置是否允Popup是否响应事件，如果不响应，则事件会穿透Popup
     *//*

    fun setTouchable(touchable: Boolean): BasePopupWindow? {
        _popwinKHelper.setFlag(BasePopupFlag.TOUCHABLE, touchable)
        if (isShowing()) {
            (getPopupWindow() as PopupWindowProxy?).updateFlag(
                if (touchable) MODE_REMOVE else MODE_ADD,
                true,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
            )
        }
        return this
    }

    fun isTouchable(): Boolean {
        return (_popwinKHelper.flag and BasePopupFlag.TOUCHABLE) !== 0
    }

    */
/**
     * （手动）更新键盘对齐
     *//*

    fun updateKeyboardAlign(): BasePopupWindow? {
        val msg = Message.obtain()
        msg.what = BasePopupEvent.EVENT_ALIGN_KEYBOARD
        _popwinKHelper.sendEvent(msg)
        return this
    }

    */
/**
     * 设置弹窗层级优先级，优先级越高则越靠近屏幕
     *
     * @param priority 优先级
     *//*

    fun setPriority(priority: EPriority?): BasePopupWindow? {
        _popwinKHelper.priority = priority ?: EPriority.NORMAL
        return this
    }

    //region ------------------------------------------状态控制-----------------------------------------------
    fun superDismiss() {
        try {
            _popupWindowProxy.superDismiss()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            _popwinKHelper.onDismiss()
        }
    }

    */
/**
     * 取消一个PopupWindow，如果有退出动画，PopupWindow的消失将会在动画结束后执行
     *//*

    fun dismiss() {
        dismiss(true)
    }

    */
/**
     * 取消一个PopupWindow，如果有退出动画，PopupWindow的消失将会在动画结束后执行
     *
     * @param animateDismiss 传入为true，则执行退出动画后dismiss（如果有的话）
     *//*

    fun dismiss(animateDismiss: Boolean) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw CalledFromWrongThreadException(PopupUtils.getString(R.string.basepopup_error_thread))
        }
        if (!isShowing() || _contentView == null) return
        _popwinKHelper.dismiss(animateDismiss)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        isDestroyed = true
        onLogInternal("onDestroy")
        _popwinKHelper.forceDismiss()
        if (_popupWindowProxy != null) {
            _popupWindowProxy.clear(true)
        }
        if (_popwinKHelper != null) {
            _popwinKHelper.clear(true)
        }
        _initRunnable = null
        _anchorLifecycleOwner = null
        mAnchorDecorView = null
        _popupWindowProxy = null
        mDisplayAnimateView = null
        _contentView = null
        _context = null
    }

    private fun checkPerformShow(v: View?): Boolean {
        var result = true
        if (_popwinKHelper.mOnBeforeShowCallback != null) {
            result = _popwinKHelper.mOnBeforeShowCallback.onBeforeShow(
                _contentView, v,
                _popwinKHelper.mShowAnimation != null || _popwinKHelper.mShowAnimator != null
            )
        }
        return result
    }

    */
/**
     * 捕捉keyevent
     *
     * @param event
     * @return true意味着你已经处理消耗了事件，后续不再传递
     *//*

    fun onDispatchKeyEvent(event: KeyEvent?): Boolean {
        return false
    }

    */
/**
     * 捕捉interceptTouchEvent
     *
     * @param event
     * @return true意味着你已经处理消耗了事件，后续不再传递
     *//*

    fun onInterceptTouchEvent(event: MotionEvent?): Boolean {
        return false
    }

    */
/**
     * 捕捉touchevent
     *
     * @param event
     * @return true意味着你已经处理消耗了事件，后续不再传递
     *//*

    fun onTouchEvent(event: MotionEvent?): Boolean {
        return false
    }

    */
/**
     * 捕捉返回键事件
     *
     * @return true意味着你已经处理消耗了事件，后续不再传递
     *//*

    fun onBackPressed(): Boolean {
        if (_popwinKHelper.isBackPressEnable()) {
            dismiss()
            return true
        }
        return false
    }

    */
/**
     * PopupWindow ContentView外的事件点击回调
     *
     * @param touchInBackground 是否在背景中触摸
     * @param isPressed         是否点击事件
     * @return 返回True则意味着您消耗了该事件，该事件不再会被分发下去
     *//*

    fun onOutSideTouch(event: MotionEvent, touchInBackground: Boolean, isPressed: Boolean): Boolean {
        if (_popwinKHelper.isOutSideDismiss() && (event.action == MotionEvent.ACTION_UP) && isPressed) {
            dismiss()
            return true
        }
        return false
    }

    fun onWindowFocusChanged(popupDecorViewProxy: View?, hasWindowFocus: Boolean) {}
    //endregion

    //region ------------------------------------------callback-----------------------------------------------
    */
/**
     * 在anchorView上方显示，autoLocatePopup为true时适用
     *//*

    fun onAnchorTop() {}

    */
/**
     * 在anchorView下方显示，autoLocatePopup为true时适用
     *//*

    fun onAnchorBottom() {}

    fun onDismiss() {
        if (_popwinKHelper.mOnDismissListener != null) {
            _popwinKHelper.mOnDismissListener.onDismiss()
        }
        isExitAnimatePlaying = false
    }

    */
/**
     * 在PopupWindow显示后回调该方法
     *//*

    fun onShowing() {}

    */
/**
     * 返回BasePopup布局中的位置信息
     *//*

    fun onPopupLayout(popupRect: Rect, anchorRect: Rect) {}

    fun computeGravity(popupRect: Rect, anchorRect: Rect): Int {
        return PopupUiUtils.computeGravity(popupRect, anchorRect)
    }

    */
/**
     * BasePopup大小的时候回调
     *//*

    fun onSizeChange(oldW: Int, oldH: Int, newW: Int, newH: Int) {}

    //endregion

    //region ------------------------------------------tools-----------------------------------------------
    protected fun dipToPx(dip: Float): Float {
        return dip * getContextOrApplicationContext(true)!!.resources.displayMetrics.density + 0.5f
    }

    fun setDebugMode(debugMode: Boolean) {
        PopupLog.setOpenLog(debugMode)
    }

    */
/**
     * 日志输出口
     *//*

    protected fun onLogInternal(msg: String?) {
        PopupLog.d(TAG, msg)
    }

    private fun ownerParentLog(): String? {
        return PopupUtils.getString(R.string.basepopup_host, _anchorLifecycleOwner.toString())
    }
//endregion

    class CalledFromWrongThreadException(msg: String?) : AndroidRuntimeException(msg)
}*/
