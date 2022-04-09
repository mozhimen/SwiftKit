package com.mozhimen.app.uicorek.refreshk

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.mozhimen.app.databinding.ActivityRefreshkBinding
import com.mozhimen.uicorek.refreshk.commons.IRefreshK
import com.mozhimen.uicorek.refreshk.temps.TextOverView

class RefreshKLayoutActivity : AppCompatActivity() {
    private val vb by lazy { ActivityRefreshkBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)

        val textOverView =
            TextOverView(this)
        vb.refreshLayout.setRefreshOverView(textOverView)
        vb.refreshLayout.setRefreshListener(object : IRefreshK.RefreshKListener {
            override fun onRefresh() {
                Handler(mainLooper).postDelayed({ vb.refreshLayout.refreshFinished() }, 1000)
            }

            override fun enableRefresh(): Boolean {
                return true
            }
        })
    }
}