package com.mozhimen.basick.elemk.mos

import android.os.Parcel
import android.os.Parcelable


/**
 * @ClassName MPoint
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/3/1 15:08
 * @Version 1.0
 */
data class MPointF(
    val x: Float,
    val y: Float
) : java.io.Serializable, Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readFloat(),
        parcel.readFloat()
    )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeFloat(x)
        dest.writeFloat(y)
    }

    companion object CREATOR : Parcelable.Creator<MPointF> {
        override fun createFromParcel(parcel: Parcel): MPointF {
            return MPointF(parcel)
        }

        override fun newArray(size: Int): Array<MPointF?> {
            return arrayOfNulls(size)
        }
    }
}