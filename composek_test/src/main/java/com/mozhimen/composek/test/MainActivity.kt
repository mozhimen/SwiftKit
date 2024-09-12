package com.mozhimen.composek.test

import android.app.Activity
import android.os.Bundle
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.savedstate.findViewTreeSavedStateRegistryOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import com.mozhimen.composek.test.ui.theme.SwiftKitTheme
import com.mozhimen.dslk.anko.core.frameLayout
import com.mozhimen.dslk.anko.core.funs.add
import com.mozhimen.dslk.anko.core.funs.lParams
import com.mozhimen.dslk.anko.core.proterties.matchParent
import com.mozhimen.kotlin.elemk.androidx.lifecycle.SavedStateRegistryOwnerProxy
import com.mozhimen.kotlin.lintk.optins.OApiInit_ByLazy
import com.mozhimen.kotlin.utilk.android.widget.showToast
import com.mozhimen.kotlin.utilk.commons.IUtilK
import com.mozhimen.xmlk.layoutk.magnet.LayoutKMagnet2

class MainActivity : Activity(),IUtilK {
    @OptIn(OApiInit_ByLazy::class)
    private val _savedStateRegistryOwnerProxy: SavedStateRegistryOwnerProxy by lazy { SavedStateRegistryOwnerProxy() }

    @OptIn(OApiInit_ByLazy::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _savedStateRegistryOwnerProxy.onCreate(NAME)
        if (this.window.decorView.findViewTreeLifecycleOwner()==null){
            this.window.decorView.setViewTreeLifecycleOwner(_savedStateRegistryOwnerProxy)
        }
        if (this.window.decorView.findViewTreeSavedStateRegistryOwner()==null){
            this.window.decorView.setViewTreeSavedStateRegistryOwner(_savedStateRegistryOwnerProxy)
        }
        setContentView(
            frameLayout {
                addView(LayoutKMagnet2(this@MainActivity,ComposeView(this@MainActivity).apply {
                    setContent {
                        SwiftKitTheme {
                            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                                Greeting(
                                    name = "Android",
                                    modifier = Modifier.padding(innerPadding).clickable {
                                        "Hello".showToast()
                                    }
                                )
                            }
                        }
                    }
                }))
                layoutParams = lParams {
                    width = matchParent
                    height = matchParent
                }
            }
        )
    }

    @OptIn(OApiInit_ByLazy::class)
    override fun onStart() {
        super.onStart()
        _savedStateRegistryOwnerProxy.onStart(NAME)
    }

    @OptIn(OApiInit_ByLazy::class)
    override fun onStop() {
        _savedStateRegistryOwnerProxy.onStop(NAME)
        super.onStop()
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SwiftKitTheme {
        Greeting("Android")
    }
}