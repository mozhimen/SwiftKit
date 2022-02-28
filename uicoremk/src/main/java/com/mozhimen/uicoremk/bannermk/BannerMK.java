package com.mozhimen.uicoremk.bannermk;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.mozhimen.uicoremk.R;
import com.mozhimen.uicoremk.bannermk.commons.IBannerMK;
import com.mozhimen.uicoremk.bannermk.commons.IBannerMKBindAdapter;
import com.mozhimen.uicoremk.bannermk.commons.IBannerMKIndicator;
import com.mozhimen.uicoremk.bannermk.mos.BannerMKMo;

import java.util.List;

/**
 * @ClassName BannerMK
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/8/11 12:57
 * @Version 1.0
 */

/**
 * 核心问题:
 * 1. 如何实现UI的高度定制
 * 2. 作为有限的item如何实现无限轮播?
 * 3. Banner需要展示网络图片.如何将网络图片库和Banner组件进行解耦?
 * 4. 指示器样式各异, 如何实现指示器的高度定制?
 * 5. 如何设置Viewpager的滚动速度
 */
public class BannerMK extends FrameLayout implements IBannerMK {

    private BannerMKDelegate delegate;

    public BannerMK(@NonNull Context context) {
        this(context, null);
    }

    public BannerMK(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerMK(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        delegate = new BannerMKDelegate(context, this);
        initCustomAttrs(context, attrs);
    }

    private void initCustomAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BannerMK);
        boolean autoPlay = typedArray.getBoolean(R.styleable.BannerMK_bannerMK_autoPlay, true);
        boolean loop = typedArray.getBoolean(R.styleable.BannerMK_bannerMK_loop, true);
        int intervalTime = typedArray.getInteger(R.styleable.BannerMK_bannerMK_intervalTime, -1);
        setAutoPlay(autoPlay);
        setLoop(loop);
        setIntervalTime(intervalTime);
        typedArray.recycle();
    }

    @Override
    public void setBannerData(int layoutResId, @NonNull List<? extends BannerMKMo> models) {
        delegate.setBannerData(layoutResId, models);
    }

    @Override
    public void setBannerData(@NonNull List<? extends BannerMKMo> models) {
        delegate.setBannerData(models);
    }

    @Override
    public void setBannerIndicator(IBannerMKIndicator bannerMKIndicator) {
        delegate.setBannerIndicator(bannerMKIndicator);
    }

    @Override
    public void setAutoPlay(boolean autoPlay) {
        delegate.setAutoPlay(autoPlay);
    }

    @Override
    public void setLoop(boolean loop) {
        delegate.setLoop(loop);
    }

    @Override
    public void setIntervalTime(int intervalTime) {
        delegate.setIntervalTime(intervalTime);
    }

    @Override
    public void setCurrentItem(int position) {
        delegate.setCurrentItem(position);
    }

    @Override
    public void setBindAdapter(IBannerMKBindAdapter bindAdapter) {
        delegate.setBindAdapter(bindAdapter);
    }

    @Override
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        delegate.setOnPageChangeListener(onPageChangeListener);
    }

    @Override
    public void setOnBannerClickListener(OnBannerClickListener onBannerClickListener) {
        delegate.setOnBannerClickListener(onBannerClickListener);
    }

    @Override
    public void setScrollerDuration(int duration) {
        delegate.setScrollerDuration(duration);
    }
}
