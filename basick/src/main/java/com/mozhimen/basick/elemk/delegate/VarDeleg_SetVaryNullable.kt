package com.mozhimen.basick.elemk.delegate

import com.mozhimen.basick.elemk.commons.IAA_BListener
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * @ClassName BaseDataDelegate
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/3/13 15:07
 * @Version 1.0
 */
/**
 * true 则赋值, 否则不赋值
 */
open class VarDeleg_SetVaryNullable<T>(default: T, onSetField: IAA_BListener<T, Boolean>) :
    VarDeleg_Set<T>(default, true, false, onSetField)