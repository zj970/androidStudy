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
        android:orientation="vertical"
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
            android:layout_centerInParent="true"
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
&emsp;&emsp;这里我们先是定义了一个整型常量UPDATE_TEXT，用于表示更新TextView这个动作。然后新增一个Handler对象，并重写父类的handleMessage()方法，在这里对具体的Message进行处理。如果发现Message的what字段的值等于UPDATE_TEXT，就将TextView显示的内容改成Nice to meet you。  
&emsp;&emsp;下面再来看一下Change Text按钮的点击事件中的代码。可以看到，这次我们并没有子线程里直接进行UI操作，而是创建了一个Message(android.os.Message)对象，并将它的what字段的值指定为UPDATE_TEXT，然后调用Handler的sendMessage()方法将这条Message发送出去。很快，Handler就会收到这条Message，并在handleMessage()方法中对它进行处理。注意此时UI操作。接下来对Message携带的what字段的值进行判断，如果等于UPDATE_TEXT，就将TextView显示的内容改成Nice to meet you。  
&emsp;&emsp;现在重新运行程序，可以看到屏幕的正中央显示着Hello world。如果等于UPDATE_TEXT，就将TextView显示的内容改成Nice to meet you。

![img_1.png](img_1.png)  

&emsp;&emsp;这样你就已经掌握了Android异步消息处理的基本用法，使用这种机制就可以出色地解决掉在子线程中更新UI问题。不过恐怕你对它的工作原理还不是很清楚，下面我们就来分析一下Android异步消息处理机制到底是如何工作的。  

### 10.2.3 解析异步消息处理机制  

&emsp;&emsp;Android中的异步消息处理主要由4个部分组成：Message、Handler、MessageQueue和Looper。其中Message和Handler在上一小节中我们已经接触过了，而MessageQueue和Looper对于你来说还是全新的概念，下面我就对这4个部分进行一下简单的介绍。  

1. Message  

&emsp;&emsp;Message是线程之间传递的消息，它可以在内部携带少量的信息，用于在不同线程之间交换数据。上一个小节中我们使用到了Message的what字段，除此之外还可以使用arg1和arg2字段来携带一些整型数据，使用obj字段携带一个Object对象。
    
2. Handler  

&emsp;&emsp;Handler顾名思义也就是处理者的意思，它主要是用于发送和处理消息的。发送消息一般是使用Han的sendMessage()方法，而发出的消息经过一系列地辗转处理后，最终会传递到Handler的handleMessage()方法中。  

3. MessageQueue  

&emsp;&emsp;MessageQueue是消息队列的意思，它主要用于存放通过Handler发送的消息。这部分消息会一直存在于消息队列中，等待被处理。每个线程中会有一个MessageQueue对象。  

4. Looper  

&emsp;&emsp;Looper是每个线程中的MessageQueue的管家，调用Looper的loop()方法后，就会进入到一个无限循环当中，然后每当发现MessageQueue中存在一条消息，就会将它取出，并传递到Handler的handleMessage()方法中。每个线程也只会有一个Looper对象。

&emsp;&emsp;了解了Message、Handler、MessageQueue以及Looper的基本概念后，我们再来把异步消息处理的整个流程梳理一遍。首先需要在主线程当中创建一个Handler对象，并重新handleMessage()方法。然后当子线程中需要进行UI操作时，就创建一个Message对象，并通过Handler将这条消息发送出去。之后这条消息就会被添加到MessageQueue的队列中等待被处理，而Looper则会一直尝试从MessageQueue中取出待处理的消息，最后分发回Handler的handleMessage()方法中。由于Handler是在主线程中创建的，所以此时handlerMessage()方法的代码也会在主线程中运行，于是我们在这里就可以安心地进行UI操作了。整个异步消息处理机制的流程示意如图所示：  

![img_2.png](img_2.png)  

&emsp;&emsp;一条Message经过这样一个流程的辗转调用，也就从子线程进入到了主线程，从不能更新UI变成可以更新UI，整个异步消息处理的核心思想也就是如此。  
&emsp;&emsp;而我们在第9章中使用到的runOnUiThread()方法其实就是一个异步消息处理机制的接口封装，它虽然表面上看起来用法更为简单，但其实背后的实现原题如上图的描述是一模一样。  

### 10.2.4 使用AsyncTask  

&emsp;&emsp;不过为了更加方便我们在子线程中对UI进行操作，Android还提供了另外一些好用的工具，比如AsyncTask。借助AsyncTask，即使你对异步消息处理机制完全不了解，也可以十分简单地从子线程切换到主线程，当然，AsyncTask背后的实现原理也是基于异步消息处理机制的，只是Android帮我们做了很好的封装而已。  
&emsp;&emsp;首先来看一下AsyncTask的基本用法，由于AsyncTask是一个抽象类，所以如果我们想要使用它，就必须要创建一个子类去继承它。在继承时我们可以为AsyncTask类指定3个泛型参数，这3个参数的用途如下。  
- Params。在执行AsyncTask时需要传入的参数，可用于在后台任务中使用。  
- Progress。后台任务执行时，如果需要在界面上显示当前的进度，则使用这里指定的泛型作为进度单位。  
- Result。当任务执行完毕后，如果需要对结果进行返回，则使用这里指定的泛型作为返回值类型。  

