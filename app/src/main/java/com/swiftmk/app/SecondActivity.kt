package com.swiftmk.app

import android.os.Bundle
import com.swiftmk.app.databinding.ActivitySecondBinding
import com.swiftmk.library.base.BaseActivity

class SecondActivity:BaseActivity() {
    private lateinit var secondBinding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        secondBinding= ActivitySecondBinding.inflate(layoutInflater)
        setContentView(secondBinding.root)

        secondBinding.secondReturn.setOnClickListener {

        }
    }

    
}