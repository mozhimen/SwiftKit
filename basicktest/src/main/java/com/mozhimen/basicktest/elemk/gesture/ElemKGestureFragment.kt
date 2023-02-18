package com.mozhimen.basicktest.elemk.gesture

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.mozhimen.basick.elemk.fragment.bases.BaseFragmentVB
import com.mozhimen.basick.elemk.gesture.DragAndDropDelegate
import com.mozhimen.basicktest.databinding.FragmentElemkFragmentVbBinding
import com.mozhimen.basicktest.databinding.FragmentElemkGestureBinding
import com.mozhimen.basicktest.elemk.viewmodel.ElemKViewModel

class ElemKGestureFragment : BaseFragmentVB<FragmentElemkGestureBinding>() {
    private val _dragAndDropDelegate by lazy { DragAndDropDelegate(this) }

    override fun initView(savedInstanceState: Bundle?) {
        _dragAndDropDelegate.dragAndDrop(vb.elemkGestureFragmentTxt1, vb.elemkGestureFragmentTxt2) { source, dest ->
            (dest as TextView).text = (source as TextView).text.toString()
        }
    }
}