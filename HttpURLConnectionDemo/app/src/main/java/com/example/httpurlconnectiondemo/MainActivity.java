package com.example.httpurlconnectiondemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import org.json.JSONObject;
import org.json.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class MainActivity extends Activity {
    Button okBtn;
    EditText userNameEF, pwdET;
    TextView tipTV;
    //食乐网登录请求地址
    String path = "http://192.168.1.1";
    Handler handler = new Handler() {
        @Override
        public void publish(LogRecord record) {

        }

        @Override
        public void flush() {

        }

        @Override
        public void close() throws SecurityException {

        }
        public void handleMessage(Message msg){
            if (msg.what == 1){
                Intent intent = new Intent(MainActivity.this,LoginOkActivity.class);
                String userInfo = String.valueOf(msg.obj);
                JSONObject jo = (JSONObject) JSON.parse(userInfo);
                String joData = jo.getString("data");
                if ("".equals(joData) || "[]".equals(joData)){
                    tipTV.setText("用户名或密码错误，登录失败!");
                }else{
                    intent.putExtra("userInfo",userInfo);
                    startActivity(intent);
                }
            }else if (msg.what == 0){
                tipTV.setText(String.valueOf(msg.obj));
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userNameEF = (EditText) findViewById(R.id.username);
        pwdET = (EditText) findViewById(R.id.pwd);
        okBtn = (Button) findViewById(R.id.okBtn);
        tipTV = (TextView) findViewById(R.id.tipTV);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userName = userNameEF.getText().toString();
                final String pwd = pwdET.getText().toString();
                userNameEF.setText("");
                tipTV.setText("");
                if (userName.equals("") || pwd.equals("")){
                    Toast.makeText(MainActivity.this,"用户名和密码不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        try{
                            URL url = new URL(path);
                            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                            connection.setRequestMethod("POST");//请求方式POST
                            connection.setDoOutput(true);//允许写入
                            connection.setDoInput(true);//允许读入
                            connection.setUseCaches(false);//不使用户缓存
                            connection.connect();
                            String body = "Param={'where':'account="+userName+"and password ="+pwd+"'}";
                            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(),"UTF-8"));
                            writer.write(body);
                            writer.close();
                            int responseCode = connection.getResponseCode();
                            if (responseCode == HttpURLConnection.HTTP_OK){
                                InputStream inputStream = connection.getInputStream();//将流转换为字符串
                                String entityStr = is2String(inputStream);
                                Message msg = Message.obtain(handler,1,entityStr);
                                msg.sendToTarget();
                            }else {
                                Message msg = Message.obtain(handler,0,"网络连接异常");
                                msg.sendToTarget();
                            }
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        } catch (ProtocolException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                };
                new Thread(runnable).start();
            }
        });
    }
    public String is2String(InputStream is) throws IOException{
        //连接后，创建一个输入流来读取response内容
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is,"utf-8"));
        String line = "";
        StringBuilder stringBuilder = new StringBuilder();
        //每次读取一行，若非空则添加至stringBuilder
        while ((line = bufferedReader.readLine()) != null){
            stringBuilder.append(line);
            String result = stringBuilder.toString().trim();
            return  result;
        }
    }

}
