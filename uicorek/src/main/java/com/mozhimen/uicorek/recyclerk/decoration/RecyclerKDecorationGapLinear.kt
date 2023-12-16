package com.mozhimen.uicorek.recyclerk.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mozhimen.basick.utilk.androidx.recyclerview.UtilKRecyclerView
import com.mozhimen.uicorek.recyclerk.decoration.bases.BaseRecyclerKDecoration

/**
 * @ClassName RecyclerDecorationGap2
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/10/30 10:29
 * @Version 1.0
 */
class RecyclerKDecorationGapLinear : BaseRecyclerKDecoration {


    constructor(gapOuter: Int) : this(gapOuter, gapOuter / 2)

    constructor(gapOuter: Int, gapInner: Int) : this(gapOuter, gapInner, gapOuter)

    constructor(gapOuter: Int, gapInner: Int, gapOther: Int) {
        _gapOuter = gapOuter
        _gapInner = gapInner
        _gapOther = gapOther
    }

    ////////////////////////////////////////////////////////////////

    private var _gapOuter: Int = 0
    private var _gapInner: Int = 0
    private var _gapOther: Int = 0//另外两边的gap距离

    ////////////////////////////////////////////////////////////////

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        UtilKRecyclerView.equilibriumAssignmentOfLinearLayoutManager(parent, view, outRect, _gapOuter, _gapInner, _gapOther)
    }
}