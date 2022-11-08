package com.zj970.networktest;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zj970.networktest.callback.HttpCallbackListener;
import com.zj970.networktest.entity.App;
import com.zj970.networktest.util.HttpUtil;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //private static final String STRING_URL= "https://www.baidu.com";
    private static final String STRING_URL= "http://10.0.2.2/get_data.xml";
    private static final String STRING_JSON_URL= "http://10.0.2.2/get_data.json";
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
            HttpUtil.sendOkhttpRequest(STRING_JSON_URL, new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    Log.d(TAG, "onFailure: ");
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    Log.d(TAG, "onResponse: ");
                    parseJSONWithJSONObject(response.toString());
                }
            });
            HttpUtil.sendHttpRequest(STRING_URL, new HttpCallbackListener() {
                @Override
                public void onFinish(String response) {
                    parseJSONWithJSONObject(response);
                }

                @Override
                public void onError(Exception e) {

                }
            });
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
                    parseXMLWithPull(responseData);
                    parseXMLWithSAX(responseData);

                    OkHttpClient clientJson = new OkHttpClient();
                    Request requestJson = new Request.Builder().url(STRING_JSON_URL).build();
                    Response responseJson = clientJson.newCall(requestJson).execute();
                    String responseDataJson = responseJson.body().string();
                    parseJSONWithJSONObject(responseDataJson);
                    parseJSONWithGSON(responseDataJson);

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


    /**
     * Pull 解析XML
     * @param xmlData
     */
    private void parseXMLWithPull(String xmlData){
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

    /**
     * SAX 解析XML
     * @param xmlData
     */
    private void parseXMLWithSAX(String xmlData){
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            XMLReader xmlReader = factory.newSAXParser().getXMLReader();
            ContentHandler handler = new ContentHandler();
            xmlReader.setContentHandler(handler);
            // 开始执行
            xmlReader.parse(new InputSource(new StringReader(xmlData)));
        } catch (ParserConfigurationException | SAXException | IOException e){
            e.printStackTrace();
        }
    }

    /**
     * 解析JSON数据
     * @param jsonData
     */
    private void parseJSONWithJSONObject(String jsonData){
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0 ; i < jsonData.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id  = jsonObject.getString("id");
                String name  = jsonObject.getString("name");
                String version  = jsonObject.getString("version");

                Log.d(TAG, "parseJSONWithObject: id " + id);
                Log.d(TAG, "parseJSONWithObject: name " + name);
                Log.d(TAG, "parseJSONWithObject: version " + version);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    /**
     * 采取GSON解析数据
     * @param jsonData
     */
    private void parseJSONWithGSON(String jsonData){
        Gson gson = new Gson();
        List<App> appList = gson.fromJson(jsonData, new TypeToken<List<App>>(){}.getType());
        for (App app : appList){
            Log.d(TAG, "parseJSONWithGSON: id " + app.getId());
            Log.d(TAG, "parseJSONWithGSON: name " + app.getName());
            Log.d(TAG, "parseJSONWithGSON: version " + app.getVersion());
        }
    }

}