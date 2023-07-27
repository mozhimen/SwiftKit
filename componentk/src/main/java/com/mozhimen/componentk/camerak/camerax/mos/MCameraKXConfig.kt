package com.mozhimen.componentk.camerak.camerax.mos

import com.mozhimen.componentk.camerak.camerax.annors.ACameraKXFacing
import com.mozhimen.componentk.camerak.camerax.annors.ACameraKXFormat

/**
 * @ClassName CameraXKConfig
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/12/15 11:28
 * @Version 1.0
 */
data class MCameraKXConfig(
    @ACameraKXFormat val format: Int = ACameraKXFormat.YUV_420_888,
    @ACameraKXFacing val facing: Int = ACameraKXFacing.BACK
)