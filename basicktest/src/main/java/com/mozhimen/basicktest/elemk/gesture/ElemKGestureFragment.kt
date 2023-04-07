package com.mozhimen.basicktest.elemk.gesture

import android.os.Bundle
import android.widget.TextView
import com.mozhimen.basick.elemk.fragment.bases.BaseFragmentVB
import com.mozhimen.basick.elemk.gesture.DragAndDropDelegate
import com.mozhimen.basicktest.databinding.FragmentElemkGestureBinding

class ElemKGestureFragment : BaseFragmentVB<FragmentElemkGestureBinding>() {
    private val _dragAndDropDelegate by lazy { DragAndDropDelegate() }

    override fun initView(savedInstanceState: Bundle?) {
        _dragAndDropDelegate.bindLifecycle(this)
        _dragAndDropDelegate.dragAndDrop(VB.elemkGestureFragmentTxt1, VB.elemkGestureFragmentTxt2) { source, dest ->
            (dest as TextView).text = (source as TextView).text.toString()
        }
    }
}