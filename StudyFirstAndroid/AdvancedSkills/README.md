# 第 13 章 继续进阶——你还应该掌握的高级技巧

&emsp;&emsp;本书的内容虽然已经接近尾声 了，但是千万不要因此放松，现在正是你继续进阶的时机。相信基础性的Android只是已经没有太多能够难倒你了，那么本章中我们就来学习一些你还应该掌握的高级技巧吧。  

## 13.1 全局获取Context的技巧  
&emsp;&emsp;回想这么久以来我们所学的内容，你会发现有很多地方都需要用到Context，弹出的Toast的时候需要，启动活动的时候需要，发送广播的时候需要，操作数据库的时候需要，使用通知通知的时候需要等等。  
&emsp;&emsp;或许目前你还没有为得不到Context而发愁过，因为我们很多的操作都是在活动中进行的，而活动本身就是一个Context对象。但是，当应用程序的架构逐渐开始复杂起来的时候，很多的逻辑代码都将脱离Activity类，但此时你又恰恰需要使用Context，也许这个时候你就会感到有些伤脑筋了。  
&emsp;&emsp;举个例子来说吧，在第9章的最佳实践环节中，我们编写了一个HttpUtil类，在这里将一些通用的网络操作进行封装起来，代码如下所示：

```java

package com.zj970.advanced.utils;

import com.zj970.advanced.IHttpCallbackListener;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * <p>
 * 封装网络操作
 * </p>
 *
 * @author: zj970
 * @date: 2022/12/14
 */
public class HttpUtil {

    public static void sendHttpRequest(final String address, final IHttpCallbackListener callbackListener){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    InputStream in = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null){
                        response.append(line);
                    }
                    if (callbackListener != null){
                        callbackListener.onFinish(response.toString());//回调onFinish()方法
                    }
                } catch (Exception e) {
                    if (callbackListener != null){
                        callbackListener.onError(e);//回调onError()方法
                    }
                } finally {
                    if (connection != null){
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }
}

```