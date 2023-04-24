package com.mozhimen.componentk.netk.file.download

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 *
 * @author by chiclaim@google.com
 */
internal object DBManager {

    const val DOWNLOAD_DB_NAME = "download_db"
    const val DB_VERSION = 1

    // share single database object
    private lateinit var dbHelper: DBHelper

    internal class DBHelper(context: Context) :
        SQLiteOpenHelper(
            context,
            DOWNLOAD_DB_NAME, null,
            DB_VERSION
        ) {

        override fun onCreate(db: SQLiteDatabase?) {
            db?.execSQL(
                """
            CREATE TABLE ${DownloadRecord.Companion.TABLE_NAME}(
                ${DownloadRecord.Companion.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT, 
                ${DownloadRecord.Companion.COLUMN_URL} TEXT, 
                ${DownloadRecord.Companion.COLUMN_FILENAME} TEXT, 
                ${DownloadRecord.Companion.COLUMN_DESTINATION_URI} TEXT, 
                ${DownloadRecord.Companion.COLUMN_IGNORE_LOCAL} NUMERIC, 
                ${DownloadRecord.Companion.COLUMN_NEED_INSTALL} NUMERIC, 
                ${DownloadRecord.Companion.COLUMN_NOTIFICATION_VISIBILITY} INTEGER, 
                ${DownloadRecord.Companion.COLUMN_NOTIFICATION_TITLE} TEXT, 
                ${DownloadRecord.Companion.COLUMN_NOTIFICATION_CONTENT} TEXT, 
                ${DownloadRecord.Companion.COLUMN_TOTAL_BYTES} INTEGER, 
                ${DownloadRecord.Companion.COLUMN_STATUS} INTEGER)
        """.trimIndent()
            )
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        }

    }

    /**
     * return single database object
     */
    @Synchronized
    fun getDB(context: Context): DBHelper {
        if (!this::dbHelper.isInitialized) {
            DBManager.dbHelper =
                DBHelper(context)
        }
        return DBManager.dbHelper
    }

    /**
     * close database when you no longer need the database
     */
    fun close() {
        if (this::dbHelper.isInitialized) {
            DBManager.dbHelper.close()
        }
    }
}