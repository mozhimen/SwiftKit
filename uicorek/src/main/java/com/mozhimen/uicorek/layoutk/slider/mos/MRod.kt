package com.mozhimen.uicorek.layoutk.slider.mos

import android.os.Parcel
import android.os.Parcelable
import com.mozhimen.basick.utilk.kotlin.normalize
import com.mozhimen.uicorek.layoutk.slider.commons.ISliderScrollListener
import com.mozhimen.uicorek.layoutk.slider.helpers.AttrsParser

/**
 * @ClassName Rod
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/9/17 23:50
 * @Version 1.0
 */
class MRod() : Parcelable {

    //region # layout
    var minX: Float = 0f
    var maxX: Float = 0.1f
    val centerX: Float
        get() = intervalX / 2f
    var centerY: Float = 0f
    val currentX: Float
        get() = currentPercent * intervalX + minX
    var currentPercent: Float = 0f
        set(value) {
            field = value.normalize(0f to 1f)
            _sliderListener?.onScrolling(currentPercent, currentVal, this)
        }
    val currentVal: Float
        get() = currentPercent * intervalVal
    val intervalX: Float
        get() = maxX - minX
    val intervalVal: Float
        get() = maxVal - minVal
    //endregion

    //region # attrs
    var isInsideSlider: Boolean = false
    var radius: Float = 0f
    var radiusInside: Float = 0f
        set(value) {
            field = value.normalize(0f to radius)
        }
    var minVal: Float = 0f
    var maxVal: Float = 100f
    var rodColor: Int = AttrsParser.ROD_COLOR
    var rodColorInside: Int = AttrsParser.ROD_COLOR_INSIDE
    //endregion

    private var _sliderListener: ISliderScrollListener? = null

    constructor(parcel: Parcel) : this() {
        minX = parcel.readFloat()
        maxX = parcel.readFloat()
        centerY = parcel.readFloat()
        isInsideSlider = parcel.readByte() != 0.toByte()
        radius = parcel.readFloat()
        minVal = parcel.readFloat()
        maxVal = parcel.readFloat()
        rodColor = parcel.readInt()
        rodColorInside = parcel.readInt()
    }
//    private suspend fun launchScrollChange(rod: MRod) = flow {
//        emit(rod)
//    }.flowOn(Dispatchers.IO).collectLatest {//这里我希望不要频繁的更新UI, 所以采用背压的方式来获取频繁数据的最新值
//
//    }

//    flow<String>
//    {
//
//    }.flowOn(Dispatchers.Main)

//    constructor(minX: Float, maxX: Float, centerY: Float, isInsideSlider: Boolean, radius: Float, radiusInside: Float, _sliderListener: ISliderScrollListener?) {
//        this.minX = minX
//        this.maxX = maxX
//        this.currentX = minX
//        this.centerY = centerY
//        this.isInsideSlider = isInsideSlider
//        this.radius = radius
//        this.radiusInside = radiusInside
//        _sliderListener?.let { this._sliderListener = it }
//    }
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeFloat(minX)
        parcel.writeFloat(maxX)
        parcel.writeFloat(centerY)
        parcel.writeByte(if (isInsideSlider) 1 else 0)
        parcel.writeFloat(radius)
        parcel.writeFloat(minVal)
        parcel.writeFloat(maxVal)
        parcel.writeInt(rodColor)
        parcel.writeInt(rodColorInside)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MRod> {
        override fun createFromParcel(parcel: Parcel): MRod {
            return MRod(parcel)
        }

        override fun newArray(size: Int): Array<MRod?> {
            return arrayOfNulls(size)
        }
    }
}