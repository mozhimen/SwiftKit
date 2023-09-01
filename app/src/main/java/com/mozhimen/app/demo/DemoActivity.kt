package com.mozhimen.app.demo

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mozhimen.app.BR
import com.mozhimen.app.R
import com.mozhimen.app.databinding.ActivityDemoBinding
import com.mozhimen.app.databinding.ItemDemoListBinding
import com.mozhimen.basick.elemk.androidx.appcompat.bases.BaseActivityVBVM
import com.mozhimen.uicorek.adaptk.systembar.annors.AAdaptKSystemBarProperty
import com.mozhimen.uicorek.adaptk.systembar.cons.CProperty
import com.mozhimen.uicorek.adapterk.AdapterKRecyclerStuffedVB
import com.mozhimen.uicorek.adaptk.systembar.initAdaptKSystemBar
import kotlin.math.abs

@AAdaptKSystemBarProperty(property = CProperty.IMMERSED_HARD_STICKY)
class DemoActivity : BaseActivityVBVM<ActivityDemoBinding, DemoViewModel>() {
    private var _scrollY = 0
    private var _alpha = 0
    override fun initFlag() {
        initAdaptKSystemBar()
    }
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
        vb.demoRecycler.layoutManager = LinearLayoutManager(this)
        vb.demoRecycler.adapter =
            AdapterKRecyclerStuffedVB<Astro, ItemDemoListBinding>(
                list,
                R.layout.item_demo_list,
                R.layout.item_demo_header,
                null,
                BR.itemAstro
            ) { holder, itemData, position, _ ->
                if (position in 1 until list.size) {
                    holder.vb.demoItemListBtn.setOnClickListener {
                        Log.i(TAG, itemData.name)
                    }
                }
            }
        vb.demoRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                _scrollY += dy
                _alpha = if (abs(_scrollY) > 1000) {
                    vb.demoBg.setBlurOffset(100)
                    100
                } else {
                    vb.demoBg.setBlurOffset(_scrollY / 10)
                    abs(_scrollY) / 10
                }
                vb.demoBg.setBlurLevel(_alpha)
            }
        })
    }

    override fun bindViewVM(vb: ActivityDemoBinding) {
    }
}