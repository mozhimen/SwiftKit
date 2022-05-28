package com.mozhimen.basick.loadk

import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule

/**
 * @ClassName LoadKGlide
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/5/7 20:50
 * @Version 1.0
 */
@GlideModule
class LoadKGlide : AppGlideModule() {
    override fun isManifestParsingEnabled(): Boolean = false
}