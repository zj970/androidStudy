package com.zj970.providertest;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private String newId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button addData = findViewById(R.id.add_data);
        Button queryData = findViewById(R.id.query_data);
        Button updateData = findViewById(R.id.update_data);
        Button deleteData = findViewById(R.id.delete_data);
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //添加数据
                Uri uri = Uri.parse("content://com.zj970.databasetest.provider/book");
                ContentValues values = new ContentValues();
                values.put("name", "A Clash of Kings");
                values.put("author", "George Martin");
                values.put("pages", 1040);
                values.put("price", 22.85);
                Uri newUri = getContentResolver().insert(uri, values);
                Log.d(TAG, "newUri: "+newUri);
                newId = newUri.getPathSegments().get(1);
                Log.d(TAG, "newId: " + newId);
            }
        });
        queryData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //查询数据
                Uri uri = Uri.parse("content://com.zj970.databasetest.provider/book");
                Cursor cursor = getContentResolver().query(uri, null, null, null, null);
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));
                        Log.d(TAG, "book name is : " + name);
                        Log.d(TAG, "book author is : " + author);
                        Log.d(TAG, "book pages is : " + pages);
                        Log.d(TAG, "book price is : " + price);
                    }
                    cursor.close();
                }
            }
        });
        updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //更新数据
                Uri uri = Uri.parse("content://com.zj970.databasetest.provider/book/" + newId);
                ContentValues values = new ContentValues();
                values.put("name", "A Storm of Swords");
                values.put("pages", 1216);
                values.put("price", 24.05);
                getContentResolver().update(uri,values,null,null);
            }
        });
        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //删除数据
                Uri uri = Uri.parse("content://com.zj970.databasetest.provider/book/" + newId);
                getContentResolver().delete(uri,null,null);
            }
        });

    }
}