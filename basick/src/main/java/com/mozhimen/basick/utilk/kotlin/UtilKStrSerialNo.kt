package com.mozhimen.basick.utilk.kotlin

import com.mozhimen.basick.cachek.room.CacheKRM
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.java.util.UtilKCalendar
import java.util.concurrent.atomic.AtomicLong


/**
 * @ClassName UtilKGenerateNo
 * @Description 每小时一清的流水号
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/7/19 18:14
 * @Version 1.0
 */
object UtilKStrSerialNo : BaseUtilK() {

    /**
     * 生成6位流水号，如：000001
     * @return
     */
    @JvmStatic
    fun getStrSerialNo(): String {
        //定义需要返回的流水号
        val serialNo: String
        //先查询到今天的日期，格式:"yyyyMMddHH"
        //再通过key查询缓存有没有num数据，缓存操作根据自身项目封装工具类
        var no: Long = CacheKRM.getLong("utilk_generate_no")
        //如果缓存查询有值，数值+1，再赋值给下一个流水号
        no = if (no != 0L) no + 1L else 1L//如果缓存查询没值，直接赋值为1
        //流水号 = 缓存key + 拼接的数值 = 前缀 + 日期 + 拼接的数值
        serialNo = getStrNoComplementBy0(no, 6)
        //设置缓存，调用此方法，会自动将key所对应的value+1，保存时长：今天剩余的时间
        CacheKRM.putLong("utilk_generate_no", resetLongNo(no))
        return serialNo
    }

    /**
     * 将数值拼接成对应的位数
     * @param nowNo  当前要生成的数字
     * @return 拼接好的流水号
     */
    @JvmStatic
    fun getStrNoComplementBy0(nowNo: Long, bit: Int): String {
        //封装的数字对象，里面 value 加了 volatile关键字，保证了线程安全
        val count = AtomicLong(nowNo)
        return UtilKNumber.complementBy0(count.get(), bit)
    }

    /**
     * 获取下小时结束还剩余多少秒
     * @return
     */
    @JvmStatic
    fun resetLongNo(no: Long): Long {
        val remainTime = UtilKCalendar.getRemainTimeForNextHour()
        return if (remainTime <= 0) 0 else no
    }
}


