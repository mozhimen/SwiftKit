package com.mozhimen.app.abilitymk.restfulmk

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mozhimen.abilitymk.restfulmk.commons.CallbackMK
import com.mozhimen.abilitymk.restfulmk.mos.ResponseMK
import com.mozhimen.app.R
import com.mozhimen.app.databinding.ActivityRestfulmkBinding
import org.json.JSONObject

class RESTfulMKActivity : AppCompatActivity() {
    private val vb by lazy { ActivityRestfulmkBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)

        ApiFactory.create(TestApi::class.java).listCities("mozhimen").enqueue(object :
            CallbackMK<JSONObject> {
            override fun onSuccess(responseMK: ResponseMK<JSONObject>) {

            }

            override fun onFail(throwable: Throwable) {

            }
        })
    }
}