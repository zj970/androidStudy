package com.example.urlconnectionshili;


import android.os.Bundle;



import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.app.Activity;

public class MainActivity extends Activity {
    private Button get,post;
    private String response;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        get = (Button)this.findViewById(R.id.get);
        post = (Button)this.findViewById(R.id.post );

        get.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                new Thread(){
                    @Override
                    public void run() {
                        response = GetPostUtil.sendGet("http://www.jju.edu.cn/", null);
                        Log.i("response", response);
                    };
                }.start();
            }
        });

        post.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                new Thread(){
                    @Override
                    public void run() {
                        response = GetPostUtil.sendPost("http://www.jju.edu.cn/", null);
//						Log.i("response", response);
                    };
                }.start();
            }
        });
    }

}