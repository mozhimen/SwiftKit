package com.mozhimen.basicktest.elemk.gesture

import android.os.Bundle
import android.widget.TextView
import com.mozhimen.basick.lintk.optin.annors.AOptLazyInit
import com.mozhimen.basick.elemk.fragment.bases.BaseFragmentVB
import com.mozhimen.basick.elemk.gesture.DragAndDropDelegate
import com.mozhimen.basicktest.databinding.FragmentElemkGestureBinding

class ElemKGestureFragment : BaseFragmentVB<FragmentElemkGestureBinding>() {
    @OptIn(AOptLazyInit::class)
    private val _dragAndDropDelegate by lazy { DragAndDropDelegate() }

    @OptIn(AOptLazyInit::class)
    override fun initView(savedInstanceState: Bundle?) {
        _dragAndDropDelegate.bindLifecycle(this)
        _dragAndDropDelegate.dragAndDrop(vb.elemkGestureFragmentTxt1, vb.elemkGestureFragmentTxt2) { source, dest ->
            (dest as TextView).text = (source as TextView).text.toString()
        }
    }
}