package com.example.httpurlconnectiondemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import org.json.JSONObject;

public class LoginOkActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_ok);
        TextView userInfoTV = (TextView)findViewById(R.id.userInfo);
        TextView srcInfoTV = (TextView)findViewById(R.id.srcInfo);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        String userInfo = bundle.getString("userInfo");
        JSONObject jo = (JSONObject) JSON.parse(userInfo);
        JSONObject userJo = (JSONObject) jo.getJSONArray("data").get(0);
        StringBuffer sb = new StringBuffer();
        sb.append("账号： ").append(userJo.getString("account")).append("\n");
        sb.append("密码： ").append(userJo.getString("password")).append("\n");
        sb.append("电话： ").append(userJo.getString("telephone")).append("\n");
        sb.append("昵称： ").append(userJo.getString("nickName")).append("\n");
        sb.append("创建时间： ").append(userJo.getString("createTime")).append("\n");
        userInfoTV.setText(sb.toString());
        srcInfoTV.setText(userInfo);
    }
}
