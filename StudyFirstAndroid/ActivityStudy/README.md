# 第2章 先从看得到的入手——探究活动  
## 2.1 活动是什么  
&emsp;&emsp;活动(Activity)是最容易吸引用户的地方，它是一种可以包含用户界面的组件，主要用于和用户进行交互。一个应用程序中可以包含零个或多个活动，但不包含任何活动的应用程序很少见，谁也不想让自己的应用永远无法被用户看到吧？  

## 2.2 活动的基本用法  
&emsp;&emsp;到现在为止，你还没有手动创建过活动呢。因为上一章中的HelloWordActivity是Android Studio帮我们自动创建的。手动创建活动可以加深我们的理解，因此现在是时候应该自己动手了。  
&emsp;&emsp;新建一个Android项目ActivityTest，但是我们不再选择Empty Activity这个选项，而是选择Add No Activity，因为这次我们准备手动创建活动。  
### 2.2.1 手动创建活动  
&emsp;&emsp;项目创建成功后，我们仍然会默认使用Android模式的项目结构，这里我们手动修改成Project模式，本书中后面的所有项目都要这样修改，以后就不再赘述了。目前ActivityTest项目中虽然还是自动生成很多文件，现在右击包->New->Activity->Empty Activity，会弹出一个活动的对话框，我们将活动命名为FirstActivity，并且不要勾选Generate Layout File 和 Launcher Activity这两个选项，勾选Generate Layout File 表示会自动为FirstActivity创建一个对应的布局文件，勾选LauncherActivity表示会自动将FirstActivity设置成当前项目的主活动，这里由于你是第一次手动创建活动，这些自动生成的东西暂时都不要勾选，下面我们将会一个个手动来完成。勾选BackwardsCompatibility表示会为项目启用向下兼容的模式，这个选项要勾上。点击Finish完成构建。  
&emsp;&emsp;你需要知道，项目中的任何活动都应该重写Activity的onCreate()方法，而目前我们的FirstActivity中已经重写了这个方法，这是由Android Studio自动帮我们完成的，代码如下所示：  
```
public class FirstActivity extends AppCompatActivity{
    @Override 
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }
}
```
&emsp;&emsp;可以看到，onCreate()方法非常简单，就是调用了父类的onCreate()方法。当然这只是默认的实现，后面我们还需要在里面加入很多自己的逻辑。  
### 2.2.2 创建和加载布局  
&emsp;&emsp;前面我们说过，Android 程序的设计讲究逻辑和试图分离，最好每一个活动都能对应一个布局，布局就是用来显示界面内容的，因此我们现在就来手动创建一个布局文件。  
&emsp;&emsp;右击app/src/main/res目录->New->Directory，会弹出一个新建目录的窗口，这里先创建一个名为layout Source file，又会弹出一个新建布局资源窗口，我们将这个布局文件命名为first_layout，根元素就默认选择为LinearLayout。点击ok完成布局的创建，会出现Android Studio为我们提供的可视化布局编辑器，你可以在屏幕的中央区域预览当前的布局。在窗口的最下方有两个切换卡，左边是Design，右边是Text。Design是当前的可视化布局编辑器，在这里你不仅仅可以预览当前的布局，还可以通过拖放的方式编辑布局。而Text则是通过XML文件的方式来编辑布局的，现在点击一下Text切换卡，可以看到如下代码：  
```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical"
     android:layout_width="match_parent" 
     android:layout_height="match_parent">
 
 </LinearLayout>
```
&emsp;&emsp;由于我们刚才在创建布局文件时选择了LinearLayout作为根元素，因此现在布局文件中已经有了一个LinearLayout元素了。那我们现在对这个布局稍稍做编辑，添加一个按钮，如下所示：

