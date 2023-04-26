package com.mozhimen.uicorektest.recyclerk.mos

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import coil.load
import com.mozhimen.basick.cachek.cacheksp.CacheKSP
import com.mozhimen.uicorek.layoutk.banner.IBannerItemChangeListener
import com.mozhimen.uicorek.layoutk.banner.bases.BaseBannerItem
import com.mozhimen.uicorek.layoutk.banner.commons.IBannerBindListener
import com.mozhimen.uicorek.layoutk.banner.temps.PointIndicator
import com.mozhimen.uicorek.layoutk.banner.helpers.BannerViewHolder
import com.mozhimen.uicorek.vhk.VHKRecyclerVB
import com.mozhimen.uicorek.recyclerk.bases.BaseRecyclerKItem
import com.mozhimen.uicorektest.R
import com.mozhimen.uicorektest.databinding.ItemRecyclerkBannerBinding

/**
 * @ClassName DataKItemBanner
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/9/1 14:39
 * @Version 1.0
 */
class RecyclerKItemBanner : BaseRecyclerKItem<VHKRecyclerVB<ItemRecyclerkBannerBinding>>() {
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

    inner class BannerItem : BaseBannerItem()

    override fun onBindItem(holder: VHKRecyclerVB<ItemRecyclerkBannerBinding>, position: Int) {
        super.onBindItem(holder, position)
        val context = holder.itemView.context ?: return
        val moList: MutableList<BaseBannerItem> = ArrayList()
        for (i in 0..5) {
            val item = BannerItem()
            item.url = _urls[i % _urls.size]
            moList.add(item)
        }
        holder.VB.itemRecyclerkBanner.apply {
            setBannerIndicator(PointIndicator(context))
            setAutoPlay(true)
            setIntervalTime(5000)
            setScrollDuration(3000)
            setBannerData(R.layout.item_layoutk_banner, moList)
            setBannerBindListener(object : IBannerBindListener {
                override fun onBannerBind(viewHolder: BannerViewHolder, item: BaseBannerItem, position: Int) {
                    val model = item as BannerItem
                    val imageView: ImageView = viewHolder.findViewById(R.id.item_layoutk_banner_img)
                    model.url?.let { imageView.load(it) }
                }
            })
            setPagerChangeListener(object : IBannerItemChangeListener {
                override fun onPageSelected(position: Int) {
                    _index = position
                    Log.d(TAG, "onPageSelected $position")
                }
            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup): VHKRecyclerVB<ItemRecyclerkBannerBinding> {
        return VHKRecyclerVB(LayoutInflater.from(parent.context).inflate(getItemLayoutId(), parent, false))
    }

    override fun getItemLayoutId() = R.layout.item_recyclerk_banner

    override fun onViewDetachedFromWindow(holder: VHKRecyclerVB<ItemRecyclerkBannerBinding>) {
        CacheKSP.instance.with(RECYCLERK_ITEM_BANNER_SP_NAME).putInt("bannerIndex", _index)
    }

    override fun onViewAttachedToWindow(holder: VHKRecyclerVB<ItemRecyclerkBannerBinding>) {
        val index = CacheKSP.instance.with(RECYCLERK_ITEM_BANNER_SP_NAME).getInt("bannerIndex")
        Log.d(TAG, "onViewAttachedToWindow currentIndex $index")
        holder.VB.itemRecyclerkBanner.setCurrentPosition(index, false)
    }
}
