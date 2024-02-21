package com.mozhimen.basick.utilk.android.content

import android.content.ClipData
import android.content.Intent
import android.net.Uri
import com.mozhimen.basick.utilk.bases.BaseUtilK


/**
 * @ClassName UtilKClipboardManagerWrapper
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/2/21
 * @Version 1.0
 */
object UtilKClipboardManagerWrapper : BaseUtilK() {

    /**
     * 获取剪贴板的文本
     */
    @JvmStatic
    fun getText(): String? {
        val clipData: ClipData? = UtilKClipboardManager.getPrimaryClip(_context)
        return if (clipData != null && clipData.itemCount > 0) {
            clipData.getItemAt(0).coerceToText(_context).toString()
        } else null
    }

    /**
     * 获取剪贴板的uri
     */
    @JvmStatic
    fun getUri(): Uri? {
        val clip: ClipData? = UtilKClipboardManager.getPrimaryClip(_context)
        return if (clip != null && clip.itemCount > 0) {
            clip.getItemAt(0).uri
        } else null
    }

    /**
     * 获取剪贴板的意图
     */
    @JvmStatic
    fun getIntent(): Intent? {
        val clip: ClipData? = UtilKClipboardManager.getPrimaryClip(_context)
        return if (clip != null && clip.itemCount > 0) {
            clip.getItemAt(0).intent
        } else null
    }

    /////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun applyCopyText(label: CharSequence, text: CharSequence) {
        UtilKClipboardManager.get(_context).setPrimaryClip(UtilKClipData.getNewPlainText(label, text))
    }

    /**
     * 复制uri到剪贴板
     */
    @JvmStatic
    fun applyCopyUri(label: CharSequence, uri: Uri) {
        UtilKClipboardManager.get(_context).setPrimaryClip(UtilKClipData.getNewUri(_context, label, uri))
    }

    /**
     * 复制意图到剪贴板
     */
    @JvmStatic
    fun applyCopyIntent(label: CharSequence, intent: Intent) {
        UtilKClipboardManager.get(_context).setPrimaryClip(ClipData.newIntent(label, intent))
    }

    /////////////////////////////////////////////////////////////////////////

    /**
     * 清空剪切板第一条
     */
    @JvmStatic
    fun applyClearFirstBlock() {
        val clipboardManager = UtilKClipboardManager.get(_context)
        val clip: ClipData? = clipboardManager.getPrimaryClip()
        if (clip != null && clip.itemCount > 0) {
            clipboardManager.setPrimaryClip(ClipData.newPlainText(null, ""))
            if (clipboardManager.hasPrimaryClip()) {
                clipboardManager.getPrimaryClip()?.getItemAt(0)?.getText()
            }
        }
    }
}