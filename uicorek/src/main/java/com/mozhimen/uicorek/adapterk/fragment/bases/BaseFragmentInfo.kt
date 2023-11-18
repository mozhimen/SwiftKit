package com.mozhimen.uicorek.adapterk.fragment.bases

import androidx.fragment.app.Fragment
import com.mozhimen.basick.elemk.commons.I_AListener

/**
 * @ClassName BaseFragmentInfo
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/10/17 0:49
 * @Version 1.0
 */
open class BaseFragmentInfo {
    var fragmentId: String
    var fragmentTitle: String
    var newFragmentBlock: I_AListener<Fragment>
    var fragment: Fragment? = null

    constructor(fragmentId: String, fragmentTitle: String, newFragmentBlock: I_AListener<Fragment>) {
        this.fragmentId = fragmentId
        this.fragmentTitle = fragmentTitle
        this.newFragmentBlock = newFragmentBlock
    }
}