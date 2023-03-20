package com.mozhimen.componentk.videok

import android.content.Context
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.SurfaceTexture
import android.media.MediaMetadataRetriever
import android.media.MediaPlayer
import android.util.AttributeSet
import android.util.Log
import android.view.Surface
import android.view.TextureView
import com.mozhimen.basick.utilk.res.UtilKAssets
import com.mozhimen.uicorek.layoutk.bases.BaseLayoutKFrame
import com.mozhimen.basick.utilk.view.display.UtilKScreen
import com.mozhimen.uicorek.R
import java.io.IOException

/**
 * @ClassName VideoLayout
 * @Description
 * 作用: 播放视频
 * 用法1: videoLayout?.apply {
 *  onDestroyVideoLayout()
 *  setGravity(VideoLayout.VGravity.none)
 *  setIsLoop(true)
 *  setPathOrUrl("login_bg.mp4", null)}
 *  ?: run{ videoLayout = VideoLayout(this)}
 *  vb.loginBg.addView(videoLayout)
 *
 * 用法2: val videoLayout=VideoLayout(this)
 * videoLayout?.apply {
 *  onDestroyVideoLayout()
 *  setGravity(VideoLayout.VGravity.none)
 *  setIsLoop(false)
 *  setPathOrUrl("login_bg.mp4", null)}
 *  ?: run{ videoLayout = VideoLayout(this, {
 *      //视频播放完成执行完成逻辑})}
 *  vb.loginBg.addView(videoLayout)
 * @Author mozhimen
 * @Date 2021/7/2 22:31
 * @Version 1.0
 */
class VideoKLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    BaseLayoutKFrame(context, attrs, defStyleAttr), TextureView.SurfaceTextureListener {

    companion object {
        const val GRAVITY_START = 0//位置权重
        const val GRAVITY_END = 1
        const val GRAVITY_CENTER_CROP = 2
        const val GRAVITY_NONE = 3
    }

    private var _videoSource: String? = null
        set(value) {
            Log.d(TAG, "_videoSource set $value")
            field = value
        }
    private var _videoGravity = GRAVITY_NONE
    private var _videoIsLoop = false
    private var _videoVolume = 0f

    private var _videoIsUrl = false
    private var _videoWidth = 0f
    private var _videoHeight = 0f

    private var _videoSurface: TextureView? = null
    private var _videoPlayer: MediaPlayer? = null
    private var _videoCompletionListener: MediaPlayer.OnCompletionListener? = null

    init {
        initAttrs(attrs, defStyleAttr)
        initView()
    }

