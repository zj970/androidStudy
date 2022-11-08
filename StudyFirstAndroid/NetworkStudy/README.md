## 第9章 看看精彩的世界——使用网络技术

&emsp;&emsp;如果你在玩手机的时候不能上网，那你一定会感到特别地枯燥乏味。没错，现在早已不是玩单机的时代，不论是PC、手机、平板、还是电视，几乎都会具备上网的功能，在可遇见的未来，手表、眼镜、汽车等设备也会加入这个行列，21世纪的确是互联网的时代。  
&emsp;&emsp;当然，Android手机肯定也是可以上网的，所以作为开发者，我们就需要考虑如何利用网络来编写更加出色的应用程序，像QQ、微博、微信等常见的应用都会大量使用网络技术。本章主要会讲如何在手机端使用HTTP协议和服务器进行网络交互，并对服务器返回的数据进行解析，这也是Android中最常用的网络技术。

### 9.1 WebView的用法

&emsp;&emsp;有时候我们可能会碰到一些比较特殊的需求，比如说要求在应用程序里展示一些网页，相信每个人都知道，加载和显示网页通常都是浏览器的任务，但是需求里又明确指出，不允许打开系统浏览器，而我们当然也不可能自己去编写一个浏览器，这时应该怎么办？Android早已考虑到这种需求，并提供了一个WebView控件，借助它我们就可以在自己的应用程序里嵌入一个浏览器，从而显示各种各样的网页。新建一个WebViewTest模块，然后修改activity_mani.xml中的代码，如下所示：

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:tools="http://schemas.android.com/tools"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        tools:context=".MainActivity">
    <WebView
            android:id="@+id/web_view"
            android:layout_width="match_parent"
            android:layout_height="match_parent"/>
</LinearLayout>
```

&emsp;&emsp;可以看到，我们在布局文件中使用到了一个新的控件：WebView。这个控件当然也就是用来显示网页的了，这里的写法很简单，给它设置了一个id，并让它充满整个屏幕。然后修改MainActivity中的代码，如下所示：

```java
package com.zj970.webviewtest;

import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private static final String URL= "https://www.baidu.com";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WebView webView = findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(URL);
    }
}
```

&emsp;&emsp;ManiActivity中的代码很短，首先使用findViewById()方法获取了WebView的实例，然后调用WebView的getSettings()方法可以去设置一些浏览器的属性，这里我们并不去设置过多的属性，只是调用了setJavaScriptEnabled()方法来让WebView支持JavaScript脚本。  
&emsp;&emsp;接下来是非常重要的一个部分，我们调用了WebView的setWebViewClient()方法，并传入了一个WebViewClient的实例。这端代码的作用是，当需要从一个网页跳转到一个另一个网页的，我们希望目标网页仍然在当前WebView中显示，而不是打开系统浏览器。  
&emsp;&emsp;最后一步就非常简单了，调用WebView的loadUrl()方法，并将网址传入，即可展示相应网页的内容。由于本程序使用到了网络功能，访问功能是需要声明权限的，所以还需要在AndroidManifest.xml文件中加入权限："<uses-permission android:name="android.permission.INTERNET"/>"，除此之外我们还需要启用HTTP明文请求，加入android:usesCleartextTraffic="true"。

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
          package="com.zj970.webviewtest">
    <uses-permission android:name="android.permission.INTERNET"/>
    <application
            android:allowBackup="true"
            android:icon="@mipmap/ic_launcher"
            android:label="@string/app_name"
            android:roundIcon="@mipmap/ic_launcher_round"
            android:supportsRtl="true"
            android:usesCleartextTraffic="true"
            android:theme="@style/Theme.NetworkStudy">
        <activity android:name=".MainActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN"/>

                <category android:name="android.intent.category.LAUNCHER"/>
            </intent-filter>
        </activity>
    </application>

</manifest>
```
效果如下： 

![img.png](img.png)

