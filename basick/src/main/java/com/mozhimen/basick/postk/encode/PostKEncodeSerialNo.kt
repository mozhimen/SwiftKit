package com.mozhimen.basick.postk.encode

import com.mozhimen.basick.postk.encode.helpers.EncodeSerialNoProvider
import java.util.concurrent.ConcurrentHashMap


/**
 * @ClassName PostKEncodeSerialNo
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/7/21 19:08
 * @Version 1.0
 */
class PostKEncodeSerialNo {
    companion object {
        @JvmStatic
        val instance = INSTANCE.holder
    }

    /////////////////////////////////////////////////////////////////////////////////////

    private val _snMap = ConcurrentHashMap<String, EncodeSerialNoProvider>()

    /////////////////////////////////////////////////////////////////////////////////////

    fun with(name: String): EncodeSerialNoProvider {
        var sn = _snMap[name]
        if (sn == null) {
            sn = EncodeSerialNoProvider(name)
            _snMap[name] = sn
        }
        return sn
    }

    /////////////////////////////////////////////////////////////////////////////////////

    private object INSTANCE {
        val holder = PostKEncodeSerialNo()
    }
}