package com.mozhimen.uicorek.bannerk;

/**
 * @ClassName BannerKDelegate
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/8/11 13:01
 * @Version 1.0
 */

import android.content.Context;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import com.mozhimen.uicorek.R;
import com.mozhimen.uicorek.bannerk.commons.IBannerK;
import com.mozhimen.uicorek.bannerk.commons.IBannerKBindAdapter;
import com.mozhimen.uicorek.bannerk.commons.IBannerKIndicator;
import com.mozhimen.uicorek.bannerk.helpers.BannerKAdapter;
import com.mozhimen.uicorek.bannerk.helpers.BannerKViewPager;
import com.mozhimen.uicorek.bannerk.mos.BannerKMo;
import com.mozhimen.uicorek.bannerk.temps.CircleIndicator;

import java.util.List;

/**
 * BannerK的控制器
 * 辅助BannerK完成各种功能的控制
 * 将BannerK的一些逻辑内聚在这,保证暴露给使用者的BannerK干净整洁
 */
public class BannerKDelegate implements IBannerK, ViewPager.OnPageChangeListener {
    private Context mContext;
    private BannerK mBannerK;

    private BannerKAdapter kAdapter;
    private IBannerKIndicator<?> kIndicator;
    private boolean mAutoPlay;
    private boolean mLoop;
    private List<? extends BannerKMo> mBannerKMos;
    private ViewPager.OnPageChangeListener mOnPageChangeListener;
    private int mIntervalTime = 5000;
    private int mCurrentItem = 0;
    private OnBannerClickListener mOnBannerClickListener;
    private BannerKViewPager kViewPager;

    private int mScrollerDuration = -1;

    public BannerKDelegate(Context context, BannerK bannerK) {
        mContext = context;
        mBannerK = bannerK;
    }

    @Override
    public void setBannerData(int layoutResId, @NonNull List<? extends BannerKMo> models) {
        mBannerKMos = models;
        init(layoutResId);
    }

    @Override
    public void setBannerData(@NonNull List<? extends BannerKMo> models) {
        setBannerData(R.layout.bannerk, models);
    }

    @Override
    public void setBannerIndicator(IBannerKIndicator bannerKIndicator) {
        this.kIndicator = bannerKIndicator;
    }

    @Override
    public void setAutoPlay(boolean autoPlay) {
        this.mAutoPlay = autoPlay;
        if (kAdapter != null) kAdapter.setAutoPlay(autoPlay);
        if (kViewPager != null) kViewPager.setAutoPlay(autoPlay);
    }

    @Override
    public void setLoop(boolean loop) {
        this.mLoop = loop;
    }

    @Override
    public void setIntervalTime(int intervalTime) {
        if (intervalTime > 0) {
            this.mIntervalTime = intervalTime;
        }
    }

    @Override
    public void setCurrentItem(int position) {
        if (mCurrentItem >= 0 && mCurrentItem < mBannerKMos.size()) {
            mCurrentItem = position;
        }
    }

    @Override
    public void setBindAdapter(IBannerKBindAdapter bindAdapter) {
        kAdapter.setKBindAdapter(bindAdapter);
    }

    @Override
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        this.mOnPageChangeListener = onPageChangeListener;
    }

    @Override
    public void setOnBannerClickListener(OnBannerClickListener onBannerClickListener) {
        this.mOnBannerClickListener = onBannerClickListener;
    }

    @Override
    public void setScrollerDuration(int duration) {
        this.mScrollerDuration = duration;
        if (kViewPager != null && duration > 0) {
            kViewPager.setScrollerDuration(duration);
        }
    }

    private void init(int layoutResId) {
        if (kAdapter == null) {
            kAdapter = new BannerKAdapter(mContext);
        }
        if (kIndicator == null) {
            kIndicator = new CircleIndicator(mContext);
        }
        kIndicator.onInflate(mBannerKMos.size());
        kAdapter.setLayoutResId(layoutResId);
        kAdapter.setBannerData(mBannerKMos);
        kAdapter.setAutoPlay(mAutoPlay);
        kAdapter.setLoop(mLoop);
        kAdapter.setOnBannerClickListener(mOnBannerClickListener);

        kViewPager = new BannerKViewPager(mContext);
        kViewPager.setIntervalTime(mIntervalTime);
        kViewPager.addOnPageChangeListener(this);
        kViewPager.setAutoPlay(mAutoPlay);
        kViewPager.setAdapter(kAdapter);
        if (mScrollerDuration > 0) {
            kViewPager.setScrollerDuration(mScrollerDuration);
        }

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        if ((mLoop || mAutoPlay) && kAdapter.getRealCount() != 0) {
            //无限轮播关键点: 使第一张能反向滑动到最后一张, 已经达到无限滚动的效果
            int firstItem = mCurrentItem != 0 ? mCurrentItem : kAdapter.getFirstItem();
            kViewPager.setCurrentItem(firstItem, false);
        }
        //清除缓存view
        mBannerK.removeAllViews();
        mBannerK.addView(kViewPager, layoutParams);
        mBannerK.addView(kIndicator.get(), layoutParams);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (mOnPageChangeListener != null && kAdapter.getRealCount() != 0) {
            mOnPageChangeListener.onPageScrolled(position % kAdapter.getRealCount(), positionOffset, positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(int position) {
        if (kAdapter.getRealCount() == 0) {
            return;
        }
        position = position % kAdapter.getRealCount();
        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageSelected(position);
        }
        if (kIndicator != null) {
            kIndicator.onPointChange(position, kAdapter.getRealCount());
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageScrollStateChanged(state);
        }
    }
}
