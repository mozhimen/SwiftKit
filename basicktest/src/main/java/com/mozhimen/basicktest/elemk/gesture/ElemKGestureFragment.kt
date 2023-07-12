package com.mozhimen.basicktest.elemk.gesture

import android.os.Bundle
import android.widget.TextView
import com.mozhimen.basick.lintk.optin.annors.ALintKOptIn_ApiInit_ByLazy
import com.mozhimen.basick.elemk.androidx.fragment.bases.BaseFragmentVB
import com.mozhimen.basick.elemk.android.view.DragAndDropProxy
import com.mozhimen.basick.lintk.optin.annors.ALintKOptIn_ApiCall_BindLifecycle
import com.mozhimen.basicktest.databinding.FragmentElemkGestureBinding

class ElemKGestureFragment : BaseFragmentVB<FragmentElemkGestureBinding>() {
    @OptIn(ALintKOptIn_ApiInit_ByLazy::class, ALintKOptIn_ApiCall_BindLifecycle::class)
    private val _dragAndDropProxy by lazy { DragAndDropProxy() }

    @OptIn(ALintKOptIn_ApiInit_ByLazy::class, ALintKOptIn_ApiCall_BindLifecycle::class)
    override fun initView(savedInstanceState: Bundle?) {
        _dragAndDropProxy.bindLifecycle(this)
        _dragAndDropProxy.dragAndDrop(vb.elemkGestureFragmentTxt1, vb.elemkGestureFragmentTxt2) { source, dest ->
            (dest as TextView).text = (source as TextView).text.toString()
        }
    }
}