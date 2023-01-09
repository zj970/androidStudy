package com.zj970.tourism.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.Glide;
import com.zj970.tourism.R;
import com.zj970.tourism.entity.HotItem;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author: zj970
 * @date: 2023/1/9
 */
public class HotAdapter extends ArrayAdapter<HotItem> {
    private int resourceId;

    public HotAdapter(@NonNull Context context, int resource, @NonNull List<HotItem> objects) {
        super(context, resource, objects);
        this.resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        HotItem hotItem = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        ImageView icon = view.findViewById(R.id.hot_item_icon);
        TextView content = view.findViewById(R.id.hot_item_content);
        TextView label1 = view.findViewById(R.id.hot_label_1);
        TextView label2 = view.findViewById(R.id.hot_label_2);
        TextView label3 = view.findViewById(R.id.hot_label_3);
        TextView label4 = view.findViewById(R.id.hot_label_4);
        TextView price = view.findViewById(R.id.hot_price);
        Glide.with(view).load(hotItem.getHotItemIconURL()).into(icon);
        content.setText(hotItem.getHotItemContent());
        label1.setText(hotItem.getHotLabel1());
        label2.setText(hotItem.getHotLabel2());
        label3.setText(hotItem.getHotLabel3());
        label4.setText(hotItem.getHotLabel4());
        price.setText(hotItem.getHotPrice());
        return view;
    }
}
