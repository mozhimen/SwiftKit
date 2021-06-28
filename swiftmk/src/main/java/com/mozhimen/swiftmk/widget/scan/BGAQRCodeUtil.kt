package com.mozhimen.swiftmk.widget.scan

import android.content.Context
import android.graphics.*
import android.util.Log
import android.util.TypedValue
import android.view.MotionEvent
import android.view.WindowManager

/**
 * @ClassName BGAQRCodeUtil
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/6/21 14:18
 * @Version 1.0
 */
object BGAQRCodeUtil {
    private var debug = false
    fun setDebug(debug: Boolean) {
        BGAQRCodeUtil.debug = debug
    }

    fun isDebug(): Boolean {
        return debug
    }

    fun d(msg: String?) {
        d("BGAQRCode", msg)
    }

    fun printRect(prefix: String, rect: Rect) {
        d("BGAQRCodeFocusArea", prefix + " centerX：" + rect.centerX() + " centerY：" + rect.centerY() + " width：" + rect.width() + " height：" + rect.height()
                + " rectHalfWidth：" + rect.width() / 2 + " rectHalfHeight：" + rect.height() / 2 + " left：" + rect.left + " top：" + rect.top + " right：" + rect.right + " bottom：" + rect.bottom)
    }

    fun d(tag: String?, msg: String?) {
        if (debug) {
            if (msg != null) {
                Log.d(tag, msg)
            }
        }
    }

    fun e(msg: String?) {
        if (debug) {
            if (msg != null) {
                Log.e("BGAQRCode", msg)
            }
        }
    }

    /**
     * 是否为竖屏
     */
    fun isPortrait(context: Context): Boolean {
        val screenResolution = getScreenResolution(context)
        return screenResolution.y > screenResolution.x
    }

    fun getScreenResolution(context: Context): Point {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        val screenResolution = Point()
        display.getSize(screenResolution)
        return screenResolution
    }

    fun getStatusBarHeight(context: Context): Int {
        val typedArray = context.theme.obtainStyledAttributes(intArrayOf(
            android.R.attr.windowFullscreen
        ))
        val windowFullscreen = typedArray.getBoolean(0, false)
        typedArray.recycle()
        if (windowFullscreen) {
            return 0
        }
        var height = 0
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            height = context.resources.getDimensionPixelSize(resourceId)
        }
        return height
    }

    fun dp2px(context: Context, dpValue: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.resources.displayMetrics).toInt()
    }

    fun sp2px(context: Context, spValue: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, context.resources.displayMetrics).toInt()
    }

    fun adjustPhotoRotation(inputBitmap: Bitmap?, orientationDegree: Int): Bitmap? {
        if (inputBitmap == null) {
            return null
        }
        val matrix = Matrix()
        matrix.setRotate(orientationDegree.toFloat(), inputBitmap.width.toFloat() / 2, inputBitmap.height.toFloat() / 2)
        val outputX: Float
        val outputY: Float
        if (orientationDegree == 90) {
            outputX = inputBitmap.height.toFloat()
            outputY = 0f
        } else {
            outputX = inputBitmap.height.toFloat()
            outputY = inputBitmap.width.toFloat()
        }
        val values = FloatArray(9)
        matrix.getValues(values)
        val x1 = values[Matrix.MTRANS_X]
        val y1 = values[Matrix.MTRANS_Y]
        matrix.postTranslate(outputX - x1, outputY - y1)
        val outputBitmap = Bitmap.createBitmap(inputBitmap.height, inputBitmap.width, Bitmap.Config.ARGB_8888)
        val paint = Paint()
        val canvas = Canvas(outputBitmap)
        canvas.drawBitmap(inputBitmap, matrix, paint)
        return outputBitmap
    }

    fun makeTintBitmap(inputBitmap: Bitmap?, tintColor: Int): Bitmap? {
        if (inputBitmap == null) {
            return null
        }
        val outputBitmap = Bitmap.createBitmap(inputBitmap.width, inputBitmap.height, inputBitmap.config)
        val canvas = Canvas(outputBitmap)
        val paint = Paint()
        paint.colorFilter = PorterDuffColorFilter(tintColor, PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(inputBitmap, 0f, 0f, paint)
        return outputBitmap
    }

    /**
     * 计算对焦和测光区域
     *
     * @param coefficient        比率
     * @param originFocusCenterX 对焦中心点X
     * @param originFocusCenterY 对焦中心点Y
     * @param originFocusWidth   对焦宽度
     * @param originFocusHeight  对焦高度
     * @param previewViewWidth   预览宽度
     * @param previewViewHeight  预览高度
     */
    fun calculateFocusMeteringArea(coefficient: Float,
                                   originFocusCenterX: Float, originFocusCenterY: Float,
                                   originFocusWidth: Int, originFocusHeight: Int,
                                   previewViewWidth: Int, previewViewHeight: Int): Rect {
        val halfFocusAreaWidth = (originFocusWidth * coefficient / 2).toInt()
        val halfFocusAreaHeight = (originFocusHeight * coefficient / 2).toInt()
        val centerX = (originFocusCenterX / previewViewWidth * 2000 - 1000).toInt()
        val centerY = (originFocusCenterY / previewViewHeight * 2000 - 1000).toInt()
        val rectF = RectF(clamp(centerX - halfFocusAreaWidth, -1000, 1000).toFloat(),
            clamp(centerY - halfFocusAreaHeight, -1000, 1000).toFloat(),
            clamp(centerX + halfFocusAreaWidth, -1000, 1000).toFloat(),
            clamp(centerY + halfFocusAreaHeight, -1000, 1000).toFloat())
        return Rect(Math.round(rectF.left), Math.round(rectF.top),
            Math.round(rectF.right), Math.round(rectF.bottom))
    }

    fun clamp(value: Int, min: Int, max: Int): Int {
        return Math.min(Math.max(value, min), max)
    }

    /**
     * 计算手指间距
     */
    fun calculateFingerSpacing(event: MotionEvent): Float {
        val x = event.getX(0) - event.getX(1)
        val y = event.getY(0) - event.getY(1)
        return Math.sqrt((x * x + y * y).toDouble()).toFloat()
    }

    /**
     * 将本地图片文件转换成可解码二维码的 Bitmap。为了避免图片太大，这里对图片进行了压缩。感谢 https://github.com/devilsen 提的 PR
     *
     * @param picturePath 本地图片文件路径
     */
    fun getDecodeAbleBitmap(picturePath: String?): Bitmap? {
        return try {
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            BitmapFactory.decodeFile(picturePath, options)
            var sampleSize = options.outHeight / 400
            if (sampleSize <= 0) {
                sampleSize = 1
            }
            options.inSampleSize = sampleSize
            options.inJustDecodeBounds = false
            BitmapFactory.decodeFile(picturePath, options)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}