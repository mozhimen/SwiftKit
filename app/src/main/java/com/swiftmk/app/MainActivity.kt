package com.swiftmk.app

import android.content.Intent
import android.os.Bundle
import com.swiftmk.app.databinding.ActivityMainBinding
import com.swiftmk.library.base.BaseActivity

class MainActivity : BaseActivity() {
    private lateinit var mainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)


        mainBinding.mainGoto.setOnClickListener {
            val map = mapOf("param1" to "1", "param2" to "2")
            SecondActivity.startContext(this,map as HashMap<String, String>)
        }
    }
}