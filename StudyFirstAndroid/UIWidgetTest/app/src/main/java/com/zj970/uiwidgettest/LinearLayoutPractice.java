package com.zj970.uiwidgettest;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class LinearLayoutPractice extends AppCompatActivity {

    private static final String TAG = "LayoutPractice";

    private Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linear_layout_practice);

        send =  findViewById(R.id.send);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LinearLayoutPractice.this,RelativeLayoutPractice.class);
                startActivity(intent);
            }
        });
    }

}