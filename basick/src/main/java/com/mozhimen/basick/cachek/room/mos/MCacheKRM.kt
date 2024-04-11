package com.mozhimen.basick.cachek.room.mos

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

/**
 * @ClassName Cache
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Version 1.0
 */
@Entity(tableName = "cachekrm")
data class MCacheKRM(
    @PrimaryKey(autoGenerate = false)
    var key: String,
    var data: ByteArray
) : Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MCacheKRM

        if (key != other.key) return false
        return data.contentEquals(other.data)
    }

    override fun hashCode(): Int {
        var result = key.hashCode()
        result = 31 * result + data.contentHashCode()
        return result
    }
}