```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical"
     android:layout_width="match_parent" 
     android:layout_height="match_parent">
    <Button
        adnrooid:id="@+id/button_1"
        android:layout_width="match_parent"
        android:layout_heght="wrap_content"
        adnroid:text="Button 1"/>
 </LinearLayout>
```
&emsp;&emsp;这里添加了一个Button元素，并在Button元素的内部增加了几个属性。android:id是给当前的元素定义一个唯一标识符，之后可以在代码中对这个元素进行操作。你可能对@+id/button_1这种语法感到陌生，但如果把加号去掉，变成@id/button_1，这样你就会觉得有些熟悉了吧，这不就是在XML中引用资源的语法吗？只不过是把string替换成了id。使得，如果你需要在XML中引用一个id，就使用@id/id_name这种语法。随后android:layout_width指定了当前元素的宽度和父元素一样宽。android:layout_hegiht指定了当前元素的高度，这里使用wrap_content表示当前元素的高度只要能刚好包含里面的内容就行。android:text指定了元素中显示的文字内容。  
&emsp;&emsp;通过预览窗口可以看到，按钮已经成功显示出来了，这样一个简单的布局就编写完成了，那么接下来我们要做的，就是在活动中加载这个布局。重新回到FirstActivity，在onCreate()方法中加入如下代码：  
```java
public class FirstActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_layout);
    }
    
}
```
&emsp;&emsp;可以看到，这里调用了setContentView()方法来给当前的活动加载一个布局，而在setContentView()方法中，我们一般都会传入一个布局文件的id。在第一章介绍项目资源的时候曾经提到过，项目中添加的任何资源都会在R文件中生成一个相应的资源id，因此我们刚才创建的first_layout.xml布局的id现在应该是已经添加到R文件中了。在代码中引用布局文件的方法你也已经学过了，只需要将这个值传入setContentView()方法即可。  

### 2.2.3 在AndroidManifest文件中注册
&emsp;&emsp;别忘了在上一章我们学过，所有的活动都要在AndroidManifest.xml中进行注册才能生效，而实际上FirstActivity已经在AndroidManifest.xml中注册过了，我们打开app/src/main/AndroidManifest.xml，代码如下所示：  
```xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
          xmlns:tools="http://schemas.android.com/tools" package="com.zj970.activitytest">
    <application
            android:allowBackup="true"
            android:icon="@mipmap/ic_launcher"
            android:label="@string/app_name"
            android:roundIcon="@mipmap/ic_launcher_round"
            android:supportsRtl="true"
            android:theme="@style/Theme.ActivityTest">
        <activity android:name=".FirstActivity">
            
        </activity>
    </application>
</manifest>
```
&emsp;&emsp;可以看到，活动的注册声明要放在<application>标签内，这里是通过<activity>标签来对活动进行注册的。那么又是谁帮我们自动完成了FirstActivity的注册呢？当然是Android Studio了，之前在使用Eclipse创建活动或其他系统组件时，很多人都会忘记去AndroidManifest.xml中注册一下，从而导致程序运行崩溃，很显然Android Studio在这方面做得更加人性化。  
&emsp;&emsp;在<activity>标签中我们使用了android:name来指定具体注册哪一个活动，那么这里填入的.FirstActivity是什么意思呢？其实这不过就是就是全部包名的缩写。由于在最外层的<manifest>标签中已经通过package属性执行了程序的包名，因此这里直接使用.FirstActivity就足够了。  
&emsp;&emsp;不过，仅仅是这样注册了活动，我们的程序仍然是不能运行的，因为还没有为程序配置主活动，也就是说，当程序运行起来的时候，不知道要首先启动哪个活动。配置主活动的方法其实在第1章中已经介绍过了，就是在<activity>标签的内部加入<intent-filter>标签，并在这个标签里添加<action android:name="android.intent.action.MAIN"/>和<category android:name="android.intent.category.LAUNCHER"/>这两句即可。  
&emsp;&emsp;除此之外，我们还可以使用Android:label指定活动中标题栏的内容，标题栏是显示在活动最顶部的，待会儿运行的时候你就会看到。需要注意的是，给主活动指定的label不仅会成为标题栏中的内容，还会成为启动器(Launcher)中应用程序显示的名称。