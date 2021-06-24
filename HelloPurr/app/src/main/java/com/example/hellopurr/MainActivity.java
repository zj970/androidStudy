package com.example.hellopurr;

import androidx.annotation.RequiresApi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;


@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MainActivity extends Activity {

    private static final String TAG = "SensorTest";

    private SensorManager mSensorManager;//传感器管理
    private Sensor mAccelerometer;
    private TestSensorListener mSensorListener;
    private TextView mSensorInfoA;
    private ImageButton imageButton;//建立图片按钮
    private SoundPool soundPool;//存放音频流的ID
    private int soundId;//存放音频
    private static long lastClickTime;
    private Button btn_n;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        // 初始化传感器
        mSensorListener = new TestSensorListener();
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        imageButton = (ImageButton) findViewById(R.id.key_1);
        soundPool = new SoundPool(1, AudioManager.STREAM_SYSTEM,0);
        soundId = soundPool.load(this,R.raw.meow,1);
        btn_n = (Button) findViewById(R.id.btn_n);
        btn_n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.os.Process.killProcess(android.os.Process.myPid());

            }
        });
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFastDoubleClick()){
                    soundPool.play(soundId, 1,1,0,0,1);
                }

            }

        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 注册传感器监听函数
        mSensorManager.registerListener(mSensorListener, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 注销监听函数
        mSensorManager.unregisterListener(mSensorListener);
    }

    private void initViews() {
        mSensorInfoA = (TextView) findViewById(R.id.sensor_info_a);
    }

    class TestSensorListener implements SensorEventListener {

        @Override
        public void onSensorChanged(SensorEvent event) {
            // 读取加速度传感器数值，values数组0,1,2分别对应x,y,z轴的加速度
            Log.i(TAG, "加速度变化: " + event.values[0] + ", " + event.values[1] + ", " + event.values[2]);
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            if (x > 9 || y > 9 || z > 11){
                soundPool.play(soundId,1 ,1,0,0,1);
            }
        }


        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            Log.i(TAG, "onAccuracyChanged");
        }
    }
    public static boolean isFastDoubleClick(){
        long time = System.currentTimeMillis();
        Log.i(TAG, "\nonAccuracyChanged"+time);
        long timeD = time - lastClickTime;
        if (0 <timeD && timeD < 2000){
            //2000毫秒内按钮无效，控制快速点击，可自行调整频率
            return true;
        }
        lastClickTime = time;
        return false;
    }

}