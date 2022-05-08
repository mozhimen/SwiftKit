package com.mozhimen.app.abilityk.restfulk

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mozhimen.abilityk.restfulk.commons._ICallback
import com.mozhimen.abilityk.restfulk.mos._Response
import com.mozhimen.app.databinding.ActivityRestfulkBinding
import org.json.JSONObject

class RESTfulKActivity : AppCompatActivity() {
    private val vb by lazy { ActivityRestfulkBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)

        ApiFactory.create(TestApi::class.java).listCities("mozhimen").enqueue(object :
            _ICallback<JSONObject> {
            override fun onSuccess(response: _Response<JSONObject>) {

            }

            override fun onFail(throwable: Throwable) {

            }
        })
    }
}