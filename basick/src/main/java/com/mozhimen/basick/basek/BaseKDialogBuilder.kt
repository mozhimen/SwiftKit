package com.mozhimen.basick.basek

import android.view.ViewGroup

/**
 * @ClassName BaseKDialogBuilder
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/5/20 17:03
 * @Version 1.0
 */
abstract class BaseKDialogBuilder {
    abstract var _layoutId: Int

    /**
     * 单位: dp
     */
    abstract var _width: Float

    /**
     * 单位: dp
     */
    abstract var _height: Float

    abstract var _layoutParams: ViewGroup.LayoutParams

    abstract var _animStyleId: Int?

    abstract var _styleId: Int

    open var _cancelable: Boolean = true

    open fun setLayoutParams(layoutParams: ViewGroup.LayoutParams): BaseKDialogBuilder {
        _layoutParams = layoutParams
        return this
    }

    open fun setLayoutId(layoutId: Int): BaseKDialogBuilder {
        _layoutId = layoutId
        return this
    }

    open fun setLayout(width: Float, height: Float): BaseKDialogBuilder {
        _width = width
        _height = height
        return this
    }

    open fun setCancelable(cancelable: Boolean): BaseKDialogBuilder {
        _cancelable = cancelable
        return this
    }

    open fun setAnimStyleId(animStyleId: Int): BaseKDialogBuilder {
        _animStyleId = animStyleId
        return this
    }

    open fun setStyleId(styleId: Int): BaseKDialogBuilder {
        _styleId = styleId
        return this
    }
}