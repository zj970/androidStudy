package com.zj970.activitytest;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

//每个新建的活动都要去AndroidManifest.xml中注册
public class SecondActivity extends AppCompatActivity {
    private static final String TAG = "SecondActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_layout);//必须先加载资源文件
        Button button_3 = findViewById(R.id.button_3);

        /**
         * 首先通过getIntent()方法获取到用于启动SecondActivity的Intent,
         * 然后调用getStringExtra()方法，传入相应的键值，就可以得到传递的数据了。
         * 这里由于我们传递的是字符串，所以使用的getStringExtra()
         */
        Intent intent = getIntent();
        String data = intent.getStringExtra("extra_data");//如果传递的是整型数据则使用getInExtra()方法以此类推
        Log.d(TAG, "onCreate: "+data);


        Button button_6 = findViewById(R.id.button_6);
        button_6.setOnClickListener(new View.OnClickListener() {
            /**
             * 构建一个Intent,这个Intent仅仅是用于传递数据
             * 它没有指定任何的”意图“。
             * 紧接着把要传递的数据存放在Intent中，然后调用了setResult()方法，此方法专门用于向上一个活动返回数据的。
             * setResult()方法接收两个参数
             * 第一参数用于向上一个活动返回处理结果，一一般使用RESULT_OK或RESULT_CANCELED这两个值
             * 第二个参数则把带有数据的Intent传递回去，然后调用了finish(0方法来销毁当前活动
             */
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent();
                intent1.putExtra("data_return", "Hello FirstActivity");
                setResult(RESULT_OK,intent1);
                finish();
            }

        });

        button_3.setOnClickListener(v -> {

            /**
             * 首先制定了Intent的action是Intent.ACTION.VIEW,这是一个Android系统内置的动作，其常量值为 android.intent.action.VIEW。
             * 然后通过Uri.parse()方法。将一个网址自负床解析成一个Uri对象，再调用Intent的setData()方法把这个Uri对象传递进去
             */
            Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.baidu.com"));//这里无法实现选择浏览器操作
            Intent chooser = Intent.createChooser(intent1, "Choose Your Browser");
            if (intent1.resolveActivity(getPackageManager()) != null) {
                startActivity(chooser);
            }
            //intent.setData(Uri.parse("https://www.baidu.com"));
        });
    }

    /**
     * 用户在SecondActivity中并不是通过点击按钮，
     * 而是通过按下Back键回到FirstActivity
     * 数据返回通过重写onBackPressed()方法
     */
    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("data_return","Hello FirstActivity,I am SecondActivity");
        setResult(RESULT_OK,intent);
        finish();
    }
}