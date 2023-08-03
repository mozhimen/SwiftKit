package com.mozhimen.componentk.netk.file.download

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.mozhimen.componentk.netk.file.download.cons.CDownloadParameter

/**
 *
 * @author by chiclaim@google.com
 */
internal object DBManager {

    // share single database object
    private lateinit var dbHelper: DBHelper

    internal class DBHelper(context: Context) :
        SQLiteOpenHelper(
            context,
            "download_db", null,
            1
        ) {

        override fun onCreate(db: SQLiteDatabase?) {
            db?.execSQL(
                """
            CREATE TABLE ${CDownloadParameter.TABLE_NAME}(
                ${CDownloadParameter.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT, 
                ${CDownloadParameter.COLUMN_URL} TEXT, 
                ${CDownloadParameter.COLUMN_FILENAME} TEXT, 
                ${CDownloadParameter.COLUMN_DESTINATION_URI} TEXT, 
                ${CDownloadParameter.COLUMN_IGNORE_LOCAL} NUMERIC, 
                ${CDownloadParameter.COLUMN_NEED_INSTALL} NUMERIC, 
                ${CDownloadParameter.COLUMN_NOTIFICATION_VISIBILITY} INTEGER, 
                ${CDownloadParameter.COLUMN_NOTIFICATION_TITLE} TEXT, 
                ${CDownloadParameter.COLUMN_NOTIFICATION_CONTENT} TEXT, 
                ${CDownloadParameter.COLUMN_TOTAL_BYTES} INTEGER, 
                ${CDownloadParameter.COLUMN_STATUS} INTEGER)
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