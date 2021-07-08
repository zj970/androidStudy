package com.example.notepad;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.notepad.adapter.NotepadAdapter;
import com.example.notepad.bean.NotepadBean;
import com.example.notepad.database.SQLiteHelper;

import java.util.List;

public class NotepadActivity extends AppCompatActivity {
    ListView listView;
    List<NotepadBean> list;
    SQLiteHelper mSQLiteHelper;
    NotepadAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notepad);
        listView=(ListView)findViewById(R.id.listview);
        ImageView add=(ImageView)findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(NotepadActivity.this,RecordActivity.class);
                startActivityForResult(intent,1);
            }
        });
        initData();
    }
    protected void initData(){
        mSQLiteHelper=new SQLiteHelper(this);
        showQueryData();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NotepadBean notepadBean=list.get(position);
                Intent intent=new Intent(NotepadActivity.this,RecordActivity.class);
                intent.putExtra("id",notepadBean.getId());
                intent.putExtra("time",notepadBean.getNotepadTime());
                intent.putExtra("content",notepadBean.getNotepadContent());
                NotepadActivity.this.startActivityForResult(intent,1);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog dialog;
                AlertDialog.Builder builder=new AlertDialog.Builder(NotepadActivity.this).setMessage("是否删除此记录").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        NotepadBean notepadBean=list.get(position);
                        if(mSQLiteHelper.deteleData(notepadBean.getId())){
                            list.remove(position);
                            adapter.notifyDataSetChanged();
                            Toast.makeText(NotepadActivity.this,"删除成功",Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog=builder.create();
                dialog.show();
                return  true;
            }
        });
    }
    private void showQueryData(){
        if(list!=null){
            list.clear();
        }
        list=mSQLiteHelper.query();
        adapter=new NotepadAdapter(this,list);
        listView.setAdapter(adapter);
    }
    @Override
    protected  void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==1&&resultCode==2){
            showQueryData();;
        }
    }
}

