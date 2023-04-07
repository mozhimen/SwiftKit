package com.mozhimen.uicorektest.drawablek

import android.graphics.Color
import android.os.Bundle
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.utilk.exts.dp2px
import com.mozhimen.basick.utilk.res.UtilKRes
import com.mozhimen.uicorek.drawablek.arrow.DrawableKArrow
import com.mozhimen.uicorek.drawablek.arrow.cons.EArrowDirection
import com.mozhimen.uicorek.drawablek.arrow.cons.EArrowPosPolicy
import com.mozhimen.uicorektest.R
import com.mozhimen.uicorektest.databinding.ActivityDrawablekArrowBinding

class DrawableKArrowActivity : BaseActivityVB<ActivityDrawablekArrowBinding>() {
    private var drawableKArrow: DrawableKArrow? = null
        get() {
            if (field != null) return field
            val drawableKArrow = DrawableKArrow()
            drawableKArrow.apply {
                resetRect(VB.drawablekArrow1.width, VB.drawablekArrow1.height)

                setFillColor(Color.BLACK)
                setGapWidth(5f.dp2px())
                setCornerRadius(10f.dp2px())

                setBorderWidth(3f.dp2px())
                setBorderColor(UtilKRes.getColor(R.color.blue_normal))

                setArrowWidth(20f.dp2px())
                setArrowHeight(10f.dp2px())
                setArrowDirection(EArrowDirection.Left)
                setArrowPosPolicy(EArrowPosPolicy.TargetCenter)
                setArrowToPoint(30f, 30f)
                setArrowPosDelta(20f)

                updateShapes()
            }
            return drawableKArrow.also { field = it }
        }

    override fun initView(savedInstanceState: Bundle?) {
        VB.drawablekArrow1.post {
            VB.drawablekArrow1.background = drawableKArrow
        }
    }
}