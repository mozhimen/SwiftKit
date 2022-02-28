package com.mozhimen.app.uicoremk.bannermk

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.mozhimen.app.R
import com.mozhimen.app.databinding.ActivityBannermkBinding
import com.mozhimen.uicoremk.bannermk.CircleIndicator
import com.mozhimen.uicoremk.bannermk.commons.IBannerMKIndicator
import com.mozhimen.uicoremk.bannermk.mos.BannerMKMo

class BannerMKActivity : AppCompatActivity() {
    private val TAG = "BannerMKActivity>>>>>"

    private var urls = arrayOf(
        "https://images.pexels.com/photos/2709388/pexels-photo-2709388.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
        "https://images.pexels.com/photos/8721987/pexels-photo-8721987.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
        "https://images.pexels.com/photos/6679876/pexels-photo-6679876.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
        "https://images.pexels.com/photos/7078587/pexels-photo-7078587.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
        "https://images.pexels.com/photos/6948010/pexels-photo-6948010.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
        "https://images.pexels.com/photos/7078486/pexels-photo-7078486.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"
    )
    private val vb: ActivityBannermkBinding by lazy { ActivityBannermkBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(vb.root)

        initBanner(CircleIndicator(this), false)
        vb.bannermkSwitch.setOnCheckedChangeListener { _, isChecked ->
            autoPlay = isChecked
            initBanner(iBannerMKIndicator, autoPlay)
        }
        vb.bannermkIndicator.setOnClickListener {
            if (iBannerMKIndicator is CircleIndicator) {
                initBanner(CircleIndicator(this), autoPlay)
            } else {
                initBanner(
                    CircleIndicator(
                        this
                    ), autoPlay
                )
            }
        }
    }

    private var autoPlay = false
    private var iBannerMKIndicator: IBannerMKIndicator<*>? = null

    private fun initBanner(iBannerMKIndicator: IBannerMKIndicator<*>?, autoPlay: Boolean) {
        this.iBannerMKIndicator = iBannerMKIndicator
        val moList: MutableList<BannerMKMo> = ArrayList()
        for (i in 0..5) {
            val mo = BannerMo()
            mo.url = urls[i % urls.size]
            moList.add(mo)
        }
        vb.bannermk.apply {
            setBannerIndicator(iBannerMKIndicator)
            setAutoPlay(autoPlay)
            setIntervalTime(5000)
            setScrollerDuration(3000)
            //setCurrentItem(?)
            //设置自定义布局
            setBannerData(R.layout.item_bannermk, moList)
            setBindAdapter { viewHolder, mo, position ->
                val imageView: ImageView = viewHolder.findViewById(R.id.item_bannermk_image)
                Glide.with(this@BannerMKActivity).load(mo.url).into(imageView)
                val titleView: TextView = viewHolder.findViewById(R.id.item_bannermk_title)
                titleView.text = mo.url

                Log.d(TAG, position.toString() + " url:" + mo.url)
            }
        }
    }
}