&emsp;&emsp;可以看到，WebViewTest这个程序现在就已经具备了一个简易浏览器的功能，不仅成功将百度的首页展示了出来，还可以通过点击链接浏览更过的网页。  
&emsp;&emsp;当然，WebView还有很多更加高级的使用技巧。这里不一一赘述。  


### 9.2 使用HTTP协议访问网络

&emsp;&emsp;使用HTTP协议，可能需要花费一本书篇幅。这里当然不会这么干，对于HTTP协议，只需要稍微了解一些就足够了，它的工作原理特别简单，就是客户端向服务器发出一条HTTP请求，服务器受到请求后会返回一些数据给客户端，然后客户端再对这些数据进行解析和处理就可以了。是不是很简单？一个浏览器的基本工作原理也就是如此了。比如说上一节中使用到的WebView控件，其实也就是我们向百度的服务器发出了一条HTTP请求，接着服务器分期出我们想要访问的是百度的首页，于是会把该网页的HTTP代码进行返回，然后WebView再调用手机浏览器的内核对返回的HTML代码进行解析，最终将页面展示出来。  
&emsp;&emsp;简单来说，WebView已经在后台帮我们处理好了发送HTTP请求、接收服务响应、解析返回数据，以及最终的界面展示这几步工作，不过由于它封装得实在太好了，反而使得我们不能那么直管地看出HTTP协议到底是如何工作的。因此，接下来就让我们通过手动发送HTTP请求的方式，来更加深入地理解一下这个过程。  

#### 9.2.1 使用HttpURLConnection  

&emsp;&emsp;在过去，Android上发送HTTP请求一般有两种方式：HttpURLConnection 和 HttpClient。不过由于HttpClient存在API数量过多、扩展困难等缺点，Android团队越来越不建议我们使用这种方式。终于在Android 6.0 系统中，HttpClient的功能完全被移除了，标志着此功能被正式弃用。  
&emsp;&emsp;首先需要获取到HttpURLConnection的实例，一般只需要new出一个URL对象，并传入目标的网络地址，然后调用一下openConnection()方法即可，如下所示：  

```
URL url = new URL("http://www.baidu.com");
HttpURLConnection connection = (HttpURLConnection) url.openConnection();
```
&emsp;&emsp;在得到了HttpURLConnection的实例之后，我们可以设置一下HTTP请求所使用的方法。常用的方法主要有两个：GET和POST。GET表示从服务器那里获取数据，而POST则表示希望提交数据给服务器，写法如下：  

```
connection.setRequestMethod("GET");
```

&emsp;&emsp;接下来就可以进行一些自由的定制，比如设置链接超时、读取超时的毫秒数，以及服务器希望得到的一些消息头等。这部分的内容根据自己的实际情况进行编写，示例写法如下：  

```
connection.setConnectTimeout(8000);
connection.setReadTimeout(8000);
```

&emsp;&emsp;之后再调用getInputStream()方法就可以获取到服务器返回的输入流了，剩下的任务就是对输入流进行读取，如下所示：  
```
InputStream in = connection.getInputStream();
```
&emsp;&emsp;最后可以调用disconnect()方法将这个HTTP连接关闭。新建一个HttpURLConnectionTest项目，修改activity_main.xml中的代码，如下所示：

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:tools="http://schemas.android.com/tools"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical"
        tools:context=".MainActivity">
    <Button android:id="@+id/send_request"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="Send Request"
            android:textAllCaps="false"/>
    <ScrollView
            android:layout_width="match_parent"
            android:layout_height="match_parent">
        <TextView
                android:id="@+id/response_text"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"/>
    </ScrollView>

