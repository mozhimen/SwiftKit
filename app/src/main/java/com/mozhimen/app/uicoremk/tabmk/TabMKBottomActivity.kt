package com.mozhimen.app.tabmk

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mozhimen.app.R
import com.mozhimen.app.databinding.ActivityTabmkBottomBinding
import com.mozhimen.uicoremk.tabmk.bottom.mos.TabMKBottomInfo

/**
 * @ClassName TabBottomActivity
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/8/2 14:39
 * @Version 1.0
 */
class TabMKBottomActivity : AppCompatActivity() {
    private val vb by lazy { ActivityTabmkBottomBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)

        val homeInfo = TabMKBottomInfo(
            "首页",
            "fonts/iconfont.ttf",
            getString(R.string.icon_home),
            null,
            "#ff656667",
            "#ffd44949"
        )
        vb.tabBottom.setTabInfo(homeInfo)
    }
}