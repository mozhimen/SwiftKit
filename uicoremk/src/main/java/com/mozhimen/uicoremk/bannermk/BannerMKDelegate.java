package com.mozhimen.uicoremk.bannermk;

/**
 * @ClassName BannerMKDelegate
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/8/11 13:01
 * @Version 1.0
 */

import android.content.Context;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import com.mozhimen.uicoremk.R;
import com.mozhimen.uicoremk.bannermk.commons.IBannerMK;
import com.mozhimen.uicoremk.bannermk.commons.IBannerMKBindAdapter;
import com.mozhimen.uicoremk.bannermk.commons.IBannerMKIndicator;
import com.mozhimen.uicoremk.bannermk.helpers.BannerMKAdapter;
import com.mozhimen.uicoremk.bannermk.helpers.BannerMKViewPager;
import com.mozhimen.uicoremk.bannermk.mos.BannerMKMo;

import java.util.List;

/**
 * BannerMK的控制器
 * 辅助BannerMK完成各种功能的控制
 * 将BannerMK的一些逻辑内聚在这,保证暴露给使用者的BannerMK干净整洁
 */
public class BannerMKDelegate implements IBannerMK, ViewPager.OnPageChangeListener {
    private Context mContext;
    private BannerMK mBannerMK;

    private BannerMKAdapter mkAdapter;
    private IBannerMKIndicator<?> mkIndicator;
    private boolean mAutoPlay;
    private boolean mLoop;
    private List<? extends BannerMKMo> mBannerMKMos;
    private ViewPager.OnPageChangeListener mOnPageChangeListener;
    private int mIntervalTime = 5000;
    private int mCurrentItem = 0;
    private OnBannerClickListener mOnBannerClickListener;
    private BannerMKViewPager mkViewPager;

    private int mScrollerDuration = -1;

    public BannerMKDelegate(Context context, BannerMK bannerMK) {
        mContext = context;
        mBannerMK = bannerMK;
    }

    @Override
    public void setBannerData(int layoutResId, @NonNull List<? extends BannerMKMo> models) {
        mBannerMKMos = models;
        init(layoutResId);
    }

    @Override
    public void setBannerData(@NonNull List<? extends BannerMKMo> models) {
        setBannerData(R.layout.bannermk, models);
    }

    @Override
    public void setBannerIndicator(IBannerMKIndicator bannerMKIndicator) {
        this.mkIndicator = bannerMKIndicator;
    }

    @Override
    public void setAutoPlay(boolean autoPlay) {
        this.mAutoPlay = autoPlay;
        if (mkAdapter != null) mkAdapter.setAutoPlay(autoPlay);
        if (mkViewPager != null) mkViewPager.setAutoPlay(autoPlay);
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
        if (mCurrentItem >= 0 && mCurrentItem < mBannerMKMos.size()) {
            mCurrentItem = position;
        }
    }

    @Override
    public void setBindAdapter(IBannerMKBindAdapter bindAdapter) {
        mkAdapter.setMKBindAdapter(bindAdapter);
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
        if (mkViewPager != null && duration > 0) {
            mkViewPager.setScrollerDuration(duration);
        }
    }

    private void init(int layoutResId) {
        if (mkAdapter == null) {
            mkAdapter = new BannerMKAdapter(mContext);
        }
        if (mkIndicator == null) {
            mkIndicator = new CircleIndicator(mContext);
        }
        mkIndicator.onInflate(mBannerMKMos.size());
        mkAdapter.setLayoutResId(layoutResId);
        mkAdapter.setBannerData(mBannerMKMos);
        mkAdapter.setAutoPlay(mAutoPlay);
        mkAdapter.setLoop(mLoop);
        mkAdapter.setOnBannerClickListener(mOnBannerClickListener);

        mkViewPager = new BannerMKViewPager(mContext);
        mkViewPager.setIntervalTime(mIntervalTime);
        mkViewPager.addOnPageChangeListener(this);
        mkViewPager.setAutoPlay(mAutoPlay);
        mkViewPager.setAdapter(mkAdapter);
        if (mScrollerDuration > 0) {
            mkViewPager.setScrollerDuration(mScrollerDuration);
        }

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        if ((mLoop || mAutoPlay) && mkAdapter.getRealCount() != 0) {
            //无限轮播关键点: 使第一张能反向滑动到最后一张, 已经达到无限滚动的效果
            int firstItem = mCurrentItem != 0 ? mCurrentItem : mkAdapter.getFirstItem();
            mkViewPager.setCurrentItem(firstItem, false);
        }
        //清除缓存view
        mBannerMK.removeAllViews();
        mBannerMK.addView(mkViewPager, layoutParams);
        mBannerMK.addView(mkIndicator.get(), layoutParams);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (mOnPageChangeListener != null && mkAdapter.getRealCount() != 0) {
            mOnPageChangeListener.onPageScrolled(position % mkAdapter.getRealCount(), positionOffset, positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(int position) {
        if (mkAdapter.getRealCount() == 0) {
            return;
        }
        position = position % mkAdapter.getRealCount();
        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageSelected(position);
        }
        if (mkIndicator != null) {
            mkIndicator.onPointChange(position, mkAdapter.getRealCount());
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageScrollStateChanged(state);
        }
    }
}
