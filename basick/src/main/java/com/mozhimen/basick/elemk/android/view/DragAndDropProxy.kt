package com.mozhimen.basick.elemk.android.view

import android.view.DragEvent
import android.view.View
import android.view.View.DragShadowBuilder
import androidx.lifecycle.LifecycleOwner
import com.mozhimen.basick.lintk.optin.OptInApiInit_ByLazy
import com.mozhimen.basick.elemk.commons.IAA_Listener
import com.mozhimen.basick.elemk.androidx.lifecycle.bases.BaseWakeBefPauseLifecycleObserver
import com.mozhimen.basick.lintk.optin.OptInApiCall_BindLifecycle
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion
import com.mozhimen.basick.utilk.android.util.et
import com.mozhimen.basick.utilk.android.view.UtilKViewDrag

/**
 * @ClassName DragAndDropProxy
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/2/8 17:25
 * @Version 1.0
 */
@OptInApiCall_BindLifecycle
@OptInApiInit_ByLazy
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
            for (view in _viewList) UtilKViewDrag.fixDragLeak(view.first, view.second)
            _viewList.clear()
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
        }
        super.onPause(owner)
    }
}