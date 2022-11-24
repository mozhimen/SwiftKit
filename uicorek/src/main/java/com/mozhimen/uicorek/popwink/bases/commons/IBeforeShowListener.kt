package com.mozhimen.uicorek.popwink.bases.commons

import android.view.View


/**
 * @ClassName IOnBeforeShowListener
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/11/17 12:53
 * @Version 1.0
 */
interface IBeforeShowListener {
    /**
     *
     *
     * 在PopupWindow展示出来之前，如果您设置好了该监听器[.setOnBeforeShowCallback]
     * 那么show之前将会回调到本方法，在这里您可以进一步决定是否可以展示PopupWindow
     *
     *
     * @param contentView    PopupWindow的ContentView
     * @param anchorView     锚点View
     * @param hasShowAnimate 是否有showAnimation
     * @return
     *  * 【true】：允许展示PopupWindow
     *  * 【false】：不允许展示PopupWindow
     *
     */
    fun onBeforeShow(contentView: View?, anchorView: View?, hasShowAnimate: Boolean): Boolean
}