</LinearLayout>
```
由于手机屏幕的空间一般都比较小，有时候过多的内容一屏是显示不下的，借助ScrollView控件的话，我们就可以以滚动的形式查看屏幕外的那部分内容。另外，还放置了一个Button和一个TextView，Button用于发送HTTP请求，TextView用于将服务器返回的数据显示出来。接下来修改ManiActivity中的代码。

```java
package com.zj970.httpurlconnectiontest;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String STRING_URL= "https://www.baidu.com";
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
            sendRequestWithHttpURLConnection();
        }
    }

    private void sendRequestWithHttpURLConnection(){
        //开启线程发起网络请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try {
                    URL url = new URL(STRING_URL);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in = connection.getInputStream();
                    //对输入流就行处理
                    reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder reponse = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null){
                        reponse.append(line);
                    }
                    showResponse(reponse.toString());
                } catch (Exception e){
                    e.printStackTrace();
                } finally {
                    if (reader != null){
                        try {
                            reader.close();
                        } catch (IOException e){
                            e.printStackTrace();
                        }
                    }
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
}
```

&emsp;&emsp;可以看到， 我们在Send Request按钮的点击事件里调用了sendRequestWithHttpURLConnection发出一条Http请求，请求的目标地址就是百度的首页。接着利用BufferedReader对服务器返回的流进行读取，并将结果传入到了showResponse()方法中。而在showResponse()方法里则是调用了一个runOnUiThread()方法，然后在这个方法的匿名类参数进行操作，将返回的数据显示到界面上。那么这里为什么要用这个runOnUiThread()方法呢，这是因为Android是不允许在子线程中进行UI操作的，我们需要通过这个方法将线程切换到主线程，然后再更新UI元素。在运行之前，必须在AndroidManifest.xml申请网络权限。

![img_1.png](img_1.png)

&emsp;&emsp;是不是看得头晕眼花了？没错，服务器返回给我们的就是这种HTML代码，通常情况下浏览器都会将这些代码解析成漂亮的网页再展示出来。那么如果是想要提交数据给服务器应该怎么办呢？其实也不复杂，只需要将HTTP请求的方式改成POST，并在获取输入流之前将要提交数据写出即可。注意每条数据都要以键值对的形式存在，数据与数据之间用"&"符号隔开，比如说我们想要向服务器提交用户名和密码，就可以这样写：  

```
connection.setRequestMethod("POST");
DataOutputStream out = new DataOutputStream(connection.getOutputStream());
out.writeBytes("username=admin&password=123456");
```

#### 9.2.2 使用OkHttp

&emsp;&emsp;当然我们并不是只能使用HttpURLConnection，完全没有任何其他选择，事实上在开源盛行的今天，有许多出色的网络通信库都可以替代原生的HttpURLConnection，而其中OkHttp无疑是做的最出色的一个。  
&emsp;&emsp;OkHttp是由鼎鼎大名的Square公司开发的，这个公司在开源事业上面贡献良多，除了OkHttp之外，还开发了；了像Picasso、Retrofit等著名的开源项目。OkHttp不仅在接口封装上面做得简单易用，就连在底层实现上也是自成一派，比起原生的HttpURLConnection，可以说是有过之而无不及，现在已经成了广大Android开发者首选的网络通信库。那么本小节我们就来学习一下OkHttp的用法，okHttp的项目地址是：https://github.com/square/okhttp。在使用OkHttp之前，我们需要先在项目中添加OkHttp库的依赖。编辑app/build.gradle文件，在dependencies闭包中添加如下内容：

```groovy
dependencies {
    // define a BOM and its version
    implementation(platform("com.squareup.okhttp3:okhttp-bom:4.10.0"))
    // define any required OkHttp artifacts without version
    implementation("com.squareup.okhttp3:okhttp")
    implementation("com.squareup.okhttp3:logging-interceptor")
}
```
&emsp;&emsp;添加上述依赖会自动下载两个库，一个是OkHttp库，一个是Okio库，后者是前者的通信基础。首先需要创建一个OkHttpClient的实例，如下所示：
&emsp;&emsp;OkHttpClient client = new OkHttpClient();
&emsp;&emsp;接下来如果想要发出一条HTTP请求，就需要创建一个Request对象：
&emsp;&emsp;Request request = new Request.Builder().build();
&emsp;&emsp;当然，上述代码只是创建一个空的Request对象，并没有什么实际作用，我们可以在最终的build()方法之前连缀很多其他方法来丰富这个Request对象。比如可以通过url()方法来设置目标的网络地址，如下所示：

```
Request request = new Request.Builder().url("http://www.baidu.com").build();
```
&emsp;&emsp;之后调用OkHttpClient的newCall()方法来创建一个Call对象，并调用它的execute()方法来发送请求并获取服务器返回的数据，写法如下：  
```
Response response = client.newCall(request).execute();
```
&emsp;&emsp;其中Response对象就是服务器返回的数据了，我们可以使用如下写法来得到返回的具体内容：

```
String responseData = response.body().string();
```
&emsp;&emsp;如果是发起一条POST请求会比GET请求稍微复杂一点，我们需要先构建一个RequestBody对象来存放待提交的参数，如下所示：
```
RequestBody requestBody = new FormBody.Builder().add("username","admin")
                            .add("password","123456")
                            .build();
