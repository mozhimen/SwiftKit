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
    fun build(): ANIM {
        return build(MAnimKConfig())
    }

    fun build(animKConfig: MAnimKConfig): ANIM

    fun format(animKConfig: MAnimKConfig, anim: ANIM)
}