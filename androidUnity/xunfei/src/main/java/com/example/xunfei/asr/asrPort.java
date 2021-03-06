package com.example.xunfei.asr;

import android.os.Bundle;

import com.example.xunfei.JsonParser;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechUtility;
import com.unity3d.player.UnityPlayer;
import com.unity3d.player.UnityPlayerActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class asrPort extends  UnityPlayerActivity{

    private SpeechRecognizer mIat;
    private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=60307482");
        mIat = SpeechRecognizer.createRecognizer(this, null);
        //设置mIat的参数
        //表示是什么服务
        mIat.setParameter(SpeechConstant.DOMAIN, "iat");
        //设置语言
        mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        //接受语言的类型
        mIat.setParameter(SpeechConstant.ACCENT, "mandarin");
        //使用什么样引擎
        mIat.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);

    }
    RecognizerListener mRecognizerLis=new RecognizerListener() {
        @Override
        public void onVolumeChanged(int i, byte[] bytes) {

        }

        @Override
        public void onBeginOfSpeech() {

        }

        @Override
        public void onEndOfSpeech() {

        }

        @Override
        public void onResult(RecognizerResult recognizerResult, boolean b) {
            printResult(recognizerResult);
        }

        @Override
        public void onError(SpeechError speechError) {

        }

        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {

        }
    };
    //解析Json的方法
    //方法来自speechDemo->java->voicedemo->IatDemo中的printResult方法
    private void printResult(RecognizerResult results) {
        String text = JsonParser.parseIatResult(results.getResultString());

        String sn = null;
        // 读取json结果中的sn字段
        try {
            JSONObject resultJson = new JSONObject(results.getResultString());
            sn = resultJson.optString("sn");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mIatResults.put(sn, text);

        StringBuffer resultBuffer = new StringBuffer();
        for (String key : mIatResults.keySet()) {
            resultBuffer.append(mIatResults.get(key));
        }
        //把消息发送给Unity场景中iFlytekASRController物体上的OnResult方法
        UnityPlayer.UnitySendMessage("iFlytekASRController", "OnResult", resultBuffer.toString());
    }

    public void beginListen(){
        //开始识别
        mIat.startListening(mRecognizerLis);
    }

    public void connected(){
        UnityPlayer.UnitySendMessage("iFlytekASRController","tryConnected","连通成功了");

    }
    public int beginTest(int a, int b){
        //交互测试
        return a+b;
    }


}


