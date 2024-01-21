package com.mozhimen.componentktest.pagingk

import android.util.Log
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.mozhimen.basick.elemk.commons.I_Listener

/**
 * @ClassName LoadStateFooterAdapter
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/1/21 23:13
 * @Version 1.0
 */
class LoadStateFooterAdapter(private val _onRetry: I_Listener) : LoadStateAdapter<LoadStateViewHolder>() {

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        Log.d("MainActivity", "---去绑定 onBindViewHolder")
        holder.bindState(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        return LoadStateViewHolder(parent, _onRetry)
    }

}