```
&emsp;&emsp;然后在Request.Builder中调用一下post()方法，并将Request.Builder中调用一下post()方法，并将RequestBody对象传入：

```
Request request = new Request.Builder()
                .url("http://www.baidu.com")
                .post(requestBody)
                .build();
```
&emsp;&emsp;接下来的操作就和GET请求一样，调用execute()方法来发送请求并获取服务器返回的数据即可。新建一个NetworkTest项目，修改MainActivity中的代码，如下所示：

```java
package com.zj970.networktest;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String STRING_URL= "https://www.baidu.com";
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
}
```

&emsp;&emsp;这里我们并没有做太多的改动，只是添加了一个sendRequestWithOkHttp()方法，并在SendRequest按钮的点击时事件里去调用这个方法。在这个方法中同样还是先开启一个子线程，然后在子线程中使用OkHttp发出一条Http请求，请求的目标地址还是百度的首页，Okhttp的用法也正如前面介绍一样。最后调用showResponse()方法将数据显示到界面。

### 9.3 解析XML格式数据
&emsp;&emsp;通常情况下，每个需要访问网络数据的应用程序都会有一个自己的服务器，我们可以向服务器提交数据，也可以从服务器上获取数据，不过这个时候就出现了一个问题，这个数据到底要以什么样的格式在网络上传输呢？随便传递一段文本肯定是不行的，因为另一方根本就不会知道这段文本的用途是什么。因此，一般我们都会在网络上传输一些格式化后的数据，这种数据会有一定的结构规格和语义，当另一方收到数据消息之后就可以按照相同的结构规格进行解析，从而取出他想要的那部分内容。  
&emsp;&emsp;在网络上传输数据时最常用的格式有两种：XML和JSON，下面我们就来一个一个地进行学习，首先学习如何解析XML格式的数据。  
&emsp;&emsp;在此之前搭建一个Web服务器，用Apache服务器，然后在服务器地址下新建一个get_data.xml文件


```xml
<apps>
    <app>
        <id>1</id>
        <name>Google Map</name>
        <version>1.0</version>
    </app>
    
    <app>
        <id>2</id>
        <name>Chrome</name>
        <version>2.1</version>
    </app>
    
    <app>
        <id>3</id>
        <name>Google Play</name>
        <version>2.3</version>
    </app>
    
