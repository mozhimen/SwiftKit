package com.mozhimen.app.uicorek.refreshk

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mozhimen.app.databinding.ActivityRefreshkBinding
import com.mozhimen.basicsk.extsk.dp2px
import com.mozhimen.basicsk.extsk.postDelayed
import com.mozhimen.basicsk.utilk.UtilKHandler
import com.mozhimen.uicorek.refreshk.commons.IRefreshK
import com.mozhimen.uicorek.refreshk.customs.TextOverView

class RefreshKLayoutActivity : AppCompatActivity() {
    private val vb by lazy { ActivityRefreshkBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)

        val textOverView = TextOverView(this)
        vb.refreshkContainer.setRefreshOverView(textOverView)
        vb.refreshkContainer.setRefreshParams(90f.dp2px(), 1.6f, null)
        vb.refreshkContainer.setRefreshListener(object : IRefreshK.IRefreshKListener {
            override fun onRefresh() {
                UtilKHandler(this@RefreshKLayoutActivity).postDelayed(1000) { vb.refreshkContainer.refreshFinished() }
            }

            override fun enableRefresh(): Boolean {
                return true
            }
        })
    }
}