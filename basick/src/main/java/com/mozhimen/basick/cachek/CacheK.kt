package com.mozhimen.basick.cachek

import com.mozhimen.basick.cachek.commons.CacheKDatabase
import com.mozhimen.basick.cachek.mos.MCacheK
import com.mozhimen.basick.utilk.exts.et
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
    private const val TAG = "CacheK>>>>>"

    /**
     * 保存cache
     * @param key String
     * @param body T
     */
    fun <T> saveCache(key: String, body: T) {
        val cache = MCacheK(key, toByteArray<T>(body))
        CacheKDatabase.get().cacheKDao.saveCache(cache)
    }

    /**
     * 获取cache
     * @param key String
     * @return T?
     */
    @Suppress("UNCHECKED_CAST")
    fun <T> getCache(key: String): T? {
        val cache = CacheKDatabase.get().cacheKDao.getCache(key)
        return (if (cache?.data != null) {
            toObject(cache.data)
        } else null) as? T?
    }

    fun deleteCache(key: String) {
        val cache = MCacheK(key, null)
        CacheKDatabase.get().cacheKDao.deleteCache(cache)
    }

    private fun <T> toByteArray(body: Any?): ByteArray? {
        var byteArrayOutputStream: ByteArrayOutputStream? = null
        var objectOutputStream: ObjectOutputStream? = null
        try {
            byteArrayOutputStream = ByteArrayOutputStream()
            objectOutputStream = ObjectOutputStream(byteArrayOutputStream)
            objectOutputStream.writeObject(body)
            objectOutputStream.flush()
            return byteArrayOutputStream.toByteArray()
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
        } finally {
            byteArrayOutputStream?.close()
            objectOutputStream?.close()
        }

        return ByteArray(0)
    }

    private fun toObject(data: ByteArray?): Any? {
        var byteArrayInputStream: ByteArrayInputStream? = null
        var objectInputStream: ObjectInputStream? = null
        try {
            byteArrayInputStream = ByteArrayInputStream(data)
            objectInputStream = ObjectInputStream(byteArrayInputStream)
            return objectInputStream.readObject()
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
        } finally {
            byteArrayInputStream?.close()
            objectInputStream?.close()
        }

        return null
    }
}