package com.mozhimen.basicsk.cachek

import com.mozhimen.basicsk.cachek.mos.CacheK
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

/**
 * @ClassName CacheK
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/20 14:50
 * @Version 1.0
 */
object CacheK {

    /**
     * 保存cache
     * @param key String
     * @param body T
     */
    fun <T> saveCache(key: String, body: T) {
        val cache = CacheK(key, toByteArray(body))
        CacheKDatabase.get().cacheKDao.saveCache(cache)
    }

    /**
     * 获取cache
     * @param key String
     * @return T?
     */
    fun <T> getCache(key: String): T? {
        val cache = CacheKDatabase.get().cacheKDao.getCache(key)
        return (if (cache?.data != null) {
            toObject(cache.data)
        } else null) as? T
    }

    fun deleteCache(key: String) {
        val cache = CacheK(key, null)
        CacheKDatabase.get().cacheKDao.deleteCache(cache)
    }

    private fun <T> toByteArray(body: T): ByteArray? {
        var baos: ByteArrayOutputStream? = null
        var oos: ObjectOutputStream? = null
        try {
            baos = ByteArrayOutputStream()
            oos = ObjectOutputStream(baos)
            oos.writeObject(body)
            oos.flush()
            return baos.toByteArray()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            baos?.close()
            oos?.close()
        }

        return ByteArray(0)
    }

    private fun toObject(data: ByteArray?): Any? {
        var bais: ByteArrayInputStream? = null
        var ois: ObjectInputStream? = null
        try {
            bais = ByteArrayInputStream(data)
            ois = ObjectInputStream(bais)
            return ois.readObject()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            bais?.close()
            ois?.close()
        }

        return null
    }
}