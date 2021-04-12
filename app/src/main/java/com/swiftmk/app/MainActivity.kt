package com.swiftmk.app

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import com.swiftmk.app.databinding.ActivityMainBinding
import com.swiftmk.library.base.BaseActivity
import com.swiftmk.library.helper.activity.ActivityBridger

class MainActivity : BaseActivity(),SQLiteDatabase {
    private lateinit var mainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)


        mainBinding.mainGoto.setOnClickListener {
            val values = ActivityBridger.paramsOf("param1" to "1", "param2" to "2")
            SecondActivity.actionStart(this,values)
        }


    }
}