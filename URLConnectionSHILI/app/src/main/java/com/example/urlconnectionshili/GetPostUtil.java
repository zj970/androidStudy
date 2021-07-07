package com.example.urlconnectionshili;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import android.util.Log;

public class GetPostUtil {

    /**
     * 向指定URL发送GET方法的请求
     * @param url	发送请求的URL
     * @param parmas	请求参数，请求参数应该是name1=value1&name2=value2的形式
     * @return URL所代表远程资源的响应
     */
    public static String sendGet(String url, String parmas){
        String result = "";
        BufferedReader bufferedReader = null;
        String urlName = url + "?" + parmas;
        try {
            URL realUrl = new URL(urlName);
            //打开和URL之间的连接
            try {
                URLConnection urlConnection = realUrl.openConnection();
                /*设置通用请求属性*/
                //告诉WEB服务器自己接受什么介质类型，*/* 表示任何类型，type/* 表示该类型下的所有子类型，type/sub-type。
                urlConnection.setRequestProperty("accept", "*/*");
                urlConnection.setRequestProperty("connection", "Keep-Alive");
                //浏览器表明自己的身份（是哪种浏览器）
                urlConnection.setRequestProperty("user-agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.8.1.14)");
                //建立实际连接
                urlConnection.connect();
                Log.e("contentType", ""+urlConnection.getContentType());
                Log.e("contentLength", ""+urlConnection.getContentLength());
                Log.e("contentEncoding", ""+urlConnection.getContentEncoding());
                Log.e("contentDate", ""+urlConnection.getDate());
                //获取所有相应头字段
                Map<String, List<String>> map = urlConnection.getHeaderFields();
                //遍历所有响应头字段
                for (String key:map.keySet()){
                    Log.i("GET方式请求", ""+map.get(key));
                }
                //定义BufferReader输入流来读取URL的响应
                bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String line;
                for (;(line = bufferedReader.readLine()) != null;){
                    result += "\n" + line;
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                Log.e("GET方式请求", "发送GET请求异常"+e);
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (null != bufferedReader){
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * 向指定URL发送POST方法的请求
     * @param url	发送请求的URL
     * @param parmas	请求参数，请求参数应该是name1=value1&name2=value2的形式
     * @return URL所代表远程资源的响应
     */
    public static String sendPost(String url, String parmas){
        String result = "";
        PrintWriter printWriter = null;
        BufferedReader bufferedReader = null;
        try {
            URL realUrl = new URL(url);
            //打开和URL之间的连接
            try {
                URLConnection urlConnection = realUrl.openConnection();
                //设置通用请求属性
                urlConnection.setRequestProperty("accept", "*/*");
                urlConnection.setRequestProperty("connection", "Keep-Alive");
                urlConnection.setRequestProperty("user-agent", "Mozilla/4.0(compatible; MSIE 6.0; Windows NT 5.1; SV1)");
                //发送POST请求必须设置如下两行
                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);
                //获取所有相应头字段
                Map<String, List<String>> map = urlConnection.getHeaderFields();
                //遍历所有响应头字段
                for (String key:map.keySet()){
                    Log.i("POST方式请求", ""+map.get(key));
                }
                //获取URLConnection对象对应的输出流
                printWriter = new PrintWriter(urlConnection.getOutputStream());
                //发送请求参数
                printWriter.print(parmas);
                //flush输出流缓冲
                printWriter.flush();
                //定义BufferReader输入流来读取URL的响应
                bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String line;
                for (;(line = bufferedReader.readLine()) != null;){
                    result += "\n" + line;
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                Log.e("GET方式请求", "发送GET请求异常"+e);
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (null != bufferedReader){
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (null != printWriter){
                printWriter.close();
            }
        }
        return result;
    }
}
