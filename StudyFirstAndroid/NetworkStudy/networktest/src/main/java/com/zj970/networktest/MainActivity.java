package com.zj970.networktest;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //private static final String STRING_URL= "https://www.baidu.com";
    private static final String STRING_URL= "http://10.0.2.2/get_data.xml";
    private static final String TAG = "MainActivity";
    TextView responseText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button sendRequest = findViewById(R.id.send_request);
        responseText = findViewById(R.id.response_text);
        sendRequest.setOnClickListener(this::onClick);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.send_request){
            sendRequestWithOkHttp();
        }
    }

    private void sendRequestWithOkHttp(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url(STRING_URL).build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    pareXMLWithPull(responseData);
                    showResponse(responseData);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }


    private void showResponse(String value){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //这里更新UI操作
                responseText.setText(value);
            }
        });
    }


    private void pareXMLWithPull(String xmlData){
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();
            xmlPullParser.setInput(new StringReader(xmlData));
            int eventType = xmlPullParser.getEventType();
            String id = "";
            String name = "";
            String version = "";
            while (eventType != XmlPullParser.END_DOCUMENT){
                String nodeName = xmlPullParser.getName();
                switch (eventType){
                    //开始解析某个节点
                    case XmlPullParser.START_TAG:
                        if ("id".equals(nodeName)){
                            id = xmlPullParser.nextText();
                        } else if ("name".equals(nodeName)){
                            name = xmlPullParser.nextText();
                        }else if ("version".equals(nodeName)){
                            version = xmlPullParser.nextText();
                        }
                        break;
                    //完成解析某个节点
                    case XmlPullParser.END_TAG:
                        if ("app".equals(nodeName)){
                            Log.d(TAG, "pareXMLWithPull: id "+id);
                            Log.d(TAG, "pareXMLWithPull: name "+name);
                            Log.d(TAG, "pareXMLWithPull: version "+version);
                        }
                        break;
                    default:
                        break;
                }
                eventType = xmlPullParser.next();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}