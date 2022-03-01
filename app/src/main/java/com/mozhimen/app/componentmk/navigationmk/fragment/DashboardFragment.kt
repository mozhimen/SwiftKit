package com.mozhimen.app.componentmk.navigationmk.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mozhimen.app.R
import com.mozhimen.navigationmkannor.NavigationMKDestination

/**
 * @ClassName HomeFragment
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/9/3 13:15
 * @Version 1.0
 */
@NavigationMKDestination(pageUrl = "main/navigationmk/dashboard")
class DashboardFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_navigationmk_dashboard, container, false)
    }
}