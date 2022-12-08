# 第12章 最佳的UI体验——MaterialDesign实战  
&emsp;&emsp;其实长久以来，大多数人都认为Android系统的UI并不算美观，至少没有iOS系统的美观。以至于很多的IT公司在进行应用界面设计的时候，为了保证双平台的统一性，强制性要求Android端的界面风格必须和iOS端一致。这种情况在现实工作当中实在太常见了，虽然我认为这是非常不合理。因为对于一般用户来说，他们不太可能会在两个操作系统上分别去使用同一个应用，但是却必须在同一个操作系统上使用不同的应用。但是却必定会操作系统上使用不同的应用。因此，同一个操作系统中各个应用之间的界面统一性要远比一个应用在双平台的界面统一性重要得多，只有这样，才能给使用者带来更好的用户体验。  
&emsp;&emsp;但问题在于，Android标准的界面设计风格并不是特别被大众所接受，很多公司都觉得自己完全可以设计出更加好看的界面，从而导致Android平台的界面风格长期难以得到统一。为了解决这个问题，谷歌也是祭出了杀手锏，在2014年Google I/O大会上重磅推出亮度一套全新的界面设计语言——Material Design。  

### 12.1 什么是 Material Design  
&emsp;&emsp;Material Design 是由谷歌的设计工程师基于传统优秀的设计原则，结合丰富的创意和科学技术所发明的一套全新的界面设计语言，包含了视觉、运动、互动效果等特性。那么谷歌凭什么认为Material Design就能解决Android平台界面风格不统一的问题呢？一言以蔽之，好看！  
&emsp;&emsp;没错。这次谷歌在界面在界面设计上确实是下足了功夫，很多媒体评论，Material Design的出现使得Android首次在UI方面超越了iOS。按照正常的思维来想，如果各个公司都无法设计出比Material Design更加出色的界面风格，那么它们就应该理所应当地使用Material Design来设计界面，从而也就能解决Android平台界面风格不统一的问题了。  
&emsp;&emsp;为了做出表率，谷歌从Android 5.0 开始，就将所有内置的应用都使用了Material Design风格来进行设计。并且在2015年的Google I/O大会上退出了一个Design Support库，这个库将Material Design中最具有代表性的一些空阿金和效果进行了封装，使得开发者在即使不了解Material Design的情况下也能非常轻松地将自己的应用Material化。b本章中我们就将对Design Support这个库进行深入学习，并且配合一些其他的控件来完成一个优秀的Material Design 应用。新建一个MaterialTest应用。

### 12.2 Toolbar 

&emsp;&emsp;Toolbar将会是我们接触的第一个Material控件。虽然对于Toolbar你暂时应该还是比较陌生的，但是对于它的另一个相关控件ActionBar，你应该有点熟悉了。  
&emsp;&emsp;回忆一下，我们曾经在3.4.1小节为了使用一个自定义的标题栏，而把系统原生的ActionBar影藏掉。没错，每个活动最顶部的那个标题栏其实就是ActionBar，之前我们编写的所有程序里一直都有ActionBar的身影。  
&emsp;&emsp;不过ActionBar由于其设计问题，被限定只能位于活动的顶部，从而不能实现一些Material Design的效果，因此官方现在已经不再使用ActionBar。现在直接讲解更加推荐使用的Toolbar。  
&emsp;&emsp;Toolbar的强大之处在于，它不仅继承了ActionBar的所有功能，而且灵活性很高，可以配合其他控件来完成一些Material Design的效果，下面我们就来具体学习一下。  
&emsp;&emsp;首先你要知道，任何一个新建的项目。默认都是会显示ActionBar的，这个想必你已经见识过太多次了。那么这个ActionBar到底是从哪里来的呢？其实这时根据项目中指定的主题来显示的，打开AndroidManifest.xml文件看一下，如下所示：  

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
          package="com.zj970.materialtest">

    <application
            android:allowBackup="true"
            android:icon="@mipmap/ic_launcher"
            android:label="@string/app_name"
            android:roundIcon="@mipmap/ic_launcher_round"
            android:supportsRtl="true"
            android:theme="@style/Theme.MaterialDesign">
        <activity android:name=".MainActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN"/>

                <category android:name="android.intent.category.LAUNCHER"/>
            </intent-filter>
        </activity>
    </application>

