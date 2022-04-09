package com.mozhimen.uicorek.bannerk.commons;

import android.view.View;

/**
 * 指示器统一接口
 * 实现该接口来定义你需要央视的指示器
 *
 * @param <T>
 */
public interface IBannerKIndicator<T extends View> {
    T get();

    /**
     * 初始化Indicator
     *
     * @param count 幻灯片数量
     */
    void onInflate(int count);

    /**
     * 幻灯片切换回调
     *
     * @param current 切换到的幻灯片位置
     * @param count   幻灯片数量
     */
    void onPointChange(int current, int count);
}
