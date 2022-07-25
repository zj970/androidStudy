package com.example.lianxitest;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Editviewtext";
    //内容数组
    String[] gradeArray={"上海","北京","四川","贵州","重庆"};
    private TextView view_t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view_t = (TextView)findViewById(R.id.view_t);

        Spinner spinnerGrade=findViewById(R.id.spinnerGrade);
        //数组适配器
        ArrayAdapter<String> gradeAdapter=new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,gradeArray);
        spinnerGrade.setAdapter(gradeAdapter);
        //设置默认选中项
        spinnerGrade.setSelection(0);
        //获取按钮
        Button buttonOk = findViewById(R.id.buttonOk);
        //设置按钮点击监听器
        buttonOk.setOnClickListener(new MyOnClickListener());

        spinnerGrade.setOnItemSelectedListener(new MyOnItemSelectedListener());
        Log.i(TAG, "\nnumber1:"+savedInstanceState);
    }

    //定义按钮点击监听器
    class MyOnClickListener implements View.OnClickListener {
        //按钮点击
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.buttonOk) {//被点击的是确认按钮
                //获取选中项
                Spinner spinnerGrade = findViewById(R.id.spinnerGrade);
                //显示提示框
                Toast.makeText(MainActivity.this, gradeArray[spinnerGrade.getSelectedItemPosition()] , Toast.LENGTH_SHORT).show();
                view_t.setText("你选择的是："+spinnerGrade.getSelectedItem().toString());
                Log.i(TAG, "\nnumber2:"+view);
            }
        }
    }

    class MyOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
        //选择
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            Toast.makeText(MainActivity.this, gradeArray[i], Toast.LENGTH_SHORT).show();
            Log.i(TAG, "\nnumber2:"+i);
        }
        //未选择
        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }
}




