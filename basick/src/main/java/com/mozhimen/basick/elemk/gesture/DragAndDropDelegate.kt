package com.mozhimen.basick.elemk.gesture

import android.os.Build
import android.view.DragEvent
import android.view.View
import android.view.View.DragShadowBuilder
import androidx.lifecycle.LifecycleOwner
import com.mozhimen.basick.elemk.annors.ADescription
import com.mozhimen.basick.elemk.cons.CVersionCode
import com.mozhimen.basick.elemk.lifecycle.bases.BaseWakeBefPauseLifecycleObserver
import com.mozhimen.basick.utilk.exts.et
import com.mozhimen.basick.utilk.view.gesture.UtilKDragAndDrop

/**
 * @ClassName DragAndDropDelegate
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/2/8 17:25
 * @Version 1.0
 */
@ADescription("init by lazy")
class DragAndDropDelegate : BaseWakeBefPauseLifecycleObserver() {

    private val _viewList = ArrayList<Pair<View, View>>()
    fun dragAndDrop(sourceView: View, destView: View, onDrop: (sourceView: View, destView: View) -> Unit) {
        _viewList.add(sourceView to destView)
        sourceView.setOnLongClickListener {
            if (Build.VERSION.SDK_INT < CVersionCode.V_24_7_N) {
                //support pre-Nougat versions
                @Suppress("DEPRECATION")
                sourceView.startDrag(null, DragShadowBuilder(sourceView), sourceView, 0)
            } else {
                //supports Nougat and beyond
                sourceView.startDragAndDrop(null, DragShadowBuilder(sourceView), sourceView, 0)
            }
            //v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS, HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING)
            true
        }
        destView.setOnDragListener { view, event ->
            when (event.action) {
                DragEvent.ACTION_DRAG_STARTED -> {
                    true
                }
                DragEvent.ACTION_DRAG_ENTERED -> {
                    true
                }
                DragEvent.ACTION_DRAG_LOCATION -> {
                    true
                }
                DragEvent.ACTION_DRAG_EXITED -> {
                    true
                }
                DragEvent.ACTION_DROP -> {
                    onDrop(event.localState as View, view)
                    true
                }
                DragEvent.ACTION_DRAG_ENDED -> {
                    true
                }
                else -> false
            }
        }
    }

    override fun onPause(owner: LifecycleOwner) {
        try {
            _viewList.forEach {
                UtilKDragAndDrop.fixDragAndDropLeak(it.first, it.second)
            }
            _viewList.clear()
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
        }
        super.onPause(owner)
    }
}