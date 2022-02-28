 package com.mozhimen.uicoremk.bannermk.helpers;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.mozhimen.uicoremk.bannermk.mos.BannerMKMo;
import com.mozhimen.uicoremk.bannermk.commons.IBannerMK;
import com.mozhimen.uicoremk.bannermk.commons.IBannerMKBindAdapter;

import java.util.List;

/**
 * ViewPagerMK的适配器,为页面填充数据
 */
public class BannerMKAdapter extends PagerAdapter {
    private Context mContext;
    private SparseArray<BannerMKViewHolder> mCachedViews = new SparseArray<>();
    private IBannerMK.OnBannerClickListener mBannerClickListener;
    private IBannerMKBindAdapter mkBindAdapter;
    private List<? extends BannerMKMo> models;

    /**
     * 是否开启自动轮播
     */
    private boolean mAutoPlay = true;

    /**
     * 非自动轮播状态下是否可以循环切换
     */
    private boolean mLoop = false;

    private int mLayoutResId = -1;

    public BannerMKAdapter(Context context) {
        this.mContext = context;
    }

    public void setBannerData(@NonNull List<? extends BannerMKMo> models) {
        this.models = models;
        //初始化数据
        initCachedView();
        notifyDataSetChanged();
    }

    public void setMKBindAdapter(IBannerMKBindAdapter bindAdapter) {
        this.mkBindAdapter = bindAdapter;
    }

    public void setOnBannerClickListener(IBannerMK.OnBannerClickListener onBannerClickListener) {
        this.mBannerClickListener = onBannerClickListener;
    }

    public void setLayoutResId(@LayoutRes int layoutResId) {
        this.mLayoutResId = layoutResId;
    }

    public void setAutoPlay(boolean autoPlay) {
        this.mAutoPlay = autoPlay;
    }

    public void setLoop(boolean loop) {
        this.mLoop = loop;
    }

    @Override
    public int getCount() {
        //无限轮播
        return mAutoPlay ? Integer.MAX_VALUE : (mLoop ? Integer.MAX_VALUE : getRealCount());
    }

    /**
     * 获取Banner页面的数量
     *
     * @return
     */
    public int getRealCount() {
        return models == null ? 0 : models.size();
    }

    /**
     * 获取初次展示的item位置
     *
     * @return
     */
    public int getFirstItem() {
        return Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2) % getRealCount();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        int realPosition = position;
        if (getRealCount() > 0) {
            realPosition = position % getRealCount();
        }
        BannerMKViewHolder viewHolder = mCachedViews.get(realPosition);
        if (container.equals(viewHolder.rootView.getParent())) {
            container.removeView(viewHolder.rootView);
        }
        //数据绑定
        onBind(viewHolder, models.get(realPosition), realPosition);
        if (viewHolder.rootView.getParent() != null) {
            ((ViewGroup) viewHolder.rootView.getParent()).removeView(viewHolder.rootView);
        }
        container.addView(viewHolder.rootView);
        return viewHolder.rootView;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        //让item每次都会刷新
        return POSITION_NONE;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

    }

    public static class BannerMKViewHolder {
        private SparseArray<View> viewSparseArray;
        View rootView;

        public BannerMKViewHolder(View rootView) {
            this.rootView = rootView;
        }

        public View getRootView() {
            return rootView;
        }

        public <V extends View> V findViewById(int id) {
            if (!(rootView instanceof ViewGroup)) {
                return (V) rootView;
            }
            if (this.viewSparseArray == null) {
                this.viewSparseArray = new SparseArray<>(1);
            }
            V childView = (V) viewSparseArray.get(id);
            if (childView == null) {
                childView = rootView.findViewById(id);
                this.viewSparseArray.put(id, childView);
            }
            return childView;
        }
    }

    protected void onBind(@NonNull final BannerMKViewHolder viewHolder, @NonNull final BannerMKMo bannerMKMos, final int position) {
        viewHolder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBannerClickListener != null) {
                    mBannerClickListener.onBannerClick(viewHolder, bannerMKMos, position);
                }
            }
        });
        if (mkBindAdapter != null) {
            mkBindAdapter.onBind(viewHolder, bannerMKMos, position);
        }
    }

    private void initCachedView() {
        mCachedViews = new SparseArray<>();
        for (int i = 0; i < models.size(); i++) {
            BannerMKViewHolder viewHolder = new BannerMKViewHolder(createView(LayoutInflater.from(mContext), null));
            mCachedViews.put(i, viewHolder);
        }
    }

    private View createView(LayoutInflater layoutInflater, ViewGroup parent) {
        if (mLayoutResId == -1) {
            throw new IllegalArgumentException("you must be set setLayoutResId first");
        }
        return layoutInflater.inflate(mLayoutResId, parent, false);
    }
}
