package com.mozhimen.uicoremk.bannermk.commons;

import com.mozhimen.uicoremk.bannermk.helpers.BannerMKAdapter;
import com.mozhimen.uicoremk.bannermk.mos.BannerMKMo;

/**
 * IBannerMKBindAdapter的数据绑定接口,基于该接口可以实现数据的绑定和框架层解耦
 */
public interface IBannerMKBindAdapter {
    void onBind(BannerMKAdapter.BannerMKViewHolder viewHolder, BannerMKMo mo, int position);
}
