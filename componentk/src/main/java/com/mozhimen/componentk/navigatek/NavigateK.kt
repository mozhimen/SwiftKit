package com.mozhimen.componentk.navigatek

import androidx.fragment.app.FragmentActivity
import com.mozhimen.componentk.navigatek.commons.INavigateK
import com.mozhimen.componentk.navigatek.helpers.NavigateKDelegate


/**
 * @ClassName NavigateK
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/7/26 13:26
 * @Version 1.0
 */
class NavigateK(activity: FragmentActivity) : INavigateK by NavigateKDelegate(activity)