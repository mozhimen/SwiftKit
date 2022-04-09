package com.mozhimen.app.uicorek.datak;

import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.mozhimen.app.R;
import com.mozhimen.uicorek.datak.DataK;
import com.mozhimen.uicorek.datak.commons.DataKItem;

import org.jetbrains.annotations.NotNull;

/**
 * @ClassName DataKItemTOp
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/8/31 17:54
 * @Version 1.0
 */
public class DataKItemTop extends DataKItem<DataK, RecyclerView.ViewHolder> {

    public DataKItemTop(DataK itemData) {
        super(itemData);
    }

    @Override
    public void onBindData(@NotNull RecyclerView.ViewHolder holder, int position) {
        ImageView imageView = holder.itemView.findViewById(R.id.datak_item_top_img);
        imageView.setImageResource(R.mipmap.datak_item_top);
    }

    @Override
    public int getItemLayoutRes() {
        return R.layout.datak_item_top;
    }
}
