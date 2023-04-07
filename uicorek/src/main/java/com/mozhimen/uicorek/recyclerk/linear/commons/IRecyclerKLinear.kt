package com.mozhimen.uicorek.recyclerk.linear.commons

import com.mozhimen.basick.elemk.mos.MKey
import com.mozhimen.uicorek.recyclerk.linear.IRecyclerKLinearListener


/**
 * @ClassName IRecyclerKLinear
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/4/6 10:58
 * @Version 1.0
 */
interface IRecyclerKLinear {
    /**
     * 设置监听器
     * @param listener Function2<[@kotlin.ParameterName] Int, MoKKey, Unit>
     */
    fun setOnItemClickListener(listener: IRecyclerKLinearListener)

    /**
     * 绑定数据
     * @param keys List<MoKKey>
     * @param listener Function2<[@kotlin.ParameterName] Int, MoKKey, Unit>
     */
    fun bindKeys(keys: List<MKey>, listener: IRecyclerKLinearListener)

    /**
     * 清除数据
     */
    fun clearKeys()

    /**
     * 增加字段
     * @param key MoKKey
     */
    fun addKey(key: MKey)

    /**
     * 删除字段
     * @param key MoKKey
     */
    fun removeKey(key: MKey)

    /**
     * 删除字段
     * @param index Int
     */
    fun removeKey(index: Int)
}