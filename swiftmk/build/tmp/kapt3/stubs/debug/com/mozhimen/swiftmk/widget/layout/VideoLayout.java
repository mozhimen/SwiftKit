package com.mozhimen.swiftmk.widget.layout;

import java.lang.System;

/**
 * @ClassName VideoLayout
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/7/2 22:31
 * @Version 1.0
 */
@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0010\u0018\u00002\u00020\u00012\u00020\u0002B\u0011\b\u0016\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004\u00a2\u0006\u0002\u0010\u0005B\u001b\b\u0016\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\u0002\u0010\bB#\b\u0016\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\u0006\u0010\t\u001a\u00020\n\u00a2\u0006\u0002\u0010\u000bJ\b\u0010 \u001a\u00020!H\u0002J\u0010\u0010\"\u001a\u00020!2\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J\b\u0010#\u001a\u0004\u0018\u00010\u000fJ\b\u0010$\u001a\u0004\u0018\u00010\u001fJ\u0012\u0010%\u001a\u00020!2\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007H\u0002J\b\u0010&\u001a\u00020!H\u0002J \u0010\'\u001a\u00020!2\u0006\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020\n2\u0006\u0010+\u001a\u00020\nH\u0016J\u0010\u0010,\u001a\u00020\r2\u0006\u0010(\u001a\u00020)H\u0016J \u0010-\u001a\u00020!2\u0006\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020\n2\u0006\u0010+\u001a\u00020\nH\u0016J\u0010\u0010.\u001a\u00020!2\u0006\u0010(\u001a\u00020)H\u0016J\u0006\u0010/\u001a\u00020!J\u0006\u00100\u001a\u00020!J\u0006\u00101\u001a\u00020!J\b\u00102\u001a\u00020!H\u0002J\u0016\u00103\u001a\u00020!2\u0006\u00104\u001a\u00020\u00112\u0006\u0010\u0015\u001a\u00020\u0016J\b\u00105\u001a\u00020!H\u0002J\u0018\u00106\u001a\u00020!2\u0006\u00107\u001a\u00020\n2\u0006\u00108\u001a\u00020\nH\u0002R\u000e\u0010\f\u001a\u00020\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0013X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0015\u001a\u0004\u0018\u00010\u0016X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0017\u001a\u00020\u0011X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\u000e\u0010\u001c\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u001e\u001a\u0004\u0018\u00010\u001fX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u00069"}, d2 = {"Lcom/mozhimen/swiftmk/widget/layout/VideoLayout;", "Landroid/widget/FrameLayout;", "Landroid/view/TextureView$SurfaceTextureListener;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "attrs", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "defStyle", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "isVideoUrl", "", "mMediaPlayer", "Landroid/media/MediaPlayer;", "mTag", "", "mVideoHeight", "", "mVideoWidth", "onCompletionListener", "Landroid/media/MediaPlayer$OnCompletionListener;", "videoFileName", "getVideoFileName", "()Ljava/lang/String;", "setVideoFileName", "(Ljava/lang/String;)V", "videoGravity", "videoIsLoop", "videoSurface", "Landroid/view/TextureView;", "calculateVideoSize", "", "changeVideo", "getMediaPlayer", "getVideoSurface", "init", "initView", "onSurfaceTextureAvailable", "surface", "Landroid/graphics/SurfaceTexture;", "width", "height", "onSurfaceTextureDestroyed", "onSurfaceTextureSizeChanged", "onSurfaceTextureUpdated", "onVideoLayoutDestroy", "onVideoLayoutPause", "onVideoLayoutResume", "setListeners", "setPathOrUrl", "fileName", "surfaceSetup", "updateTextureViewSize", "screenWidth", "screenHeight", "swiftmk_debug"})
public final class VideoLayout extends android.widget.FrameLayout implements android.view.TextureView.SurfaceTextureListener {
    @org.jetbrains.annotations.NotNull()
    private java.lang.String videoFileName = "";
    
