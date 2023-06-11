package com.mozhimen.basick.imagek.glide

/**
 * @ClassName ImageKGlide
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/6/10 16:53
 * @Version 1.0
 */
@Deprecated("replace to coil 用coil替代glide")
object ImageKGlide {

//
//    /**
//     * 加载普通图片
//     * @param imageView ImageView
//     * @param res Any
//     */
//    @JvmStatic
//    fun loadImage(
//        imageView: ImageView,
//        res: Any
//    ) {
//        if (!isGlideTypeMatch(res)) return
//        Glide.with(imageView).load(res).into(imageView)
//    }
//
//    /**
//     * 加载普通图片(复杂)
//     * @param imageView ImageView
//     * @param res Any
//     * @param placeholder Int
//     * @param error Int
//     */
//    @JvmStatic
//    fun loadImageComplex(
//        imageView: ImageView,
//        res: Any,
//        placeholder: Int,
//        error: Int
//    ) {
//        if (!isGlideTypeMatch(res)) return
//        Glide.with(imageView).load(res).transition(DrawableTransitionOptions.withCrossFade()).error(error).placeholder(placeholder)
//            .into(imageView)
//    }
//
//    /**
//     * 加载圆形图片
//     * @param imageView ImageView
//     * @param res Any
//     * @param placeholder Int
//     * @param error Int
//     */
//    @JvmStatic
//    fun loadImageCircle(
//        imageView: ImageView,
//        res: Any,
//        placeholder: Int,
//        error: Int
//    ) {
//        if (!isGlideTypeMatch(res)) return
//        Glide.with(imageView).load(res).transition(DrawableTransitionOptions.withCrossFade())
//            .transform(CircleCrop()).placeholder(placeholder).error(error)
//            .into(imageView)
//    }
//
//    /**
//     * 加载带边框的圆角图片
//     * @param imageView ImageView
//     * @param res Any
//     * @param borderWidth Float
//     * @param borderColor Int
//     * @param placeholder Int
//     * @param error Int
//     */
//    @JvmStatic
//    fun loadImageCircleBorder(
//        imageView: ImageView,
//        res: Any,
//        borderWidth: Float,
//        borderColor: Int,
//        placeholder: Int,
//        error: Int
//    ) {
//        if (!isGlideTypeMatch(res)) return
//        Glide.with(imageView).load(res).transition(DrawableTransitionOptions.withCrossFade())
//            .transform(CircleBorderTransform(borderWidth, borderColor)).placeholder(placeholder).error(error)
//            .into(imageView)
//    }
//
//    /**
//     * 加载圆角图片
//     * @param imageView ImageView
//     * @param res Any
//     * @param cornerRadius Int
//     * @param placeholder Int
//     * @param error Int
//     */
//    @JvmStatic
//    fun loadImageCorner(
//        imageView: ImageView,
//        res: Any,
//        cornerRadius: Int,
//        placeholder: Int,
//        error: Int
//    ) {
//        if (!isGlideTypeMatch(res)) return
//        Glide.with(imageView).load(res).transition(DrawableTransitionOptions.withCrossFade())
//            .transform(CenterCrop(), RoundedCorners(cornerRadius)).placeholder(placeholder).error(error)
//            .into(imageView)
//    }
//
//    /**
//     * glide类型匹配
//     * @param arg Any
//     * @return Boolean
//     */
//    @JvmStatic
//    fun isGlideTypeMatch(arg: Any): Boolean =
//        UtilKDataType.isTypeMatch(
//            arg,
//            String::class.java, Bitmap::class.java, Uri::class.java, File::class.java, Number::class.java, ByteArray::class.java,
//        )
//
//    private class CircleBorderTransform(private val _borderWidth: Float, borderColor: Int) : CircleCrop() {
//        private val borderPaint = Paint(Paint.ANTI_ALIAS_FLAG)
//
//        init {
//            borderPaint.color = borderColor
//            borderPaint.style = Paint.Style.STROKE
//            borderPaint.strokeWidth = _borderWidth
//        }
//
//        override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap {
//            val transform = super.transform(pool, toTransform, outWidth, outHeight)
//            val canvas = Canvas(transform)
//            val radiusWidth = outWidth / 2f
//            val radiusHeight = outHeight / 2f
//            canvas.drawCircle(
//                radiusWidth,
//                radiusHeight,
//                radiusWidth.coerceAtMost(radiusHeight) - _borderWidth / 2f,
//                borderPaint
//            )
//            canvas.setBitmap(null)
//            return transform
//        }
//    }
}