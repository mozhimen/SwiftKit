package com.mozhimen.app.uicorek.bannerk

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.mozhimen.app.R
import com.mozhimen.app.databinding.ActivityBannerkBinding
import com.mozhimen.uicorek.bannerk.temps.CircleIndicator
import com.mozhimen.uicorek.bannerk.commons.IBannerKIndicator
import com.mozhimen.uicorek.bannerk.mos.BannerKMo

class BannerKActivity : AppCompatActivity() {
    private val TAG = "BannerKActivity>>>>>"

    private var urls = arrayOf(
        "https://images.pexels.com/photos/2709388/pexels-photo-2709388.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
        "https://images.pexels.com/photos/8721987/pexels-photo-8721987.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
        "https://images.pexels.com/photos/6679876/pexels-photo-6679876.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
        "https://images.pexels.com/photos/7078587/pexels-photo-7078587.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
        "https://images.pexels.com/photos/6948010/pexels-photo-6948010.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
        "https://images.pexels.com/photos/7078486/pexels-photo-7078486.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"
    )
    private val vb: ActivityBannerkBinding by lazy { ActivityBannerkBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(vb.root)

        initBanner(CircleIndicator(this), false)
        vb.bannerkSwitch.setOnCheckedChangeListener { _, isChecked ->
            autoPlay = isChecked
            initBanner(iBannerKIndicator, autoPlay)
        }
        vb.bannerkIndicator.setOnClickListener {
            if (iBannerKIndicator is CircleIndicator) {
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
    private var iBannerKIndicator: IBannerKIndicator<*>? = null

    private fun initBanner(iBannerKIndicator: IBannerKIndicator<*>?, autoPlay: Boolean) {
        this.iBannerKIndicator = iBannerKIndicator
        val moList: MutableList<BannerKMo> = ArrayList()
        for (i in 0..5) {
            val mo = BannerMo()
            mo.url = urls[i % urls.size]
            moList.add(mo)
        }
        vb.bannerk.apply {
            setBannerIndicator(iBannerKIndicator)
            setAutoPlay(autoPlay)
            setIntervalTime(5000)
            setScrollerDuration(3000)
            //setCurrentItem(?)
            //设置自定义布局
            setBannerData(R.layout.item_bannerk, moList)
            setBindAdapter { viewHolder, mo, position ->
                val imageView: ImageView = viewHolder.findViewById(R.id.item_bannerk_image)
                Glide.with(this@BannerKActivity).load(mo.url).into(imageView)
                val titleView: TextView = viewHolder.findViewById(R.id.item_bannerk_title)
                titleView.text = mo.url

                Log.d(TAG, position.toString() + " url:" + mo.url)
            }
        }
    }
}