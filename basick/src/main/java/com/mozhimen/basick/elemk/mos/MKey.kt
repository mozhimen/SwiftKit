package com.mozhimen.basick.elemk.mos

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

/**
 * @ClassName MoKKey
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Version 1.0
 */
data class MKey(
    var id: String,
    var name: String
) : Serializable, Parcelable {

    constructor() : this("", "")

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MKey> {
        override fun createFromParcel(parcel: Parcel): MKey {
            return MKey(parcel)
        }

        override fun newArray(size: Int): Array<MKey?> {
            return arrayOfNulls(size)
        }
    }


}