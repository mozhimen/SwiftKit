package com.mozhimen.componentk.animk

import android.util.SparseArray
import android.view.View
import androidx.core.util.forEach
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.mozhimen.basick.utilk.UtilKAnim

/**
 * @ClassName AnimK
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/9/24 23:58
 * @Version 1.0
 */
class AnimK(lifecycleOwner: LifecycleOwner) : DefaultLifecycleObserver {
    private val _views = SparseArray<View>()

    init {
        lifecycleOwner.lifecycle.addObserver(this)
    }

    override fun onPause(owner: LifecycleOwner) {
        _views.forEach { key, value ->

        }
        _views.clear()
        owner.lifecycle.removeObserver(this)
        super.onPause(owner)
    }
}