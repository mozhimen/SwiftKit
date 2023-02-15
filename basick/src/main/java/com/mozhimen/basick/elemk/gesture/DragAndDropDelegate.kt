package com.mozhimen.basick.elemk.gesture

import android.util.SparseArray
import android.view.DragEvent
import android.view.View
import android.view.View.DragShadowBuilder
import androidx.lifecycle.LifecycleOwner
import com.mozhimen.basick.elemk.lifecycle.bases.BaseDelegateLifecycleObserver
import java.lang.ref.WeakReference


/**
 * @ClassName DragAndDropDelegate
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/2/8 17:25
 * @Version 1.0
 */
class DragAndDropDelegate(owner: LifecycleOwner) : BaseDelegateLifecycleObserver(owner) {

    fun dragAndDrop(sourceView: WeakReference<View>, destView: WeakReference<View>, onDrop: (source: WeakReference<View>, dest: WeakReference<View>) -> Unit) {
        sourceView.get()?.setOnLongClickListener {
            sourceView.get()?.startDrag(
                null,
                DragShadowBuilder(sourceView.get()),
                sourceView,
                0
            )            //v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS, HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING)
            //v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS, HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING)
            true
        }
        destView.get()?.setOnDragListener { view, event ->
            if (event.action == DragEvent.ACTION_DROP) {
                onDrop(WeakReference(event.localState as View), WeakReference(view))
            }
            true
        }
    }
}