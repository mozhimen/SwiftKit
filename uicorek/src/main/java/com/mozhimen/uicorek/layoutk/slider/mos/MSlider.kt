package com.mozhimen.uicorek.layoutk.slider.mos

import android.graphics.Color
import android.os.Parcel
import android.os.Parcelable
import com.mozhimen.uicorek.layoutk.slider.helpers.AttrsParser

/**
 * @ClassName Slider
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/9/17 15:48
 * @Version 1.0
 */
class MSlider() :Parcelable {
    //region # init
    var width: Float = 0f
        set(value) {
            field = if (value <= 0) AttrsParser.SLIDER_WIDTH else value
        }
    var height: Float = 0f
        set(value) {
            field = if (value <= 0) AttrsParser.SLIDER_HEIGHT else value
        }
    var leftX: Float = 0f
    var topY: Float = 0f
    val widthHalf: Float
        get() = width / 2f
    val heightHalf: Float
        get() = height / 2f
    val centerX: Float
        get() = leftX + widthHalf
    val centerY: Float
        get() = topY + heightHalf
    val rightX: Float
        get() = leftX + width
    val bottomY: Float
        get() = topY + height
    //endregion

    //region # attrs
    var rodLeftColor = Color.BLACK
    var rodLeftGradientColor = rodLeftColor
    var rodRightColor = Color.LTGRAY

    constructor(parcel: Parcel) : this() {
        leftX = parcel.readFloat()
        topY = parcel.readFloat()
        rodLeftColor = parcel.readInt()
        rodLeftGradientColor = parcel.readInt()
        rodRightColor = parcel.readInt()
    }
    //endregion

//    constructor()
//
//    constructor(width: Float, height: Float, leftX: Float, topY: Float) {
//        this.width = width
//        this.height = height
//        this.leftX = leftX
//        this.topY = topY
//    }

//    fun refreshX() {
//        //widthHalf = width / 2f
//        //centerX = leftX + widthHalf
//        //rightX = leftX + width
//    }
//
//    fun refreshY() {
//        //heightHalf = height / 2f
//        //centerY = topY + heightHalf
//        //bottomY = topY + height
//    }
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeFloat(leftX)
        parcel.writeFloat(topY)
        parcel.writeInt(rodLeftColor)
        parcel.writeInt(rodLeftGradientColor)
        parcel.writeInt(rodRightColor)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MSlider> {
        override fun createFromParcel(parcel: Parcel): MSlider {
            return MSlider(parcel)
        }

        override fun newArray(size: Int): Array<MSlider?> {
            return arrayOfNulls(size)
        }
    }
}