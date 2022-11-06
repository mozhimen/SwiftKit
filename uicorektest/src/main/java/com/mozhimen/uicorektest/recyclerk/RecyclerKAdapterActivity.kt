package com.mozhimen.uicorektest.recyclerk

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mozhimen.basick.basek.BaseKActivityVB
import com.mozhimen.uicorek.recyclerk.RecyclerKItem
import com.mozhimen.uicorek.recyclerk.RecyclerKAdapter
import com.mozhimen.uicorektest.databinding.ActivityRecyclerkAdapterBinding
import com.mozhimen.uicorektest.recyclerk.mos.*

/**
 * @ClassName DataKActivity
 * @Description
 * @Author Kolin Zhao
 * @Date 2021/9/1 16:16
 * @Version 1.0
 */
class RecyclerKAdapterActivity : BaseKActivityVB<ActivityRecyclerkAdapterBinding>() {

    override fun initData(savedInstanceState: Bundle?) {
        initAdapter()
    }

    private fun initAdapter() {
        val recyclerKAdapter = RecyclerKAdapter(this)
        vb.recyclerkRecycler.apply {
            layoutManager = GridLayoutManager(this@RecyclerKAdapterActivity, 2)
            adapter = recyclerKAdapter
        }

        val dataSets = ArrayList<RecyclerKItem<*, out RecyclerView.ViewHolder>>()
        dataSets.apply {
            add(RecyclerKItemTop())
            add(RecyclerKItemBanner())
            add(RecyclerKItemGrid())
            add(RecyclerKItemActivity())
            add(RecyclerKItemTab())
        }

        for (i in 0..9) {
            if (i % 2 == 0) {
                //feeds流的视频类型
                dataSets.add(RecyclerKItemVideo(1))
            } else {
                //feeds流的图片类型
                dataSets.add(RecyclerKItemImage(1))
            }
        }
        recyclerKAdapter.addItems(dataSets, false)
    }
}