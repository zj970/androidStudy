package com.example.mylibrary.base;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

/**
 * @auther zj970
 * @create 2023-05-20 下午3:11
 */
public class BaseActivity extends AppCompatActivity {

    private AppCompatActivity mContext;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        BaseApplication.getActivityManager().addActivity(this);
    }

    protected void showMsg(CharSequence msg){
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    protected void showLongMsg(CharSequence msg){
        Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
    }

    /**
     * 跳转Activity
     * @param clazz
     */
    protected void jumpActivity(final Class<?> clazz){
        startActivity(new Intent(mContext,clazz));
        finish();
    }

    protected void back(Toolbar toolbar){
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    protected void backAndFinish(Toolbar toolbar){
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    /**
     * 检查是有拥有某权限
     * @param permission    权限名称
     * @return  true 有 false 没有
     */
    protected boolean hasPermission(String permission){
        return checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 退出应用程序
     */
    protected void exitTheProgram(){
        BaseApplication.getActivityManager().finishAll();
    }


    /**
     * 设置去掉默认的ActionBar
     */
    protected void setFullScreenImmersion() {
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        int option = window.getDecorView().getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
        window.getDecorView().setSystemUiVisibility(option);
        window.setStatusBarColor(Color.TRANSPARENT);
        window.setNavigationBarColor(Color.TRANSPARENT);
        //隐藏状态栏
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

}
