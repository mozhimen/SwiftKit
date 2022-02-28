package com.mozhimen.uicoremk.tabmk.top;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

import com.mozhimen.uicoremk.R;
import com.mozhimen.uicoremk.tabmk.commons.ITabMK;
import com.mozhimen.uicoremk.tabmk.top.mos.TabMKTopInfo;

/**
 * @ClassName TabMKBottom
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/7/27 13:09
 * @Version 1.0
 */

public class TabMKTop extends RelativeLayout implements ITabMK<TabMKTopInfo<?>> {

    private TabMKTopInfo<?> tabInfo;
    private ImageView tabImageView;
    private TextView tabNameView;
    private View indicator;

    public TabMKTop(Context context) {
        this(context, null);
    }

    public TabMKTop(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabMKTop(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.tabmk_top, this);
        tabImageView = findViewById(R.id.tabmk_top_image);
        tabNameView = findViewById(R.id.tabmk_top_name);
        indicator = findViewById(R.id.tabmk_top_indicator);
    }

    @Override
    public void setTabInfo(@NonNull TabMKTopInfo tabMKTopInfo) {
        this.tabInfo = tabMKTopInfo;
        inflateInfo(false, true);
    }

    private void inflateInfo(boolean selected, boolean init) {
        if (tabInfo.tabType == TabMKTopInfo.TabMKType.TEXT) {
            if (init) {
                tabImageView.setVisibility(GONE);
                tabNameView.setVisibility(VISIBLE);
                if (!TextUtils.isEmpty(tabInfo.name)) {
                    tabNameView.setText(tabInfo.name);
                }
            }
            if (selected) {
                indicator.setVisibility(VISIBLE);
                tabNameView.setTextColor(getTextColor(tabInfo.tintColor));
            } else {
                indicator.setVisibility(GONE);
                tabNameView.setTextColor(getTextColor(tabInfo.defaultColor));
            }
        } else if (tabInfo.tabType == TabMKTopInfo.TabMKType.BITMAP) {
            if (init) {
                tabImageView.setVisibility(VISIBLE);
                tabNameView.setVisibility(GONE);
            }
            if (selected) {
                tabImageView.setImageBitmap(tabInfo.selectedBitmap);
            } else {
                tabImageView.setImageBitmap(tabInfo.defaultBitmap);
            }
        }
    }

    /**
     * 改变某个Tab的高度
     *
     * @param height
     */
    @Override
    public void resetHeight(int height) {
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        layoutParams.height = height;
        setLayoutParams(layoutParams);
        getTabNameView().setVisibility(GONE);
    }

    @Override
    public void onTabSelectedChange(int index, @NonNull TabMKTopInfo<?> prevInfo, @NonNull TabMKTopInfo<?> nextInfo) {
        if (prevInfo != tabInfo && nextInfo != tabInfo || prevInfo == nextInfo) {
            return;
        }
        if (prevInfo == tabInfo) {
            inflateInfo(false, false);
        } else {
            inflateInfo(true, false);
        }
    }

    public TabMKTopInfo<?> getTabMKInfo() {
        return tabInfo;
    }

    public ImageView getTabImageView() {
        return tabImageView;
    }

    public TextView getTabNameView() {
        return tabNameView;
    }

    @ColorInt
    private int getTextColor(Object color) {
        if (color instanceof String) {
            return Color.parseColor((String) color);
        } else {
            return (int) color;
        }
    }
}
