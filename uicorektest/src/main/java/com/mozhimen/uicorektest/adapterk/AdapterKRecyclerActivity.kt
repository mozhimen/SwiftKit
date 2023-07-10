package com.mozhimen.uicorektest.adapterk

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mozhimen.basick.elemk.androidx.appcompat.bases.BaseActivityVB
import com.mozhimen.uicorek.recyclerk.bases.BaseRecyclerKItem
import com.mozhimen.uicorek.adapterk.AdapterKRecycler
import com.mozhimen.uicorek.adapterk.AdapterKRecyclerStuffed
import com.mozhimen.uicorektest.databinding.ActivityAdapterkRecyclerBinding
import com.mozhimen.uicorektest.recyclerk.mos.*

/**
 * @ClassName DataKActivity
 * @Description
 * @Author Kolin Zhao
 * @Date 2021/9/1 16:16
 * @Version 1.0
 */
class AdapterKRecyclerActivity : BaseActivityVB<ActivityAdapterkRecyclerBinding>() {

    private val _adapterKRecycler by lazy { AdapterKRecycler() }
    override fun initView(savedInstanceState: Bundle?) {
        initAdapter()
    }

    private fun initAdapter() {
        vb.adapterkRecycler.apply {
            layoutManager = GridLayoutManager(this@AdapterKRecyclerActivity, 2)
            adapter = _adapterKRecycler
        }

        val dataSets = ArrayList<BaseRecyclerKItem<out RecyclerView.ViewHolder>>()
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
        _adapterKRecycler.addItems(dataSets, true)
    }
}