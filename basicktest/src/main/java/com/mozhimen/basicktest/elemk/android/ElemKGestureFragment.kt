package com.mozhimen.basicktest.elemk.android

import android.os.Bundle
import android.widget.TextView
import com.mozhimen.basick.lintk.optin.OptInApiInit_ByLazy
import com.mozhimen.basick.elemk.androidx.fragment.bases.BaseFragmentVB
import com.mozhimen.basick.elemk.android.view.DragAndDropProxy
import com.mozhimen.basick.lintk.optin.OptInApiCall_BindLifecycle
import com.mozhimen.basicktest.databinding.FragmentElemkGestureBinding

class ElemKGestureFragment : BaseFragmentVB<FragmentElemkGestureBinding>() {
    @OptIn(OptInApiInit_ByLazy::class, OptInApiCall_BindLifecycle::class)
    private val _dragAndDropProxy by lazy { DragAndDropProxy() }

    @OptIn(OptInApiInit_ByLazy::class, OptInApiCall_BindLifecycle::class)
    override fun initView(savedInstanceState: Bundle?) {
        _dragAndDropProxy.bindLifecycle(this)
        _dragAndDropProxy.dragAndDrop(vb.elemkGestureFragmentTxt1, vb.elemkGestureFragmentTxt2) { source, dest ->
            (dest as TextView).text = (source as TextView).text.toString()
        }
    }
}