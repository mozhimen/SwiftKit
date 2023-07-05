package com.mozhimen.uicorek.dialogk.bases.commons

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import com.mozhimen.basick.utilk.bases.IUtilK
import com.mozhimen.uicorek.dialogk.bases.annors.ADialogMode

/**
 * @ClassName IBaseDialogK
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/6/2 15:38
 * @Version 1.0
 */
interface IBaseDialogK<I : IDialogKClickListener> : IUtilK {
    fun getDialogClickListener(): I?

    /**
     * 弹框模式
     * @return Int
     */
    @ADialogMode
    fun getDialogMode(): Int

    fun setDialogClickListener(listener: I): IBaseDialogK<*>

    /**
     * 设置dialog的模式, 设置后会回调到[.onInitMode]
     * @param mode
     */
    fun setDialogMode(@ADialogMode mode: Int): IBaseDialogK<*>

    /**
     * 设置dialog的模式
     * 设置后会回调到[.onInitMode]
     * @param mode
     * @param callModeChange false 禁止回调[.onInitMode]
     */
    fun setDialogMode(@ADialogMode mode: Int, callModeChange: Boolean): IBaseDialogK<*>

    /**
     * 设置可取消
     * @param flag Boolean
     * @return BaseDialogK<*>
     */
    fun setDialogCancelable(flag: Boolean): IBaseDialogK<*>

    /**
     * 延迟显示
     * @param delayMillis Long
     */
    fun showByDelay(delayMillis: Long)

    /**
     * 不依附Activity来show，比如在service里面
     * 此举将会把dialog的window level提升为system
     * 需要权限
     * <h3>uses-permission Android:name="android.permission.SYSTEM_ALERT_WINDOW"
    </h3> */
    fun showInSystemWindow()

    //////////////////////////////////////////////////////////////////////////////
    //callback
    //////////////////////////////////////////////////////////////////////////////

    /**
     * 初始化window宽度
     * 默认屏幕宽度左右间距25dp
     * (getCurrentScreenWidth() * 0.8f).roundToInt()
     * @return
     */
    fun onInitWindowWidth(): Int

    /**
     * 初始化window高度
     * 默认wrap_content
     * @return
     */
    fun onInitWindowHeight(): Int

    /**
     * 初始化window的gravity
     * @return 默认返回 Gravity.CENTER
     * @see Gravity
     */
    fun onInitWindowGravity(): Int

    /**
     * 创建View
     * @param inflater LayoutInflater
     * @return View?
     */
    fun onCreateView(inflater: LayoutInflater): View?

    /**
     * 销毁View
     */
    fun onDestroyView()

    /**
     * view创建
     * @param view View
     */
    fun onViewCreated(view: View)

    /**
     * 初始化Mode
     * @param mode Int
     */
    fun onInitMode(@ADialogMode mode: Int) {}
}