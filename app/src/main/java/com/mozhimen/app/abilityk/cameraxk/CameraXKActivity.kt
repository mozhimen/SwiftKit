package com.mozhimen.app.abilityk.cameraxk

import android.graphics.Bitmap
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import com.mozhimen.abilityk.cameraxk.commons.ICameraXKCaptureListener
import com.mozhimen.app.databinding.ActivityCameraxkBinding
import com.mozhimen.basick.utilk.UtilKBitmap

class CameraXKActivity : AppCompatActivity() {
    private val vb by lazy { ActivityCameraxkBinding.inflate(layoutInflater) }

    /*private val outputDirectory: String by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            "${Environment.DIRECTORY_DCIM}/CameraXKActivity/"
        } else {
            "${this.getExternalFilesDir(Environment.DIRECTORY_DCIM)}/CameraXKActivity/"
        }
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)

        vb.camerakPreviewView.initCamera(this, CameraSelector.DEFAULT_FRONT_CAMERA)
        vb.camerakPreviewView.setCameraXKCaptureListener(_cameraXKCaptureListener)
        vb.camerakPreviewView.startCamera()
        vb.camerakBtn.setOnClickListener {
            vb.camerakPreviewView.takePicture()
        }
    }

    private val _cameraXKCaptureListener = object : ICameraXKCaptureListener {
        override fun onCaptureSuccess(bitmap: Bitmap) {
            //UtilKImage.saveBitmap(outputDirectory, bitmap)
            runOnUiThread {
                vb.camerakImg.setImageBitmap(UtilKBitmap.rotateBitmap(bitmap, -90, flipY = true))
            }
        }

        override fun onCaptureFail() {}
    }
}