package com.mozhimen.uicorek.adapterk.fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.mozhimen.uicorek.adapterk.fragment.bases.BaseFragmentInfo

/**
 * @ClassName FragmentInfoPagerAdapter
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/10/7 11:12
 * @Version 1.0
 */
class AdapterKFragmentPagerFragmentInfo : FragmentPagerAdapter {

    private val _fragmentInfos: List<BaseFragmentInfo>

    constructor(fragmentManager: FragmentManager, fragmentInfos: List<BaseFragmentInfo>) : super(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        _fragmentInfos = fragmentInfos
    }

    ////////////////////////////////////////////////////////////////////////

    override fun getCount(): Int {
        return _fragmentInfos.size
    }

    override fun getItem(position: Int): Fragment {
        return _fragmentInfos[position].newFragmentBlock.invoke()
    }

    override fun getPageTitle(position: Int): CharSequence {
        return _fragmentInfos[position].fragmentTitle
    }
}