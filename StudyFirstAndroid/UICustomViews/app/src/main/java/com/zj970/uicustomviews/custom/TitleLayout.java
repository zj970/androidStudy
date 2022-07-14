package com.zj970.uicustomviews.custom;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.annotation.Nullable;
import com.zj970.uicustomviews.R;

/**
 * 自定义控件
 */
public class TitleLayout extends LinearLayout {
    public TitleLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        /**
         * 首先我们重写了LinearLayout中带有两个参数的构造函数，
         * 在布局中引入了TitleLayout控件就会调用这个构造函数。
         * 然后在构造函数中需要对标题栏布局进行动态加载，
         * 这就需要借助LayoutInflater来实现了。
         * 通过LayoutInflater的from()方法可以构建出一个LayoutInflater对象，
         * 然后调用inflater()方法就可以动态加载一个布局文件，
         * inflater()方法接收两个参数，
         * 第一个参数是要加载的布局文件的id，这里我们传入R.layout.title，
         * 第二个参数是给加载好的布局再添加一个父布局，这里我们想要指定为TitleLayout，于是直接传入this
         */
        LayoutInflater.from(context).inflate(R.layout.title,this);
        Button titleBack = findViewById(R.id.title_back);
        Button titleEdit = findViewById(R.id.title_edit);
        titleBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) getContext()).finish();
            }
        });

        titleEdit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"You clicked Edit button",Toast.LENGTH_LONG).show();
            }
        });
    }
}
