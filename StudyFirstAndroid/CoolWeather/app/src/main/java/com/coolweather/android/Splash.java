package com.coolweather.android;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.text.Layout;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Splash extends AppCompatActivity {
    private RelativeLayout container;
    /**
     * 用于判断是否可以跳过广告，进入MainActivyt
     */
    private boolean canJump;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        container = findViewById(R.id.container);

        //运行时权限处理
        List<String> permissionList = new ArrayList<String>();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()){
            String[]  permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(this, permissions,1);
        } else {
            requestAds();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        canJump = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (canJump){
            forward();
        }
        canJump = true;
    }

    /**
     * 请求开屏广告
     */
    private void requestAds(){
        String appId = "xxxxxx";
        String adId = "xxxxxx";
        new SplashAD(this, container, appId, adId, new SplashADListener() {
            @Override
            public void onADDismissed() {
                //广告显示完毕
                forward();
            }

            @Override
            public void onNoAD() {
                //广告加载失败
                forward();
            }

            @Override
            public void OnADPresent() {
                //广告加载成功
            }

            @Override
            public void onADClicked() {
                //广告点击
            }
        });

    }

    private void forward(){
        if (canJump){
            //跳转到MainActivity
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        }else {
            canJump = true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result == PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "必须同意所有权限才能使用本程序", Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                    requestAds();
                } else {
                    Toast.makeText(this, "未知错误", Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
            default:

        }
    }

    /**
     * 模拟SDk jar包
     */
    class SplashAD {
        public SplashAD(Context context, View container, String appId, String adId, SplashADListener splashADListener){

        }
    }
    /**
     * 模拟广告SDK
     */
    interface SplashADListener {
        public void onADDismissed();
        public void onNoAD();
        public void OnADPresent();
        public void onADClicked();
    }
}

