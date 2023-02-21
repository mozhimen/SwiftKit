package com.mozhimen.componentk.camerak.preview;

/**
 * @ClassName ScalableType
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2023/2/21 21:45
 * @Version 1.0
 */
public enum ScalableType {
    NONE,
    FIT_XY,
    FIT_START,
    FIT_CENTER,
    FIT_END,
    LEFT_TOP,
    LEFT_CENTER,
    LEFT_BOTTOM,
    CENTER_TOP,
    CENTER,
    CENTER_BOTTOM,
    RIGHT_TOP,
    RIGHT_CENTER,
    RIGHT_BOTTOM,
    LEFT_TOP_CROP,
    LEFT_CENTER_CROP,
    LEFT_BOTTOM_CROP,
    CENTER_TOP_CROP,
    CENTER_CROP,
    CENTER_BOTTOM_CROP,
    RIGHT_TOP_CROP,
    RIGHT_CENTER_CROP,
    RIGHT_BOTTOM_CROP,
    START_INSIDE,
    CENTER_INSIDE,
    END_INSIDE;

    private ScalableType() {
    }
}
