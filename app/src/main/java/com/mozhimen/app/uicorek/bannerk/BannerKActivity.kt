package com.mozhimen.app.uicorek.bannerk

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.mozhimen.app.R
import com.mozhimen.app.databinding.ActivityBannerkBinding
import com.mozhimen.basicsk.extsk.load
import com.mozhimen.uicorek.bannerk.commons.IBannerKBindAdapter
import com.mozhimen.uicorek.bannerk.commons.IBannerKIndicator
import com.mozhimen.uicorek.bannerk.helpers.BannerKAdapter
import com.mozhimen.uicorek.bannerk.mos.BannerKMo
import com.mozhimen.uicorek.bannerk.customs.NumberIndicator
import com.mozhimen.uicorek.bannerk.customs.PointIndicator

class BannerKActivity : AppCompatActivity() {

    private val vb: ActivityBannerkBinding by lazy { ActivityBannerkBinding.inflate(layoutInflater) }
    private var _autoPlay = true
    private lateinit var _indicator: IBannerKIndicator<*>

    private var _urls = arrayOf(
        "https://images.pexels.com/photos/2709388/pexels-photo-2709388.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
        "https://images.pexels.com/photos/8721987/pexels-photo-8721987.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
        "https://images.pexels.com/photos/6679876/pexels-photo-6679876.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
        "https://images.pexels.com/photos/7078587/pexels-photo-7078587.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
        "https://images.pexels.com/photos/6948010/pexels-photo-6948010.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
        "https://images.pexels.com/photos/7078486/pexels-photo-7078486.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)

        initView()
    }

    private fun initView() {
        _indicator = PointIndicator(this)
        vb.bannerkSwitch.isChecked = _autoPlay
        vb.bannerkSwitch.setOnCheckedChangeListener { _, isChecked ->
            _autoPlay = isChecked
            initBanner(_indicator, _autoPlay)
        }
        vb.bannerkIndicator.setOnClickListener {
            if (_indicator is PointIndicator) {
                initBanner(NumberIndicator(this), _autoPlay)
            } else {
                initBanner(PointIndicator(this), _autoPlay)
            }
        }
        initBanner(_indicator, _autoPlay)
    }

    private fun initBanner(indicator: IBannerKIndicator<*>, autoPlay: Boolean) {
        val moList: MutableList<MyBannerKMo> = ArrayList()
        for (i in 0..5) {
            val mo = MyBannerKMo()
            mo.url = _urls[i % _urls.size]
            mo.name = "$i: ${_urls[i]}"
            moList.add(mo)
        }
        vb.bannerkContainer.apply {
            setBannerIndicator(indicator)
            setAutoPlay(autoPlay)
            setIntervalTime(5000)
            setScrollDuration(3000)
            //setCurrentItem(?)
            setBannerData(R.layout.item_bannerk, moList)
            setBindAdapter(object : IBannerKBindAdapter {
                override fun onBind(viewHolder: BannerKAdapter.BannerKViewHolder, mo: BannerKMo, position: Int) {
                    val model = mo as MyBannerKMo
                    val imageView: ImageView = viewHolder.findViewById(R.id.item_bannerk_img)
                    val titleView: TextView = viewHolder.findViewById(R.id.item_bannerk_title)
                    model.url?.let { imageView.load(it) }
                    model.name?.let { titleView.text = it }
                }
            })
        }
    }

    inner class MyBannerKMo : BannerKMo() {
        var name: String? = null
    }
}