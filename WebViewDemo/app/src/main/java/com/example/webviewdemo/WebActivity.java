package com.example.webviewdemo;

import android.accessibilityservice.GestureDescription;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebActivity extends Activity {

    private WebView wv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        wv = (WebView)findViewById(R.id.wv);

        wv.getSettings().setJavaScriptEnabled(true);
        wv.getSettings().setSupportZoom(true);
        wv.getSettings().setBuiltInZoomControls(true);
        wv.getSettings().setDisplayZoomControls(false);//隐藏Zoom缩放按钮

        wv.loadUrl("http://www.hunnu.edu.cn");
        wv.setWebViewClient(new WebViewClient());//防止URL使用默认浏览器打开

        //注册点击后退事件，如果不能回退，提示是否退出
       wv.setOnKeyListener(new View.OnKeyListener() {
           @Override
           public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN){
                    //表示按返回键
                    if (keyCode == KeyEvent.KEYCODE_BACK && wv.canGoBack()){
                        wv.goBack();//后退
                        return true;
                    }else if (keyCode == KeyEvent.KEYCODE_BACK && !wv.canGoBack()){
                        showExitAlert(WebActivity.this);
                        return true;
                    }
                }

               return false;
           }
       });

    }

    //系统退出提示对话框
    public static void showExitAlert(final Activity activity){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("确定退出吗？\n").setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                NotificationManager nm = (NotificationManager)activity.getSystemService(Activity.NOTIFICATION_SERVICE);
                nm.cancel(0x1123);
                activity.finish();
                System.exit(0);
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).show();
    }

}