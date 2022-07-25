package com.example.lianxi;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;


public class MainActivity extends Activity {
    private static final String TAG = "Editviewtext";
    private ImageView imageView;
    private SoundPool mySoundPool;
    private EditText mEditText;
    private TextView mTextView;
    private int musicID;
    private SensorManager mSensorManager;//传感器管理
    private Sensor mAccelerometer;
    private TestSensorListener mSensorListener;
    private Button btn_next;

    //List<String> stringList = new List<String>(){"那是肯定的！","毫无疑问","在我看来，是的","别指望它","我的消息来源说没有","无法预测"};
    String myText[] = {"那是肯定的！","毫无疑问","在我看来，是的","别指望它","我的消息来源说没有","无法预测"};
    String lastQustion,nextQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        // 初始化传感器
        mSensorListener = new TestSensorListener();
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        mEditText = (EditText) findViewById(R.id.shuru);
        mTextView = (TextView) findViewById(R.id.tip);
        imageView = (ImageView) findViewById(R.id.image1);
        btn_next = (Button) findViewById(R.id.btn_next);

        mySoundPool = new SoundPool(1, AudioManager.STREAM_SYSTEM,0);
        musicID = mySoundPool.load(this,R.raw.mm,1);

       /*btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HelloActivity.class);
                startActivity(intent);
            }
        });*/
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                ComponentName cn = new ComponentName("com.example.calculator", "com.example.calculator"+"MainActivity");
//param1:Activity所在应用的包名
//param2:Activity的包名+类名
                intent.setComponent(cn);
                startActivity(intent);

            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextQuestion = mEditText.getText().toString();
                if (nextQuestion.equals(lastQustion)==false && nextQuestion.equals("")==false )
                {
                    lastQustion = nextQuestion;
                    mySoundPool.play(musicID, 1,1,0,0,1);
                    int count = (new Random()).nextInt(6);//返回一个0到6之间的随机整数
                    Log.i(TAG, "\nnumber"+count);
                    mTextView.setText("这个问题的答案是： "+myText[count]);
                    //System.out.println("问题重复咯！");
                    //mEditText.getText().equals("gdduais");
                }
                else {
                    mTextView.setText("请输入问题并且点击图片或摇晃手机 ");
                }
                //change.afterTextChanged(mEditText.getText());
                Log.i(TAG, "\nths :"+lastQustion);
            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();
        // 注册传感器监听函数
        mSensorManager.registerListener(mSensorListener, mAccelerometer, SensorManager.SENSOR_DELAY_UI);

        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 注销监听函数
        mSensorManager.unregisterListener(mSensorListener);

    }
    class TestSensorListener implements SensorEventListener {

        @Override
        public void onSensorChanged(SensorEvent event) {
            // 读取加速度传感器数值，values数组0,1,2分别对应x,y,z轴的加速度
            //Log.i(TAG, "加速度变化: " + event.values[0] + ", " + event.values[1] + ", " + event.values[2]);
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            if (x > 11 || y > 11 || z > 11) {
                mySoundPool.play(musicID, 1, 1, 0, 0, 1);
                int count = (new Random()).nextInt(6);//返回一个0到6之间的随机整数
                Log.i(TAG, "\nnumber"+count);
                mTextView.setText("这个问题的答案是： "+myText[count]);
            }
        }

        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            Log.i(TAG, "onAccuracyChanged");
        }
    }

    //if(lastQustion.equals()


}
