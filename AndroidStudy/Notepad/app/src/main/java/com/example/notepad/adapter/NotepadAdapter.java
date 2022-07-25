package com.example.notepad.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.notepad.R;
import com.example.notepad.bean.NotepadBean;

import java.util.List;

public class NotepadAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private List<NotepadBean> list;
    public NotepadAdapter(Context context, List<NotepadBean>list){
        this.layoutInflater=LayoutInflater.from(context);
        this.list=list;
    }
    @Override
    public int getCount(){
        return list==null?0:list.size();
    }
    @Override
    public Object getItem(int position){
        return list.get(position);
    }
    @Override
    public long getItemId(int position){
        return position;
    }
    @Override
    public View getView(int position,View convertView, ViewGroup parent){
        ViewHolder viewHolder;
        if(convertView==null){
            convertView=layoutInflater.inflate(R.layout.notepad_item_layout,null);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder)convertView.getTag();
        }
        NotepadBean noteInfo=(NotepadBean)getItem(position);
        viewHolder.tvNoteoadContent.setText(noteInfo.getNotepadContent());
        viewHolder.tvNotepadTime.setText(noteInfo.getNotepadTime());
        return convertView;
    }
    class ViewHolder{
        TextView tvNoteoadContent;
        TextView tvNotepadTime;
        public ViewHolder(View view){
            tvNoteoadContent=(TextView)view.findViewById(R.id.item_content);
            tvNotepadTime=(TextView)view.findViewById(R.id.item_time);
        }
    }
}
