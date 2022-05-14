package com.mozhimen.app.uicorek.datak.mos

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.ViewPager
import com.mozhimen.app.R
import com.mozhimen.app.databinding.ItemDatakBannerBinding
import com.mozhimen.basicsk.extsk.load
import com.mozhimen.basicsk.logk.LogK
import com.mozhimen.basicsk.utilk.UtilKSP
import com.mozhimen.uicorek.bannerk.commons.IBannerKBindAdapter
import com.mozhimen.uicorek.bannerk.helpers.BannerKAdapter
import com.mozhimen.uicorek.bannerk.mos.BannerKMo
import com.mozhimen.uicorek.bannerk.customs.PointIndicator
import com.mozhimen.uicorek.bindk.BindKViewHolder
import com.mozhimen.uicorek.datak.commons.DataKItem

/**
 * @ClassName DataKItemBanner
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/9/1 14:39
 * @Version 1.0
 */
class DataKItemBanner : DataKItem<Any, BindKViewHolder<ItemDatakBannerBinding>>() {

    private var _index = 0
    private var _urls = arrayOf(
        "https://images.pexels.com/photos/2709388/pexels-photo-2709388.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
        "https://images.pexels.com/photos/8721987/pexels-photo-8721987.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
        "https://images.pexels.com/photos/6679876/pexels-photo-6679876.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
        "https://images.pexels.com/photos/7078587/pexels-photo-7078587.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
        "https://images.pexels.com/photos/6948010/pexels-photo-6948010.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
        "https://images.pexels.com/photos/7078486/pexels-photo-7078486.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"
    )

    inner class ItemMo : BannerKMo()

    override fun onBindData(holder: BindKViewHolder<ItemDatakBannerBinding>, position: Int) {
        val context = holder.itemView.context ?: return
        val moList: MutableList<BannerKMo> = ArrayList()
        for (i in 0..5) {
            val mo = ItemMo()
            mo.url = _urls[i % _urls.size]
            moList.add(mo)
        }
        holder.binding.datakItemBanner.apply {
            setBannerIndicator(PointIndicator(context))
            setAutoPlay(true)
            setIntervalTime(5000)
            setScrollDuration(3000)
            setBannerData(R.layout.item_bannerk, moList)
            setBindAdapter(object : IBannerKBindAdapter {
                override fun onBind(viewHolder: BannerKAdapter.BannerKViewHolder, mo: BannerKMo, position: Int) {
                    val model = mo as ItemMo
                    val imageView: ImageView = viewHolder.findViewById(R.id.item_bannerk_img)
                    model.url?.let { imageView.load(it) }
                }
            })
            setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                }

                override fun onPageSelected(position: Int) {
                    _index = position
                    LogK.it(TAG, "onPageSelected $position")
                }

                override fun onPageScrollStateChanged(state: Int) {}
            })
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup): BindKViewHolder<ItemDatakBannerBinding> {
        return BindKViewHolder(LayoutInflater.from(parent.context).inflate(getItemLayoutRes(), parent, false))
    }

    override fun getItemLayoutRes() = R.layout.item_datak_banner

    override fun onViewDetachedFromWindow(holder: BindKViewHolder<ItemDatakBannerBinding>) {
        UtilKSP.instance.with(TAG).setInt("bannerIndex", _index)
    }

    override fun onViewAttachedToWindow(holder: BindKViewHolder<ItemDatakBannerBinding>) {
        val index = UtilKSP.instance.with(TAG).getInt("bannerIndex")
        LogK.it(TAG, "onViewAttachedToWindow currentIndex $index")
        holder.binding.datakItemBanner.setCurrentItem(index)
    }
}
