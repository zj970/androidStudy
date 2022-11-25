package com.zj970.servicetest;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import androidx.annotation.Nullable;

/**
 * <p>
 *
 * </p>
 *
 * @author: zj970
 * @date: 2022/11/25
 */
public class MyIntentService extends IntentService {
    private static final String TAG = "MyIntentService";
    public MyIntentService() {
        super("MyIntentService");//调用父类的有参构造函数
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public MyIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        //打印当前线程的id
        Log.d(TAG,"Thread id is "+ Thread.currentThread().getId());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: executed ");
    }
}
