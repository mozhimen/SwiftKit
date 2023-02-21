package com.mozhimen.componentktest.camerak

import android.graphics.SurfaceTexture
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.TextureView
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.componentk.camerak.*
import com.mozhimen.componentk.camerak.commons.CallBackEvents
import com.mozhimen.componentk.camerak.cons.CameraApiType
import com.mozhimen.componentk.camerak.cons.CameraFocus
import com.mozhimen.componentk.camerak.cons.FacingType
import com.mozhimen.componentk.camerak.mos.CameraFacing
import com.mozhimen.componentk.camerak.mos.CameraSize
import com.mozhimen.componentk.camerak.mos.IAttributes
import com.mozhimen.componentk.camerak.preview.Direction
import com.mozhimen.componentktest.databinding.ActivityCamerakBinding

/**
 * @ClassName CameraKActivity
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2023/2/21 21:54
 * @Version 1.0
 */
class CameraKActivity : BaseActivityVB<ActivityCamerakBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        initCamera()
    }

    private lateinit var _surfaceRbg: SurfaceTexture
    private var _cameraMgrRgb: CameraManager? = null

    @Transient
    private var _bytesRgb: ByteArray? = null
    private fun initCamera() {
        vb.camerakScale.surfaceTextureListener = object : TextureView.SurfaceTextureListener {
            override fun onSurfaceTextureAvailable(surface: SurfaceTexture, p1: Int, p2: Int) {
                _surfaceRbg = surface
                openRgbCamera()
            }

            override fun onSurfaceTextureSizeChanged(
                surface: SurfaceTexture,
                width: Int,
                height: Int
            ) {
            }

            override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean {
                return false
            }

            override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {
            }
        }
        vb.camerakScale.apply {
            setDisplayDir(Direction.getDegree(90))
            resetPreviewSize(1280, 800)
            setMirror(false)
        }
    }

    private fun openRgbCamera() {
        _cameraMgrRgb = CameraManager.getInstance(
            CameraFacing.Builder().setFacingType(FacingType.OTHER)
                .setCameraId(/*DEFAULT_RGB_CAMERA_FACING*/0).build(),
            CameraApiType.CAMERA1,
            baseContext,
            Handler(Looper.getMainLooper())
        )
        _cameraMgrRgb!!.setCallBackEvents(object : CallBackEvents {
            override fun onCameraOpen(p0: IAttributes?) {
                val width = /*CameraConfig.preview_width*/1280
                val height = /*CameraConfig.preview_height*/720
                _cameraMgrRgb!!.apply {
                    setPhotoSize(CameraSize(width, height))
                    setPreviewSize(CameraSize(width, height))
//                    if (FaceConfig.face_ori == ImageOrientation.UP) {
                    setPreviewOrientation(Direction.UP.value * 90)
//                    } else {
//                        setPreviewOrientation(Direction.LEFT.value * 90)
//                    }
                    setFocusMode(CameraFocus.CONTINUOUS_VIDEO)
                    clearPreviewCallbackWithBuffer()
                    addPreviewCallbackWithBuffer {
                        _bytesRgb = it
                        //回调代码
                    }
                    startPreview(_surfaceRbg)
                }
            }

            override fun onCameraClose() {}
            override fun onCameraError(p0: String?) {}
            override fun onPreviewStarted() {}
            override fun onPreviewStopped() {}
            override fun onPreviewError(p0: String?) {}
        })
        _cameraMgrRgb!!.openCamera()
    }

}