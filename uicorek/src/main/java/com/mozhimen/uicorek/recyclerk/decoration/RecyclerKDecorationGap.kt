package com.mozhimen.uicorek.recyclerk.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mozhimen.uicorek.recyclerk.decoration.bases.BaseRecyclerKDecoration

/**
 * @ClassName RecyclerKItemDecoration
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/10/30 10:02
 * @Version 1.0
 */
class RecyclerKDecorationGap : BaseRecyclerKDecoration {
    constructor(left: Int, top: Int, right: Int, bottom: Int) {
        _left = left
        _top = top
        _right = right
        _bottom = bottom
    }

    constructor(horizontal: Int, vertical: Int) : this(horizontal, vertical, horizontal, vertical)

    constructor(gap: Int) : this(gap, gap)

    /////////////////////////////////////////////////////////////////////

    private var _left = 0
    private var _top = 0
    private var _right = 0
    private var _bottom = 0

    /////////////////////////////////////////////////////////////////////

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        //设置间距
        outRect.left = _left
        outRect.top = _top
        outRect.right = _right
        outRect.bottom = _bottom
    }
}