package com.mozhimen.basick.elemk.cons

import android.view.View

/**
 * @ClassName CParameter
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/6/20 18:56
 * @Version 1.0
 */
object CParameter {
    const val UTILK_SNACK_BAR_MAX_LINES = 50//能显示的最多行数
    const val UTILK_INPUT_CHANGE_TAG_ON_GLOBAL_LAYOUT_LISTENER = -8
    const val UTILK_LOG_PRO_MAX_LOG_MSG_LENGTH = 4000//logcat最大长度为4*1024，此处取4000
    const val UTILK_LOG_PRO_SUPPORT_LONG_LOG = true//超长Log
    const val UTILK_SCREEN_FULL_SCREEN_FLAG = (
            CView.FLAG_LAYOUT_STABLE
                    or CView.FLAG_LAYOUT_HIDE_NAVIGATION
                    or CView.FLAG_HIDE_NAVIGATION
                    or CView.FLAG_IMMERSIVE_STICKY
                    or CView.FLAG_LAYOUT_FULLSCREEN
                    or CView.FLAG_FULLSCREEN
            )
}