    override fun initAttrs(attrs: AttributeSet?, defStyleAttr: Int) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.LayoutKVideo)
        _videoSource =
            typedArray.getString(R.styleable.LayoutKVideo_layoutKVideo_pathOrUrl)
        _videoGravity =
            typedArray.getInt(R.styleable.LayoutKVideo_layoutKVideo_videoGravity, GRAVITY_CENTER_CROP)
        _videoIsLoop =
            typedArray.getBoolean(R.styleable.LayoutKVideo_layoutKVideo_isLoop, false)
        _videoVolume =
            typedArray.getInteger(R.styleable.LayoutKVideo_layoutKVideo_volume, 0).toFloat()
        typedArray.recycle()
    }

    override fun initView() {
        if (_videoSurface == null && _videoSource != null) {
            _videoSurface = TextureView(context)
            _videoIsUrl = _videoSource!!.contains("http://") or _videoSource!!.contains("https://")
            _videoSurface?.surfaceTextureListener = this
            addView(_videoSurface)
        }

        if (_videoGravity != GRAVITY_NONE) {
            zoomVideoSize()
            zoomTextureSize()
        }
    }

    fun getVideoPlayer() = _videoPlayer

    fun getVideoSurface() = _videoSurface

    fun initVideo(
        pathOrUrl: String,
        videoGravity: Int = GRAVITY_NONE,
        videoIsLoop: Boolean = false,
        videoVolume: Float = 0f,
        listener: MediaPlayer.OnCompletionListener? = null
    ) {
        _videoSource = pathOrUrl
        _videoGravity = videoGravity
        _videoIsLoop = videoIsLoop
        _videoVolume = videoVolume
        _videoCompletionListener = listener
        initView()
    }

    fun resetVideo() {
        try {
            if (_videoPlayer?.isPlaying == true) _videoPlayer?.stop()
            _videoPlayer?.reset()
        } catch (e: IllegalStateException) {
            e.printStackTrace()
            Log.e(TAG, "onVideoDestroy IllegalStateException ${e.message}")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "onVideoDestroy Exception ${e.message}")
        }
    }

    fun destroyVideo() {
        try {
            if (_videoPlayer?.isPlaying == true) _videoPlayer?.stop()
            _videoPlayer?.release()
            _videoPlayer = null
        } catch (e: IllegalStateException) {
            e.printStackTrace()
            Log.e(TAG, "onVideoDestroy IllegalStateException ${e.message}")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "onVideoDestroy Exception ${e.message}")
        }
    }

    fun resumeVideo() {
        if (_videoPlayer?.isPlaying == false) {
            try {
                _videoPlayer?.start()
            } catch (e: IllegalStateException) {
                e.printStackTrace()
                Log.e(TAG, "onVideoLayoutResume: IllegalStateException ${e.message ?: ""}")
            }
        }
    }

    fun pauseVideo() {
        if (_videoPlayer?.isPlaying == true) {
            try {
                _videoPlayer?.pause()
            } catch (e: IllegalStateException) {
                e.printStackTrace()
                Log.e(TAG, "onVideoLayoutPause: IllegalStateException ${e.message ?: ""}")
            }
        }
    }

    fun changeVideo(
        pathOrUrl: String,
        videoGravity: Int = GRAVITY_NONE,
        videoIsLoop: Boolean = false,
        videoVolume: Float = 0f,
        listener: MediaPlayer.OnCompletionListener? = null
    ) {
        try {
            initVideo(pathOrUrl, videoGravity, videoIsLoop, videoVolume, listener)
            resetVideo()
            playVideo()
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
            Log.e(TAG, "changeVideo IllegalArgumentException ${e.message}")
        } catch (e: SecurityException) {
            e.printStackTrace()
            Log.e(TAG, "changeVideo SecurityException ${e.message}")
        } catch (e: IllegalStateException) {
            e.printStackTrace()
            Log.e(TAG, "changeVideo IllegalStateException ${e.message}")
        } catch (e: IOException) {
            e.printStackTrace()
            Log.e(TAG, "changeVideo IOException ${e.message}")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "changeVideo Exception ${e.message}")
        }
    }

    override fun onSurfaceTextureAvailable(surface: SurfaceTexture, width: Int, height: Int) {
        try {
            if (_videoSource == null) {
                Log.e(TAG, "onSurfaceTextureAvailable: _videoSource == null")
                return
            }
            _videoSurface?.surfaceTexture?.let { playVideo() }
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
            Log.e(TAG, "onSurfaceTextureAvailable IllegalArgumentException ${e.message}")
        } catch (e: SecurityException) {
            e.printStackTrace()
            Log.e(TAG, "onSurfaceTextureAvailable SecurityException ${e.message}")
        } catch (e: IllegalStateException) {
            e.printStackTrace()
            Log.e(TAG, "onSurfaceTextureAvailable IllegalStateException ${e.message}")
        } catch (e: IOException) {
            e.printStackTrace()
            Log.e(TAG, "onSurfaceTextureAvailable IOException ${e.message}")
        }
    }

    override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture, width: Int, height: Int) {}

    override fun onSurfaceTextureDestroyed(surface: SurfaceTexture) = false

    override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {}

    override fun onDetachedFromWindow() {
        destroyVideo()
        super.onDetachedFromWindow()
    }

    //region private fun
    private fun zoomVideoSize() {
        try {
            if (_videoSource == null) {
                Log.d(TAG, "zoomVideoSize: _videoSource == null")
                return
            }
            val mediaMetadataRetriever = MediaMetadataRetriever()
            if (_videoIsUrl) {
                mediaMetadataRetriever.setDataSource(_videoSource, HashMap())
            } else {
                val assetFileDescriptor = UtilKAssets.openFd(_videoSource!!)
                mediaMetadataRetriever.setDataSource(
                    assetFileDescriptor.fileDescriptor,
                    assetFileDescriptor.startOffset,
                    assetFileDescriptor.length
                )
                val height =
                    mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT)
                val width =
                    mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH)
                _videoWidth = width!!.toFloat()
                _videoHeight = height!!.toFloat()
                mediaMetadataRetriever.release()
            }
        } catch (e: IOException) {
            e.printStackTrace()
            Log.e(TAG, "calculateVideoSize: IOException $${e.message ?: ""}")
        } catch (e: NumberFormatException) {
            e.printStackTrace()
            Log.e(TAG, "calculateVideoSize: NumberFormatException $${e.message ?: ""}")
        }
    }

    private fun zoomTextureSize() {
        val screenWidth: Int = UtilKScreen.getRealScreenWidth()
        val screenHeight: Int = UtilKScreen.getRealScreenHeight()
        var scaleX = 1.0f
        var scaleY = 1.0f
        if (_videoWidth > screenWidth && _videoHeight > screenHeight) {
            scaleX = _videoWidth / screenWidth
            scaleY = _videoHeight / screenHeight
        } else if (_videoWidth < screenWidth && _videoHeight < screenHeight) {
            scaleX = screenWidth / _videoWidth
            scaleY = screenHeight / _videoHeight
        } else if (screenWidth > _videoWidth) {
            scaleY = (screenWidth / _videoWidth) / (screenHeight / _videoHeight)
        } else if (screenHeight > _videoHeight) {
            scaleX = (screenHeight / _videoHeight) / (screenWidth / _videoWidth)
        }

        val pivotPointX: Float = if (_videoGravity == 0) 0f else {
            if (_videoGravity == 1) screenWidth else screenWidth / 2f
        }.toFloat()
        val pivotPointY = (screenHeight / 2).toFloat()
        val matrix = Matrix()
        matrix.setScale(scaleX, scaleY, pivotPointX, pivotPointY)
        _videoSurface?.setTransform(matrix)
        _videoSurface?.layoutParams = LayoutParams(screenWidth, screenHeight)
    }

    private fun playVideo() {
        if (_videoPlayer == null) _videoPlayer = MediaPlayer()
        _videoPlayer!!.apply {
            if (_videoIsUrl) {
                setDataSource(_videoSource)
            } else {
                if (_videoSource!!.contains("/")){
                    setDataSource(_videoSource)
                }else{
                    val assetFileDescriptor = UtilKAssets.openFd(_videoSource!!)
                    setDataSource(assetFileDescriptor.fileDescriptor, assetFileDescriptor.startOffset, assetFileDescriptor.length)
                }
            }
            setVolume(_videoVolume / 10f, _videoVolume / 10f)
            isLooping = _videoIsLoop
            setSurface(Surface(_videoSurface!!.surfaceTexture!!))
            prepareAsync()
            setOnPreparedListener {
                setOnErrorListener { _, _, _ ->
                    false
                }
                setOnInfoListener { _, what, _ ->
                    if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START)
                        setBackgroundColor(Color.TRANSPARENT)
                    true
                }
                setOnCompletionListener {
                    _videoCompletionListener?.onCompletion(it)
                }
                start()
            }
        }
    }
    //endregion
}