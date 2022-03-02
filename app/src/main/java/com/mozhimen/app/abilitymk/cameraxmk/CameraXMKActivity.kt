package com.mozhimen.app.abilitymk.cameraxmk

import android.graphics.Bitmap
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import com.mozhimen.abilitymk.cameraxmk.commons.CameraXMKListener
import com.mozhimen.app.databinding.ActivityCameraxmkBinding
import com.mozhimen.basicsmk.utilmk.UtilMKBitmap

class CameraXMKActivity : AppCompatActivity() {
    private val vb by lazy { ActivityCameraxmkBinding.inflate(layoutInflater) }

    /*private val outputDirectory: String by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            "${Environment.DIRECTORY_DCIM}/CameraXMKActivity/"
        } else {
            "${this.getExternalFilesDir(Environment.DIRECTORY_DCIM)}/CameraXMKActivity/"
        }
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)

        vb.cameramkPreviewView.initCamera(this, cameraXMKListener, CameraSelector.DEFAULT_FRONT_CAMERA)
        vb.cameramkPreviewView.startCamera()
        vb.cameramkBtn.setOnClickListener {
            vb.cameramkPreviewView.takePicture()
        }
    }

    private val cameraXMKListener = object : CameraXMKListener() {
        override fun onCaptureSuccess(bitmap: Bitmap) {
            //UtilMKImage.saveBitmap(outputDirectory, bitmap)
            runOnUiThread {
                vb.cameramkImg.setImageBitmap(UtilMKBitmap.rotateBitmap(bitmap, -90, true))
            }
        }

        override fun onCaptureFail() {}
    }
}