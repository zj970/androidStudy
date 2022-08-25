# 第6章 数据存储全方案——详解持久化技术

&emsp;&emsp;任何一个应用程序，其实说白了就是不停地和数据打交道，我们聊QQ，看新闻，刷微博，所关心的都是里面的数据，没有数据的应用程序就变成了一个空壳子，对用户来说没有任何实际用途，那么这些数据都是从哪里来呢？现在多数的数据基本上都是由用户产生的，比如你发微博、评论新闻，其实都是在产生数据。  

&emsp;&emsp;而我们前面章节所编写的众多例子中也有用到各种各样的数据，例如第3章最佳实践部分在聊天界面编写的聊天内容，第5章最佳实践部分在登录界面上输入的密码和账户。这些数据都有一个共同点，即它们都属于瞬时数据。那什么是瞬时数据呢？就是指哪些存储在内存当中，有可能会因为程序关闭或其他原因导致内存被回收而丢失的数据，这对于一些关键性的数据信息来说是绝对不能容忍的，谁都不希望发自己刚发出去的一条微博，刷新一下就没有了吧。那么怎么才能保证一些关键性数据不会丢失呢？这就需要用到数据持久化技术了。

## 6.1 持久化技术简介

&emsp;&emsp;数据持久化就是将哪些内存中的瞬时数据保存到存储设备中，保证即使在手机或电脑关机的情况下，这些数据仍然不会丢失，保存在内存中的数据是处于瞬时状态的，而保存在存储设备中的数据是处于持久状态的，持久化技术提供了一种机制可以让数据在瞬时状态和持久化状态间进行转换。

&emsp;&emsp;持久化技术被广泛应用于各种程序设计的领域当中，而本书中主要探讨的是在Android中的数据持久化技术。Android系统中主要提供了3种方式用于简单地是实现数据持久化功能，即文件存储、SharedPreference存储以及数据库存储。当然，除了这3种方式之外，还可以把数据保存在手机的SD卡中，不过使用文件、SharedPreference或数据库来保存数据会相对更简单一点，而且比起将数据保存在SD卡中会更加地安全。

## 6.2 文件存储

&emsp;&emsp;文件存储是Android中最基本的一种数据存储方式，它不对存储的内容进行任何的格式化处理，所有的数据都是原封不动地保存到文件中，因而它比较适合用于存储一些简单的文本数据或二进制数据。如果想要用文件存储的方式来存储一些较为复杂的文本数据，就需要自定义一套自己的格式规范，这样可以方便之后将数据从文件中重新解析出来。

### 6.2.1 将数据存储文件中

&emsp;&emsp;Context类中提供了一个openFileOutput()方法，可以用于将数据存储到指定的文件中，这个方法接收两个参数，第一个参数是文件名，在文件创建的时候使用的就是这个名称，注意这里指定的文件名不可以包含路径，因为所有的文件都是默认存储到/data/data/<package_name>/files/目录下的，第二个参数是文件的操作模式，主要有两种模式可选，MODE_PRIVATE和MODE_APPEND。其中MODE_PRIVATE是默认的操作模式，表示当指定同样文件名的时候，所写入的内容将会覆盖原文件中的内容，而MODE_APPEND则表示如果该文件已存在，就往文件里面追加内容，不存在就创建新文件。其实文件的操作模式本来还有两种：MODE_WORLD_READABLE和MODE_WORLD_WRITEABLE，这两种模式允许其他的应用程序对我们程序中的文件进行读写操作，不过由于这两种模式过于危险，很容易引起应用的安全性漏洞，已在Android 4.2版本中废弃。

&emsp;&emsp;openFileOutput()方法返回是一个FileOutputStream对象，得到了这个对象之后就可以使用java流的方式将数据写入到文件中了。以下是一段简单的代码示例，展示了如何将文本内容保存到文件中：

```
public void save(){
    String data = "Data to save";
    FileOutputStream out = null;
    BufferedWriter writer = null;
    try {
        out = openFileOutput("data",Context.MODE_PRIVATE);
        writer = new BufferedWriter(new OutputStreamWriter(out));
        writer.write(data);
    } catch (IOException e){
        e.printStackTrace();
    } finally{
        try {
             if  (writer != null){
             writer.close();
             }
        } catch (IOException e){
             e.printStackTrace();
        }
    }   
}             
```

&emsp;&emsp;如果你已经比较熟悉java流，理解上面的代码一定轻而易举吧。这里通过openFileOutput()方法能够得到一个FileOutputStream对象，然后再借助它构建出一个BufferedWriter对象，这样就可以通过BufferedWriter来将文本内容写入到文件中了。

