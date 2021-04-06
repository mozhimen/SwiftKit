package com.swiftmk.app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.swiftmk.app.databinding.ActivitySecondBinding
import com.swiftmk.library.base.BaseActivity
import com.swiftmk.library.helper.ActivityBridger

class SecondActivity : BaseActivity() {
    private lateinit var secondBinding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        secondBinding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(secondBinding.root)

        secondBinding.secondReturn.setOnClickListener {

        }
    }

    companion object : ActivityBridger
}