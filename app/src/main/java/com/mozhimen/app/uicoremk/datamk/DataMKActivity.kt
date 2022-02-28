package com.mozhimen.app.uicoremk.datamk

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mozhimen.app.databinding.ActivityDatamkBinding
import com.mozhimen.uicoremk.datamk.DataMK
import com.mozhimen.uicoremk.datamk.commons.DataMKItem
import com.mozhimen.uicoremk.datamk.helpers.DataMKAdapter

/**
 * @ClassName DataMKActivity
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/9/1 16:16
 * @Version 1.0
 */
class DataMKActivity : AppCompatActivity() {
    private val vb by lazy { ActivityDatamkBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)

        val dataMKAdapter = DataMKAdapter(this)
        vb.datamkRecycler.apply {
            layoutManager = GridLayoutManager(this@DataMKActivity, 2)
            adapter = dataMKAdapter
        }

        val dataSets = ArrayList<DataMKItem<*, out RecyclerView.ViewHolder>>()
        dataSets.apply {
            add(DataMKItemTop(DataMK()))
            add(DataMKItemBanner(DataMK()))
            add(DataMKItemGrid(DataMK()))
            add(DataMKItemActivity(DataMK()))
            add(DataMKItemTab(DataMK()))
        }

        for (i in 0..9) {
            if (i % 2 == 0) {
                //feeds流的视频类型
                dataSets.add(DataMKItemVideo(1, DataMK()))
            } else {
                //feeds流的图片类型
                dataSets.add(DataMKItemImage(1, DataMK()))
            }
        }

        dataMKAdapter.addItems(dataSets, false)
    }
}