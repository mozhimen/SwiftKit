package com.mozhimen.app.basick.utilk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SeekBar
import androidx.lifecycle.lifecycleScope
import com.mozhimen.app.R
import com.mozhimen.app.databinding.ActivityUtilkBinding
import com.mozhimen.basick.extsk.drawable2Bitmap
import com.mozhimen.basick.extsk.showToast
import com.mozhimen.basick.extsk.start
import com.mozhimen.basick.utilk.*
import kotlinx.coroutines.launch

class UtilKActivity : AppCompatActivity() {
    private val TAG = "UtilKActivity>>>>>"
    private val vb by lazy { ActivityUtilkBinding.inflate(layoutInflater) }
    private val url =
        "http://img.crcz.com/allimg/202003/25/1585100748975745.jpg"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)

        initView()
    }

    private fun initView() {
        lifecycleScope.launch {
            vb.utilKImg.setImageBitmap(UtilKBitmap.url2Bitmap2(url))
        }

        vb.utilkBtnPortVerify.setOnClickListener {
            "是否合法: ${UtilKVerifyUrl.isPortValid(vb.utilkTxtPortVerify.text.toString())}".showToast()
        }

        val bitmap = UtilKRes.getDrawable(R.mipmap.opencvk_contrast_test)!!.drawable2Bitmap()
        vb.utilkSeekbarBmpZoom.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                val progress = seekBar!!.progress
                val ratio: Float = 1 + progress / 100f
                Log.i(TAG, "onStopTrackingTouch: ratio $ratio")
                Log.i(TAG, "onStopTrackingTouch: bmp w ${bitmap.width} h ${bitmap.height}")
                val zoomBmp = UtilKBitmap.zoomBitmap(bitmap, ratio)
                Log.i(TAG, "onStopTrackingTouch: zoomBmp w ${zoomBmp.width} h ${zoomBmp.height}")
                val scaleBmp = UtilKBitmap.scaleBitmap(zoomBmp, bitmap.width, bitmap.height)
                Log.i(TAG, "onStopTrackingTouch: scaleBmp w ${scaleBmp.width} h ${scaleBmp.height}")
                Log.i(TAG, "onStopTrackingTouch: --->")
                vb.utilkImgBmpZoom.setImageBitmap(scaleBmp)
            }
        })

        vb.utilkSeekbarBmpScale.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                val progress = seekBar!!.progress
                var ratio: Float = 5 * progress / 100f
                if (ratio < 1) ratio = 1f
                val scaleBmp = UtilKBitmap.scaleBitmap(bitmap, (bitmap.width / ratio).toInt(), (bitmap.height / ratio).toInt())
                Log.i(TAG, "onStopTrackingTouch: scaleBmp w ${scaleBmp.width} h ${scaleBmp.height}")
                vb.utilkImgBmpScale.setImageBitmap(scaleBmp)
            }
        })

        vb.utilkBtnAudio.setOnClickListener {
            UtilKAudio.getInstance(this).play(R.raw.utilk_audio_test)
            UtilKAudio.getInstance(this).play(R.raw.utilk_audio_test)
            UtilKAudio.getInstance(this).play(R.raw.utilk_audio_test)
            UtilKAudio.getInstance(this).play(R.raw.utilk_audio_test)
        }
    }

    fun gotoUtilKGesture(view: View) {
        start<UtilKGestureActivity>()
    }

    fun gotoUtilKAnim(view: View) {
        start<UtilKAnimActivity>()
    }
}