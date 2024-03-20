package com.mozhimen.basick.postk.encode.helpers

import com.mozhimen.basick.cachek.room.CacheKRM
import com.mozhimen.basick.postk.encode.commons.IEncodeProvider
import com.mozhimen.basick.utilk.kotlin.UtilKNumber
import java.util.Calendar
import java.util.GregorianCalendar
import java.util.concurrent.atomic.AtomicLong


/**
 * @ClassName EncodeSerialNoProvider
 * @Description 每小时一清的流水号
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/7/21 19:15
 * @Version 1.0
 */
class EncodeSerialNoProvider(private val _encodeName: String) : IEncodeProvider {
    /**
     * 生成6位流水号，如：000001
     * @return
     */
    override fun generate(): String {
        //定义需要返回的流水号
        val serialNo: String
        //先查询到今天的日期，格式:"yyyyMMddHH"
        //再通过key查询缓存有没有num数据，缓存操作根据自身项目封装工具类
        var no: Long = CacheKRM.getLong(_encodeName)
        //如果缓存查询有值，数值+1，再赋值给下一个流水号
        no = if (no != 0L) no + 1L else 1L//如果缓存查询没值，直接赋值为1
        //流水号 = 缓存key + 拼接的数值 = 前缀 + 日期 + 拼接的数值
        serialNo = getCodeOfSix(no)
        //设置缓存，调用此方法，会自动将key所对应的value+1，保存时长：今天剩余的时间
        CacheKRM.putLong(_encodeName, getRemainTime(no))
        return serialNo
    }

    /**
     * 将数值拼接成对应的位数
     * @param nowNum  当前要生成的数字
     * @return 拼接好的流水号
     */
    fun getCodeOfSix(nowNum: Long): String {
        //封装的数字对象，里面 value 加了 volatile关键字，保证了线程安全
        val count = AtomicLong(nowNum)
        return UtilKNumber.complement_of0(count.get(), 6)
    }

    /**
     * 获取下小时结束还剩余多少秒
     * @return
     */
    fun getRemainTime(no: Long): Long {
        //获取今天当前时间
        val calendar = Calendar.getInstance()
        //获取明天凌晨0点的日期
        val nextHourCalendar: Calendar = GregorianCalendar(
            calendar[Calendar.YEAR],
            calendar[Calendar.MONTH],
            calendar[Calendar.DATE],
            calendar[Calendar.HOUR_OF_DAY] + 1, 0, 0
        )
        //返回 明天凌晨0点 和 今天当前时间 的差值（秒数）
        val remainTime = (nextHourCalendar.timeInMillis - calendar.timeInMillis) / 1000L
        return if (remainTime <= 0) 0 else no
    }
}