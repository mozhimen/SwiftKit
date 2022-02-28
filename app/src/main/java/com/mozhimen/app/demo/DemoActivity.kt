package com.mozhimen.app.demo

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mozhimen.app.BR
import com.mozhimen.app.R
import com.mozhimen.app.databinding.ActivityDemoBinding
import com.mozhimen.basicsmk.statusbarmk.StatusBarMKAnnor
import com.mozhimen.componentmk.basemk.BaseMKActivity
import com.mozhimen.uicoremk.adaptermk.AdapterMKStuffedRecycler
import kotlin.math.abs

@StatusBarMKAnnor(isImmersed = true)
class DemoActivity : BaseMKActivity<ActivityDemoBinding, DemoViewModel>(R.layout.activity_demo) {

    override fun initData(savedInstanceState: Bundle?) {
        initView()
    }

    var mScrollY = 0
    var mAlpha = 0

    override fun initView() {
        val list = arrayListOf<Astro>(
            Astro("白羊座", "晴天", 90),
            Astro("天蝎座", "雨天", 70),
            Astro("金牛座", "晴天", 90),
            Astro("水瓶座", "雷阵雨", 80),
            Astro("处女座", "晴天", 90),
            Astro("双子座", "晴天", 90),
            Astro("射手座", "晴天", 90),
        )
        vb.demoList.layoutManager = LinearLayoutManager(this)
        vb.demoList.adapter = object :
            AdapterMKStuffedRecycler<Astro>(
                list,
                R.layout.item_demo_list,
                R.layout.item_demo_header,
                null,
                BR.item
            ) {
            override fun addListener(view: View, itemData: Astro, position: Int) {
                (view.findViewById(R.id.demo_item_list_btn) as FrameLayout).setOnClickListener {
                    Log.i(TAG, itemData.name)
                }
            }
        }
        vb.demoList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                mScrollY += dy
                mAlpha = if (abs(mScrollY) > 1000) {
                    vb.demoBg.setBlurredTop(100)
                    100
                } else {
                    vb.demoBg.setBlurredTop(mScrollY / 10)
                    abs(mScrollY) / 10
                }
                vb.demoBg.setBlurredLevel(mAlpha)
            }
        })
    }
}