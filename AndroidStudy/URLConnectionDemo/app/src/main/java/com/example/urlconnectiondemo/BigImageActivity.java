package com.example.urlconnectiondemo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

/** 从首页传递过来Bitmap序列化的字节数组，初始化图片 **/
public class BigImageActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.big_image);

        ImageView iv = (ImageView)findViewById(R.id.bigImage);
        Intent intent = this.getIntent();
        iv.setImageBitmap(getBitmap(intent.getExtras().getByteArray("bigImageBytes")));
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public Bitmap getBitmap(byte[] date){
        return BitmapFactory.decodeByteArray(date,0,date.length);
    }
}
