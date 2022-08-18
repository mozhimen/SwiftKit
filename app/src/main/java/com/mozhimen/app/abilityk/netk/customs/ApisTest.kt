package com.mozhimen.app.abilityk.netk.customs

import com.mozhimen.abilityk.netk.mos.NetKCommon
import com.mozhimen.app.abilityk.netk.mos.LingXiReq
import com.mozhimen.app.abilityk.netk.mos.LingXiRes
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * @ClassName ApisTest
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/8/17 12:53
 * @Version 1.0
 */
interface ApisTest {
    @POST("anno/open-platform/lingxi/getRingSuHealthStatus")
    suspend fun getRingSuHealthStatus(@Body req: LingXiReq): NetKCommon<LingXiRes>
}