</manifest>
```
&emsp;&emsp;可以看到，这里使用android:theme属性指定了一个Theme.MaterialDesign的主题，这个是最新的，之前是指定了一个AppTheme的主题。这个主题在哪里定义的呢？打开res/values/themes.xml文件，如下所示：  

```xml
<resources xmlns:tools="http://schemas.android.com/tools">
    <!-- Base application theme. -->
    <style name="Theme.MaterialDesign" parent="Theme.MaterialComponents.DayNight.DarkActionBar">
        <!-- Primary brand color. -->
        <item name="colorPrimary">@color/purple_500</item>
        <item name="colorPrimaryVariant">@color/purple_700</item>
        <item name="colorOnPrimary">@color/white</item>
        <!-- Secondary brand color. -->
        <item name="colorSecondary">@color/teal_200</item>
        <item name="colorSecondaryVariant">@color/teal_700</item>
        <item name="colorOnSecondary">@color/black</item>
        <!-- Status bar color. -->
        <item name="android:statusBarColor" tools:targetApi="l">?attr/colorPrimaryVariant</item>
        <!-- Customize your theme here. -->
    </style>
</resources>
```
&emsp;&emsp;之前的老版本这里会定义一个叫AppTheme的主题，然后指定它的parent主题是Theme.AppCompat.Light.DarkActionBar。这个DarkActionBar是一个深色的ActionBar主题，我们之前所有的项目中自带的ActionBar就是因为指定了这个主题才出现的。同样的，在这里，也是因为指定了DarkActionBar才出现。  
&emsp;&emsp;而现在我们准备使用Toolbar来替代ActionBar，因此需要指定一个不带ActionBar的主题，通常有Theme.AppCompat.NoActionBar和Theme.AppCompat.Light.NoActionBar这两种主题可选。其中Theme.AppCompat.NoActionBar表示深色主题，它会将界面的主体颜色设成深色，陪衬颜色设成淡色。而Theme.AppCompat.Light.NoActionBar表示淡色主题，它会将界面的主题颜色设成淡色，陪衬颜色设成深色。这里我们就修改成：    

```
<style name="Theme.MaterialDesign" parent="Theme.MaterialComponents.DayNight.NoActionBar">
......
</style>
```
&emsp;&emsp;然后观察一下主题中的属性重写，这里重写了colorPrimary、colorPrimaryDark和colorAccent这3个属性的颜色。通过一张图来理解一下：  
![img.png](img.png)  

&emsp;&emsp;可以看到，每个属性所指的颜色位置直接一目了然。除了上述属性之外，我们还可以通过textColorPrimary、windowBackground和navigationBarColor等属性来控制更多位置的颜色。不过维度colorAccent这个属性比较难理解，它不只是用来指定一个按钮的颜色，而是更多表达了一个强调的意思，比如一些控件的选中状态也会使用colorAccent的颜色。  
&emsp;&emsp;现在我们已经将ActionBar隐藏起来了，那么接下来看一看如何使用Toolbar；来替代ActionBar。修改activity_main.xml中的代码：  

```xml
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:tools="http://schemas.android.com/tools"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        tools:context=".MainActivity">
    <androidx.appcompat.widget.Toolbar
            android:id="@+id/toolbar"
            android:layout_width="match_parent"
            android:layout_height="?android:attr/actionBarSize"
            android:background="?android:attr/colorPrimary"
            android:theme="@style/ThemeOverlay.AppCompat.ActionBar"
            app:popupTheme="@style/ThemeOverlay.AppCompat.Light"/>
