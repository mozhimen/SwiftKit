package com.mozhimen.basick.extsk.view

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.mozhimen.basick.utilk.view.UtilKViewImage

/**
 * @ClassName ExtsKViewImage
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/6 14:47
 * @Version 1.0
 */
/**
 * 自适应图片
 * @receiver ImageView
 * @param drawable Drawable
 */
fun ImageView.fitImage(
    drawable: Drawable
) {
    UtilKViewImage.fitImage(this, drawable)
}