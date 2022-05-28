package com.mozhimen.app.uicorek.refreshk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mozhimen.app.databinding.ActivityRefreshkLottieBinding
import com.mozhimen.basick.extsk.dp2px
import com.mozhimen.basick.extsk.postDelayed
import com.mozhimen.basick.utilk.UtilKHandler
import com.mozhimen.uicorek.refreshk.commons.IRefreshK
import com.mozhimen.uicorek.refreshk.customs.LottieOverView

class RefreshKLayoutLottieActivity : AppCompatActivity() {
    private val vb by lazy { ActivityRefreshkLottieBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)

        val lottieOverView = LottieOverView(this)
        vb.refreshkContainerLottie.setRefreshOverView(lottieOverView)
        vb.refreshkContainerLottie.setRefreshParams(90f.dp2px(), null, null)
        vb.refreshkContainerLottie.setRefreshListener(object : IRefreshK.IRefreshKListener {
            override fun onRefresh() {
                UtilKHandler(this@RefreshKLayoutLottieActivity).postDelayed(1000) { vb.refreshkContainerLottie.refreshFinished() }
            }

            override fun enableRefresh(): Boolean {
                return true
            }
        })
    }
}