package com.mozhimen.uicorek.popwink.bases.commons

import android.widget.PopupWindow


/**
 * @ClassName OnDismissCallback
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/11/17 13:00
 * @Version 1.0
 */
abstract class OnDismissCallback : PopupWindow.OnDismissListener {
    /**
     *
     *
     * 在PopupWindow消失之前，如果您设置好了该监听器[.setOnDismissListener]
     * 那么dismiss之前将会回调到本方法，在这里您可以进一步决定是否可以继续取消PopupWindow
     *
     *
     * @return
     *  * 【true】：继续取消PopupWindow
     *  * 【false】：不允许取消PopupWindow
     *
     */
    fun onBeforeDismiss(): Boolean {
        return true
    }

    /**
     *
     *
     * 在PopupWindow消失之前，如果您设置好了该监听器[.setOnDismissListener]
     * 如果有退出动画，则在退出动画播放的时候会回调该方法
     *
     */
    fun onDismissAnimationStart() {}
}