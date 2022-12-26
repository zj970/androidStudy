package com.zj970.activitytest;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class FirstActivity extends BaseActivity {
    private static final String TAG = "FirstActivity";

    /**
     * 通过getMenuInflater()方法能够得到MenuInflater对象，再调用它的inflate()方法就可以给当前活动创建菜单。
     * inflate()方法接收两个参数，第一个参数用于指定我们通过哪一个资源文件俩创建菜单，这里传入R.menu.main
     * 第二个参数用于指定我们的菜单项将提娜佳到哪一个Menu对象当中，这里直接使用onCreateOptionsMenu()方法中传入的menu参数，然后给这个方法返回true,表示允许创建的菜单显示出来。如果返回false，表示无法显示
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    /**
     * 重写 onOptionsItemSelected() 实现菜单
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.add_item:
                Toast.makeText(FirstActivity.this,"You clicked Add",Toast.LENGTH_LONG).show();
                Log.i(TAG,"You clicked Add");
                break;
            case R.id.remove_item:
                Toast.makeText(FirstActivity.this,"You clicked Remove",Toast.LENGTH_SHORT).show();
                break;
            case R.id.teat_item:
                Toast.makeText(FirstActivity.this,"You clicked Test",Toast.LENGTH_LONG).show();
                break;
            default:
        }
        //return super.onOptionsItemSelected(item);
        return true;
    }

    /**
     * 这里不支持back键返回获取
     * 由于我们是使用startActivityForResult()方法来启动SecondActivity的，
     * 在SecondActivity被销毁之后回调上一个活动的onActivityResult()方法，
     * 因此我们需要在FirstActivity中重写onActivityResult()方法来得到返回的数据
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    String returnedData = data.getStringExtra("data_return");
                    Log.d(TAG, returnedData);
                }
                break;
            default:
        }
        super.onActivityResult(requestCode, resultCode, data);
        /**
         * onActivityResult()方法带有三个参数
         * 第一个参数requestCode,即我们在启动活动时传入的请求码
         * 第二个参数resultCode,即我们在返回数据时传入的处理结果
         * 第三个参数data,即携带着返回数据的Intent
         *
         * 由于在上一个活动中有坑你待用startActivityForResult()方法来启动很多不同的活动，
         * 每个活动返回的数据都会回调到onActivityResult()这个方法中，
         * 所以首先要检查的时requestCode的值来判断数据来源，确定数据来源是SecondActivity返回的之后，
         * 再拖过resultCode的值判断处理结果是否成功
         * 最后从data中将值取出来
         */
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);//调用父类的OnCreate方法
        //加载布局
        setContentView(R.layout.first_layout);
        Log.i(TAG,"OnCreate 开始执行 ");

        Button button_1 = findViewById(R.id.button_1);//fingViewById 返回的是View 对象，向下转型将它转换为 Button对象
        Button button_2 = findViewById(R.id.button_2);

        Button button_5 = findViewById(R.id.button_5);

        button_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG,"销毁活动");
                finish();
            }
        });

        //为按钮button_1注册一个监听器setOnClickListener
        button_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"用户点击了按钮"+button_1.toString());
                //通过静态方法makeText()创建一个Toast对象，然后调用show()方法将Toast显示出来
                Toast.makeText(FirstActivity.this,"You clicked Button 1",Toast.LENGTH_LONG).show();
                //第一个参数是Context，也就是Toast要求的上下文， 由于活动本身就是Context对象，这里直接传入FirstActivity;第二个参数是Toast显示的文本内容，第三个参数是Toast显示的时长Toast.LENGTH_LONG 和Toast.LENGTH_SHORT

                /**
                 * Intent 有多个构造函数的重载，其中一个是Intent(Context packageContext,Class<?> cls)。这个构造函数接收两个构造参数
                 * 1. Context 要求有提供一个启动活动的上下文
                 * 2. Class则是指定启动的目标活动，通过这个构造函数就可以构建出Intent的”意图“。
                 * 然后由 Activity类提供的startActivity()方法启动活动
                 */
                //显示Intent
                //Intent intent = new Intent(FirstActivity.this,SecondActivity.class);
                //startActivity(intent);
                //隐式Intent
/*                Intent intent = new Intent("aom.zj970.activitytest.ACTION_START");
                intent.addCategory("android.intent.category.MY_CATEGORY");
                startActivity(intent);*/
/*                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://www.baidu.com"));
                startActivity(intent);*/

                //向下一个活动传递数据
                /**
                 * 这里我们还是使用显示Intent的方式来启动SecondActivity,
                 * 并通过putExtra()方法接收两个参数，第一个参数是键，用于后面从Inteent中取值，第二个参数才是真正要传递的数据
                 *
                 */
                String data = "Hello SecondActivity,I am FirstActivity";
                Intent intent = new Intent(FirstActivity.this,SecondActivity.class);
                intent.putExtra("extra_data",data);
                startActivity(intent);
            }
        });
        /**
         * 返回数据给上一个活动
         */
        button_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 这里使用startActivityForResult()方法来启动SecondActivity,
                 * 请求码只要是唯一值就可以
                 */
                Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
                startActivityForResult(intent,1);
            }
        });

    }
}