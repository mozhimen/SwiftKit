package com.mozhimen.uicoremk.tabmk.commons;

import androidx.annotation.NonNull;
import androidx.annotation.Px;

/**
 * @ClassName ITabMK
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/7/27 12:52
 * @Version 1.0
 */
public interface ITabMK<D> extends ITabMKLayout.OnTabSelectedListener<D> {
    void setTabInfo(@NonNull D data);

    /**
     * 动态修改某个item的大小
     *
     * @param height
     */
    void resetHeight(@Px int height);
}
