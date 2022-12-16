package com.mozhimen.componentk.cameraxk.mos

import com.mozhimen.componentk.cameraxk.annors.ACameraXKFacing
import com.mozhimen.componentk.cameraxk.annors.ACameraXKFormat

/**
 * @ClassName CameraXKConfig
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/12/15 11:28
 * @Version 1.0
 */
data class CameraXKConfig(
    @ACameraXKFormat val format: Int = ACameraXKFormat.YUV_420_888,
    @ACameraXKFacing val facing: Int = ACameraXKFacing.BACK
)