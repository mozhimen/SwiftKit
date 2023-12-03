package com.mozhimen.uicorek.textk.edit.bar.bases

import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import java.io.Serializable

/**
 * @ClassName BaseEditBarHolder
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/12/3 15:56
 * @Version 1.0
 */

open class BaseEditBarHolder(
    @LayoutRes val layoutId: Int,
    @IdRes val viewIdSubmit: Int,
    @IdRes val viewIdCancel: Int?,
    @IdRes val editId: Int
) : Serializable
