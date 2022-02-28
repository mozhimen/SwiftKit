package com.mozhimen.app.uicoremk.datamk;

import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.mozhimen.app.R;
import com.mozhimen.uicoremk.datamk.DataMK;
import com.mozhimen.uicoremk.datamk.commons.DataMKItem;

import org.jetbrains.annotations.NotNull;

/**
 * @ClassName DataMKItemTOp
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/8/31 17:54
 * @Version 1.0
 */
public class DataMKItemTop extends DataMKItem<DataMK, RecyclerView.ViewHolder> {

    public DataMKItemTop(DataMK itemData) {
        super(itemData);
    }

    @Override
    public void onBindData(@NotNull RecyclerView.ViewHolder holder, int position) {
        ImageView imageView = holder.itemView.findViewById(R.id.datamk_item_top_img);
        imageView.setImageResource(R.mipmap.datamk_item_top);
    }

    @Override
    public int getItemLayoutRes() {
        return R.layout.datamk_item_top;
    }
}
