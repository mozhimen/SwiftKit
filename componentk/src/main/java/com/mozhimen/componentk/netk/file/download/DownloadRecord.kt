package com.mozhimen.componentk.netk.file.download

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import com.mozhimen.basick.elemk.android.app.cons.CDownloadManager
import com.mozhimen.basick.utilk.android.database.getColumnValue
import com.mozhimen.basick.utilk.android.util.UtilKLog.dt
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.componentk.netk.file.download.annors.ANotificationVisibility

/**
 * 'download' table record
 * @author by chiclaim@google.com
 */
class DownloadRecord(
    var id: Long = 0L,
    var url: String? = null,
    var fileName: String? = null,
    var destinationUri: String? = null,
    var ignoreLocal: Boolean = false,
    var needInstall: Boolean = false,
    var notificationVisibility: Int = ANotificationVisibility.VISIBLE_NOTIFY_COMPLETED,
    var notificationTitle: String? = null,
    var notificationContent: String? = null,
    var totalBytes: Long = 0L,
    var status: Int = CDownloadManager.STATUS_UNKNOWN
) : BaseUtilK() {

    private fun createFromCursor(cursor: Cursor): DownloadRecord {
        val record = DownloadRecord()
        cursor.getColumnValue<Long>(CDownloadParameter.COLUMN_ID)?.let {
            record.id = it
        }
        cursor.getColumnValue<String>(CDownloadParameter.COLUMN_URL)?.let {
            record.url = it
        }
        cursor.getColumnValue<String>(CDownloadParameter.COLUMN_FILENAME)?.let {
            record.fileName = it
        }
        cursor.getColumnValue<String>(CDownloadParameter.COLUMN_DESTINATION_URI)?.let {
            record.destinationUri = it
        }
        cursor.getColumnValue<Boolean>(CDownloadParameter.COLUMN_IGNORE_LOCAL)?.let {
            record.ignoreLocal = it
        }
        cursor.getColumnValue<Boolean>(CDownloadParameter.COLUMN_NEED_INSTALL)?.let {
            record.needInstall = it
        }
        cursor.getColumnValue<Int>(CDownloadParameter.COLUMN_NOTIFICATION_VISIBILITY)?.let {
            record.notificationVisibility = it
        }
        cursor.getColumnValue<String>(CDownloadParameter.COLUMN_NOTIFICATION_TITLE)?.let {
            record.notificationTitle = it
        }
        cursor.getColumnValue<String>(CDownloadParameter.COLUMN_NOTIFICATION_CONTENT)?.let {
            record.notificationContent = it
        }
        cursor.getColumnValue<Long>(CDownloadParameter.COLUMN_TOTAL_BYTES)?.let {
            record.totalBytes = it
        }
        cursor.getColumnValue<Int>(CDownloadParameter.COLUMN_STATUS)?.let {
            record.status = it
        }
        return record
    }

    fun queryByUrl(context: Context): List<DownloadRecord> {
        return buildList {
            val db: SQLiteDatabase
            try {
                db = DBManager.getDB(context).readableDatabase
            } catch (e: SQLiteException) {
                e.printStackTrace()
                return this
            }
            db.query(
                CDownloadParameter.TABLE_NAME,
                arrayOf(
                    CDownloadParameter.COLUMN_ID,
                    CDownloadParameter.COLUMN_URL,
                    CDownloadParameter.COLUMN_FILENAME,
                    CDownloadParameter.COLUMN_DESTINATION_URI,
                    CDownloadParameter.COLUMN_IGNORE_LOCAL,
                    CDownloadParameter.COLUMN_NEED_INSTALL,
                    CDownloadParameter.COLUMN_NOTIFICATION_VISIBILITY,
                    CDownloadParameter.COLUMN_NOTIFICATION_TITLE,
                    CDownloadParameter.COLUMN_NOTIFICATION_CONTENT,
                    CDownloadParameter.COLUMN_TOTAL_BYTES,
                    CDownloadParameter.COLUMN_STATUS
                ),
                " ${CDownloadParameter.COLUMN_URL}=? ",
                arrayOf(url), null, null, null
            )?.use { cursor ->
                while (cursor.moveToNext()) {
                    add(createFromCursor(cursor))
                }
            }
        }
    }


    private fun toContentValues(): ContentValues {
        return ContentValues().apply {
            put(CDownloadParameter.COLUMN_URL, url)
            put(CDownloadParameter.COLUMN_FILENAME, fileName)
            put(CDownloadParameter.COLUMN_DESTINATION_URI, destinationUri)
            put(CDownloadParameter.COLUMN_IGNORE_LOCAL, if (ignoreLocal) 1 else 0)
            put(CDownloadParameter.COLUMN_NEED_INSTALL, if (needInstall) 1 else 0)
            put(CDownloadParameter.COLUMN_NOTIFICATION_VISIBILITY, notificationVisibility)
            put(CDownloadParameter.COLUMN_NOTIFICATION_TITLE, notificationTitle)
            put(CDownloadParameter.COLUMN_NOTIFICATION_CONTENT, notificationContent)
            put(CDownloadParameter.COLUMN_TOTAL_BYTES, totalBytes)
            put(CDownloadParameter.COLUMN_STATUS, status)
        }
    }

    /**
     * return row number
     */
    fun insert(context: Context): Long {
        val rowId = try {
            val db = DBManager.getDB(context).writableDatabase
            db.insert(CDownloadParameter.TABLE_NAME, null, toContentValues())
        } catch (e: SQLiteException) {
            e.printStackTrace()
            -1
        }
        if (rowId > 0) {
            dt(TAG, "record insert success $this")
        } else {
            dt(TAG, "record insert failed $rowId $this")
        }
        return rowId
    }

    /**
     * return the number of rows affected
     */
    fun update(context: Context): Int {
        val result = try {
            val db = DBManager.getDB(context).writableDatabase
            db.update(
                CDownloadParameter.TABLE_NAME,
                toContentValues(),
                " ${CDownloadParameter.COLUMN_ID}=? ",
                arrayOf(id.toString())
            )
        } catch (e: SQLiteException) {
            e.printStackTrace()
            -1
        }
        if (result > 0) {
            dt(TAG, "record update success $this")
        } else {
            dt(TAG, "record update failed $result $this")
        }
        return result
    }

    /**
     * return the number of rows affected
     */
    fun delete(context: Context): Int {
        val rows = try {
            val db = DBManager.getDB(context).writableDatabase
            db.delete(CDownloadParameter.TABLE_NAME, "${CDownloadParameter.COLUMN_ID}=?", arrayOf(id.toString()))
        } catch (e: SQLiteException) {
            e.printStackTrace()
            -1
        }
        if (rows > 0) {
            dt(TAG, "record delete success $this")
        } else {
            dt(TAG, "record delete failed $this")
        }
        return rows
    }

    override fun toString(): String {
        return "DownloadRecord(id=$id, url=$url, fileName=$fileName, destinationUri=$destinationUri, ignoreLocal=$ignoreLocal, needInstall=$needInstall, notificationVisibility=$notificationVisibility, notificationTitle=$notificationTitle, notificationContent=$notificationContent, totalBytes=$totalBytes, status=$status)"
    }


}