package com.mozhimen.uicorek.bannerk;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.mozhimen.uicorek.R;
import com.mozhimen.uicorek.bannerk.commons.IBannerK;
import com.mozhimen.uicorek.bannerk.commons.IBannerKBindAdapter;
import com.mozhimen.uicorek.bannerk.commons.IBannerKIndicator;
import com.mozhimen.uicorek.bannerk.mos.BannerKMo;

import java.util.List;

/**
 * @ClassName BannerK
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
public class BannerK extends FrameLayout implements IBannerK {

    private BannerKDelegate delegate;

    public BannerK(@NonNull Context context) {
        this(context, null);
    }

    public BannerK(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerK(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        delegate = new BannerKDelegate(context, this);
        initCustomAttrs(context, attrs);
    }

    private void initCustomAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BannerK);
        boolean autoPlay = typedArray.getBoolean(R.styleable.BannerK_bannerK_autoPlay, true);
        boolean loop = typedArray.getBoolean(R.styleable.BannerK_bannerK_loop, true);
        int intervalTime = typedArray.getInteger(R.styleable.BannerK_bannerK_intervalTime, -1);
        setAutoPlay(autoPlay);
        setLoop(loop);
        setIntervalTime(intervalTime);
        typedArray.recycle();
    }

    @Override
    public void setBannerData(int layoutResId, @NonNull List<? extends BannerKMo> models) {
        delegate.setBannerData(layoutResId, models);
    }

    @Override
    public void setBannerData(@NonNull List<? extends BannerKMo> models) {
        delegate.setBannerData(models);
    }

    @Override
    public void setBannerIndicator(IBannerKIndicator bannerKIndicator) {
        delegate.setBannerIndicator(bannerKIndicator);
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
    public void setBindAdapter(IBannerKBindAdapter bindAdapter) {
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
