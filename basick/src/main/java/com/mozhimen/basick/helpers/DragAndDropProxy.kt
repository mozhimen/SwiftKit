package com.mozhimen.basick.helpers

import android.view.DragEvent
import android.view.View
import android.view.View.DragShadowBuilder
import androidx.lifecycle.LifecycleOwner
import com.mozhimen.basick.bases.BaseWakeBefPauseLifecycleObserver
import com.mozhimen.kotlin.lintk.optins.OApiInit_ByLazy
import com.mozhimen.kotlin.elemk.commons.IAA_Listener
import com.mozhimen.kotlin.lintk.optins.OApiCall_BindLifecycle
import com.mozhimen.kotlin.utilk.android.os.UtilKBuildVersion
import com.mozhimen.kotlin.utilk.android.util.e
import com.mozhimen.kotlin.utilk.android.view.UtilKViewLeak

/**
 * @ClassName DragAndDropProxy
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Version 1.0
 */
@OApiCall_BindLifecycle
@OApiInit_ByLazy
class DragAndDropProxy : BaseWakeBefPauseLifecycleObserver() {
    private val _viewList = ArrayList<Pair<View, View>>()

    fun dragAndDrop(sourceView: View, destView: View, onDrop: IAA_Listener<View>/*(sourceView: View, destView: View) -> Unit*/) {
        _viewList.add(sourceView to destView)
        sourceView.setOnLongClickListener {
            if (UtilKBuildVersion.isAfterV_24_7_N()) {
                //supports Nougat and beyond
                sourceView.startDragAndDrop(null, DragShadowBuilder(sourceView), sourceView, 0)
            } else {
                //support pre-Nougat versions
                sourceView.startDrag(null, DragShadowBuilder(sourceView), sourceView, 0)
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
            for (view in _viewList) UtilKViewLeak.fixLeak_ofDragChild(view.first, view.second)
            _viewList.clear()
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.e(TAG)
        }
        super.onPause(owner)
    }
}