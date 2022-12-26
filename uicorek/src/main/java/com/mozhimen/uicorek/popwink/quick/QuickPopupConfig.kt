package com.mozhimen.uicorek.popwink.quick

import android.animation.Animator
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.Pair
import android.view.View
import android.view.animation.Animation
import com.mozhimen.basick.animk.builder.AnimKBuilder.asAnimation
import com.mozhimen.basick.animk.builder.temps.ScaleType.Companion.CENTER_HIDE
import com.mozhimen.basick.animk.builder.temps.ScaleType.Companion.CENTER_SHOW
import com.mozhimen.basick.utilk.UtilKClazz
import com.mozhimen.basick.utilk.UtilKKeyBoard.IUtilKKeyboardListener
import com.mozhimen.basick.utilk.bitmap.blur.UtilKBitmapBlurOption
import com.mozhimen.basick.utilk.log.UtilKSmartLog
import com.mozhimen.uicorek.popwink.basepopwin.basepopup.BasePopupWindow
import com.mozhimen.uicorek.popwink.basepopwin.basepopup.BasePopupWindow.KeyEventListener
import com.mozhimen.uicorek.popwink.basepopwin.basepopup.BasePopupWindow.OnBlurOptionInitListener
import com.mozhimen.uicorek.popwink.bases.commons.IClearMemoryObjectListener
import com.mozhimen.uicorek.popwink.bases.cons.CFlag
import java.lang.reflect.Method

/**
 * @ClassName QuickPopupConfig
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/28 22:23
 * @Version 1.0
 */
class QuickPopupConfig : IClearMemoryObjectListener {

    companion object {

        @JvmStatic
        fun generateDefault(): QuickPopupConfig {
            return QuickPopupConfig()
                .setShowAnimation(asAnimation().add(CENTER_SHOW).build())
                .setDismissAnimation(asAnimation().add(CENTER_HIDE).build())
                .setFadeInAndOut(Build.VERSION.SDK_INT != Build.VERSION_CODES.M)
        }
    }

    private val _invokeMap: MutableMap<String, Method> = HashMap()
    private var _listenersHolderMap: HashMap<Int, Pair<View.OnClickListener, Boolean>>? = null
    private var _invokeParams: MutableMap<String, Any> = HashMap()

    @Volatile
    private var _destroyed = false
    private var _flag = CFlag.IDLE
    private var _contentViewLayoutId = 0
    private var _onBlurOptionInitListener: OnBlurOptionInitListener? = null
    private var _bitmapBlurOption: UtilKBitmapBlurOption? = null

