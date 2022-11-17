package com.mozhimen.uicorek.popwink.base.commons

import android.view.KeyEvent


/**
 * @ClassName IKeyEventListener
 * @Description key事件，如果返回true，则不再回调BasePopupWindow的onDispatchKeyEvent
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/11/17 13:05
 * @Version 1.0
 */
interface IKeyEventListener {
    fun onKeyDown(keyEvent: KeyEvent): Boolean
}