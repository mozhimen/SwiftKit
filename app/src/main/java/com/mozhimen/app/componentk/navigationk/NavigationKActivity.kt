package com.mozhimen.app.componentk.navigationk

import android.os.Bundle
import androidx.navigation.findNavController
import com.mozhimen.app.R
import com.mozhimen.app.databinding.ActivityNavigationkBinding
import com.mozhimen.componentk.basek.BaseKActivity
import com.mozhimen.componentk.basek.BaseKViewModel
import com.mozhimen.componentk.navigationk.NavigationK

/**
 * @ClassName NavigationKActivity
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/9/3 11:56
 * @Version 1.0
 */
class NavigationKActivity :
    BaseKActivity<ActivityNavigationkBinding, BaseKViewModel>(R.layout.activity_navigationk) {

    override fun initData(savedInstanceState: Bundle?) {
        initView()
    }

    override fun initView() {
        val navController = findNavController(R.id.navigationk_fragment_container)
        val hostFragment =
            supportFragmentManager.findFragmentById(R.id.navigationk_fragment_container);

        NavigationK.buildNavGraph(
            this,
            hostFragment!!.childFragmentManager,
            navController,
            R.id.navigationk_fragment_container
        )
        NavigationK.buildBottomTab(vb.navigationkBottomView)

        vb.navigationkBottomView.setOnNavigationItemSelectedListener {
            navController.navigate(it.itemId)
            true
        }
    }
}