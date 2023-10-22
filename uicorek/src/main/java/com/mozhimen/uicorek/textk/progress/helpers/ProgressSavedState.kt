package com.mozhimen.uicorek.textk.progress.helpers

import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator
import android.view.View

/**
 * @ClassName ProgressSavedState
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/10/22 22:09
 * @Version 1.0
 */

class ProgressSavedState : View.BaseSavedState {
    private var _progress: Int
    val progress :Int get() = _progress
    private var _state: Int
    val state:Int get() = _state
    private var _currentText: String
    val currentText get() = _currentText

    constructor(parcel: Parcelable, progress: Int, state: Int, currentText: String) : super(parcel) {
        _progress = progress
        _state = state
        _currentText = currentText
    }

    private constructor(parcel: Parcel) : super(parcel) {
        _progress = parcel.readInt()
        _state = parcel.readInt()
        _currentText = parcel.readString()?:""
    }

    override fun writeToParcel(out: Parcel, flags: Int) {
        super.writeToParcel(out, flags)
        out.writeInt(_progress)
        out.writeInt(_state)
        out.writeString(_currentText)
    }

    companion object CREATOR : Creator<ProgressSavedState> {
        override fun createFromParcel(parcel: Parcel): ProgressSavedState {
            return ProgressSavedState(parcel)
        }

        override fun newArray(size: Int): Array<ProgressSavedState?> {
            return arrayOfNulls(size)
        }
    }

//    companion object {
//        val CREATOR: Creator<ProgressSavedState> = object : Creator<ProgressSavedState> {
//            override fun createFromParcel(`in`: Parcel): ProgressSavedState {
//                return ProgressSavedState(`in`)
//            }
//
//            override fun newArray(size: Int): Array<ProgressSavedState?> {
//                return arrayOfNulls(size)
//            }
//        }
//    }
}
