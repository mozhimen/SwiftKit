package com.mozhimen.componentk.animk

import android.util.SparseArray
import android.view.View
import androidx.core.util.forEach
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.mozhimen.basick.extsk.stopAnim
import com.mozhimen.basick.utilk.UtilKAnim

/**
 * @ClassName AnimK
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/9/24 23:58
 * @Version 1.0
 */
class AnimK(owner: LifecycleOwner) : DefaultLifecycleObserver {

    init {
        owner.lifecycle.addObserver(this)
    }

//    fun with(view: View): UtilKAnim {
//        _views.append(view.id, view)
//        return UtilKAnim
//    }
//
//    override fun onPause(owner: LifecycleOwner) {
//        _views.forEach { _, view ->
//            view.stopAnim()
//        }
//        _views.clear()
//        owner.lifecycle.removeObserver(this)
//        super.onPause(owner)
//    }
}