package com.example.mylibrary.base;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.mylibrary.R;

/**
 * @auther zj970
 * @create 2023-05-20 下午3:11
 */
public class BaseActivity extends AppCompatActivity {

    protected AppCompatActivity mContext;

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
    }

    protected void setStatusBar(boolean dark) {
        /**
         * Android11及以上版本中弃用 setSystemUiVisibility
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WindowInsetsController controller = getWindow().getInsetsController();
            controller.setSystemBarsAppearance(dark ? WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS : 0,
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS);
            controller.setSystemBarsAppearance(dark ? WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS : 0,
                    WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS);
        } else {
            getWindow().getDecorView().setSystemUiVisibility(dark ?
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR :
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }

    private Dialog mDialog;

    protected void showLoadingDialog() {
        if (mDialog == null) {
            mDialog = new Dialog(mContext, R.style.loading_dialog);
        }
        mDialog.setContentView(R.layout.dialog_loading);
        mDialog.setCancelable(true);
        mDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        mDialog.show();
    }

    protected void dismissLoadingDialog() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
        mDialog = null;
    }

}
