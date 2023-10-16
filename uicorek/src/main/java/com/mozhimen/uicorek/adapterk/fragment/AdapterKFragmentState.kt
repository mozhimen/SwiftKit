package com.mozhimen.uicorek.adapterk.fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mozhimen.basick.elemk.commons.I_AListener
import com.mozhimen.uicorek.adapterk.fragment.bases.BaseFragmentInfo

/**
 * @ClassName AdapterKFragmentState
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/10/17 0:29
 * @Version 1.0
 */
class AdapterKFragmentState : FragmentStateAdapter {

    constructor(fragmentActivity: FragmentActivity, fragmentInfos: List<BaseFragmentInfo>) : super(fragmentActivity) {
        _fragmentInfos = fragmentInfos
    }

    constructor(fragment: Fragment, fragmentInfos: List<BaseFragmentInfo>) : super(fragment) {
        _fragmentInfos = fragmentInfos
    }

    ////////////////////////////////////////////////////////////////////////

    private var _fragmentInfos: List<BaseFragmentInfo>

    ////////////////////////////////////////////////////////////////////////

    override fun getItemCount(): Int {
        return _fragmentInfos.size
    }

    override fun createFragment(position: Int): Fragment {
        val fragmentInfo = _fragmentInfos[position]
        fragmentInfo.fragment = fragmentInfo.onCreateFragment!!.invoke()
        return fragmentInfo.fragment!!
    }

    ////////////////////////////////////////////////////////////////////////

    fun getFragment(position: Int): Fragment? =
        _fragmentInfos[position].fragment

    fun getFragmentInfos(): List<BaseFragmentInfo> =
        _fragmentInfos
}