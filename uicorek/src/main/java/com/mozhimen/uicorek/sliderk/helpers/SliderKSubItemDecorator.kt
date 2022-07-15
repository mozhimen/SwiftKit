package com.mozhimen.uicorek.sliderk.helpers

import android.graphics.*
import android.text.TextUtils
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mozhimen.basick.extsk.dp2px
import com.mozhimen.basick.extsk.sp2px

/**
 * @ClassName SliderKSubItemDecorator
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/7/15 15:09
 * @Version 1.0
 */
class SliderKSubItemDecorator(
    private val _spanCount: Int,
    private val _textSize: Int,
    private val _textColor: Int,
    val onGetGroupNameByPos: (position: Int) -> String?
) : RecyclerView.ItemDecoration() {
    private val _groupFirstPositions = mutableMapOf<String, Int>()
    private val _paint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        initPaint()
    }

    /**
     * 清除
     */
    fun clear() {
        _groupFirstPositions.clear()
    }

    private fun initPaint() {
        _paint.style = Paint.Style.FILL
        _paint.color = Color.BLACK
        _paint.isFakeBoldText = true
        _paint.textSize = 16f.sp2px().toFloat()
        _paint.setTypeface(Typeface.create(Typeface.DEFAULT,))
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {

        //1. 根据 view对象，找到他在列表中处于的位置 adapterPosition
        val adapterPosition = parent.getChildAdapterPosition(view)
        if (adapterPosition >= parent.adapter!!.itemCount || adapterPosition < 0) return

        //2.拿到当前位置adapterPosition  对应的 groupName
        val groupName: String = onGetGroupNameByPos(adapterPosition)!!
        //3.拿到前面一个位置的 groupName
        val preGroupName = if (adapterPosition > 0) onGetGroupNameByPos(adapterPosition - 1) else null
        val sameGroup = TextUtils.equals(groupName, preGroupName)
        if (!sameGroup && !_groupFirstPositions.containsKey(groupName)) {
            //就说明当前位置adapterPosition   对应的item 是 当前组的 第一个位置。
            //此时 咱们存储起来，记录下来，目的是为了方便后面扥计算，计算 后面item  是否是第一行
            _groupFirstPositions[groupName] = adapterPosition
        }

        val firstRowPosition = _groupFirstPositions[groupName] ?: 0
        val samRow = adapterPosition - firstRowPosition in 0 until _spanCount  //3
        if (!sameGroup || samRow)
            outRect.set(0, 40f.dp2px(), 0, 0)
        else
            outRect.set(0, 0, 0, 0)
    }


    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val childCount = parent.childCount
        for (index in 0 until childCount) {
            val view = parent.getChildAt(index)
            val adapterPosition = parent.getChildAdapterPosition(view)
            if (adapterPosition >= parent.adapter!!.itemCount || adapterPosition < 0) continue
            val groupName: String = onGetGroupNameByPos(adapterPosition)!!

            //判断当前位置 是不是分组的第一个位置
            //如果是，咱们在他的位置上绘制标题
            val groupFirstPosition = _groupFirstPositions[groupName]
            if (groupFirstPosition == adapterPosition) {
                val decorationBounds = Rect()
                //为了拿到当前item 的 左上右下的坐标信息 包含了，margin 和 扩展空间的
                parent.getDecoratedBoundsWithMargins(view, decorationBounds)

                val textBounds = Rect()
                _paint.getTextBounds(groupName, 0, groupName.length, textBounds)

                c.drawText(
                    groupName,
                    16f.dp2px().toFloat(),
                    (decorationBounds.top + 2 * textBounds.height()).toFloat(),
                    _paint
                )
            }
        }
    }
}