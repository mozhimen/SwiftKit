package com.mozhimen.componentk.navigatek.annors

import androidx.annotation.IntDef

@IntDef(value = [NavigateKType.ACTIVITY, NavigateKType.FRAGMENT, NavigateKType.DIALOG])
annotation class NavigateKType {
    companion object {
        const val ACTIVITY = 0
        const val FRAGMENT = 1
        const val DIALOG = 2
    }
}
