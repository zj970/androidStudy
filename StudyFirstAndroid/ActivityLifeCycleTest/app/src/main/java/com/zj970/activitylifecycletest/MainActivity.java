package com.zj970.activitylifecycletest;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

/**
 * 每个活动在其生命周期最多可能有4种状态
 * 1. 运行状态
 * 2. 暂停状态
 * 3. 停止状态
 * 4. 销毁状态
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
   // private static int startNum = 1;//启动次数
    /**
     * 每个活动我们都重写了这个方法，它会在活动第一次被创建的时候调用。
     * 你应该在这个方法中完成活动的初始化操作，比如说加载布局、绑定事件等.
     * 这个验证失败
     * @param savedInstanceState 如果在活动被系统回收之前通过onSaveInstanceStata()方法保存数据，这个参数就有之前保存的全部数据
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button startNormalActivity = findViewById(R.id.start_normal_activity);
        Button startDialogActivity = findViewById(R.id.start_dialog_activity);
        Log.d(TAG,"开始执行onCreate()方法");
        if (savedInstanceState != null){
            String tempdata = savedInstanceState.getString("data_key");
            Log.d(TAG,tempdata);
        }
        startNormalActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_normal = new Intent(MainActivity.this,NormalActivity.class);
                startActivity(intent_normal);
            }
        });
        startDialogActivity.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent_dialog = new Intent(MainActivity.this,DialogActivity.class);
                startActivity(intent_dialog);
            }
        });
    }

    /**
     * 这个方法在活动由不可见变为可见的时候调用
     */
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"开始执行onStart()方法");
    }

    /**
     * 这个方法在活动准备号和用户进行交互的时候调用
     * 此时的活动一定处于栈顶，并且处于运行的状态
     */
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"开始执行onResume()方法");
    }

    /**
     * 这个方法在系统准备去启动或者去恢复另一个活动的时候调用
     * 我们通常在这个方法中将一些消耗cpu的资源释放掉，以及保存一些关键数据
     * 但这个方法的执行速度一定要快，不然会影响到栈顶活动的调用
     */
    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG,"开始执行onPause()方法");
    }

    /**
     * 这个方法在活动完全不可见的时候调用
     * 它和onPause方法的主要区别在于，如果启动的是一个对话框式的活动，那么onPause会执行，而onStop不会执行
     */
    @Override
    protected void onStop() {
        super.onStop();
        new Handler().post(new Runnable() {
            public void run() {
                // TODO Auto-generated method stub
                Runtime.getRuntime().exit(1);
            }
        });
        Log.d(TAG,"开始执行onStop()方法");
    }

    /**
     * 这个方法在活动被销毁之前调用，之后的活动的状态将变为销毁状态
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"开始执行onDestroy()方法");
    }

    /**
     * 这个方法在活动由停止状态变为运行状态之前调用，也就是活动被重新启动了
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG,"开始执行onRestart()方法");
    }


    /**
     * 当活动被回收时如何保存数据，使用onSaveInstanceState(0回调方法
     * 这个方法可以保证在活动被回收之前一定会被调用
     * 我们可以通过这个方法来解决活动被回收之前一定会被调用，
     * 因此我们可以通过这个方法来解决活动被回收时临时数据得不到保存的问题
     * onSaveInstanceState(Bundle outState)用于在系统由于内存紧张而回收程序的内存等情况时保存一些关键数据
     *
     * onSaveInstanceState()方法会携带一个Bundle类型的参数，
     * Bundle提供了一系列的方法用于保存数据。
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG,"开始执行onSaveInstanceState");
        String tempData = "Something you just typed ==== ";
        outState.putString("data_key",tempData);
    }
}