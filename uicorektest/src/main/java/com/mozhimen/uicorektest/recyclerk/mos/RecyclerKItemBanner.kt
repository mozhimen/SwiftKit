package com.mozhimen.uicorektest.recyclerk.mos

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.ViewPager
import coil.load
import com.mozhimen.basick.cachek.CacheKSP
import com.mozhimen.uicorek.layoutk.banner.commons.IBannerBindListener
import com.mozhimen.uicorek.layoutk.banner.temps.PointIndicator
import com.mozhimen.uicorek.layoutk.banner.helpers.BannerViewHolder
import com.mozhimen.uicorek.layoutk.banner.mos.MBannerItem
import com.mozhimen.uicorek.recyclerk.RecyclerKVBViewHolder
import com.mozhimen.uicorek.recyclerk.RecyclerKItem
import com.mozhimen.uicorektest.R
import com.mozhimen.uicorektest.databinding.ItemRecyclerkBannerBinding

/**
 * @ClassName DataKItemBanner
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/9/1 14:39
 * @Version 1.0
 */
class RecyclerKItemBanner : RecyclerKItem<Any, RecyclerKVBViewHolder<ItemRecyclerkBannerBinding>>() {
    private val RECYCLERK_ITEM_BANNER_SP_NAME = "recyclerk_item_banner_sp_name"
    private var _index = 0
    private var _urls = arrayOf(
        "https://images.pexels.com/photos/2709388/pexels-photo-2709388.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
        "https://images.pexels.com/photos/8721987/pexels-photo-8721987.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
        "https://images.pexels.com/photos/6679876/pexels-photo-6679876.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
        "https://images.pexels.com/photos/7078587/pexels-photo-7078587.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
        "https://images.pexels.com/photos/6948010/pexels-photo-6948010.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
        "https://images.pexels.com/photos/7078486/pexels-photo-7078486.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"
    )

    inner class BannerItem : MBannerItem()

    override fun onBindData(holder: RecyclerKVBViewHolder<ItemRecyclerkBannerBinding>, position: Int) {
        val context = holder.itemView.context ?: return
        val moList: MutableList<MBannerItem> = ArrayList()
        for (i in 0..5) {
            val item = BannerItem()
            item.url = _urls[i % _urls.size]
            moList.add(item)
        }
        holder.vb.itemRecyclerkBanner.apply {
            setBannerIndicator(PointIndicator(context))
            setAutoPlay(true)
            setIntervalTime(5000)
            setScrollDuration(3000)
            setBannerData(R.layout.item_layoutk_banner, moList)
            setBindAdapter(object : IBannerBindListener {
                override fun onBind(viewHolder: BannerViewHolder, item: MBannerItem, position: Int) {
                    val model = item as BannerItem
                    val imageView: ImageView = viewHolder.findViewById(R.id.item_layoutk_banner_img)
                    model.url?.let { imageView.load(it) }
                }
            })
            setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                }

                override fun onPageSelected(position: Int) {
                    _index = position
                    Log.d(TAG, "onPageSelected $position")
                }

                override fun onPageScrollStateChanged(state: Int) {}
            })
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup): RecyclerKVBViewHolder<ItemRecyclerkBannerBinding> {
        return RecyclerKVBViewHolder(LayoutInflater.from(parent.context).inflate(getItemLayoutRes(), parent, false))
    }

    override fun getItemLayoutRes() = R.layout.item_recyclerk_banner

    override fun onViewDetachedFromWindow(holder: RecyclerKVBViewHolder<ItemRecyclerkBannerBinding>) {
        CacheKSP.instance.with(RECYCLERK_ITEM_BANNER_SP_NAME).putInt("bannerIndex", _index)
    }

    override fun onViewAttachedToWindow(holder: RecyclerKVBViewHolder<ItemRecyclerkBannerBinding>) {
        val index = CacheKSP.instance.with(RECYCLERK_ITEM_BANNER_SP_NAME).getInt("bannerIndex")
        Log.d(TAG, "onViewAttachedToWindow currentIndex $index")
        holder.vb.itemRecyclerkBanner.setCurrentItem(index)
    }
}
