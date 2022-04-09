package com.mozhimen.app.abilityk.hotfixk

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.mozhimen.abilityk.hotfixk.HotFixK
import com.mozhimen.app.databinding.ActivityHotfixkBinding
import java.io.File

class HotFixKActivity : AppCompatActivity() {
    private val vb by lazy { ActivityHotfixkBinding.inflate(layoutInflater) }
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
        Toast.makeText(this, HotFixKTest().test(), Toast.LENGTH_SHORT).show()
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
        HotFixK.fix(this, File(Environment.getExternalStorageDirectory(), "patch.dex"))
    }
}