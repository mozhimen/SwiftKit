package com.mozhimen.uicorektest.layoutk.banner

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import coil.load
import com.mozhimen.basick.basek.BaseKActivityVB
import com.mozhimen.uicorek.layoutk.banner.commons.IBannerBindListener
import com.mozhimen.uicorek.layoutk.banner.commons.IBannerIndicator
import com.mozhimen.uicorek.layoutk.banner.temps.NumberIndicator
import com.mozhimen.uicorek.layoutk.banner.temps.PointIndicator
import com.mozhimen.uicorek.layoutk.banner.helpers.BannerViewHolder
import com.mozhimen.uicorek.layoutk.banner.mos.MBannerItem
import com.mozhimen.uicorektest.R
import com.mozhimen.uicorektest.databinding.ActivityLayoutkBannerBinding

class LayoutKBannerActivity : BaseKActivityVB<ActivityLayoutkBannerBinding>() {

    private var _autoPlay = true
    private lateinit var _indicator: IBannerIndicator<*>

    private var _urls = arrayOf(
        "https://images.pexels.com/photos/2709388/pexels-photo-2709388.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
        "https://images.pexels.com/photos/8721987/pexels-photo-8721987.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
        "https://images.pexels.com/photos/6679876/pexels-photo-6679876.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
        "https://images.pexels.com/photos/7078587/pexels-photo-7078587.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
        "https://images.pexels.com/photos/6948010/pexels-photo-6948010.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
        "https://images.pexels.com/photos/7078486/pexels-photo-7078486.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"
    )

    override fun initData(savedInstanceState: Bundle?) {
        initView(savedInstanceState)
    }

    override fun initView(savedInstanceState: Bundle?) {
        _indicator = PointIndicator(this)
        vb.layoutkBannerSwitch.isChecked = _autoPlay
        vb.layoutkBannerSwitch.setOnCheckedChangeListener { _, isChecked ->
            _autoPlay = isChecked
            initBanner(_indicator, _autoPlay)
        }
        vb.layoutkBannerIndicator.setOnClickListener {
            if (_indicator is PointIndicator) {
                initBanner(NumberIndicator(this), _autoPlay)
            } else {
                initBanner(PointIndicator(this), _autoPlay)
            }
        }
        initBanner(_indicator, _autoPlay)
    }

    private fun initBanner(indicator: IBannerIndicator<*>, autoPlay: Boolean) {
        val moList: MutableList<MyBannerMo> = ArrayList()
        for (i in 0..5) {
            val mo = MyBannerMo()
            mo.url = _urls[i % _urls.size]
            mo.name = "$i: ${_urls[i]}"
            moList.add(mo)
        }
        vb.layoutkBannerContainer.apply {
            setBannerIndicator(indicator)
            setAutoPlay(autoPlay)
            setIntervalTime(5000)
            setScrollDuration(3000)
            //setCurrentItem(?)
            setBannerData(R.layout.item_layoutk_banner, moList)
            setBindAdapter(object : IBannerBindListener {
                override fun onBind(viewHolder: BannerViewHolder, item: MBannerItem, position: Int) {
                    val model = item as MyBannerMo
                    val imageView: ImageView = viewHolder.findViewById(R.id.item_layoutk_banner_img)
                    val titleView: TextView = viewHolder.findViewById(R.id.item_layoutk_banner_title)
                    model.url?.let { imageView.load(it) }
                    model.name?.let { titleView.text = it }
                }
            })
        }
    }

    inner class MyBannerMo : MBannerItem()
}