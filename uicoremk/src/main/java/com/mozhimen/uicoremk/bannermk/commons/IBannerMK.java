package com.mozhimen.uicoremk.bannermk.commons;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import com.mozhimen.uicoremk.bannermk.helpers.BannerMKAdapter;
import com.mozhimen.uicoremk.bannermk.mos.BannerMKMo;

import java.util.List;

public interface IBannerMK {
    void setBannerData(@LayoutRes int layoutResId, @NonNull List<? extends BannerMKMo> models);

    void setBannerData(@NonNull List<? extends BannerMKMo> models);

    void setBannerIndicator(IBannerMKIndicator<?> bannerMKIndicator);

    void setAutoPlay(boolean autoPlay);

    void setLoop(boolean loop);

    void setIntervalTime(int intervalTime);

    void setCurrentItem(int position);

    void setBindAdapter(IBannerMKBindAdapter bindAdapter);

    void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener);

    void setOnBannerClickListener(OnBannerClickListener onBannerClickListener);

    void setScrollerDuration(int duration);

    interface OnBannerClickListener {
        void onBannerClick(@NonNull BannerMKAdapter.BannerMKViewHolder viewHolder, @NonNull BannerMKMo bannerMKMo, int position);
    }
}
