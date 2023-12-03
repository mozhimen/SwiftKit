package com.mozhimen.uicorek.textk.edit.bar.helpers

import com.mozhimen.uicorek.R
import com.mozhimen.uicorek.textk.edit.bar.bases.BaseEditBarHolder

/**
 * @ClassName TextKEditBarHolder
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/12/3 15:50
 * @Version 1.0
 */

object TextKEditBarHolderHelper {
    val DEFAULT_LAYOUT: Int = R.layout.textk_edit_bar

    //    val DEFAULT_TITLE: Int = R.id.tv_title
    val DEFAULT_ID_CANCEL: Int = R.id.textk_edit_bar_btn_cancel
    val DEFAULT_ID_SEND: Int = R.id.textk_edit_bar_btn_send
    val DEFAULT_ID_EDIT: Int = R.id.textk_edit_bar_edit

    @JvmStatic
    fun createDefaultEditBarHolder(): BaseEditBarHolder =
        BaseEditBarHolder(DEFAULT_LAYOUT, DEFAULT_ID_SEND, DEFAULT_ID_CANCEL, DEFAULT_ID_EDIT)
}
