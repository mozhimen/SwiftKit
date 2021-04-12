package com.swiftmk.app

import android.content.Context
import android.os.Bundle
import com.swiftmk.app.databinding.ActivitySecondBinding
import com.swiftmk.library.base.BaseActivity
import com.swiftmk.library.helper.activity.ActivityBridger
import com.swiftmk.library.helper.activity.ActivityBridgerImpl

@Suppress("CAST_NEVER_SUCCEEDS")
class SecondActivity : BaseActivity() {
    private lateinit var secondBinding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        secondBinding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(secondBinding.root)

        secondBinding.secondReturn.setOnClickListener {

        }
    }

    companion object : ActivityBridgerImpl {
        override fun actionStart(context: Context, pairs: Pair<String, Any?>) {
            ActivityBridger.actionStart(context, SecondActivity::class.java, pairs)
        }
    }
}