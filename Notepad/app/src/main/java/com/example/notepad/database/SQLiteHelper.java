package com.example.notepad.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.notepad.bean.NotepadBean;
import com.example.notepad.utils.DBUtils;

import java.util.ArrayList;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper {
    private SQLiteDatabase sqLiteDatabase;
    public SQLiteHelper(Context context){
        super(context,DBUtils.DATABASE_NAME,null,DBUtils.DATEBASE_VERION);
        sqLiteDatabase=this.getWritableDatabase();
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table "+DBUtils.DATAVASE_TABLE+"( "+DBUtils.NOTEPAD_ID+" integer primary key autoincrement ,"+DBUtils.NOTEPAD_CONTENT+" text , "+DBUtils.NOTEPAD_TIME+" text ) " );
    }
    @Override
    public  void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){}
    public boolean insertData(String userContent,String userTime){
        ContentValues contentValues=new ContentValues();
        contentValues.put(DBUtils.NOTEPAD_CONTENT,userContent);
        contentValues.put(DBUtils.NOTEPAD_TIME,userTime);
        return sqLiteDatabase.insert(DBUtils.DATAVASE_TABLE,null,contentValues)>0;
    }
    public boolean deteleData(String id){
        String sql=DBUtils.NOTEPAD_ID+"=?";
        String[] contentValuesArray=new String[]{String.valueOf(id)};
        return sqLiteDatabase.delete(DBUtils.DATAVASE_TABLE,sql,contentValuesArray)>0;
    }
    public boolean updateData(String id,String content,String userYear){
        ContentValues contentValues=new ContentValues();
        contentValues.put(DBUtils.NOTEPAD_CONTENT,content);
        contentValues.put(DBUtils.NOTEPAD_TIME,userYear);
        String sql=DBUtils.NOTEPAD_ID+"=?";
        String[] strings=new String[]{id};
        return sqLiteDatabase.update(DBUtils.DATAVASE_TABLE,contentValues,sql,strings)>0;
    }
    public List<NotepadBean>query(){
        List<NotepadBean> list=new ArrayList<NotepadBean>();
        Cursor cursor=sqLiteDatabase.query(DBUtils.DATAVASE_TABLE,null,null,null,null,null,DBUtils.NOTEPAD_ID+" desc");
        if(cursor!=null){
            while (cursor.moveToNext()){
                NotepadBean noteInfo=new NotepadBean();
                String id=String.valueOf(cursor.getInt(cursor.getColumnIndex(DBUtils.NOTEPAD_ID)));
                String content=cursor.getString(cursor.getColumnIndex(DBUtils.NOTEPAD_CONTENT));
                String time=cursor.getString(cursor.getColumnIndex(DBUtils.NOTEPAD_TIME));
                noteInfo.setId(id);
                noteInfo.setNotepadContent(content);
                noteInfo.setNotepadTime(time);
                list.add(noteInfo);
            }
            cursor.close();
        }
        return list;
    }
}
