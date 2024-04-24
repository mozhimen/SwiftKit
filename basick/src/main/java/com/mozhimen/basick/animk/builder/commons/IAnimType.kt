package com.mozhimen.basick.animk.builder.commons

import com.mozhimen.basick.animk.builder.mos.MAnimKConfig
import com.mozhimen.basick.utilk.commons.IUtilK

/**
 * @ClassName IAnimType
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/4/24
 * @Version 1.0
 */
interface IAnimType<ANIM> : IUtilK {
    fun buildAnim(animKConfig: MAnimKConfig): ANIM

    fun formatAnim(animKConfig: MAnimKConfig, anim: ANIM)
}