package com.mozhimen.basick.prefabk

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
class PrefabKGlide : AppGlideModule() {
    override fun isManifestParsingEnabled(): Boolean = false
}