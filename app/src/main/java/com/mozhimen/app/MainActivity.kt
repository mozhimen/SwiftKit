package com.mozhimen.app

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.mozhimen.app.databinding.ActivityMainBinding
import com.mozhimen.swiftmk.base.BaseActivity
import com.mozhimen.swiftmk.helper.adapter.StuffedRecyclerAdapterMK
import com.mozhimen.swiftmk.helper.statusbar.StatusBarAnnor
import kotlin.math.abs

@StatusBarAnnor(isImmersed = true)
class MainActivity : BaseActivity() {
    private val vm by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }

    private lateinit var vb: ActivityMainBinding
    override fun getViewBinding(): ViewBinding {
        vb = ActivityMainBinding.inflate(layoutInflater)
        return vb
    }

    override fun initData(savedInstanceState: Bundle?) {

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
        vb.list.layoutManager = LinearLayoutManager(this)
        vb.list.adapter = object :
            StuffedRecyclerAdapterMK<Astro>(
                list,
                R.layout.item_list,
                R.layout.item_header,
                null,
                BR.item
            ) {
            override fun addListener(view: View, itemData: Astro, position: Int) {
                (view.findViewById(R.id.item_btn) as FrameLayout).setOnClickListener {
                    Log.i(mTag, itemData.name)
                }
            }
        }
        vb.list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                mScrollY += dy
                mAlpha = if (abs(mScrollY) > 1000) {
                    vb.bg.setBlurredTop(100)
                    100
                } else {
                    vb.bg.setBlurredTop(mScrollY / 10)
                    abs(mScrollY) / 10
                }
                vb.bg.setBlurredLevel(mAlpha)
            }
        })
    }
}