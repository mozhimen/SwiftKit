package com.mozhimen.basicktest.elemk.android

import android.os.Bundle
import android.widget.TextView
import com.mozhimen.basick.lintk.optins.OApiInit_ByLazy
import com.mozhimen.basick.elemk.androidx.fragment.bases.BaseFragmentVB
import com.mozhimen.basick.elemk.android.view.DragAndDropProxy
import com.mozhimen.basick.lintk.optins.OApiCall_BindLifecycle
import com.mozhimen.basicktest.databinding.FragmentElemkGestureBinding

class ElemKGestureFragment : BaseFragmentVB<FragmentElemkGestureBinding>() {
    @OptIn(OApiInit_ByLazy::class, OApiCall_BindLifecycle::class)
    private val _dragAndDropProxy by lazy { DragAndDropProxy() }

    @OptIn(OApiInit_ByLazy::class, OApiCall_BindLifecycle::class)
    override fun initView(savedInstanceState: Bundle?) {
        _dragAndDropProxy.bindLifecycle(this)
        _dragAndDropProxy.dragAndDrop(vdb.elemkGestureFragmentTxt1, vdb.elemkGestureFragmentTxt2) { source, dest ->
            (dest as TextView).text = (source as TextView).text.toString()
        }
    }
}