package com.mozhimen.componentk.mediak.status.commons

import com.mozhimen.componentk.mediak.status.cons.EPlayStatus

/**
 * @ClassName IMediaKStatusPLayer
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/5/29 15:38
 * @Version 1.0
 */
interface IMediaKStatusPLayer {
    /**
     * 设置播放状态
     * @param status EPlayStatus
     */
    fun setPlayStatus(status: EPlayStatus)

    /**
     * 获取播放状态
     * @return EPlayStatus
     */
    fun getPlayStatus(): EPlayStatus

    /**
     * 是否播放结束
     * @return Boolean
     */
    fun isPlayComplete(): Boolean
}