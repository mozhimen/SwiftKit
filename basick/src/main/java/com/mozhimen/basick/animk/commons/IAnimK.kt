package com.mozhimen.basick.animk.commons

import android.util.SparseArray
import com.mozhimen.basick.animk.mos.*

/**
 * @ClassName AnimationApi
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/17 22:50
 * @Version 1.0
 */
abstract class IAnimK<T> {
    var configs: SparseArray<IAnimKConfig<*>>? = null

    fun asAlpha(config: AlphaConfig): T {
        appendConfigs(config)
        return this as T
    }

    fun asScale(config: ScaleConfig): T {
        appendConfigs(config)
        return this as T
    }

    fun asTranslation(config: TranslationConfig): T {
        appendConfigs(config)
        return this as T
    }

    fun asRotation(config: RotationConfig): T {
        appendConfigs(config)
        return this as T
    }

    private fun appendConfigs(config: IAnimKConfig<*>) {
        if (configs == null) {
            configs = SparseArray()
        }
        configs!!.delete(config.key())
        configs!!.append(config.key(), config)
    }
}