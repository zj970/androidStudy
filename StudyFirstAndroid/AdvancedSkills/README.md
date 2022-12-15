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

&emsp;&emsp;这里使用sendHttpRequest()方法来发送HTTP请求显然是没有问题的，并且我们还可以在回调方法中处理服务器返回的数据。但现在我们想对sendHttpRest()方法进行一些优化，当检测到网络不存在的时候就给用户一个Toast提示，并且不再执行后面的代码。看似一个挺简单的功能，可是却存在一个让人头疼的问题，弹出Toast提示需要一个Context参数，而我们在HttpUtil类中显然是获取不到Context对象的，这该怎么办呢？  
&emsp;&emsp;其实要想解决这个问题也很简单，大不了在sendHttpRequest()方法中添加一个Context参数就行了嘛，于是就将HttpUtil中的代码进行如下修改：  

```java
package com.zj970.advanced.utils;

import android.content.Context;
import android.widget.Toast;
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

    public static void sendHttpRequest(final Context context, final String address, final IHttpCallbackListener callbackListener){
        if(isNetworkAvailable(context)){
            Toast.makeText(context, "network is unavailable", Toast.LENGTH_SHORT).show();
        }

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

    private static boolean isNetworkAvailable(final Context context){
        return NetUtil.getNetWorkStart(context) == NetUtil.NETWORK_MOBILE ? true : false;
    }
}

```

&emsp;&emsp;可以看到，这里在方法中添加了一个Context参数，并且有一个isNetworkAvailable()方法用于判断当前网络是否可用，如果网络不可用的话就弹出Toast提示，并将方法return掉。  
&emsp;&emsp;虽说这也确实是一种解决方案，但是却有点推卸责任的嫌疑，因为我们将获取Context的任务转移给了sendHttpRequest()方法的调用方，至于调用方能不能的得到Context对象，那就不是我么需要考虑的问题了。由此可以看出，在某些情况下，获取Context并非不是那么容易的一件事，有时候还是很伤脑静的。不过别担心，下面我们学习一种技巧，让你在项目的任何地方都能轻松获取到Context。  
&emsp;&emsp;Android 提供了一个Application类，每当应用程序启动的时候，系统就会自动将这个类进行初始化。而我们可以定制一个自己的Application类，以便于管理程序内一些全局的状态信息，比如说全局Context。  
&emsp;&emsp;定义一个自己的Application其实并不复杂，首先我们需要创建一个MyApplication类继承自Application，代码如下所示：  

```java
package com.zj970.advanced;

import android.app.Application;
import android.content.Context;

/**
 * <p>
 *
 * </p>
 *
 * @author: zj970
 * @date: 2022/12/15
 */
public class MyApplication extends Application {
    private static Context context;
    @Override
    public void onCreate() {
        context = getApplicationContext();
    }
    public static Context getContext() {
        return context;
    }

}

```

&emsp;&emsp;可以看到，MyApplication中的代码非常简单。这里我们重写了父类的onCreate()方法，并通过调用getApplicationContext()方法得到了一个应用程序级别的Context，然后又提供了一个静态的getContext()方法，在这里将刚才获取到的Context进行返回。  
&emsp;&emsp;接下里我们需要告知系统，当程序启动的时候应该初始化MyApplication类，而不是默认的Application类。这一步其实也很简单，在AndroidManifest.xml的<application>标签下进行指定就可以了，代码如下所示：  

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
          package="com.zj970.advanced">

    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
    <application
            android:name="com.zj970.advanced.MyApplication"
            android:allowBackup="true"
            android:label="@string/app_name"
            android:icon="@mipmap/ic_launcher"
            android:roundIcon="@mipmap/ic_launcher_round"
            android:supportsRtl="true"
            android:theme="@style/Theme.AdvancedSkills"/>

</manifest>
```
&emsp;&emsp;注意这里在指定MyApplication的时候最好加上完整的包名，不然系统将无法找到这个类。这样我们就已经实现了一种全局获取Context的机制，之后不管你在想在项目的任何地方使用Context，只需要调用一下MyApplication.getContext()就可以了。接下来我们再对sendHttpRequest()方法进行优化，代码如下所示： 

```java
package com.zj970.advanced.utils;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;
import com.zj970.advanced.IHttpCallbackListener;
import com.zj970.advanced.MyApplication;

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

    public static void sendHttpRequest(final Context context, final String address, final IHttpCallbackListener callbackListener){
        if(isNetworkAvailable(context)){
            Toast.makeText(context, "network is unavailable", Toast.LENGTH_SHORT).show();
        }

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

    private static boolean isNetworkAvailable(final Context context){
        return NetUtil.getNetWorkStart(context) == NetUtil.NETWORK_MOBILE ? true : false;
    }


    public static void sendHttpRequest(final String address, final IHttpCallbackListener callbackListener){
        if(isNetworkAvailable()){
            Toast.makeText(MyApplication.getContext(), "network is unavailable", Toast.LENGTH_SHORT).show();
        }

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

    private static boolean isNetworkAvailable(){
        return NetUtil.getNetWorkStart(MyApplication.getContext()) == NetUtil.NETWORK_MOBILE ? true : false;
    }
}

```

&emsp;&emsp;可以看到，sendHttpRequest()方法不需要再通过传参的方式来得到Context对象，而是调用一下MyApplication.getContext()方法就可以了。有了这个技巧，你再也不用为得不到Context对象而发愁了。然后回顾一下6.5.2小节中学过的内容，当时为了让LitePal可以正常工作，要求必选在AndroidManifest.xml配置以下内容

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
          package="com.zj970.litepaltest">

    <application
            android:name="org.litepal.LitePalApplication"
            android:allowBackup="true"
            android:icon="@mipmap/ic_launcher"
            android:label="@string/app_name"
            android:roundIcon="@mipmap/ic_launcher_round"
            android:supportsRtl="true"
            android:theme="@style/Theme.PersistenceTechnology">

        <activity android:name=".MainActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN"/>

                <category android:name="android.intent.category.LAUNCHER"/>
            </intent-filter>
        </activity>
    </application>

</manifest>
```

&emsp;&emsp;其实道理也是一样的，因为经过这样的配置之后，LitePal就能在内部自动获取到Context了。不过这里你可能又会产生疑问了，如果我们已经配置过了自己的Application怎么办》这样岂不是和LitePalApplication冲突了？没错，任何一个项目都只能配置一个Application，对于这种情况，LitePal提供了很简单的解决方案，那就是在我们自己的Application中去调用LitePal的初始化方法就可以了，在onCreate()方法中加入 LitePalApplication.initialize(context)；皆就可以了。使用这种写法，就相当于我们把全局的Context对象通过参数传递给了LitePal，效果和在AndroidManifest.xml中配置LiteApplication是一模一样的。  
