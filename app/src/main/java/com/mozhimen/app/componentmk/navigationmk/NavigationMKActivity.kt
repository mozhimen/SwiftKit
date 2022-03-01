package com.mozhimen.app.componentmk.navigationmk

import android.os.Bundle
import androidx.navigation.findNavController
import com.mozhimen.app.R
import com.mozhimen.app.databinding.ActivityNavigationmkBinding
import com.mozhimen.componentmk.basemk.BaseMKActivity
import com.mozhimen.componentmk.basemk.BaseMKViewModel
import com.mozhimen.componentmk.navigationmk.NavigationMKManager

/**
 * @ClassName NavigationMKActivity
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/9/3 11:56
 * @Version 1.0
 */
class NavigationMKActivity :
    BaseMKActivity<ActivityNavigationmkBinding, BaseMKViewModel>(R.layout.activity_navigationmk) {

    override fun initData(savedInstanceState: Bundle?) {
        initView()
    }

    override fun initView() {
        val navController = findNavController(R.id.navigationmk_fragment_container)
        val hostFragment =
            supportFragmentManager.findFragmentById(R.id.navigationmk_fragment_container);

        NavigationMKManager.buildNavGraph(
            this,
            hostFragment!!.childFragmentManager,
            navController,
            R.id.navigationmk_fragment_container
        )
        NavigationMKManager.buildBottomTab(vb.navigationmkBottomView)

        vb.navigationmkBottomView.setOnNavigationItemSelectedListener {
            navController.navigate(it.itemId)
            true
        }
    }
}