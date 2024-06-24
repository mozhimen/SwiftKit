package com.mozhimen.basick.elemk.android.view.cons

import android.view.Window
import androidx.annotation.RequiresApi
import com.mozhimen.basick.elemk.android.os.cons.CVersCode

/**
 * @ClassName CWindow
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Version 1.0
 */
object CWindow {
    const val ID_ANDROID_CONTENT = Window.ID_ANDROID_CONTENT
    const val FEATURE_NO_TITLE = Window.FEATURE_NO_TITLE

    @RequiresApi(CVersCode.V_21_5_L)
    const val FEATURE_CONTENT_TRANSITIONS = Window.FEATURE_CONTENT_TRANSITIONS

    object Feature {
        const val OPTIONS_PANEL = Window.FEATURE_OPTIONS_PANEL
        const val NO_TITLE = Window.FEATURE_NO_TITLE
        const val PROGRESS = Window.FEATURE_PROGRESS
        const val LEFT_ICON = Window.FEATURE_LEFT_ICON
        const val RIGHT_ICON = Window.FEATURE_RIGHT_ICON
        const val INDETERMINATE_PROGRESS = Window.FEATURE_INDETERMINATE_PROGRESS
        const val CONTEXT_MENU = Window.FEATURE_CONTEXT_MENU
        const val CUSTOM_TITLE = Window.FEATURE_CUSTOM_TITLE
        const val ACTION_BAR = Window.FEATURE_ACTION_BAR
        const val ACTION_BAR_OVERLAY = Window.FEATURE_ACTION_BAR_OVERLAY
        const val ACTION_MODE_OVERLAY = Window.FEATURE_ACTION_MODE_OVERLAY

        @RequiresApi(CVersCode.V_21_5_L)
        const val CONTENT_TRANSITIONS = Window.FEATURE_CONTENT_TRANSITIONS

        @RequiresApi(CVersCode.V_21_5_L)
        const val ACTIVITY_TRANSITIONS = Window.FEATURE_ACTIVITY_TRANSITIONS
    }
}