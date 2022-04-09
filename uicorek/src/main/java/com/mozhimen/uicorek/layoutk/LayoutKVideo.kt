package com.mozhimen.uicorek.layoutk

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
import android.widget.FrameLayout
import com.mozhimen.uicorek.R
import java.io.IOException

/**
 * @ClassName VideoLayout
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/7/2 22:31
 * @Version 1.0
 */
class LayoutKVideo : FrameLayout, TextureView.SurfaceTextureListener {

    private val TAG = "LayoutKVideo>>>>>"
    var videoFileName = ""

    /**
     * 位置权重
     * <enum name="start" value="0"/>
     * <enum name="end" value="1"/>
     * <enum name="centerCrop" value="2"/>
     * <enum name="none" value="3"/>
     */
    private var videoGravity = 2
    private var videoIsLoop = false

    private var isVideoUrl = false

    private var videoSurface: TextureView? = null
    private var mVideoWidth = 0f
    private var mVideoHeight = 0f

    /**
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
     */
    constructor(context: Context?) : super(context!!)
    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(
        context!!,
        attrs,
        defStyle
    ) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        val typeArray = context.theme.obtainStyledAttributes(attrs, R.styleable.LayoutKVideo, 0, 0)

        try {
            videoFileName = typeArray.getString(R.styleable.LayoutKVideo_layoutKVideo_pathOrUrl).toString()
            videoGravity = typeArray.getInt(R.styleable.LayoutKVideo_layoutKVideo_videoGravity, 2)
            videoIsLoop = typeArray.getBoolean(R.styleable.LayoutKVideo_layoutKVideo_isLoop, false)
        } finally {
            typeArray.recycle()
        }

        //判断是否是url
        isVideoUrl = videoFileName.contains("http://") or videoFileName.contains("https://")

        initView()
        addView(videoSurface)
        setListeners()

