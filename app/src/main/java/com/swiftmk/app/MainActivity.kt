package com.swiftmk.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.swiftmk.app.databinding.ActivityMainBinding
import com.swiftmk.library.base.BaseActivity

class MainActivity : BaseActivity() {
    private lateinit var mainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        mainBinding.mainGoto.setOnClickListener {
            SecondActivity.
        }
    }
}