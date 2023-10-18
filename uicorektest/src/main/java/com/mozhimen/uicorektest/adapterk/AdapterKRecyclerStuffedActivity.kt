package com.mozhimen.uicorektest.adapterk

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mozhimen.basick.elemk.androidx.appcompat.bases.BaseActivityVB
import com.mozhimen.uicorek.adapterk.item.AdapterKItemRecyclerStuffed
import com.mozhimen.uicorek.recyclerk.item.RecyclerKItem
import com.mozhimen.uicorektest.databinding.ActivityAdapterkRecyclerStuffedBinding
import com.mozhimen.uicorektest.recyclerk.mos.*

/**
 * @ClassName DataKActivity
 * @Description
 * @Author Kolin Zhao
 * @Date 2021/9/1 16:16
 * @Version 1.0
 */
class AdapterKRecyclerStuffedActivity : BaseActivityVB<ActivityAdapterkRecyclerStuffedBinding>() {

    private val _adapterKRecyclerStuffed by lazy { AdapterKItemRecyclerStuffed() }
    override fun initView(savedInstanceState: Bundle?) {
        initAdapter()
    }

    private fun initAdapter() {
        vb.adapterkRecyclerStuffed.apply {
            layoutManager = GridLayoutManager(this@AdapterKRecyclerStuffedActivity, 2)
            adapter = _adapterKRecyclerStuffed
        }

        val dataSets = ArrayList<RecyclerKItem<out RecyclerView.ViewHolder>>()
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
        _adapterKRecyclerStuffed.addItems(dataSets, true)
    }
}