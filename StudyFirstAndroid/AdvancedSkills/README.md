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


## 13.2 使用Intent传递对象

&emsp;&emsp;Intent的用法相信你已经比较熟悉了，我们可以借助它来启动活动、发送广播、启动服务等。在进行上述操作的时候，我们还可以在Intent中添加一些附加数据，以达到传值的效果，比如在FirstActivity中添加如下代码： 

```
Intent intent = new Intent(FirstActivity.this,SecondActivity.class);
intent.putExtra("string_data","hello");
intent.putExtra("int_data",100);
startActivity(intent);
```
&emsp;&emsp;这里调用了Intent的putExtra()方法来添加要传递的数据，之后在SecondActivity中就可以得到这些值了，代码如下所示：

```
getIntent().getStringExtra("string_data");
getIntent().getIntExtra("int_data",0);
```
&emsp;&emsp;但是不知道你有没有发现，putExtra()方法中所支持的数据类型是有限的，虽然常用的一些数据类型它都会支持，但是当你想去传递一些自定义对象的时候，就会无从下手。不用担心，下面将就学习一下使用Intent来传递对象的技巧。  

### 13.2.1 Serializable 方式

&emsp;&emsp;使用Intent来传递对象通常有两种方式：Serializable和Parcelable，本小节中我们先来学习一下第一种实现方式。
&emsp;&emsp;Serializable是序列化的意思，表示将一个对象转换成可存储或可传输的状态。序列化后的对象可以在网络上进行传输，也可以存储到本地。至于序列化的方法也很简单，只需要让一个类去实现Serializable这个接口就可以了。比如说有一个Person类，其中包含了name和age这两个字段，想要将它序列化就可以这样写：  

```java
package com.zj970.advanced.entity;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author: zj970
 * @date: 2022/12/15
 */
public class Person implements Serializable {
    private int id;
    private String name;
    private int age;

    public Person() {
    }

    public Person(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

```

&emsp;&emsp;其中，get、set方法都是用于赋值和读取字段数据的，最重要的部分是在第一行。这里让Person类去实现了Serializable接口，这样所有的Person对象都是可序列化的了。接下来在FirstActivity中的写法非常简单：  
```
Person person = new Person(1,"李华",18);
Intent intent = new Intent(FirstActivity.this,Serializable.class);
intent.putExtra("person_data",person);
startActivity(intent);
```
&emsp;&emsp;可以看到，这里我们创建了一个Person的实例，然后就直接将它传入到putExtra()方法中了。由于Person类实现了Serializable接口，所以才可以这样写。接下来在SecondActivity中获取这个对象也很简单，写法如下： Person peron = (person)getIntent().getSerializableExtra("person_data");  
&emsp;&emsp;这里调用了getSerializableExtra()方法来获取通过参数传递过来的序列化对象，接着再将它向下转型成Person对象，这样我们就成功实现了使用Intent来传递对象的功能了。

### 13.2.2 Parcelable方式  
&emsp;&emsp;除了Serializable之外，使用Parcelable也可以实现相同的效果，不过不同于将对象进行序列化，Parcelable方式的实现原理是将一个完整的对象进行分解，而分解后的每一部分都是Intent所支持的数据类型，这样也就实现传递对象的功能了。下面看一下Parcelable的实现方式，新建Dog，如下：  

```java
package com.zj970.advanced.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * <p>
 *
 * </p>
 *
 * @author: zj970
 * @date: 2022/12/15
 */
public class Dog implements Parcelable {
    private String name;
    private int age;

    public Dog(String name, int age) {
        this.name = name;
        this.age = age;
    }

    protected Dog(Parcel in) {
        name = in.readString();
        age = in.readInt();
    }

    public static final Creator<Dog> CREATOR = new Creator<Dog>() {
        @Override
        public Dog createFromParcel(Parcel in) {
            return new Dog(in);
        }

        @Override
        public Dog[] newArray(int size) {
            return new Dog[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(age);
    }
}

```

&emsp;&emsp;Parcelable的实现方式要稍微复杂一些。可以看到，首先我们让Dog类去实现了Parcelable接口，这样就必须重写describeContents()方法和writeToParcel()方法。其中describeContents()方法直接返回0就可以了，而writeToParcel()方法中我们需要调用Parcel的writeXxx()方法，将Dog类中的字段一一写出。注意，字符串类型数据就调用writeString()方法，整型数据就调用writeInt()方法，以此类推。  
&emsp;&emsp;除此之外，我们还必须在Dog类中提供一个名为CREATOR的常量，这里创建Parcelable.Creator接口的一个实现，并将泛型指定为Dog。接着需要重写createFromParcel()方法和newArray()方法，在createFromParcel()方法中我要去读取刚才写出的name和age字段，并创建一个Dog对象返回，其中name和age都是调用Parcel的readXxx()方法读取到的，注意这里的读取顺序要和刚才的写出顺序一定要完全相同。而newArray()方法中的实现就简单多了，只需要new出一个Dog数组，并使用方法中传入的size作为数组大小就可以了。  
&emsp;&emsp;接下来，在FirstActivity中我们仍然使用之前相同的代码来传递对象，只不过在SecondActivity中获取的时候需要进行改动，如下所示：

```
Dog dog = (Dog) getIntent().getParcelableExtra("dog_data");
```
&emsp;&emsp;注意，这里不再是调用getSerializableExtra()而是调用getParcelableExtra()方法，其他的地方都完全相同。这样我们就把是使用Intent来传递对象的两种实现方式都学习完了，对比一下，Serializable的方式比较简单，但是由于会把整个对象进行序列化，因此效率会比Parcelable方法低一些，所以在通常情况下还是更加推荐Parcelable的方式来实现Intent来传递对象的功能。