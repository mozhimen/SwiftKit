package com.mozhimen.basicktest.utilk.android

import android.graphics.Bitmap
import android.os.Bundle
import com.mozhimen.kotlin.utilk.android.util.UtilKLogWrapper
import android.widget.SeekBar
import androidx.lifecycle.lifecycleScope
import com.mozhimen.mvvmk.bases.activity.databinding.BaseActivityVDB
import com.mozhimen.kotlin.lintk.optins.permission.OPermission_INTERNET
import com.mozhimen.kotlin.utilk.wrapper.UtilKRes
import com.mozhimen.kotlin.utilk.android.graphics.UtilKBitmapDeal
import com.mozhimen.kotlin.utilk.android.graphics.UtilKBitmapFormat
import com.mozhimen.kotlin.utilk.android.graphics.applyBitmapAnyScaleRatio
import com.mozhimen.kotlin.utilk.android.graphics.drawable2bitmap
import com.mozhimen.kotlin.utilk.kotlin.UtilKStrPath
import com.mozhimen.kotlin.utilk.kotlin.UtilKStrUrl
import com.mozhimen.basicktest.R
import com.mozhimen.basicktest.databinding.ActivityUtilkBitmapBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UtilKBitmapActivity : BaseActivityVDB<ActivityUtilkBitmapBinding>() {
    @OptIn(OPermission_INTERNET::class)
    override fun initView(savedInstanceState: Bundle?) {
        lifecycleScope.launch(Dispatchers.Main) {
            val bitmap: Bitmap?
            withContext(Dispatchers.IO) {
                bitmap = UtilKStrUrl.strUrl2bitmapAny("http://192.168.2.6/construction-sites-images/person/20221101/93ea2a3e11e54a76944dfc802519e3cc.jpg")//http://img.crcz.com/allimg/202003/25/1585100748975745.jpg
            }
            bitmap?.let {
                vdb.utilKBitmapImg.setImageBitmap(it)
            }
        }

        val bitmap = UtilKRes.getDrawable_ofContext(R.mipmap.utilk_img)!!.drawable2bitmap()
        vdb.utilkBitmapSeekbarBmpZoom.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                val progress = seekBar!!.progress
                val ratio: Float = 1 + progress / 100f
                UtilKLogWrapper.i(TAG, "onStopTrackingTouch: ratio $ratio")
                UtilKLogWrapper.i(TAG, "onStopTrackingTouch: bmp w ${bitmap.width} h ${bitmap.height}")
                val zoomBmp = UtilKBitmapDeal.applyBitmapAnyZoom(bitmap, ratio)
                UtilKLogWrapper.i(TAG, "onStopTrackingTouch: zoomBmp w ${zoomBmp.width} h ${zoomBmp.height}")
                val scaleBmp = zoomBmp.applyBitmapAnyScaleRatio(bitmap.width.toFloat(), bitmap.height.toFloat())
                UtilKLogWrapper.i(TAG, "onStopTrackingTouch: scaleBmp w ${scaleBmp.width} h ${scaleBmp.height}")
                UtilKLogWrapper.i(TAG, "onStopTrackingTouch: --->")
                vdb.utilkBitmapImgBmpZoom.setImageBitmap(scaleBmp)
            }
        })

        vdb.utilkBitmapSeekbarBmpScale.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                val progress = seekBar!!.progress
                var ratio: Float = 5 * progress / 100f
                if (ratio < 1) ratio = 1f
                val scaleBmp = bitmap.applyBitmapAnyScaleRatio(bitmap.width.toFloat() / ratio, bitmap.height.toFloat() / ratio)
                UtilKLogWrapper.i(TAG, "onStopTrackingTouch: scaleBmp w ${scaleBmp.width} h ${scaleBmp.height}")
                vdb.utilkBitmapImgBmpScale.setImageBitmap(scaleBmp)
            }
        })

        vdb.utilkBitmapBtnSave.setOnClickListener {
            UtilKBitmapFormat.bitmapAny2fileJpeg(bitmap, UtilKStrPath.Absolute.Internal.getCache())
        }
    }
}