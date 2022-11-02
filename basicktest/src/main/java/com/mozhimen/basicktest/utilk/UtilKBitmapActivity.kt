package com.mozhimen.basicktest.utilk

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.widget.SeekBar
import androidx.lifecycle.lifecycleScope
import com.mozhimen.basick.basek.BaseKActivityVB
import com.mozhimen.basick.extsk.drawable2Bitmap
import com.mozhimen.basick.utilk.UtilKRes
import com.mozhimen.basick.utilk.bitmap.UtilKBitmapConv
import com.mozhimen.basick.utilk.bitmap.UtilKBitmapNet
import com.mozhimen.basicktest.R
import com.mozhimen.basicktest.databinding.ActivityUtilkBitmapBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UtilKBitmapActivity : BaseKActivityVB<ActivityUtilkBitmapBinding>() {
    override fun initData(savedInstanceState: Bundle?) {
        lifecycleScope.launch(Dispatchers.Main) {
            val bitmap: Bitmap?
            withContext(Dispatchers.IO) {
                bitmap = UtilKBitmapNet.url2Bitmap("http://img.crcz.com/allimg/202003/25/1585100748975745.jpg")
            }
            bitmap?.let {
                vb.utilKBitmapImg.setImageBitmap(it)
            }
        }

        val bitmap = UtilKRes.getDrawable(R.mipmap.utilk_img)!!.drawable2Bitmap()
        vb.utilkBitmapSeekbarBmpZoom.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                val progress = seekBar!!.progress
                val ratio: Float = 1 + progress / 100f
                Log.i(TAG, "onStopTrackingTouch: ratio $ratio")
                Log.i(TAG, "onStopTrackingTouch: bmp w ${bitmap.width} h ${bitmap.height}")
                val zoomBmp = UtilKBitmapConv.zoomBitmap(bitmap, ratio)
                Log.i(TAG, "onStopTrackingTouch: zoomBmp w ${zoomBmp.width} h ${zoomBmp.height}")
                val scaleBmp = UtilKBitmapConv.scaleBitmap(zoomBmp, bitmap.width, bitmap.height)
                Log.i(TAG, "onStopTrackingTouch: scaleBmp w ${scaleBmp.width} h ${scaleBmp.height}")
                Log.i(TAG, "onStopTrackingTouch: --->")
                vb.utilkBitmapImgBmpZoom.setImageBitmap(scaleBmp)
            }
        })

        vb.utilkBitmapSeekbarBmpScale.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                val progress = seekBar!!.progress
                var ratio: Float = 5 * progress / 100f
                if (ratio < 1) ratio = 1f
                val scaleBmp = UtilKBitmapConv.scaleBitmap(bitmap, (bitmap.width / ratio).toInt(), (bitmap.height / ratio).toInt())
                Log.i(TAG, "onStopTrackingTouch: scaleBmp w ${scaleBmp.width} h ${scaleBmp.height}")
                vb.utilkBitmapImgBmpScale.setImageBitmap(scaleBmp)
            }
        })
    }
}