package com.example.broadcastbestpractice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.zj970.broadcastbestpractice.R;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button forceOffline = findViewById(R.id.foce_offline);
        forceOffline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.zj970.broadcastbestpractice.FORCE_OFFLINE");
                //intent.setPackage("com.zj970.broadcastbestpractice");
                sendBroadcast(intent);
            }
        });
    }
}