</FrameLayout>
```
&emsp;&emsp;虽然这段代码不长，但是里面着实有不少的技术点是需要我们去仔细琢磨一下的。首先看一下第5行，这里使用了xmlns:app指定了一个新的命名空间。思考一下，正是由于每个布局文件都会使用xmlns:android来指定一个命名空间，因此我们才能一直使用android:id、android:layout_width等写法，那么这里指定了一个xmlns:app，也就是说现在可以使用app:attribute这样的写法了。但是为什么这里要指定一个xmlns:app的命名空间呢？这是由于Material Design是在Android 5.0系统中才出现的，而很多的Material属性在5.0之前的系统中是不存在的，为了兼容之前的老系统，我们就不能使用android:attribute这样的写法了，而是应该使用app:attribute。  
&emsp;&emsp;接下来定义了一个Toolbar控件，这里我们给Toolbar指定了一个id，将它的宽度设置了match_parent，高度设置为了actionBar的高度，背景色设置为了colorPrimary。不过下面的部分稍微有点难理解了，由于我们刚才在theme.xml中将程序的主题指定成了淡色主题，因此Toolbar现在也是淡色主题，而Toolbar上面的各种元素就会自动使用深色系，这是为了和主体颜色区别开。但是这个效果看起来就会很差，之前使用ActionBar时文字都是白色的，现在变成黑色的会很难看。那么为了能让Toolbar单独使用深色主题，这里我们使用android:theme属性，将Toolbar的主题指定成了ThemeOverlay.AppCompat.Dark.ActionBar。但是这样指定完了之后又会出现新的问题，如果ToolBar中有菜单按钮（我们在2.2.5小节z中学过），那么弹出的菜单项也会变成深色 主题，这样就再次变得十分难看，于是这里使用了app:popupTheme这个属性是在Android 5.0 系统中新增的，我们使用app:popupTheme的话就可以兼容Android 5.0 以下的系统了。接下来修改MainActivity，代码如下所示：  

```java
package com.zj970.materialtest;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
}
```
&emsp;&emsp;这里关键的代码只有两句，首先通过findViewById()得到Toolbar的实例，然后调用setSupportActionBar()方法并将Toolbar的实例，这样我们就做到既做到使用ToolBar又让它的外观与功能与ActionBar一致了。运行效果如下所示：  
![img_1.png](img_1.png)

&emsp;&emsp;这个标题栏我们再熟悉不过了，虽然看上去和之前的标题栏没什么两样，但其实它已经是Toolbar而不是ActionBar了。因此它现在也具备了实现Material Design效果的能力，这个我们在后面就会学到。接下来我们再学习一些Toolbar比较常用的功能，比如修改标题栏上显示的文字内容。这段文字内容是在AndroidManifest.xml中指定的，如下所示：  

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
          package="com.zj970.materialtest">

    <application
            android:allowBackup="true"
            android:icon="@mipmap/ic_launcher"
            android:label="@string/app_name"
            android:roundIcon="@mipmap/ic_launcher_round"
            android:supportsRtl="true"
            android:theme="@style/Theme.MaterialDesign">
        <activity android:name=".MainActivity"
                  android:label="Fruits">
            <intent-filter>
                <action android:name="android.intent.action.MAIN"/>

                <category android:name="android.intent.category.LAUNCHER"/>
            </intent-filter>
        </activity>
    </application>

</manifest>
```

&emsp;&emsp;这里给activity增加了一个android:label属性，用于指定在Toolbar中显示的文字内容，如果没有指定的话，会默认使用application中指定的label内容，也就是我们的应用名称。不过只有一个标题的Toolbar看起来太单调了，我们还可以再添加一些action按钮来让Toolbar更加丰富一些，现在右击res目录->New->Directory，创建一个menu文件夹。然后右击menu文件夹->New->Menu resource file ，创建一个toolbar.xml文件，代码如图所示：

```xml
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android" xmlns:app="http://schemas.android.com/apk/res-auto">
    <item android:id="@+id/backup" android:icon="@drawable/ic_backup" android:title="Backup" app:showAsAction="always"/>
    <item android:id="@+id/delete" android:icon="@drawable/ic_delete" android:title="Delete" app:showAsAction="always"/>
    <item android:id="@+id/settings" android:icon="@drawable/ic_settings" android:title="Settings" app:showAsAction="always"/>
</menu>
```
&emsp;&emsp;接着使用app:showAsAction来指定按钮的显示位置，之所以这里再次使用了app命名空间，同样是为了能够兼容低版本的系统。showAsAction主要有以下几种值可选：always表示永远显示在Toolbar中，如果屏幕空间不够则不显示；iFRoom表示屏幕空间足够的情况下显示在Toolbar中，不够的话就显示在菜单当中；never则表示永远显示在菜单当中。注意，Toolbar中的action按钮只会显示图标，菜单中的action按钮只会显示文字。接下来的做法和2.2.5小节中的完全一致了，修改MainActivity 中的代码：  

```java
package com.zj970.materialtest;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.backup:
                Toast.makeText(this, "You clicked Backup", Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete:
                Toast.makeText(this, "You clicked Delete", Toast.LENGTH_SHORT).show();
                break;
            case R.id.settings:
                Toast.makeText(this, "You clicked Settings", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return true;
    }
}
```
&emsp;&emsp;非常简单，我们在onCreateOptionsMenu()方法中加载了toolbar.xml这个菜单文件，然后在onOptionsItemSelected()方法中处理各个按钮的点击事件。现在重新运行一下程序，效果如下所示：
![img_3.png](img_3.png)
&emsp;&emsp;可以看到，Toolbar上面现在出现了两个action按钮，这时因为Backup按钮指定的显示位置是always，Delete按钮指定的显示位置是ifRoom，而现在屏幕控件很充足，因此两个按钮都会显示在Toolbar中。另外一个Settings按钮由于指定的显示位置never，所以不会显示在Toolbar中，点击一下最右边的菜单按钮来展开菜单项，你就能找到Settings按钮了。另外这些action按钮都是可以响应点击事件的。