&emsp;&emsp;下面我们就编写一个完整的例子，借此学习一下如何在Android项目中使用文件存储的技术。首先创建一个FilePersistenceTest项目，并修改activity_main.xml中的代码，如下所示：

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:tools="http://schemas.android.com/tools"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        tools:context=".MainActivity">
    <EditText
            android:id="@+id/edit"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:hint="Type something here"/>
</LinearLayout>
```

&emsp;&emsp;这里只是在布局中加入了一个EditText，用于输入文本内容。其实现在你就可以运行一下程序了，界面上肯定会有一个文本输入框。然后在文本输入框中随意输入点什么内容，再按下Back键，这时输入的内容肯定就已经丢失了，因为它只是瞬时数据，在活动被销毁后就会被回收。而我们要做的，就是在数据被回收之前，将它存储到文件中。修改MainActivity中的代码，如下所示：

```java
package com.zj970.filepersistencetest;

import android.content.Context;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private EditText edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edit = findViewById(R.id.edit);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        String inputText = edit.getText().toString();
    }

    public void save(String inputText){
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try {
            out = openFileOutput("data", Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(inputText);
        }catch (IOException e){
            e.printStackTrace();
        } finally {
            try{
                if (writer != null){
                    writer.close();
                }
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
```

&emsp;&emsp;可以看到，首先我们在onCreate()方法中获取了EditText的实例，然后重写了onDestroy()方法中我们获取了EditText中输入的内容存储到文件中，文件命名为data。save()方法中的代码和之前的示例基本相同。

![img_1.png](img_1.png)

然后按下Back键关闭程序，这时我们输入的内容就已经保存在文件中了。利用Device File Explorer工具查看文件

![img.png](img.png)

双击文件就会下载：

![img_2.png](img_2.png)

### 6.2.2 从文件中读取数据

&emsp;&emsp;类似于将数据存储到文件中，Context类还提供了一个openFileInput()方法，用于从文件中读取数据。这个方法要比openFIleOutput()简单一些，它只接收一个参数，即要读取的文件名，然后系统会自动去到/data/data/<package_nem>files/目录下去加载这个文件，并返回一个FileInputStream对象，得到这个对象之后再通过java流的方式就可以将数据取出来了。下面是示例代码

```
public String load(){
    FileInputStream in = null;
    BUfferedReader reader = null;
    StringBuuilder content = new StringBulder();
    try {
        in = openFileInput("data");
        reader = new BufferedReader(new InputStreamReader(in));
        String line = "";
        while ((line = reader.readLine()) != null){
        content.append(line);
        }
     } catch (IOException e){
        e.printStrackTrace();
     } finally {
        if (reader != null){
            try {
                reader.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        
     }
     return content.toString();
     
}
        
```

&emsp;&emsp;在这段代码中，首先通过openFileInput()方法获取到了一个FileInputStream对对象，这样我们就可以通过BufferedReader进行一行一行地读取，把文件中所有的文本内容全部读取出来，并存放在一个StringBuilder对象中，最后将读取到的内容返回就可以了。了解了从文件中读取数据的方法，那么我门就继续完善上一小节中的例子，使得重新启动程序时Edit中能够保留我们上次输入的内容，修改MainActivity中的代码

```java
package com.zj970.filepersistencetest;

import android.content.Context;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.io.*;

public class MainActivity extends AppCompatActivity {

    private EditText edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edit = findViewById(R.id.edit);
        String inputText = load();
        if (!TextUtils.isEmpty(inputText)) {
            edit.setText(inputText);
            edit.setSelection(inputText.length());
            Toast.makeText(this, "Restoring succeeded", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        String inputText = edit.getText().toString();
        save(inputText);
    }

    public void save(String inputText) {
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try {
            out = openFileOutput("data", Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(inputText);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String load() {
        FileInputStream in = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        try {
            in = openFileInput("ddata");
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        } catch (IOException e) {
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

        return content.toString();
    }
}
```
&emsp;&emsp;可以看到，这里的思路非常简单，在onCreate()方法中调用load()方法来读取文件中存储的文本内容，如果读取到的内容不为null，就调用EditText的setText()方法将内容填充到EditText中，并调用setSelection()方法将输入光标移动到文本的末尾位置以便于继续输入，然后弹出一句还原成功的提示。注意上述代码中在对字符串非空判断的时候使用了TextUtils.isEmpty()方法，这是一个非常好用的方法，它可以一次性进行两种空值的判断。当传入的字符串等于null或等于空字符串的时候，这个方法都会返回true，从而使得我们先单独判断这两种空值再使用逻辑运算符连接起来了。现在重新运行，效果如下：

![img_3.png](img_3.png)

&emsp;&esmp;文件存储的方式并不适合存储一些较为复杂的文本数据，因此，下面有Android的另外一种数据持久化的方式，它比文件存储更加简单易用，而且可以很方便地对某一指定的数据进行读写操作。

## 6.3 SharedPreferences 存储

&emsp;&emsp;不同于文件的存储方式，SharedPreferences是使用键值对的方式来存储数据的。也就是说，当保存一条数据的时候，需要给这条数据提供一个对应的键，这样在读取数据的时候就可以通过这个键把相应的值取出来。而且SharedPreferences还支持多种不同的数据类型存储，如果存储的数据类型是整型，那么读取出来的数据也是整型的；如果存储的数据是一个字符串，那么读取出来的数据仍然是字符串。

### 6.3.1 将数据存储到SharedPreferences中

&emsp;&emsp;要想使用SharedPreferences来存储数据，首先要获取到SharedPreferences对象。Android中主要提供了3种方法用于得到SharedPreferences对象。

- Context类中的getShredPreferences()方法

&emsp;&emsp;此方法接收两个参数，第一个参数用于指定SharedPreferences文件的名称，如果指定的文件不存在则会创建一个，SharedPreferences文件都是存放在<data/data/<package_name>/shared_prefs/目录下。第二个参数用于指定操作模式，目前只有MODE_PRIVATE这一种模式可选，他是默认的操作模式，和直接传入0效果是相同的，表示只有当前的应用程序才可以对这个SharedPreferences文件进行读写。其他几种操作模式均已被废弃，MODE_WORLD_READABLE和MODE_WORLD_WRITEABLE这两种模式是在Android 4.2 版本中废弃的，MODE_MULTIPROCESS是在Android 6.0版本废弃的。

- Activity类中的getPreferences()方法

&emsp;&emsp;这个方法和Context中的getSharedPreferences()方法很相似，不过它只接收一个操作模式参数，因为使用这个方法时会自动将当前活动的类名作为SharedPreferences的文件名。

- PreferenceManager类中的getDefaultSharedPreferences()方法

&emso;&emsp;这是一个静态的方法，它接收一个Context参数，并自动使用当前应用程序的包名作为前缀来命名SharedPreferences文件。得到了SharedPreferences对象之后，就可以开始向SharedPreferences文件中存储数据了，主要分为3步实现。

1. 调用SharedPreferences对象的edit()方法来获取一个SharedPreferences.Editor对象。
2. 向SharedPreferences.Editor对象中添加数据，比如添加一个布尔型数据就使用putBoolean()方法，添加一个字符串则使用putString()方法，以此类推。
3. 调用apply()方法将添加的数据提交，从而完成数据存储操作。

&emsp;&emsp;新建一个SharedPreferencesTest模块，然后修改activity_main.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:tools="http://schemas.android.com/tools"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        tools:context=".MainActivity"
        android:orientation="vertical">
    <Button android:id="@+id/save_data"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="Save Date"
            android:textAllCaps="false"/>
</LinearLayout>
```
这里我们不做任何复杂的功能，只是简单地放置了一个按钮，用于将一些数据存储到SharedPreferences文件中。然后修改MainActivity.java中的代码

```java
package com.example.sharedpreferencestest;

import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button saveButton = findViewById(R.id.save_data);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences("data",MODE_PRIVATE).edit();
                editor.putString("name","Tom");
                editor.putInt("age",19);
                editor.putBoolean("男",false);
                editor.apply();
            }
        });
    }
}
```
可以看到，这里首先给按钮注册了一个点击事件，然后通过点击事件中通过getSharedPreferences()方法指定SharedPreferences的文件名指定为data，并得到了SharedPreferences.Editor对象。接着向这人个对象中添加了3条不同类型的数据，最后调用apply()方法进行提交，从而完成了数据存储的操作。现在运行一下，进入程序，点击Save Data按钮。这是数据应该保存成功了。在/data/data/com.example.sharepreferencestest/shared_prefs/目录下，生成了一个data.xml文件

并且sharedPreferences文件是使用xml格式来对数据进行管理的。

### 6.3.2 从SharedPreferences中读取数据

&emsp;&emsp;使用SharedPreferences来存储数据是非常简单的，不过下面还有更好的消息，其实从SharedPreferences文件中读取数据会更加简单。SharedPreferences对象中提供了一系列的get方法，用于对存储的数据进行读取，每种get方法都对应了SharedPreferences.Editor中的一种put方法，比如读取一个布尔型数据就使用getBoolean()方法，读取一个字符串就使用getString()方法。这些get方法都接收两个参数，第一个参数