&emsp;&emsp;因此，一个最简单的自定义AsyncTask就可以写成如下方式：  

```
class DownloadTask extends AsyncTask<Void,Integer,Boolean>{

}
```

&emsp;&emsp;这里我们把AsyncTask的第一个泛型参数指定为Void，表示在执行AsyncTask的时候不需要传入参数给后台任务。第二个泛型参数指定为Integer，表示使用整型数据来作为进度显示单位。第三个泛型参数指定为Boolean，则表示使用布尔型数据来反馈执行结果。  
&emsp;&emsp;当然，目前我们定义的DownloadTask还是一个空任务，并不能进行任何实际的操作，我们还需要去重写AsyncTask中的几个方法才能完成对任务的定制。经常需要去重写的方法有以下4个。  

1. onPreExecute()

&emsp;&emsp;这个方法会在后台任务开始执行之前调用，用于进行一些界面上的初始化操作，比如显示一个进度条对话框等。  

2. doInBackground(Params...)

&emsp;&emsp;这个方法中的所有代码都会在子线程中运行，我们应该在这里去处理所有的耗时任务。任务一旦完成就可以通过return语句来将任务的执行结果返回，如果AsyncTask的第三个泛型参数指定的是Void，就可以不返回任务结果。注意，在这个方法中不可以进行UI操作的，如果需要更新UI元素，比如说反馈当前任务的执行进度，可以调用publishProgress(Progress...)方法来完成。  

3. onProgressUpdate(Progress...)

&emsp;&emsp;当后台任务中调用了publishProgress(Progress...)方法后，onProgressUpdate(Progress...)方法就会很快被调用，该方法中携带的参数就是在后天任务中传递过来的。在这个方法中可以对UI进行操作，利用参数中的数值就可以对界面元素进行相应的更新。  

4. onPostExecute(Result)

&emsp;&emsp;当后台任务执行完毕并通过return语句进行返回时，这个方法很快会被调用。返回的数据会作为参数传递到此方法中，可以利用参数中的数据来进行一些UI操作，比如说提醒任务执行的结果，以及关闭掉进度条对话框等。  
&emsp;&emsp;因此，一个比较完整的自定义AsyncTask就可以写成如下方式：  

```
class DownloadTask extends AsyncTask<Void,Integer,Boolean>{
    @Override
    protexted void onPreExecute(){
        progressDialog.show();//显示进度对话框
    }
    
    @Override
    protected Boolean doInBackground(Void...  params){
    try {
        while(true){
            int downloadPercent = doDownload();//这是一个虚构的方法
            publishProgress(downloadPercent);
            if  (downloadPercent >= 100){
                break;
                }
        }
    } catch (Exception e){
        return false;
        }
        return true;
    }
    
    @Override
    protexted void onProgressUpdate(Integer... values){
        //在这里更新下载进度
        progressDialog.setMessage("Downloaded" + values[0] + "%");
    }
    
    @Override void onPostExecute(Boolean result){
         progressDialog.dismiss();//关闭进度对话框
        //在这里提示下载结果
        if (result){
            Toast.makeText(context,"Download succeeded",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context,"Download failed",Toast.LENGTH_SHORT).show();
        }
    }
}
```
&emsp;&emsp;在这个DownloadTask中，我们在doInBackground()方法里去执行具体的下载任务。这个方法里的代码都是在子线程中运行的，因而不会影响到主线程的运行。注意这里虚构了一个doDownload()方法，这个方法用于计算当前的下载进度并返回，我们假设这个方法已经存在了。在得到了当前的下载进度后，下面该考虑如何计算当前的下载进度并返回，我们假设这个方法已经存在了。在得到了当前的下载进度后，下面该考虑如何把它显示到界面上了，由于doInBackground()方法是在子线程中运行的，这里肯定不能进行UI操作，所以我们可以调用publishProgress()方法并将当前的下载进度传进来，这样onProgressUpdate()方法就会很快被调用，在这里就可以进行UI操作了。  
&emsp;&emsp;当下载完成后，doInBackground()方法会返回一个布尔型变量，这样onPostExecute()方法就会很快被调用，这个方法也是在主线程中运行的。然后在这里我们会根据下载的结果弹出相应的Toast提示，从而完成整个DownloadTask任务。  
&emsp;&emsp;简单来说，使用AsyncTask的诀窍就是，在doInBackground()方法中执行具体的耗时任务，在onProgressUpdate()方法中进行UI操作，在onPostExecute()方法中执行一些任务的收尾工作。  
&emsp;&emsp;如果想要启动这个任务，只需要编写以下代码即可：  

````
new DownloadTask().execute();
````
&emsp;&emsp;以上就是AsyncTask()的基本用法，怎么样，是不是感觉简单方便多了，我们并不需要去考虑什么异步消息处理机制，也不需要专门使用一个Handler来发送和接收消息，只需要调用一下publishProgress()方法，就可以轻松地从子线程切换到UI操作了。