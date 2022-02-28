package com.mozhimen.app.uicoremk.refreshmk

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.mozhimen.app.databinding.ActivityRefreshmkBinding
import com.mozhimen.uicoremk.refreshmk.commons.IRefreshMK
import com.mozhimen.uicoremk.refreshmk.TextOverView

class RefreshMKLayoutActivity : AppCompatActivity() {
    private val vb by lazy { ActivityRefreshmkBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)

        val textOverView =
            TextOverView(this)
        vb.refreshLayout.setRefreshOverView(textOverView)
        vb.refreshLayout.setRefreshListener(object : IRefreshMK.RefreshMKListener {
            override fun onRefresh() {
                Handler(mainLooper).postDelayed({ vb.refreshLayout.refreshFinished() }, 1000)
            }

            override fun enableRefresh(): Boolean {
                return true
            }
        })
    }
}