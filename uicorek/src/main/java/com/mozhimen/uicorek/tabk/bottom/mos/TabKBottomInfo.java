package com.mozhimen.uicorek.tabk.bottom.mos;

import android.graphics.Bitmap;
import android.graphics.Typeface;

import androidx.fragment.app.Fragment;

/**
 * @ClassName TabKBottomInfo
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/7/27 12:56
 * @Version 1.0
 */
public class TabKBottomInfo<Color> {
    public enum TabKType {
        BITMAP,
        ICON
    }

    public Class<? extends Fragment> fragment;
    public String name;
    public Bitmap defaultBitmap;
    public Bitmap selectedBitmap;
    public String iconFont;
    /**
     * Tips: 在Java代码中直接设置iconFont字符串无效,需要定义在string.xml中
     */
    public String defaultIconName;
    public String selectedIconName;
    public Color defaultColor;
    public Color tintColor;
    public TabKType tabType;

    public TabKBottomInfo(String name, Bitmap defaultBitmap, Bitmap selectedBitmap) {
        this.name = name;
        this.defaultBitmap = defaultBitmap;
        this.selectedBitmap = selectedBitmap;
        this.tabType = TabKType.BITMAP;
    }

    public TabKBottomInfo(String name, String iconFont, String defaultIconName, String selectedIconName, Color defaultColor, Color tintColor) {
        this.name = name;
        this.iconFont = iconFont;
        this.defaultIconName = defaultIconName;
        this.selectedIconName = selectedIconName;
        this.defaultColor = defaultColor;
        this.tintColor = tintColor;
        this.tabType = TabKType.ICON;
    }
}
