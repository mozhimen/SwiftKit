package com.mozhimen.basick.utilk.android.text

import android.graphics.drawable.Drawable
import android.text.style.BackgroundColorSpan
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.ImageSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StrikethroughSpan
import android.text.style.StyleSpan
import android.text.style.SubscriptSpan
import android.text.style.SuperscriptSpan
import android.text.style.URLSpan
import android.text.style.UnderlineSpan
import androidx.annotation.ColorInt
import androidx.annotation.FloatRange
import com.mozhimen.basick.elemk.android.graphics.annors.ATypeface
import com.mozhimen.basick.elemk.android.graphics.cons.CTypeface

/**
 * @ClassName UtilLCharacterStyle
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/1/12
 * @Version 1.0
 */
object UtilKCharacterStyle {
    @JvmStatic
    fun getForegroundColorSpan(@ColorInt intColor: Int): ForegroundColorSpan =
        ForegroundColorSpan(intColor/*Color.parseColor("#0099EE")*/)

    @JvmStatic
    fun getBackgroundColorSpan(@ColorInt intColor: Int): BackgroundColorSpan =
        BackgroundColorSpan(intColor)

    @JvmStatic
    fun getRelativeSizeSpan(@FloatRange(from = 0.0) proportion: Float): RelativeSizeSpan =
        RelativeSizeSpan(proportion)

    /**
     * 删除线
     */
    @JvmStatic
    fun getStrikethroughSpan(): StrikethroughSpan =
        StrikethroughSpan()

    /**
     * 下划线
     */
    @JvmStatic
    fun getUnderlineSpan(): UnderlineSpan =
        UnderlineSpan()

    /**
     * 上标
     */
    @JvmStatic
    fun getSuperscriptSpan(): SuperscriptSpan =
        SuperscriptSpan()

    /**
     * 下标
     */
    @JvmStatic
    fun getSubscriptSpan(): SubscriptSpan =
        SubscriptSpan()

    /**
     * 为文字设置风格（粗体、斜体）
     */
    @JvmStatic
    fun getStyleSpan(@ATypeface style: Int): StyleSpan =
        StyleSpan(style)

    @JvmStatic
    fun getStyleSpanBold(): StyleSpan =
        getStyleSpan(CTypeface.BOLD)

    /**
     * 文本图片
     */
    @JvmStatic
    fun getImageSpan(drawable: Drawable): ImageSpan =
        ImageSpan(drawable)

    /**
     * SpannableString spannableString = new SpannableString("为文字设置点击事件");
     * MyClickableSpan clickableSpan = new MyClickableSpan("http://www.jianshu.com/users/dbae9ac95c78");
     * spannableString.setSpan(clickableSpan, 5, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
     * textView.setMovementMethod(LinkMovementMethod.getInstance());
     * textView.setHighlightColor(Color.parseColor("#36969696"));
     * textView.setText(spannableString);
     *
     * /***************************************************************/
     *
     * class MyClickableSpan extends ClickableSpan {
     *
     *     private String content;
     *
     *     public MyClickableSpan(String content) {
     *         this.content = content;
     *     }
     *
     *     @Override
     *     public void updateDrawState(TextPaint ds) {
     *         ds.setUnderlineText(false);
     *     }
     *
     *     @Override
     *     public void onClick(View widget) {
     *         Intent intent = new Intent(MainActivity.this, OtherActivity.class);
     *         Bundle bundle = new Bundle();
     *         bundle.putString("content", content);
     *         intent.putExtra("bundle", bundle);
     *         startActivity(intent);
     *     }
     * }
     *
     * 去掉下划线
     * val clickableSpan = object : ClickableSpan() {
     *             override fun onClick(widget: View) {
     *                 UtilKLog.dt(TAG, "onClick: ")
     *             }
     *
     *             override fun updateDrawState(ds: TextPaint) {
     *                 ds.setColor(ds.linkColor)
     *                 ds.isUnderlineText = false
     *             }
     *         }
     *  至于setHighlightColor方法则是控制点击是的背景色
     */
//    @JvmStatic
//    fun getClickableSpan():ClickableSpan =
//        ClickableSpan()

    /**
     * SpannableString spannableString = new SpannableString("为文字设置超链接");
     * URLSpan urlSpan = new URLSpan("http://www.jianshu.com/users/dbae9ac95c78");
     * spannableString.setSpan(urlSpan, 5, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
     * textView.setMovementMethod(LinkMovementMethod.getInstance());
     * textView.setHighlightColor(Color.parseColor("#36969696"));
     * textView.setText(spannableString);
     */
    @JvmStatic
    fun getURLSpan(strUrl: String): URLSpan =
        URLSpan(strUrl)

    /**
     * 除此之外，还有MaskFilterSpan可以实现模糊和浮雕效果，RasterizerSpan可以实现光栅效果，因为以上两个使用频率不高，而且效果也不是很明显，就不做详细说明，有兴趣的小伙伴不妨去试一试
     * 链接：https://www.jianshu.com/p/84067ad289d2
     * 来源：简书
     */
}