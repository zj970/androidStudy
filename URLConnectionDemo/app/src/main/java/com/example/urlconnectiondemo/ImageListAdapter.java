package com.example.urlconnectiondemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;


import java.util.List;

public class ImageListAdapter extends ArrayAdapter {
    private List<Bitmap> list;
    private Context context;//上下文
    private int item;//GridView条目布局描述文件ID
    public ImageListAdapter(Context context, int resource, List<Bitmap> list){
        super(context, resource, list.toArray(new Bitmap[list.size()]));
        this.list = list;
        this.context = context;
        this.item = resource;
    }
    @Override
    public int getCount() {
        return list.size();
    }
    @Override
    public  Bitmap getItem(int position){
        return list.get(position);
    }
    @Override
    public long getItemId(int position){
        return position;
    }
    //getView在渲染下拉列表和点击选中GridView单元格时调用
    public View getView(int position, View convertView, ViewGroup parent){
        ImageView iv;
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(item, null);
            iv = (ImageView) convertView.findViewById(R.id.image_detail);
            convertView.setTag(iv);
        }
        else
            iv = (ImageView) convertView.getTag();
        if (position < list.size()){
            Bitmap bitmap = getItem(position);
            iv.setImageBitmap(bitmap);
        }
        else
            iv.setImageResource(R.drawable.ic_launcher_background);
        return convertView;
    }

}