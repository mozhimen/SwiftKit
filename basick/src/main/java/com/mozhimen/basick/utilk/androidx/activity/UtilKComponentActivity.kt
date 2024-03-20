package com.mozhimen.basick.utilk.androidx.activity

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.registerForActivityResult
import androidx.annotation.RequiresApi
import com.mozhimen.basick.elemk.android.os.cons.CVersCode


/**
 * @ClassName UtilKComponentActivity
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/3/20
 * @Version 1.0
 */
object UtilKComponentActivity {
    @JvmStatic
    fun <I, O> registerForActivityResult(
        componentActivity: ComponentActivity,
        contract: ActivityResultContract<I, O>,
        callback: ActivityResultCallback<O>
    ): ActivityResultLauncher<I> =
        componentActivity.registerForActivityResult(contract, callback)

    ////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun registerForActivityResult_ofStartActivityForResult(
        componentActivity: ComponentActivity,
        callback: ActivityResultCallback<ActivityResult?>
    ): ActivityResultLauncher<Intent> =
        componentActivity.registerForActivityResult(ActivityResultContracts.StartActivityForResult(), callback)

    @JvmStatic
    fun registerForActivityResult_ofStartIntentSenderForResult(
        componentActivity: ComponentActivity,
        callback: ActivityResultCallback<ActivityResult?>
    ): ActivityResultLauncher<IntentSenderRequest> =
        componentActivity.registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult(), callback)

    @JvmStatic
    fun registerForActivityResult_ofRequestMultiplePermissions(
        componentActivity: ComponentActivity,
        callback: ActivityResultCallback<Map<String, @JvmSuppressWildcards Boolean>?>
    ): ActivityResultLauncher<Array<String>> =
        componentActivity.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions(), callback)

    @JvmStatic
    fun registerForActivityResult_ofRequestPermission(
        componentActivity: ComponentActivity,
        callback: ActivityResultCallback<Boolean?>
    ): ActivityResultLauncher<String> =
        componentActivity.registerForActivityResult(ActivityResultContracts.RequestPermission(), callback)

    @JvmStatic
    fun registerForActivityResult_ofTakePicturePreview(
        componentActivity: ComponentActivity,
        callback: ActivityResultCallback<Bitmap?>
    ): ActivityResultLauncher<Void?> =
        componentActivity.registerForActivityResult(ActivityResultContracts.TakePicturePreview(), callback)

    @JvmStatic
    fun registerForActivityResult_ofTakePicture(
        componentActivity: ComponentActivity,
        callback: ActivityResultCallback<Boolean?>
    ): ActivityResultLauncher<Uri> =
        componentActivity.registerForActivityResult(ActivityResultContracts.TakePicture(), callback)

    @JvmStatic
    fun registerForActivityResult_ofTakeVideo(
        componentActivity: ComponentActivity,
        callback: ActivityResultCallback<Bitmap?>
    ): ActivityResultLauncher<Uri> =
        componentActivity.registerForActivityResult(ActivityResultContracts.TakeVideo(), callback)

    @JvmStatic
    fun registerForActivityResult_ofCaptureVideo(
        componentActivity: ComponentActivity,
        callback: ActivityResultCallback<Boolean?>
    ): ActivityResultLauncher<Uri> =
        componentActivity.registerForActivityResult(ActivityResultContracts.CaptureVideo(), callback)

    @JvmStatic
    fun registerForActivityResult_ofPickContact(
        componentActivity: ComponentActivity,
        callback: ActivityResultCallback<Uri?>
    ): ActivityResultLauncher<Void?> =
        componentActivity.registerForActivityResult(ActivityResultContracts.PickContact(), callback)

    @JvmStatic
    fun registerForActivityResult_ofGetContent(
        componentActivity: ComponentActivity,
        callback: ActivityResultCallback<Uri?>
    ): ActivityResultLauncher<String> =
        componentActivity.registerForActivityResult(ActivityResultContracts.GetContent(), callback)

    @JvmStatic
    fun registerForActivityResult_ofGetMultipleContents(
        componentActivity: ComponentActivity,
        callback: ActivityResultCallback<List<@JvmSuppressWildcards Uri>?>
    ): ActivityResultLauncher<String> =
        componentActivity.registerForActivityResult(ActivityResultContracts.GetMultipleContents(), callback)

    @JvmStatic
    fun registerForActivityResult_ofOpenDocument(
        componentActivity: ComponentActivity,
        callback: ActivityResultCallback<Uri?>
    ): ActivityResultLauncher<Array<String>> =
        componentActivity.registerForActivityResult(ActivityResultContracts.OpenDocument(), callback)

    @JvmStatic
    fun registerForActivityResult_ofOpenMultipleDocuments(
        componentActivity: ComponentActivity,
        callback: ActivityResultCallback<List<@JvmSuppressWildcards Uri>?>
    ): ActivityResultLauncher<Array<String>> =
        componentActivity.registerForActivityResult(ActivityResultContracts.OpenMultipleDocuments(), callback)

    @JvmStatic
    @RequiresApi(CVersCode.V_21_5_L)
    fun registerForActivityResult_ofOpenDocumentTree(
        componentActivity: ComponentActivity,
        callback: ActivityResultCallback<Uri?>
    ): ActivityResultLauncher<Uri?> =
        componentActivity.registerForActivityResult(ActivityResultContracts.OpenDocumentTree(), callback)

    @JvmStatic
    fun registerForActivityResult_ofCreateDocument(
        componentActivity: ComponentActivity,
        callback: ActivityResultCallback<Uri?>
    ): ActivityResultLauncher<String> =
        componentActivity.registerForActivityResult(ActivityResultContracts.CreateDocument(), callback)

    @JvmStatic
    fun registerForActivityResult_ofPickVisualMedia(
        componentActivity: ComponentActivity,
        callback: ActivityResultCallback<Uri?>
    ): ActivityResultLauncher<PickVisualMediaRequest> =
        componentActivity.registerForActivityResult(ActivityResultContracts.PickVisualMedia(), callback)

    @JvmStatic
    fun registerForActivityResult_ofPickMultipleVisualMedia(
        componentActivity: ComponentActivity,
        callback: ActivityResultCallback<List<@JvmSuppressWildcards Uri>?>
    ): ActivityResultLauncher<PickVisualMediaRequest> =
        componentActivity.registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(), callback)

}