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