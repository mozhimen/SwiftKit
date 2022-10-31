package com.mozhimen.basick.cachek.mos

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @ClassName Cache
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/20 14:54
 * @Version 1.0
 */
@Entity(tableName = "cachek")
class MCacheK(
    @PrimaryKey(autoGenerate = false)
    @NonNull var key: String, var data: ByteArray?
)