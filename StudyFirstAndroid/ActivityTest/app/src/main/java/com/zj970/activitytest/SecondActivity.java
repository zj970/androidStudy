package com.zj970.activitytest;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

//每个新建的活动都要去AndroidManifest.xml中注册
public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_layout);//必须先加载资源文件
        Button button_3 = findViewById(R.id.button_3);
        button_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 首先制定了Intent的action是Intent.ACTION.VIEW,这是一个Android系统内置的动作，其常量值为 android.intent.action.VIEW。
                 * 然后通过Uri.parse()方法。将一个网址自负床解析成一个Uri对象，再调用Intent的setData()方法把这个Uri对象传递进去
                 */
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.baidu.com"));
                Intent chooser = Intent.createChooser(intent, "Choose Your Browser");
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(chooser);
                }
                //intent.setData(Uri.parse("https://www.baidu.com"));
            }
        });
    }
}