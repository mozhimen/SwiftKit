package com.mozhimen.app.abilitymk.hotfixmk

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.mozhimen.abilitymk.hotfixmk.HotFixMK
import com.mozhimen.app.R
import com.mozhimen.app.abilitymk.hotfixmk.HotFixMKTest
import com.mozhimen.app.databinding.ActivityHotfixmkBinding
import java.io.File

class HotFixMKActivity : AppCompatActivity() {
    private val vb by lazy { ActivityHotfixmkBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)
    }

    fun fixBug(view: View) {
        val permission = android.Manifest.permission.READ_EXTERNAL_STORAGE
        if (ActivityCompat.checkSelfPermission(
                this,
                permission
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fix()
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(permission), 1000)
        }
    }

    fun test(view: View) {
        Toast.makeText(this, HotFixMKTest().test(), Toast.LENGTH_SHORT).show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            fix()
        }
    }

    private fun fix() {
        HotFixMK.fix(this, File(Environment.getExternalStorageDirectory(), "patch.dex"))
    }
}