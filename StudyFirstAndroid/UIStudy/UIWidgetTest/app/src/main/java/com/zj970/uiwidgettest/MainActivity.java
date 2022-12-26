package com.zj970.uiwidgettest;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button;
    private Button buttonChangeImage;
    private Button buttonToPractice;
    private TextView text_view;
    private EditText edit_text;
    private ImageView imageView;
    private ProgressBar progressBar;
    boolean isImage_1 = true;//默认是img_1
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //先加载资源文件再实例化
        button = findViewById(R.id.button);
        button.setOnClickListener(this::onClick);//不使用匿名类的方式，采取继承 View.OnClickListener 接口的方式

        buttonChangeImage = findViewById(R.id.changeImage);
        buttonChangeImage.setOnClickListener(this);

        buttonToPractice = findViewById(R.id.to_layout_practice);
        buttonToPractice.setOnClickListener(this::onClick);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button:
                //TODO： 实现button的响应事件
                button_OnClick();
                button_AlertDialog();
                break;

            case R.id.changeImage:
                //TODO: 实现button_ChangeImage的响应事件
                button_ChangeImage();
                buttonProgressDialog();
            case R.id.to_layout_practice:
                //TODO: 实现buttonToPractice的响应事件
                toNext();
                break;
            default:
                break;
        }
    }

    private void button_OnClick(){
        //TODO: 实现点击button按钮将输入框的内容转换为标签的内容

        /**
         * 1. 首先通过findViewById()的方法得到 text_view 和 edit_text的实例
         * 2. 然后调用 EditText.getText()方法获取输入到的内容
         * 3. 对TextView.setText()进行赋值
         * 4. 通过Toast 将输入的内容显示出来
         */
        text_view = findViewById(R.id.text_view);
        edit_text = findViewById(R.id.edit_text);
        String edit_text_toString =edit_text.getText().toString();
        Log.d(TAG,edit_text_toString);
        text_view.setText(edit_text.getText());
        //Toast ---> 消息
        Toast.makeText(MainActivity.this, edit_text_toString, Toast.LENGTH_LONG).show();
        //----<弹窗

    }

    /**
     * 展示AlertDialog的使用
     */
    public void button_AlertDialog(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);//1. 实例化AlertDialog
        dialog.setTitle("This is Dialog");//2. 设置标题
        dialog.setMessage("Something important.");//3. 设置内容
        dialog.setCancelable(false);//4. 可否取消的属性
        /**
         * 确定按钮点击事件
         */
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        /**
         * 取消按钮点击事件
         */
        dialog.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.show();
    }

    /**
     * 点击按钮实现图片的切换
     */
    private void button_ChangeImage(){
        //TODO: 实现点击button_change_image 按钮将图片img_1 和 img_2 替换
        imageView = findViewById(R.id.image_view);
        //TODO: 设置点击时长，加载时长  -- 定时器 未实现效果

        progressBar = findViewById(R.id.progress_bar);
        /*if (progressBar.getVisibility() == View.GONE){
            progressBar.setVisibility(View.VISIBLE);
        }else {
            progressBar.setVisibility(View.GONE);
        }*/
        int progress = progressBar.getProgress();
        progress +=10;
        progressBar.setProgress(progress);

        if (isImage_1){
            imageView.setImageResource(R.drawable.img_2);
            isImage_1 = false;
        } else {
            imageView.setImageResource(R.drawable.img_1);
            isImage_1 = true;
        }
    }

    /**
     * 展示ProgressDialog的使用
     */
    public void buttonProgressDialog(){
        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setTitle("This is ProgressDialog");
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(true);
        progressDialog.show();
    }

    /**
     * 跳转到 activity_layout_practice
     */
    public void toNext(){
        Intent intent = new Intent(MainActivity.this, LinearLayoutPractice.class);
        startActivity(intent);
    }


}