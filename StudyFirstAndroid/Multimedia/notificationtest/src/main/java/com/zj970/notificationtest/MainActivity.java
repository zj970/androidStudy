package com.zj970.notificationtest;

import android.app.Notification;
import android.app.NotificationManager;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.NotificationCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button sendNotice = findViewById(R.id.send_notice);
        sendNotice.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View v) {
            switch (v.getId()){
                case R.id.send_notice:
                    sendNotice();
                    break;
                default:
                    break;
            }
    }

    private void sendNotice(){
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle("This is content title")
                .setContentText("This is content text")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher_round)).build();
                manager.notify(1,notification);
    }

}