package com.example.lianxi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HelloActivity extends AppCompatActivity {
    private MainActivity a;
    private Button btn_c;
    private Button btn_m;
    private Button btn_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);


        btn_c = (Button)findViewById(R.id.btn_c);
        btn_m = (Button)findViewById(R.id.btn_m);
        btn_next = (Button)findViewById(R.id.btn_next);

        btn_m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HelloActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        btn_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });
    }
}