        if (videoGravity != 3) {
            calculateVideoSize()
            surfaceSetup()
        }
    }

    private fun initView() {
        videoSurface = TextureView(context)
    }

    private fun setListeners() {
        videoSurface?.surfaceTextureListener = this
    }

    private fun calculateVideoSize() {
        try {
            val mediaMetadataRetriever = MediaMetadataRetriever()
            if (isVideoUrl) {
                mediaMetadataRetriever.setDataSource(videoFileName, HashMap())
            } else {
                val assetFileDescriptor = context.assets.openFd(videoFileName)
                mediaMetadataRetriever.setDataSource(
                    assetFileDescriptor.fileDescriptor,
                    assetFileDescriptor.startOffset,
                    assetFileDescriptor.length
                )
                val height =
                    mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT)
                val width =
                    mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH)
                mVideoWidth = width!!.toFloat()
                mVideoHeight = height!!.toFloat()
                mediaMetadataRetriever.release()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: NumberFormatException) {
            e.printStackTrace()
        }
    }

    private fun surfaceSetup() {
        val screenWidth = resources.displayMetrics.widthPixels
        val screenHeight = resources.displayMetrics.heightPixels
        updateTextureViewSize(screenWidth, screenHeight)
    }

    private fun updateTextureViewSize(screenWidth: Int, screenHeight: Int) {
        var scaleX = 1.0f
        var scaleY = 1.0f
        if (mVideoWidth > screenWidth && mVideoHeight > screenHeight) {
            scaleX = mVideoWidth / screenWidth
            scaleY = mVideoHeight / screenHeight
        } else if (mVideoWidth < screenWidth && mVideoHeight < screenHeight) {
            scaleX = screenWidth / mVideoWidth
            scaleY = screenHeight / mVideoHeight
        } else if (screenWidth > mVideoWidth) {
            scaleY = (screenWidth / mVideoWidth) / (screenHeight / mVideoHeight)
        } else if (screenHeight > mVideoHeight) {
            scaleX = (screenHeight / mVideoHeight) / (screenWidth / mVideoWidth)
        }

        val pivotPointX = if (videoGravity == 0) {
            0
        } else {
            if (videoGravity == 1) {
                screenWidth
            } else {
                screenWidth / 2
            }
        }.toFloat()
        val pivotPointY = (screenHeight / 2).toFloat()

        val matrix = Matrix()
        matrix.setScale(scaleX, scaleY, pivotPointX, pivotPointY)

        videoSurface?.setTransform(matrix)
        videoSurface?.layoutParams = LayoutParams(screenWidth, screenHeight)
    }

    private var mMediaPlayer: MediaPlayer? = null

    private var onCompletionListener: MediaPlayer.OnCompletionListener? = null

    override fun onSurfaceTextureAvailable(surface: SurfaceTexture, width: Int, height: Int) {
        try {
            mMediaPlayer = MediaPlayer()
            mMediaPlayer!!.apply {
                if (isVideoUrl) {
                    setDataSource(videoFileName)
                } else {
                    val assetFileDescriptor = context.assets.openFd(videoFileName)
                    setDataSource(
                        assetFileDescriptor.fileDescriptor,
                        assetFileDescriptor.startOffset,
                        assetFileDescriptor.length
                    )
                }
                setVolume(0f, 0f)
                setSurface(Surface(surface))
                isLooping = videoIsLoop
                prepareAsync()
                setOnPreparedListener {
                    it.apply {
                        setOnErrorListener { _, _, _ ->
                            false
                        }
                        setOnCompletionListener {
                            onCompletionListener?.onCompletion(this)
                        }
                        setOnInfoListener { _, what, _ ->
                            if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
                                setBackgroundColor(Color.TRANSPARENT)
                            }
                            true
                        }
                        start()
                    }
                }
            }
        } catch (e: IllegalArgumentException) {
            e.message?.let { Log.e(TAG, it) }
        } catch (e: SecurityException) {
            e.message?.let { Log.e(TAG, it) }
        } catch (e: IllegalStateException) {
            e.message?.let { Log.e(TAG, it) }
        } catch (e: IOException) {
            e.message?.let { Log.e(TAG, it) }
        }
    }

    override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture, width: Int, height: Int) {}

    override fun onSurfaceTextureDestroyed(surface: SurfaceTexture) = false

    override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {}

    //region #调用方法
    fun onVideoLayoutDestroy() {
        mMediaPlayer?.let {
            try {
                it.stop()
                it.release()
                mMediaPlayer = null
            } catch (e: IllegalStateException) {
                Log.e(TAG, "onVideoDestroy: ${e.message}")
            } catch (e: Exception) {
                Log.e(TAG, "onVideoDestroy: ${e.message}")
            }
        }
    }

    fun onVideoLayoutResume() {
        mMediaPlayer?.let {
            if (it.isPlaying) {
                try {
                    it.start()
                } catch (e: IllegalStateException) {
                }
            }
        }
    }

    fun onVideoLayoutPause() {
        mMediaPlayer?.let {
            if (it.isPlaying) {
                try {
                    it.pause()
                } catch (e: IllegalStateException) {
                }
            }
        }
    }

    fun getMediaPlayer() = mMediaPlayer

    fun getVideoSurface() = videoSurface

    fun setPathOrUrl(fileName: String, onCompletionListener: MediaPlayer.OnCompletionListener) {
        this.videoFileName = fileName

        isVideoUrl = fileName.contains("http://") or fileName.contains("https://")

        if (videoSurface == null) {
            initView()
            addView(videoSurface)
            setListeners()
        }

        if (videoGravity != 3) {
            calculateVideoSize()
            surfaceSetup()
        }

        videoSurface?.let {
            this.onCompletionListener = onCompletionListener
            changeVideo(onCompletionListener)
        }
    }

    private fun changeVideo(onCompletionListener: MediaPlayer.OnCompletionListener) {
        try {
            onVideoLayoutDestroy()
            mMediaPlayer = MediaPlayer()
            mMediaPlayer!!.apply {
                if (isVideoUrl) {
                    setDataSource(videoFileName)
                } else {
                    val assetFileDescriptor = context.assets.openFd(videoFileName)
                    setDataSource(
                        assetFileDescriptor.fileDescriptor,
                        assetFileDescriptor.startOffset,
                        assetFileDescriptor.length
                    )
                }
                setVolume(0f, 0f)
                isLooping = videoIsLoop
                setSurface(Surface(videoSurface!!.surfaceTexture))
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
                        onCompletionListener.onCompletion(it)
                    }
                    start()
                }
            }
        } catch (e: IllegalArgumentException) {
            e.message?.let { Log.e(TAG, it) }
        } catch (e: SecurityException) {
            e.message?.let { Log.e(TAG, it) }
        } catch (e: IllegalStateException) {
            e.message?.let { Log.e(TAG, it) }
        } catch (e: IOException) {
            e.message?.let { Log.e(TAG, it) }
        } catch (e: Exception) {
            e.message?.let { Log.e(TAG, it) }
        }
    }

    //endregion
}