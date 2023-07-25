package com.mozhimen.basicktest.elemk.gesture

import android.os.Bundle
import android.widget.TextView
import com.mozhimen.basick.elemk.androidx.appcompat.bases.BaseActivityVB
import com.mozhimen.basick.lintk.optin.annors.ALintKOptIn_ApiInit_ByLazy
import com.mozhimen.basick.elemk.android.view.DragAndDropProxy
import com.mozhimen.basick.lintk.optin.annors.ALintKOptIn_ApiCall_BindLifecycle
import com.mozhimen.basick.utilk.android.view.UtilKDragAndDrop
import com.mozhimen.basicktest.R
import com.mozhimen.basicktest.databinding.ActivityElemkGestureBinding


/**
 * @ClassName ElemkGestureActivity
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/2/5 21:46
 * @Version 1.0
 */
class ElemKGestureActivity : BaseActivityVB<ActivityElemkGestureBinding>() {
    @OptIn(ALintKOptIn_ApiInit_ByLazy::class, ALintKOptIn_ApiCall_BindLifecycle::class)
    private val _dragAndDropProxy by lazy { DragAndDropProxy() }

    @OptIn(ALintKOptIn_ApiInit_ByLazy::class, ALintKOptIn_ApiCall_BindLifecycle::class)
    override fun initView(savedInstanceState: Bundle?) {
        _dragAndDropProxy.bindLifecycle(this)
        _dragAndDropProxy.dragAndDrop(vb.elemkGestureTxt1, vb.elemkGestureTxt2) { source, dest ->
            (dest as TextView).text = (source as TextView).text.toString()
        }

        //for example
//        vb.elemkGestureTxt1.setOnLongClickListener { view ->
//            view.startDrag(null, View.DragShadowBuilder(view), vb.elemkGestureTxt1, 0)
//            //震动反馈
//            //v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS, HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING)
//            true
//        }
//
//        vb.elemkGestureTxt2.setOnDragListener { v, event -> //v 永远是设置该监听的view，这里即fl_blue
//            when (event.action) {
//                DragEvent.ACTION_DRAG_STARTED -> {
//                    Log.i(TAG, "开始拖拽")
//                }
//                DragEvent.ACTION_DRAG_ENTERED -> {
//                    Log.i(TAG, "拖拽的view进入监听的view时")
//                }
//                DragEvent.ACTION_DRAG_EXITED -> {
//                    Log.i(TAG, "拖拽的view离开监听的view时")
//                }
//                DragEvent.ACTION_DRAG_LOCATION -> {
////                    val x = event.x
////                    val y = event.y
//                    //Log.i(TAG, "拖拽的view在BLUE中的位置:x =$x,y=$y")
//                }
//                DragEvent.ACTION_DROP -> {
//                    Log.i(TAG, "释放拖拽的view")
//                    val localState: TextView = event.localState as TextView
//                    (v as TextView).text = localState.text
////                    val localState: TextView = event.localState as TextView
////                    val layoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
////                    layoutParams.topMargin = event.y.toInt() - localState.getWidth() / 2
////                    layoutParams.leftMargin = event.x.toInt() - localState.getHeight() / 2
////                    (localState.getParent() as ViewGroup).removeView(localState)
////                    linearLayout.addView(localState, layoutParams)
////                    imageView.setX(event.x - imageView.getWidth() / 2)
////                    imageView.setY(event.y - imageView.getHeight() / 2)
//                }
//                DragEvent.ACTION_DRAG_ENDED -> {
//                    Log.i(TAG, "结束拖拽")
//                }
//            }
//            //是否响应拖拽事件，true响应，返回false只能接受到ACTION_DRAG_STARTED事件，后续事件不会收到
//            true
//        }
    }

    override fun onPause() {
        UtilKDragAndDrop.fixDragAndDropLeak(findViewById(R.id.elemk_gesture_fragment_container))
        super.onPause()
    }
}