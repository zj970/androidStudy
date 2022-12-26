package com.zj970.activitylifecycletest;

import android.app.Activity;
import android.os.Bundle;

/**
 * 这里继承Activity而不是AppCompatActivity
 * 因为  在AndroidManifest.xml中Activity的属性
 * android:theme="@android:style/Theme.Dialog"
 * 需要
 */
public class DialogActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
    }

}