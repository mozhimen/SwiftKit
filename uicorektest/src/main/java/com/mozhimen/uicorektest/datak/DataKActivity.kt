package com.mozhimen.uicorektest.datak

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mozhimen.basick.basek.BaseKActivityVB
import com.mozhimen.basick.basek.BaseKActivityVBVM
import com.mozhimen.basick.basek.BaseKViewModel
import com.mozhimen.uicorek.datak.commons.DataKItem
import com.mozhimen.uicorek.datak.helpers.DataKAdapter
import com.mozhimen.uicorektest.databinding.ActivityDatakBinding
import com.mozhimen.uicorektest.datak.mos.*

/**
 * @ClassName DataKActivity
 * @Description
 * @Author Kolin Zhao
 * @Date 2021/9/1 16:16
 * @Version 1.0
 */
class DataKActivity : BaseKActivityVB<ActivityDatakBinding>() {

    override fun initData(savedInstanceState: Bundle?) {
        initDataK()
    }

    private fun initDataK() {
        val dataKAdapter = DataKAdapter(this)
        vb.datakRecycler.apply {
            layoutManager = GridLayoutManager(this@DataKActivity, 2)
            adapter = dataKAdapter
        }

        val dataSets = ArrayList<DataKItem<*, out RecyclerView.ViewHolder>>()
        dataSets.apply {
            add(DataKItemTop())
            add(DataKItemBanner())
            add(DataKItemGrid())
            add(DataKItemActivity())
            add(DataKItemTab())
        }

        for (i in 0..9) {
            if (i % 2 == 0) {
                //feeds流的视频类型
                dataSets.add(DataKItemVideo(1))
            } else {
                //feeds流的图片类型
                dataSets.add(DataKItemImage(1))
            }
        }
        dataKAdapter.addItems(dataSets, false)
    }
}