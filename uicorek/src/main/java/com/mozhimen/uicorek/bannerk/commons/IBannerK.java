package com.mozhimen.uicorek.bannerk.commons;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import com.mozhimen.uicorek.bannerk.helpers.BannerKAdapter;
import com.mozhimen.uicorek.bannerk.mos.BannerKMo;

import java.util.List;

public interface IBannerK {
    void setBannerData(@LayoutRes int layoutResId, @NonNull List<? extends BannerKMo> models);

    void setBannerData(@NonNull List<? extends BannerKMo> models);

    void setBannerIndicator(IBannerKIndicator<?> bannerKIndicator);

    void setAutoPlay(boolean autoPlay);

    void setLoop(boolean loop);

    void setIntervalTime(int intervalTime);

    void setCurrentItem(int position);

    void setBindAdapter(IBannerKBindAdapter bindAdapter);

    void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener);

    void setOnBannerClickListener(OnBannerClickListener onBannerClickListener);

    void setScrollerDuration(int duration);

    interface OnBannerClickListener {
        void onBannerClick(@NonNull BannerKAdapter.BannerKViewHolder viewHolder, @NonNull BannerKMo bannerKMo, int position);
    }
}