    init {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.M) {
            _flag = _flag and CFlag.FADE_ENABLE.inv()
        }
    }

    fun getMethod(name: String): Method? {
        return if (_invokeMap.containsKey(name)) _invokeMap[name] else null
    }

    fun getListenersHolderMap(): HashMap<Int, Pair<View.OnClickListener, Boolean>>? {
        return _listenersHolderMap
    }

    fun getInvokeParams(): MutableMap<String, Any> {
        return _invokeParams
    }

    fun isDestroyed(): Boolean {
        return _destroyed
    }

    fun getFlag(): Int {
        return _flag
    }

    fun getContentViewLayoutId(): Int {
        return _contentViewLayoutId
    }

    fun getOnBlurOptionInitListener(): OnBlurOptionInitListener? {
        return _onBlurOptionInitListener
    }

    fun getPopupBlurOption(): UtilKBitmapBlurOption? {
        return _bitmapBlurOption
    }

    fun setShowAnimation(showAnimation: Animation): QuickPopupConfig {
        set("setShowAnimation", showAnimation)
        return this
    }

    fun setDismissAnimation(dismissAnimation: Animation): QuickPopupConfig {
        set("setDismissAnimation", dismissAnimation)
        return this
    }

    fun setShowAnimator(showAnimator: Animator): QuickPopupConfig {
        set("setShowAnimator", showAnimator)
        return this
    }

    fun setDismissAnimator(dismissAnimator: Animator): QuickPopupConfig {
        set("setDismissAnimator", dismissAnimator)
        return this
    }

    fun setOnDismissListener(dismissListener: BasePopupWindow.OnDismissListener): QuickPopupConfig {
        set("setOnDismissListener", dismissListener)
        return this
    }

    fun setBlurBackground(blurBackground: Boolean): QuickPopupConfig {
        return setBlurBackground(blurBackground, null)
    }

    fun setBlurBackground(blurBackground: Boolean, mInitListener: OnBlurOptionInitListener?): QuickPopupConfig {
        setFlag(CFlag.BLUR_BACKGROUND, blurBackground)
        _onBlurOptionInitListener = mInitListener
        return this
    }

    fun setBlurOption(bitmapBlurOption: UtilKBitmapBlurOption): QuickPopupConfig {
        _bitmapBlurOption = bitmapBlurOption
        return this
    }

    fun setOnClickListener(viewId: Int, listener: View.OnClickListener): QuickPopupConfig {
        return setOnClickListener(viewId, listener, false)
    }

    fun setOnClickListener(viewId: Int, listener: View.OnClickListener, dismissWhenClick: Boolean): QuickPopupConfig {
        if (_listenersHolderMap == null) {
            _listenersHolderMap = HashMap()
        }
        _listenersHolderMap!![viewId] = Pair.create(listener, dismissWhenClick)
        return this
    }

    fun setFadeInAndOut(fadeEnable: Boolean): QuickPopupConfig {
        setFlag(CFlag.FADE_ENABLE, fadeEnable)
        return this
    }

    fun setOffsetX(offsetX: Int): QuickPopupConfig {
        set("setOffsetX", offsetX)
        return this
    }

    fun setMaskOffsetX(offsetX: Int): QuickPopupConfig {
        set("setMaskOffsetX", offsetX)
        return this
    }


    fun setOffsetY(offsetY: Int): QuickPopupConfig {
        set("setOffsetY", offsetY)
        return this
    }

    fun setMaskOffsetY(offsetY: Int): QuickPopupConfig {
        set("setMaskOffsetY", offsetY)
        return this
    }

    fun setOverlayStatusbarMode(mode: Int): QuickPopupConfig {
        set("setOverlayStatusbarMode", mode)
        return this
    }

    fun setOverlayNavigationBarMode(mode: Int): QuickPopupConfig {
        set("setOverlayNavigationBarMode", mode)
        return this
    }

    fun setOverlayStatusbar(overlay: Boolean): QuickPopupConfig {
        set("setOverlayStatusbar", overlay)
        return this
    }

    fun setOverlayNavigationBar(overlay: Boolean): QuickPopupConfig {
        set("setOverlayNavigationBar", overlay)
        return this
    }

    fun setAlignBackground(alignBackground: Boolean): QuickPopupConfig {
        set("setAlignBackground", alignBackground)
        return this
    }

    fun setAlignBackgroundGravity(gravity: Int): QuickPopupConfig {
        set("setAlignBackgroundGravity", gravity)
        return this
    }

    fun setAutoMirrorEnable(autoMirrorEnable: Boolean): QuickPopupConfig {
        set("setAutoMirrorEnable", autoMirrorEnable)
        return this
    }

    fun setBackground(background: Drawable): QuickPopupConfig {
        set("setBackground", background)
        return this
    }

    fun setBackgroundColor(color: Int): QuickPopupConfig {
        return setBackground(ColorDrawable(color))
    }

    fun setPopupGravity(gravity: Int): QuickPopupConfig {
        set("setPopupGravity", gravity)
        return this
    }

    fun setClipChildren(clipChildren: Boolean): QuickPopupConfig {
        set("setClipChildren", clipChildren)
        return this
    }

    fun setOutSideTouchable(outSideTouchable: Boolean): QuickPopupConfig {
        set("setOutSideTouchable", outSideTouchable)
        return this
    }

    fun setLinkTo(linkedView: View): QuickPopupConfig {
        set("setLinkTo", linkedView)
        return this
    }

    fun setContentViewLayoutId(contentViewLayoutId: Int): QuickPopupConfig {
        this._contentViewLayoutId = contentViewLayoutId
        return this
    }

    fun setMinWidth(minWidth: Int): QuickPopupConfig {
        set("setMinWidth", minWidth)
        return this
    }

    fun setMaxWidth(maxWidth: Int): QuickPopupConfig {
        set("setMaxWidth", maxWidth)
        return this
    }

    fun setMinHeight(minHeight: Int): QuickPopupConfig {
        set("setMinHeight", minHeight)
        return this
    }

    fun setMaxHeight(maxHeight: Int): QuickPopupConfig {
        set("setMaxHeight", maxHeight)
        return this
    }

    fun setBackPressEnable(enable: Boolean): QuickPopupConfig {
        set("setBackPressEnable", enable)
        return this
    }

    fun setOutSideDismiss(outsideDismiss: Boolean): QuickPopupConfig {
        set("setOutSideDismiss", outsideDismiss)
        return this
    }

    fun setKeyEventListener(keyEventListener: KeyEventListener): QuickPopupConfig {
        set("setKeyEventListener", keyEventListener)
        return this
    }

    fun setOnKeyboardChangeListener(listener: IUtilKKeyboardListener): QuickPopupConfig {
        set("setOnKeyboardChangeListener", listener)
        return this
    }

    override fun clear(destroy: Boolean) {
        _destroyed = true
        _bitmapBlurOption?.clear()
        _onBlurOptionInitListener = null
        _listenersHolderMap?.clear()
        _listenersHolderMap = null
        _invokeParams.clear()
    }

    private fun appendInvokeMap(name: String, paramClass: Class<*>?): Boolean {
        if (_invokeMap.containsKey(name)) return true
        val method = findMethod(name, paramClass)
        if (method != null) {
            _invokeMap[name] = method
            return true
        }
        return false
    }

    private fun findMethod(methodName: String, parameterTypes: Class<*>?): Method? {
        return try {
            QuickPopup::class.java.getMethod(methodName, parameterTypes)
        } catch (e: Exception) {
            UtilKSmartLog.e("not found", methodName, parameterTypes!!.name)
            null
        }
    }

    private fun set(name: String, obj: Any) {
        if (appendInvokeMap(name, UtilKClazz.obj2Clazz(obj))) {
            _invokeParams[name] = obj
        }
    }

    private fun setFlag(flag: Int, added: Boolean) {
        if (!added) {
            this._flag = this._flag and flag.inv()
        } else {
            this._flag = this._flag or flag
        }
    }
}