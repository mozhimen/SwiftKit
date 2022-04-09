package com.mozhimen.uicorek.bannerk.commons;

import com.mozhimen.uicorek.bannerk.helpers.BannerKAdapter;
import com.mozhimen.uicorek.bannerk.mos.BannerKMo;

/**
 * IBannerKBindAdapter的数据绑定接口,基于该接口可以实现数据的绑定和框架层解耦
 */
public interface IBannerKBindAdapter {
    void onBind(BannerKAdapter.BannerKViewHolder viewHolder, BannerKMo mo, int position);
}
