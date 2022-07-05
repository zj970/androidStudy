package com.zj970.activitytest;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

//每个新建的活动都要去AndroidManifest.xml中注册
public class SecondActivity extends BaseActivity {
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

        button_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 除了Http协议外，我们还可以指定很多其他的协议
                 * 比如geo表示显示地理位置、tel表示拨打电话
                 */
                Intent intent_3 = new Intent(Intent.ACTION_DIAL);
                intent_3.setData(Uri.parse("tel:10086"));
                startActivity(intent_3);
            }
        });
    }

    /**
     * 用户在SecondActivity中并不是通过点击按钮
     * 而是通过按下Back键回到FirstActivity
     * 数据返回通过重写onBackPressed()方法
     *
     * 这里返回键生效必须是有 startActivityForResult(intent,1);
     * 通过startActivityForResult()方法才能生效，回传数据
     */
    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("data_return","Hello FirstActivity,I am SecondActivity");
        setResult(RESULT_OK,intent);
        finish();
    }

    //TODO:启动活动的最佳写法

    /**
     * 我们在SecondActivity中添加了一个actionStart()方法，=
     * 在这个方法中完成了Intent的构建，另外所有SecondActivity中需要的数据都是通过actionStart()方法的参数传递过来的
     * 然后把它们存储到Inten中，最后调用startActivity()方法中启动SecondActivity
     *
     * 这样写的好处是 SecondActivity所需要的数据在方法参数中
     * @param context
     * @param data1
     * @param data2
     */
    public static void actionStart(Context context,String data1,String data2){
        Intent intent = new Intent(context,SecondActivity.class);
        intent.putExtra("param1",data1);
        intent.putExtra("param2",data2);
        context.startActivity(intent);
    }
}