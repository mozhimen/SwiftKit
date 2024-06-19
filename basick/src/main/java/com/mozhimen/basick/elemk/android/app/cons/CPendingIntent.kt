package com.mozhimen.basick.elemk.android.app.cons

import android.app.PendingIntent
import com.mozhimen.basick.lintk.optins.OApiDeprecated_Official_AfterV_31_11_S

/**
 * @ClassName CPendingIntent
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/8/4 0:31
 * @Version 1.0
 */
object CPendingIntent {
    @OApiDeprecated_Official_AfterV_31_11_S
    const val FLAG_NONE = 0

    const val FLAG_UPDATE_CURRENT = PendingIntent.FLAG_UPDATE_CURRENT

    /**
     * 作用：
     *
     * 安全性：使用 FLAG_IMMUTABLE 可以提高安全性。因为当一个 PendingIntent 是不可变的，其他应用或组件无法修改其内容（例如，无法改变包含的 Intent 对象或请求代码），这就避免了潜在的安全漏洞。
     * 防止意外更改：在某些情况下，我们希望 PendingIntent 的行为是确定的，不会因为其他应用的干扰而改变。使用 FLAG_IMMUTABLE 可以确保这一点。
     * 与 FLAG_MUTABLE 的区别：
     *
     * FLAG_MUTABLE：允许 PendingIntent 的内容可以被更改。
     * FLAG_IMMUTABLE：禁止任何对 PendingIntent 内容的更改
     */
    const val FLAG_IMMUTABLE = PendingIntent.FLAG_IMMUTABLE
}