package com.mozhimen.app.uicorek.datak

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mozhimen.app.databinding.ActivityDatakBinding
import com.mozhimen.app.uicorek.datak.mos.*
import com.mozhimen.uicorek.datak.commons.DataKItem
import com.mozhimen.uicorek.datak.helpers.DataKAdapter

/**
 * @ClassName DataKActivity
 * @Description
 * @Author Kolin Zhao
 * @Date 2021/9/1 16:16
 * @Version 1.0
 */
class DataKActivity : AppCompatActivity() {
    private val vb by lazy { ActivityDatakBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)

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