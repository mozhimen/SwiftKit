package com.mozhimen.app.demo

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mozhimen.app.BR
import com.mozhimen.app.R
import com.mozhimen.app.databinding.ActivityDemoBinding
import com.mozhimen.app.databinding.ItemDemoListBinding
import com.mozhimen.componentk.statusbark.annors.AStatusBarK
import com.mozhimen.basick.basek.BaseKActivityVBVM
import com.mozhimen.componentk.statusbark.annors.AStatusBarKType
import com.mozhimen.uicorek.adapterk.AdapterKRecyclerStuffed
import kotlin.math.abs

@AStatusBarK(statusBarType = AStatusBarKType.FULL_SCREEN)
class DemoActivity : BaseKActivityVBVM<ActivityDemoBinding, DemoViewModel>() {

    override fun initData(savedInstanceState: Bundle?) {
        initView(savedInstanceState)
    }

    var mScrollY = 0
    var mAlpha = 0

    override fun initView(savedInstanceState: Bundle?) {
        val list = arrayListOf(
            Astro("白羊座", "晴天", 90),
            Astro("天蝎座", "雨天", 70),
            Astro("金牛座", "晴天", 90),
            Astro("水瓶座", "雷阵雨", 80),
            Astro("处女座", "晴天", 90),
            Astro("双子座", "晴天", 90),
            Astro("射手座", "晴天", 90),
        )
        vb.demoList.layoutManager = LinearLayoutManager(this)
        vb.demoList.adapter =
            AdapterKRecyclerStuffed<Astro, ItemDemoListBinding>(
                list,
                R.layout.item_demo_list,
                R.layout.item_demo_header,
                null,
                BR.itemAstro
            ) { holder, itemData, position ->
                if (position in 1 until list.size) {
                    holder.binding?.demoItemListBtn?.setOnClickListener {
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

    override fun bindViewVM(vb: ActivityDemoBinding) {
    }
}