    /**
     * 位置权重
     * <enum name="start" value="0"/>
     * <enum name="end" value="1"/>
     * <enum name="centerCrop" value="2"/>
     * <enum name="none" value="3"/>
     */
    private int videoGravity = 2;
    private boolean videoIsLoop = false;
    private boolean isVideoUrl = false;
    private android.view.TextureView videoSurface;
    private float mVideoWidth = 0.0F;
    private float mVideoHeight = 0.0F;
    private android.media.MediaPlayer mMediaPlayer;
    private android.media.MediaPlayer.OnCompletionListener onCompletionListener;
    private final java.lang.String mTag = "VideoLayout";
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getVideoFileName() {
        return null;
    }
    
    public final void setVideoFileName(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    /**
     * 作用: 播放视频
     * 用法1: videoLayout?.apply {
     * onDestroyVideoLayout()
     * setGravity(VideoLayout.VGravity.none)
     * setIsLoop(true)
     * setPathOrUrl("login_bg.mp4", null)}
     * ?: run{ videoLayout = VideoLayout(this)}
     * vb.loginBg.addView(videoLayout)
     *
     * 用法2: val videoLayout=VideoLayout(this)
     * videoLayout?.apply {
     * onDestroyVideoLayout()
     * setGravity(VideoLayout.VGravity.none)
     * setIsLoop(false)
     * setPathOrUrl("login_bg.mp4", null)}
     * ?: run{ videoLayout = VideoLayout(this, {
     *     //视频播放完成执行完成逻辑})}
     * vb.loginBg.addView(videoLayout)
     */
    public VideoLayout(@org.jetbrains.annotations.Nullable()
    android.content.Context context) {
        super(null);
    }
    
    public VideoLayout(@org.jetbrains.annotations.Nullable()
    android.content.Context context, @org.jetbrains.annotations.Nullable()
    android.util.AttributeSet attrs) {
        super(null);
    }
    
    public VideoLayout(@org.jetbrains.annotations.Nullable()
    android.content.Context context, @org.jetbrains.annotations.Nullable()
    android.util.AttributeSet attrs, int defStyle) {
        super(null);
    }
    
    private final void init(android.util.AttributeSet attrs) {
    }
    
    private final void initView() {
    }
    
    private final void setListeners() {
    }
    
    private final void calculateVideoSize() {
    }
    
    private final void surfaceSetup() {
    }
    
    private final void updateTextureViewSize(int screenWidth, int screenHeight) {
    }
    
    @java.lang.Override()
    public void onSurfaceTextureAvailable(@org.jetbrains.annotations.NotNull()
    android.graphics.SurfaceTexture surface, int width, int height) {
    }
    
    @java.lang.Override()
    public void onSurfaceTextureSizeChanged(@org.jetbrains.annotations.NotNull()
    android.graphics.SurfaceTexture surface, int width, int height) {
    }
    
    @java.lang.Override()
    public boolean onSurfaceTextureDestroyed(@org.jetbrains.annotations.NotNull()
    android.graphics.SurfaceTexture surface) {
        return false;
    }
    
    @java.lang.Override()
    public void onSurfaceTextureUpdated(@org.jetbrains.annotations.NotNull()
    android.graphics.SurfaceTexture surface) {
    }
    
    public final void onVideoLayoutDestroy() {
    }
    
    public final void onVideoLayoutResume() {
    }
    
    public final void onVideoLayoutPause() {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final android.media.MediaPlayer getMediaPlayer() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final android.view.TextureView getVideoSurface() {
        return null;
    }
    
    public final void setPathOrUrl(@org.jetbrains.annotations.NotNull()
    java.lang.String fileName, @org.jetbrains.annotations.NotNull()
    android.media.MediaPlayer.OnCompletionListener onCompletionListener) {
    }
    
    private final void changeVideo(android.media.MediaPlayer.OnCompletionListener onCompletionListener) {
    }
}