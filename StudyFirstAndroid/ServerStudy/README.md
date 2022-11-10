# 第10章 后台默默的劳动者——探究服务  

&emsp;&emsp;记得在我上大学的时候，iPhone是属于少数人才拥有的稀有物品，Android甚至还没面世，那个时候全球的手机市场是由诺基亚统治着的。当时我觉得诺基亚的Symbian操作系统做得特别出色，因为比起一般的手机，它可以支持后台功能。那个时候能够一边打着电话、听着音乐，一边在后台挂着QQ是件非常酷的事情。所以我也曾经单纯地认为，支持后台的手机就是智能手机。  
&emsp;&emsp;而如今，Symbian早已风光不再，Android和iOS占据了大部分的智能市场份额，Windows Phone 也占据了一小部分，目前已是三分天下的局面。在这三大智能手机操作系统中，iOS和WindowsPHone一开始都是不支持后台的，后来逐渐意识到这个功能的重要性，才加入了后台功能。而Android则是沿用了Symbian的老系统，从 一开始就支持后台功能，这使得应用程序即使在关闭的情况下仍然可以在后台继续运行。不管怎么说，后台功能属于四大组件之一，其重要程度不言而喻，那么我们自然要好好学习它的用法了。  

## 10.1 服务是什么

&emsp;&emsp;服务(Service)是Android中实现程序后台运行的解决方案，它它非常适合去执行那些不需要和用户交而且还要求长期运行的任务。服务的运行不依赖任何用户界面，即使程序被切换到后台，或者用户打开了另外一个应用程序，服务仍然能够保持正常运行。  
&emsp;&emsp;不过需要注意的是，服务并不是运行在一个独立的进程当中的，而是依赖于创建服务时所在的应用程序进程。当某个应用程序进程被杀掉时，所以依赖于该进程的服务也会停止运行。  
&emsp;&emsp;另外，也不要被服务的后台概念所迷惑，实际上服务并不会自动开启线程，所有的代码都是默认运行在主线程当中。也就是说，我们需要在服务的内部手动创建子线程，并在这里执行具体的任务，否则就有可能出现主线程被阻塞住的情况。那么本章的第一堂课，我们就先来学习一下Android多线程编程的知识。  

## 10.2 Android多线程编程  

&emsp;&emsp;熟悉Java的你，对多线程编程一定不会陌生吧。当我们需要执行一些耗时操作，比如说发起一条网络请求时，考虑到网速等其他原因，服务器未必立刻响应我们的请求，如果不将这类操作放在子线程里去运行，就会导致主线程被阻塞住，从而影响用户对软件正常使用，那么就让我们从线程的基本用法开始学习吧。  

### 10.2.1 线程的基本用法  

&emsp;&emsp;Android多线程编程其实并不比Java多线程编程特殊，基本都是使用相同的语法。比如说，定义一个线程只需要新建一个类继承自Thread，然后重写父类的run()方法，并在里面编写耗时逻辑即可，如下所示：  

```
class MyThread extends Thread{
    @Override
    public void run(){
        //处理相关的逻辑
    }
}
```

&emsp;&emsp;那么该如何开启这个线程呢？其实也很简单，只需要new出MyThread的实例，然后调用它的start()方法，这样run()方法中的代码就会在子线程中运行了，如下所示：  

```
new MyThread().start();
```

&emsp;&emsp;当然，使用继承的方式耦合性有点高，更多的时候我们都会选择使用实现Runnable接口的方式定义一个线程，如下所示：  

```
class MyThread implements Runnable{
    @Override
    public void run(){
        //处理具体的逻辑
    }
}
```

&emsp;&emsp;如果使用了这种写法，启动线程的方法也需要进行相应的改变，如下所示：  

```
MyThread myThread = new MyThread();
new Thread(myThread).start();
```
&emsp;&emsp;可以看到，Thread的构造函数接收一个Runnable参数，而我们new 出的MyThread正是一个实现了Runnable接口的对象，所以可以直接将它传入到Thread的构造函数里。接着调用Thread的start()方法，run()方法中的代码就会在子线程当中运行了。  
&emsp;&emsp;当然，如果你不想专门再定义一个类去实现Runnable接口，也可以使用匿名类的方式，这种方式更加常见，如下所示：  

```
new Thread(new Runnable(){
       @Override
       public void run(){
        //处理相关逻辑
       }

}).start();
```

&emsp;&emsp;以上几种线程的使用方式相信你并不会感到陌生，因为在Java中创建和启动线程也是使用同样的方式，了解了线程的基本用法后，下面我们来看一下Android多线程与Java多线程编程不同的地方。  

### 10.2.2 在子线程中更新UI

&emsp;&emsp;和许多其他的GUI库一样，Android的UI也是线程不安全的。也就是说，如果想要更新应用程序的UI元素，则 必须在主线程中进行，否则就会出现异常。  
&emsp;&emsp;眼见为实，新建一个AndroidThreadTest模块，然后修改activity_main.xml中的代码： 

```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:tools="http://schemas.android.com/tools"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        tools:context=".MainActivity">
    <Button
            android:id="@+id/change_text"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:textAllCaps="false"
            android:text="Change text"/>

    <TextView
            android:id="@+id/text"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Hello world"
            android:textSize="20sp"/>

</RelativeLayout>
```

&emsp;&emsp;布局文件中定义了两个控件，TextView用于在屏幕的正中央显示一个Hello World字符串，Button用于改变TextView中显示的内容，我们希望在点击Button后可以把TextView中显示的字符串改成 Nice to meet you。 修改MAinActivity中代码，如下所示：  

```java
package com.zj970.androidthreadtest;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = findViewById(R.id.text);
        Button changeText = findViewById(R.id.change_text);
        changeText.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.change_text:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        text.setText("Nice to meet you");
                    }
                }).start();
                break;
            default:
                break;
        }
    }
}
```

&emsp;&emsp;可以看到，我们在Change Text按钮的点击事件里面开启个子线程，然后在子线程中调用TextView的setText()方法将显示的字符串改成Nice to meet you。代码的逻辑非常简单，只不过我们是在子线程更新UI的。现在运行一下程序，并点击一下按钮，会发现程序崩溃了，如图所示：  

![img.png](img.png)

&emsp;&emsp;由此证实了Android确实是不允许子线程里去执行一些耗时任务，然后根据任务的执行结果来更新相应的UI控件，这该如何是好呢？  
&emsp;&emsp;对于这种情况，Android提供了一套异步消息处理机制，完美地解决了在子线程进行UIc操作的问题。本小节中我们先来学习一下异步消息处理的使用方法，下一小节中再去分析它的原理。修改MainActivity中的代码:  

```java
package com.zj970.androidthreadtest;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    public static final int UPDATE_TEXT = 1;
    TextView text;

    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case UPDATE_TEXT:
                    //这里进行UI操作
                    text.setText("Nice to meet you");
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = findViewById(R.id.text);
        Button changeText = findViewById(R.id.change_text);
        changeText.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.change_text:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message message = new Message();
                        message.what = UPDATE_TEXT;
                        handler.sendMessage(message);//将Message对象发送出去
                    }
                }).start();
                break;
            default:
                break;
        }
    }
}
```