</apps>
```

然后在浏览器中输入http://loclhost/get_data.xml就会出现如下图所示：

![img_2.png](img_2.png)

#### 9.3.1 Pull解析方式

&emsp;&emsp;解析XML格式的数据其实也有很多方式，本次学习Pull解析和SAX解析。那么简单起见，这里仍然是在NetworkTest项目上继续开发，这样我们就可重用之前网络通信部分的代码，从而把工作的重心放在XML数据解析上，修改MainActivity中的代码，如下所示：

```java
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
```
&emsp;&emsp;可以看到，这里首先将HTTP请求地址修改成了http://10.0.2.2/get_data.xml，10.0.2.2对于模拟器来说就是电脑本机的IP地址。在得到了服务器返回的数据后，调用parseXMLWithPull()方法来解析服务器返回的数据。  
&emsp;&emsp;首先要获取一个XmlPullParseFactory的实例，并借助这个实例得到XmlPullParse对象，然后调用XmlPullParse的setInput()方法将服务器返回的XML数据设置进去就可以开始解析了。解析的过程非常简单，通过getEventType()可以得到当前的解析事件，然后在一个While循环中不断地进行解析，如果当前的解析事件不等于XmlPullParse.END_DOCUMENT，说明解析工作还没完成，调用next()方法后可以获取到下一个解析事件。  
&emsp;&emsp;在while循环中我们通过getName()方法得到当前节点的名字，如果发现节点名等于id、name或version，就调用nextText()方法来获取节点内具体的内容，每当解析完一个app节点后将获取到的内容打印出来。效果如下：

![img_3.png](img_3.png)

#### 9.3.2 SAX解析方式

&emsp;&emsp;Pull解析方式虽然好用，但它并不是我们唯一的选择。SAX解析也是一种特别常用的XML解析方式。虽然它的用法比Pull解析要复杂一些，但在语义上更加清楚。  
&emsp;&emsp;通常情况下我们都会新建一个类继承自DefaultHandler，并重写父类的5个方法，如下所示：

```java
package com.zj970.networktest;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ContentHandler extends DefaultHandler {

    @Override
    public void startDocument() throws SAXException {

    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {

    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {

    }

    @Override
    public void endDocument() throws SAXException {

    }
}

```

&emsp;&emsp;startDocument()方法会在开始XML解析的时候调用，startElement()方法会在开始解析某个节点的时候呀调用，characters()方法会在获取节点中内容的时候调用，endElement()方法会在解析某个节点的时候调用，endDocument()方法会在完成整个XML解析的时候调用。其中，startElement()、characters()和endElement()这3个方法是有参数的，从XML解析出的数据会以参数的形式传入到这些方法中，需要注意的是，在获取节点中的内容时，characters()方法可能会被调用多次，一些换行符也会被当做内容解析出来，我们需要针对这种情况在代码中做好控制。实现如下：

```java
package com.zj970.networktest;

import android.util.Log;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ContentHandler extends DefaultHandler {
    private static final String TAG = "ContentHandler";
    private String nodeName;
    private StringBuilder id;
    private StringBuilder name;
    private StringBuilder version;

    @Override
    public void startDocument() throws SAXException {
        id = new StringBuilder();
        name = new StringBuilder();
        version = new StringBuilder();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        //记录当前的节点名
        nodeName = localName;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        //根据当前的节点判断将内容添加到哪一个StringBuilder对象中
        if ("id".equals(nodeName)){
            id.append(ch,start,length);
        } else if ("name".equals(nodeName)){
            name.append(ch,start,length);
        } else if ("version".equals(nodeName)){
            version.append(ch,start,length);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if ("app".equals(localName)){
            Log.d(TAG, "pareXMLWithPull: id "+id);
            Log.d(TAG, "pareXMLWithPull: name "+name);
            Log.d(TAG, "pareXMLWithPull: version "+version);
            //最后将StringBuilder清空掉
            id.setLength(0);
            name.setLength(0);
            version.setLength(0);
        }
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }
}

```

&emsp;&emsp;可以看到，我们首先给id、name和version节点分别定义了一个StringBuilder对象，并在startDocument()方法里对它们进行了初始化。每当开始解析某个节点的时候，startElement()方法就会得到调用，其中localName参数记录着当前节点的名字，这里我们把它记录下来。接着在解析节点中具体内容的时候就会调用StringBuilder对象中。最后在endElement()方法中进行判断，如果app节点已经解析完成，就打印出id、name和version中的内容。需要注意的是，目前id、name和version中都可能是包括回车或换行符的，因此在打印之前我们还需要调用一下trim()方法，并且打印完成后还要将stringBuilder的内容清空掉，不然的话会影响下一次内容的获取，接下来修改MainActivity中的代码：

```java
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
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
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
                    pareXMLWithSAX(responseData);
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

    /**
     * SAX 解析XML
     * @param xmlData
     */
    private void pareXMLWithSAX(String xmlData){
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
}
```

&emsp;&emsp;在得到了服务器返回的数据后，我们这次去调用parseXMLWithSAX()方法来解析XML数据。parseXMLWithSAX()方法中先是创建了一个SAXParserFactory的对象，然后再获取到了XMLReader对象，接着我们编写的ContentHandler的实例设置到了XMLReader中，最后调用parse()方法开始执行。  
&emsp;&emsp;除了Pull解析和SAX解析之外，其实还有一种解DOM解析方式。

### 9.4 解析JSON格式数据  

&emsp;&emsp;下面学习如何解析JSON格式的数据。比起XML，JSON的主要优势在于它的体积更小，在网络上传输的时候更省流浪。但缺点在于，他的语义性较差，看起来不如XML直观。我们在服务器地址下新建一个get_data.json的文件，内容如下：

```json
[
  {"id":"5","version":"5.5","name":"clash of clans"},
  {"id":"6","version":"7.0","name":"Boom Beach"},
  {"id":"7","version":"3.5","name":"Clash Royale"}
]

```

本机访问效果如下

![img_4.png](img_4.png)

#### 9.4.1 使用JSONObject

&emsp;&emsp;类似地，解析JSON数据也有很多种方法，可以使用官方提供的JSONObject，也可以使用谷歌开源库GSON。另外，一些第三方的开源库如Jackson、FastJSON等也非常不错。本节中我们就来学习一下前两种解析方式的用法。修改MainActivity中的代码

```java
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

}
```

&emsp;&emsp;首先记得要将HTTP请求店址改成http://10.0.2.2/get_data.json，然后在得到了服务器返回的数据后调用parseJSONWithJSONObject()方法来解析数据。可以看到，解析JSON的代码非常简单，由于我们在服务器中定义的是一个JSON数组，因此这里首先是将服务器返回的数据传入到了一个JSONArray对象中。然后循环这个遍历JSONArray，总中取出的每一个元素都是一个JSONObject对象，每个JSONObject对象zh9ong又包含id、name和version这些数据。接下来就只需要调用getString()方法将这些数据打印出来。现在重新运行一下程序，效果如下：  

![img_5.png](img_5.png)

备注：  
1.如果看到是{ }–>使用JSONObject   
2.如果看到的[ ]–>使用JSONArray解析

#### 9.4.2 使用GSON

&emsp;&emsp;谷歌提供的GSON开源库可以让JSON数据的工作更加简单，不过GSON并没有添加到Android官方的API中，因此如果想要使用这个功能的话，就必须要在项目中添加GSON库的依赖。编辑app/build.gradle文件，在dependencies闭包中添加如下内容：  

```groovy
dependencies {
    implementation 'androidx.appcompat:appcompat:1.1.0'
    implementation 'com.google.android.material:material:1.1.0'
    implementation 'androidx.constraintlayout:constraintlayout:1.1.3'
    testImplementation 'junit:junit:4.13.2'
    androidTestImplementation 'androidx.test.ext:junit:1.1.1'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.2.0'
    // define a BOM and its version
    implementation(platform("com.squareup.okhttp3:okhttp-bom:4.10.0"))
    // define any required OkHttp artifacts without version
    implementation("com.squareup.okhttp3:okhttp")
    implementation("com.squareup.okhttp3:logging-interceptor")
    // https://mvnrepository.com/artifact/com.google.code.gson/gson
    implementation 'com.google.code.gson:gson:2.10'
}
```

&emsp;&emsp;那么GSON库究竟是神奇在哪里呢？其实它主要是可以将一段JSON格式的字符串自动映射一个对象，从而不需要在手动去编写代码进行解析了。比如说一段JSON格式的数据如下：  
&emsp;&emsp; {"name":"Tome","age":20} 那么我们可以定义一个Person类，并加入name和age这两个字段，然后只需要简单地调用如下代码就可以将JSON数据自动解析成一个Person类，并加入name和age这两个字段，然后只需要简单地调用如下代码就可以将JSOn数据自动解析成一个Person对象了：  
&emsp;&emsp; Gson gson = new Gson();  
&emsp;&emsp; Person person = gson.fromJson(jsonData, new TypeToken<List<Person>>);  
&emsp;&emsp;如果需要解析的是一段JSON数组会稍微麻烦一点，我们需要借助TypeToken将期望解析成的数据类型传入到fromJson()方法中，如下所示：  
&emsp;&emsp; List<Person> person = gson.from(jsonData, new TypeToken<List<Person>>(){}.getType());  
&emsp;&emsp;好了，基本的用法就是这样，下面就让我们来真正地尝试一下吧。首先是新建一个App类，并加入id、name和version这3个字段中，如下所示：  

```java
package com.zj970.networktest.entity;

public class App {
    private String id;

    private String name;

    private String version;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}

```

然后修改MainActivity中的代码，如下所示：

```java
package com.zj970.networktest;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zj970.networktest.entity.App;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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
```

运行效果如下 ：  

![img_7.png](img_7.png)

### 9.5 网络编程的最佳实践  

&emsp;&emsp;目前已经掌握了HttpURLConnection和OkHttp的用法，知道了如何发起HTTP请求，以及解析服务器返回的数据，但也许你还没有发现，之前我们的写法其实是很有问题的。因为一个应用程序很可能会在许多地方都使用到网络功能，而发送HTTP请求的代码基本上都是相同的，如果我们每次都去编写一遍发送HTTP请求的代码，这显然是非常差劲的做法。  
&emsp;&emsp;没错，通常情况下我们都应该将这些通用的网络操作提取到一个公共类中，并提供一个静态方法，当想要发起网络请求的时候，只需要简单地调用一下这个方法即可。比如使用如下的写法：  

```java
package com.zj970.networktest.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class HttpUtil {

    public static String sendHttpRequest(String address){
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
            return response.toString();
        } catch (ProtocolException e) {
            return e.getMessage();
        } catch (MalformedURLException e) {
            return e.getMessage();
        } catch (IOException e) {
            return e.getMessage();
        } finally {
            if (connection != null){
                connection.disconnect();
            }
        }
    }
}

```

以后每当需要发起一条HTTP请求的时候可以这样写：  

```
String address = "http://www.baidu.com";
String response = HttpUtil.sendHttpRequest(address);

```

&emsp;&emsp;在获取到服务器响应的数据后，我们就可以对它进行解析和处理了。但是需要注意，网络请求通常都是属于耗时操作，而sendHttpRequest()方法的内部没有开启线程，这样就有可能导致在调用sendHttpRequest()方法的时候使得主线程被阻塞住。  
&emsp;&emsp;你可能说，很简单嘛，在sendHttpRequest()方法内部开启一个线程不就解决这个问题了嘛？其实没有想象中的那么容易，因为如果我们在sendHttpRequest()方法内部开启一个线程来发起HTTP请求，那么服务器响应的数据是无法进行返回的，所有的耗时逻辑都是在子线程里进行的,sendHttpRequest()方法会在服务器还没来得及响应的时候就执行结束了，然后也就无法返回响应的数据了。  
&emsp;&emsp;那么遇到这种情况下应该怎么办呢？其实解决方法并不难，只需要使用Java的回调机制就可以了，下面让我们来学习一下回调机制到底是如何使用的。  
&emsp;&emsp;首先需要定义一个结构，比如将它命名成HttpCallbackListener，代码如下所示：  

```java
package com.zj970.networktest.callback;

public interface HttpCallbackListener {
    void onFinish(String response);

    void OnError(Exception e);
}

```

&emsp;&emsp;可以看到，我们在接口中定义了两个方法，onFinish()方法表示当服务器响应我们请求的时候调用，onError()表示当进行网络出现错误的时候调用。这两个方法都带有参数，onFinish()方法中的参数代表着服务器返回的数据，而onError()方法中的 参数记录着错误的详细信息。接着修改HttpUtil中的代码：如下所示：  

```java
package com.zj970.networktest.util;

import com.zj970.networktest.callback.HttpCallbackListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class HttpUtil {

    public static String sendHttpRequest(String address){
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
            return response.toString();
        } catch (ProtocolException e) {
            return e.getMessage();
        } catch (MalformedURLException e) {
            return e.getMessage();
        } catch (IOException e) {
            return e.getMessage();
        } finally {
            if (connection != null){
                connection.disconnect();
            }
        }
    }


    public static void sendHttpRequest(final String address, final HttpCallbackListener callbackListener){
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
&emsp;&emsp;我们首先给sendHttpRequest()方法中添加了一个HttpCallbackListener餐胡，并在方法的内部开启了一个子线程，然后在子线程里去执行具体的网络操作。注意，子线程中是无法通过return语句来返回数据的，因此这里我们将服务器响应的数据传入了HttpCallbackListener的onFinish()方法中，如果出现了异常就将异常原因传入到onError()方法中。现在sendHttpRequest()方法接收两个参数了，因此我们在调用它的时候还需要将HttpCallbackListener的实例传入，如下所示：  

```
            HttpUtil.sendHttpRequest(STRING_URL, new HttpCallbackListener() {
                @Override
                public void onFinish(String response) {
                    //TODO: 在这里根据返回内容执行具体逻辑
                }

                @Override
                public void onError(Exception e) {
                    //TODO: 在这里对异常情况进行处理
                }
            });
```
&emsp;&emsp;这样的话，当服务器成功响应的时候，我们就可以在onFinish()方法里对响应数据进行处理了。类似地，如果出现了异常，就可以在onError()方法里对异常情况进行处理。如此依赖，我们就巧妙地利用回调机制将响应数据成功返回给调用方了。  
&emsp;&emsp;不过你会发现，上述使用HttpURLConnection 的写法总体来说还是比较复杂的，那么使用OkHttp会变得简单？答案是肯定的，而且要简单的多，下面我们来具体看一下，在HttpUtil中加入一个sendOkhttpRequest()方法，如下所示：  

```
    public static void sendOkhttpRequest(String address, Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }
```
&emsp;&emsp;可以看到，sendOkHttpRequest()方法中有个Callback参数，这个是OkHttp参数，这个是OkHttp自带的一个回调接口。类似于我们刚才自己编写的HttpCallbackListener。然后在client.newCall()之后并没有像之前一直调用execute()方法而是调用了一个enqueue()方法，并把callback参数传入。OkHttp在enqueue()方法的内部已经帮我们开好子线程了，然后会在子线程中去执行HTTP请求，并将最终的请求结果回调到callback当中。那么我么在调用sendOkHttpRequest()方法的时候可以这样写：  

```
public void onClick(View v) {
        if (v.getId() == R.id.send_request){
            sendRequestWithOkHttp();
            HttpUtil.sendOkhttpRequest(STRING_JSON_URL, new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {

                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
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
```
&emsp;&emsp;由此可以看出，okHttp的接口设计更加人性化，它将常用的功能进行了封装，使得我们用少量的代码完成较为复杂的网络操作。另外需要注意的是，不管是使用HttpURLConnection还是Okhttp，最终的回调接口还是在子线程中运行，因此我们不可以在这里执行任何的UI操作，除非借助runOnUiThread()方法进行线程转换。至于具体原因，在下一章中学习到了。

### 9.6 小节与点评  

&emsp;&emsp;本章中我们主要学习了在Android中使用HTTP协议来进行网络交互的知识，虽然Android中支持的网络通信协议有很多种，但HTTP协议是最常用的一种。通常我们有两种方式来发送HTTP请求，分别是HttpURLConnection和OkHttp。  
&emsp;&emsp;接着我们又学习了XML和JSON个数数据的解析方式，因为服务器响应给我们的数据一般都是属于这两种格式的。不论是XML还是JSON，它们各自都有很多种解析方式，这里只是介绍了几种最常用的。  
&emsp;&emsp;本章的最后同样是最佳实践环节，在这里，我们主要学习了如何利用Java的回调机制来将服务器响应的数据进行返回。其实除此之外，还有很多地方都可以使用到java的回调机制。