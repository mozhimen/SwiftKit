package com.mozhimen.uicorektest.drawablek

import android.graphics.Color
import android.os.Bundle
import com.mozhimen.basick.basek.BaseKActivityVB
import com.mozhimen.basick.extsk.dp2px
import com.mozhimen.basick.utilk.UtilKRes
import com.mozhimen.uicorek.drawablek.arrow.DrawableKArrow
import com.mozhimen.uicorek.drawablek.arrow.cons.EArrowDirection
import com.mozhimen.uicorek.drawablek.arrow.cons.EArrowPosPolicy
import com.mozhimen.uicorektest.R
import com.mozhimen.uicorektest.databinding.ActivityDrawablekArrowBinding

class DrawableKArrowActivity : BaseKActivityVB<ActivityDrawablekArrowBinding>() {
    private var drawableKArrow: DrawableKArrow? = null
        get() {
            if (field != null) return field
            val drawableKArrow = DrawableKArrow()
            drawableKArrow.apply {
                resetRect(vb.drawablekArrow1.width, vb.drawablekArrow1.height)

                setFillColor(Color.BLACK)
                setGapWidth(5f.dp2px().toFloat())
                setCornerRadius(10f.dp2px().toFloat())

                setBorderWidth(3f.dp2px().toFloat())
                setBorderColor(UtilKRes.getColor(R.color.blue_normal))

                setArrowWidth(20f.dp2px().toFloat())
                setArrowHeight(10f.dp2px().toFloat())
                setArrowDirection(EArrowDirection.Left)
                setArrowPosPolicy(EArrowPosPolicy.TargetCenter)
                setArrowToPoint(30f, 30f)
                setArrowPosDelta(20f)

                updateShapes()
            }
            return drawableKArrow.also { field = it }
        }

    override fun initData(savedInstanceState: Bundle?) {
        vb.drawablekArrow1.post {
            vb.drawablekArrow1.background = drawableKArrow
        }
    }
}