package com.mozhimen.app.componentk.guidek

import android.os.Bundle
import androidx.navigation.findNavController
import com.mozhimen.app.R
import com.mozhimen.app.databinding.ActivityGuidekBinding
import com.mozhimen.basick.basek.BaseKActivity
import com.mozhimen.basick.basek.BaseKViewModel
import com.mozhimen.componentk.guidek.GuideK
import com.mozhimen.componentk.guidek.GuideKMgr
import com.mozhimen.componentk.guidek.commons.GuideKSelectedListener
import com.mozhimen.componentk.guidek.mos.GuideKPageInfo
import com.mozhimen.uicorek.tabk.bottom.mos.TabKBottomMo

/**
 * @ClassName GuideKActivity
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/9/3 11:56
 * @Version 1.0
 */
class GuideKActivity :
    BaseKActivity<ActivityGuidekBinding, BaseKViewModel>(R.layout.activity_guidek) {

    private var _currentItemId: Int = 0

    companion object {
        private const val GUIDEK_SAVED_CURRENT_ID = "GUIDEK_SAVED_CURRENT_ID"
    }

    override fun initData(savedInstanceState: Bundle?) {
        savedInstanceState?.let {
            _currentItemId = savedInstanceState.getInt(GUIDEK_SAVED_CURRENT_ID)
        }

        initView(savedInstanceState)
    }

    override fun initView(savedInstanceState: Bundle?) {
        val navController = findNavController(R.id.guidek_fragment_container)
        val hostFragment =
            supportFragmentManager.findFragmentById(R.id.guidek_fragment_container);

        GuideKMgr.instance.getConfig().observe(this) {
            if (it != null) {
                GuideK.buildNavGraph(
                    it,
                    this,
                    hostFragment!!.childFragmentManager,
                    navController,
                    R.id.guidek_fragment_container
                )
                GuideK.buildBottomLayout(
                    it,
                    GuideK.indexOf(it.pkgPages, _currentItemId),
                    vb.guidekBottomLayout,
                    listener = object : GuideKSelectedListener {
                        override fun onSelected(
                            index: Int,
                            pageInfo: GuideKPageInfo,
                            prevMo: TabKBottomMo?,
                            nextMo: TabKBottomMo
                        ) {
                            navController.navigate(pageInfo.id)
                            _currentItemId = pageInfo.id
                        }
                    })
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(GUIDEK_SAVED_CURRENT_ID, _currentItemId)
        super.onSaveInstanceState(outState)
    }

    override fun onBackPressed() {
        this.finish()
    }
}