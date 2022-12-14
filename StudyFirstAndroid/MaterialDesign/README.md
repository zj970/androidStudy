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

## 12.3 滑动菜单  
&emsp;&emsp;滑动菜单可以说是Material Design中最常见的效果之一了，在许多著名的应用（如Gmail、Google+等）中，都有滑动菜单的功能。虽说这个功能看上去好像挺复杂的，不过借助谷歌提供的各种工具，我们可以很轻松地实现非常炫酷的滑动菜单效果。  
### 12.3.1 DrawerLayout  
&emsp;&emsp;所谓的滑动菜单就是将一些菜单选项隐藏起来，而不是放置在主屏幕上，然后可以通过滑动的方式将菜单显示出来。这种方式既节省了屏幕空间、又实现了非常好的动画效果，是Material Design中推荐的做法。  
&emsp;&emsp;不过如果我们全靠自己去实现上述功能的话，难度恐怕就很大了。幸运的是，谷歌提供了一个DrawerLayout控件，借助这个控件，实现滑动菜单简单又方便。  
&emsp;&emsp;先来简单介绍一下DrawerLayout的用法。首先它是一个布局，在布局中允许两个直接子控件，第一个子控件是主屏幕中显示的内容，第二个子控件是滑动菜单中显示的内容。因此，我们就可以对activity_main.xml中的代码做如下修改：  

```xml
<?xml version="1.0" encoding="utf-8"?>

<androidx.drawerlayout.widget.DrawerLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:tools="http://schemas.android.com/tools"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:layout_height="match_parent"
        android:layout_width="match_parent"
        tools:context=".MainActivity"
        android:id="@+id/drawer_layout">
    <FrameLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent">
        <androidx.appcompat.widget.Toolbar
                android:id="@+id/toolbar"
                android:layout_width="match_parent"
                android:layout_height="?android:attr/actionBarSize"
                android:background="?android:attr/colorPrimary"
                android:theme="@style/ThemeOverlay.AppCompat.ActionBar"
                app:popupTheme="@style/ThemeOverlay.AppCompat.Light"/>
    </FrameLayout>

    <TextView android:layout_width="match_parent"
              android:layout_height="match_parent"
              android:layout_gravity="start"
              android:textColor="This is menu"
              android:textSize="30sp"
              android:background="#FFF"/>
</androidx.drawerlayout.widget.DrawerLayout>

```
&emsp;&emsp;可以看到，这里最外层的控件使用了DrawerLayout，DrawerLayout中放置了两个子控件，第一个子控件是FrameLayout，用于作为主屏幕中显示的内容，当然里面还有我们刚刚定义的Toolbar。第二个子控件这里使用了一个TextView,用于作为滑动菜单中显示的内容，其实使用什么都可以，DrawerLayout并没有限制只能使用固定的控件。  
&emsp;&emsp;但是关于第二个子控件有一点需要注意，layout_gravity这个属性是必须指定的，因为我们需要告诉DrawerLayout滑动菜单是在屏幕的左边还是右边，指定left表示在滑动菜单在左边，指定right表示滑动菜单在右边。这里指定了start，表示会根据系统语言进行判断，如果系统语言是从左往右的，比如英语、汉语、滑动菜单就在左边，如果系统系统语言是右往左，比如阿拉伯语，滑动菜单就在右边。只需要改动这么多就可以了，现在重新运行一下程序，然后在屏幕的左侧边缘向右拖动，就可以让滑动菜单显示出来，如图所示： 

![img_2.png](img_2.png)

&emsp;&emsp;然后向左滑动菜单，或者点击一下菜单以外的区域，都可以让滑动菜单关闭，从而回到主界面。不论是展示还是隐藏滑动菜单，都是有非常流畅的动画过渡的。  
&emsp;&emsp;可以看到，我们只是稍微改动了一下布局文件，就能实现如此炫酷的效果，是不是觉得挺激动的。不过现在的滑动还是有点问题，因为只有在屏幕的左侧边缘进行拖动时才能将菜单拖出来，而很多用户可能根本就不知道有这个功能，那么该怎样提示它们呢？  
&emsp;&emsp;Material Design建议的做法是在Toolbar的最左边加入一个导航按钮，点击了按钮也会滑动菜单的内容展示出来。这样就相当于给用户提供两种打开滑动菜单的方式，防止一些用户不知道屏幕的左侧边缘是可以拖动的。然后修改MainActivity中的代码，如下所示：  

```java
package com.zj970.materialtest;

import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
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

&emsp;&emsp;这里我们并没有改动多少代码，首先调用findViewById()方法得到了DrawerLayout的实例，然后调用getSupportActionBar()方法得到了ActionBar的实例，虽然这个ActionBar的具体实现是由Toolbar来实现。接着调用ActionBar的setDisplayHomeAsUpEnabled()方法让导航按钮显示出来，又调用了setHomeAdUpIndicator()方法来设置一个导航按钮图标。实际上，Toolbar最左侧的这个按钮就叫做HomeAsUp按钮，它默认的图标是一个返回的箭头，含义是返回上一个活动。很明显，这里我们将它默认的样式和作用进行了修改。  
&emsp;&emsp;接下来在onOptionsItemSelected()方法中对HomeAsUp按钮的点击事件进行处理，HomeAsUp按钮的id永远都是android.R.id.home。然后调用DrawerLayout的openDrawer()方法将滑动菜单展示出来，注意openDrawer()方法要求传入一个Gravity参数，为了保证这里的行为和XML中定义的一致，我们传入了GravityCompat.START。现在运行一下程序：  
![img_4.png](img_4.png)

## 12.4 悬浮按钮和可交互提示  
&emsp;&emsp;立面设计是Material Design 中一条非常重要的设计思想，也就是说，按照Material Design的理念，应用程序的界面不仅仅只是一个平面，而应该是有立体效果的。在官方给出的示例中，最简单且最代表性的立面设计就是悬浮按钮了，这种按钮不属于主界面平面的一部分，而是位于另外一个纬度，因此就给人一种悬浮的感觉。  
&emsp;&emsp;本节中我们会对这个悬浮按钮的效果进行学习，另外还会学习一种可交互式的提示工具。关于提示工具，我们之前一直都是使用Toast，但是Toast只能用于告知用户某某事情已经发生了，用户却不能对此做出任何的响应，那么今天我们就将在这一方面进行扩展。  

### 12.4.1 FloatingActionButton  
&emsp;&emsp;FloatingActionButton是Design Support库中提供的一个控件，这个控件可以帮助我们比较轻松地实现悬浮按钮的效果。其实在之前图中，我们就已经预览过悬浮按钮是长什么样子的了，它默认会使用colorAccent来作为按钮的颜色，我们还可以通过给按钮指定一个图标来表明这个按钮的作用是什么。  
&emsp;&emsp;下面开始来具体实现。首先仍然需要提前准备好一个图标，然后修改activity_main.xml中的代码，如下所示：  

```xml
<?xml version="1.0" encoding="utf-8"?>

<androidx.drawerlayout.widget.DrawerLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:layout_height="match_parent"
        android:layout_width="match_parent"
        android:id="@+id/drawer_layout">
    <FrameLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent">
        <androidx.appcompat.widget.Toolbar
                android:id="@+id/toolbar"
                android:layout_width="match_parent"
                android:layout_height="?android:attr/actionBarSize"
                android:background="?android:attr/colorPrimary"
                android:theme="@style/ThemeOverlay.AppCompat.ActionBar"
                app:popupTheme="@style/ThemeOverlay.AppCompat.Light"/>
        <com.google.android.material.floatingactionbutton.FloatingActionButton
                android:id="@+id/fab"
                android:layout_gravity="bottom|end"
                android:layout_margin="16dp"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"/>
    </FrameLayout>

    <TextView android:layout_width="match_parent"
              android:layout_height="match_parent"
              android:layout_gravity="start"
              android:text="This is menu"
              android:textSize="30sp"
              android:background="#FFF"/>
</androidx.drawerlayout.widget.DrawerLayout>

```
&emsp;&emsp;可以看到，layout_width 和layout_height属性都指定成wrap_content，layout_gravity属性指定将这个控件放置于屏幕的右下角，其中end的工作原理和之前的start是一样的，即如果系统语言是从左往右，那么end就表示在右边。如果系统语言是右往左的，那么end就表示在左边。然后通过layout_margin属性给控件的四周留边距，紧贴着屏幕边缘肯定是不好看的，最后通过src属性给FloatingActionButton设置了一个图标。效果如下：  
![img_5.png](img_5.png)

&emsp;&emsp;一个漂亮的悬浮按钮就在屏幕的右下方出现了。  
&emsp;&emsp;如果你仔细观察的话，会发现这个悬浮按钮的下面还有一点阴影。其实这很好理解，因为FloatingActionButton是悬浮在当前界面上的，既然是悬浮，那么就理所应当会有投影，Design Support库连这种细节都帮我们考虑到了。  
&emsp;&emsp;说到悬浮，其实我们还可以指定FloatingActionButton的悬浮高度，如下所示：  


```
<com.google.android.material.floatingactionbutton.FloatingActionButton
                android:id="@+id/fab"
                android:layout_gravity="bottom|end"
                android:layout_margin="16dp"
                app:elevation="8dp"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"/>
```

&emsp;&emsp;这里使用app:elevation属性来给FloatingActionButton指定一个高度值，高度值越大，投影范围越大，但是投影效果越淡，高度值越小，投影范围越小，但是投影效果越浓。当然这些效果的差异其实并不怎么明显，我个人感觉使用默认的FloatingActionButton效果就已经足够了。接下来我们看一下FloatingActionButton是如何处理点击事件的，毕竟，一个按钮首先要能点击才有意义。修改MainActivity中的代码，如下所示：  

```java
package com.zj970.materialtest;

import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "FAB clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
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

&emsp;&emsp;如果你在期待FloatingActionButton会有什么特殊用法的话，那可能就要让你失望了，它和普通的Button其实没什么两样，都是调用setOnClickListener()方法来注册一个监听器，当点击按钮时，就会执行监听器中的onClick()方法，这里我们在onClick()方法中弹出一个Toast。现在重新运行一下程序，并点击FloatingActionButton，效果如图所示：  
![img_6.png](img_6.png)

### 12.4.2 Snackbar  
&emsp;&emsp;现在我们已经掌握了FloatingActionButton的基本用法，不过在上一小节处理点击事件的时候，仍然是使用Toast来作为提示工具。本节学习一下Design Support库提供的更加先进的提示工具——Snackbar。  
&emsp;&emsp;首先要明确，Snackbar并不是Toast的替代品， 它们两者之间有着不同的应用场景。Toast的作用是告诉用户现在发生了什么事情，但同时用户只能被动接收这个事情，因为没有什么办法能够让用户进行选择。而Snackbar则在这方面进行了扩展，它允许在提示当中加入可交互按钮，让用户点击按钮的时候可以执行一些额外的逻辑操作。打个比方，如果我们在执行删除操作的时候只弹出一个Toast提示，那么用户要是误删了某个重要数据的话肯定会非常抓狂，但是我们增加一个Undo按钮，就相当于给用户提供了一种弥补措施。从而大大降低了事故发生的概率，提升了用户体验。Snackbar的用法也非常简单，它和Toast是基本相似的，只不过可以额外增加一个按钮的点击事件。修改MainActivity中的代码，如下所示：  

```java
package com.zj970.materialtest;

import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v,"Data deleted",Snackbar.LENGTH_LONG)
                        .setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this, "FAB clicked", Toast.LENGTH_LONG).show();
                            }
                        }).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
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

&emsp;&emsp;可以看到，这里调用了Snackbar的make()方法来创建一个Snackbar对象，make()方法的第一参数需要传入一个View，只要是当前界面的任意一个View都可以，Snackbar会使用这个View来自动查找最外层的布局，用于展示Snackbar。第二个参数就是Snackbar中显示的内容，第三个参数是Snackbar显示的时长。这些和Toast都是类似的。  
&emsp;&emsp;接着这里又调用了一个setAction()方法来设置一个动作，从而让Snackbar不仅仅是一个提示，而是可以和用户进行交互的。简单起见，我们在动作按钮的点击事件里面弹出一个Toast提示。最后调用show()方法让Snackbar显示出来。现在重新运行一下程序，并点击悬浮按钮，效果如下所示：

![img_7.png](img_7.png)

&emsp;&emsp;可以看到，Snackbar从屏幕底部出现了，上面有我们所设置的提示文字，还有一个Undo按钮，按钮是可以点击的。过一段时间后Snackbar会自动从屏幕底部消失。不管是出现还是消失，Snackbar都是带有动画效果的，因此视觉体验也会比较好。不过你有没有发现一个bug，这个Snackbar竟然将我们的悬浮按钮给遮挡住了。虽说也不是什么重大的问题，因为Snackbar过一会儿就会自动消失，但这种用户体验总归是不友好的，有没有什么办法能解决一下呢？当然有，只需要借助CoordinatorLayout就可以轻松解决。

### 12.4.3 CoordinatorLayout 

&emsp;&emsp;CoordinatorLayout可以说是一个加强版的FrameLayout，这个布局也是由Design Support库提供的。它在普通情况下的作用和FrameLayout基本上一致，不过既然是Design Support库中提供的布局，那么就必然有一些Material Design的魔力了。  
&emsp;&emsp;事实上，CoordinatorLayout可以监听其所有子控件的各种事件，然后自动帮助我们做出最为合理的响应。举个简单的例子，刚才弹出的Snackbar提示将悬浮按钮遮挡住了，而如果我们能让CoordinatorLayout监听到Snackbar的弹出事件，那么它会自动将内部的FloatingActionButton向上偏移，从而确保不会被Snackbar遮挡住。至于CoordinatorLayout的使用也非常简单，我们只需要将原来的FrameLayout替换一下就可以了；了。修改activity_main.xml中的代码，如下所示：  

```xml
<?xml version="1.0" encoding="utf-8"?>

<androidx.drawerlayout.widget.DrawerLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:layout_height="match_parent"
        android:layout_width="match_parent"
        android:id="@+id/drawer_layout">
    <androidx.coordinatorlayout.widget.CoordinatorLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent">
        <androidx.appcompat.widget.Toolbar
                android:id="@+id/toolbar"
                android:layout_width="match_parent"
                android:layout_height="?android:attr/actionBarSize"
                android:background="?android:attr/colorPrimary"
                android:theme="@style/ThemeOverlay.AppCompat.ActionBar"
                app:popupTheme="@style/ThemeOverlay.AppCompat.Light"/>
        <com.google.android.material.floatingactionbutton.FloatingActionButton
                android:id="@+id/fab"
                android:layout_gravity="bottom|end"
                android:layout_margin="16dp"
                app:elevation="8dp"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"/>
    </androidx.coordinatorlayout.widget.CoordinatorLayout>

    <TextView android:layout_width="match_parent"
              android:layout_height="match_parent"
              android:layout_gravity="start"
              android:text="This is menu"
              android:textSize="30sp"
              android:background="#FFF"/>
</androidx.drawerlayout.widget.DrawerLayout>
```

&emsp;&emsp;由于CoordinatorLayout本身就是一个加强版的FrameLayout，因此这种替换不会有任何的副作用。现在重新运行一下程序，并点击悬浮按钮，效果如下所示：  

![img_8.png](img_8.png)

&emsp;&emsp;可以看到，悬浮按钮自动向上偏移了Snackbar的同等高度，从而确保不会被遮挡住，当Snackbar消失的时候，悬浮按钮会自动向下偏移回到原来位置。  
&emsp;&emsp;另外悬浮按钮的向上和向下偏移也是伴随这动画效果，且和Snackbar完全同步，整体效果看上去特别赏心悦目。  
&emsp;&emsp;不过我们回过头再思考一下，刚才说的是CoordinatorLayout可以监听其所有子控件的各种事件，但是Snackbar好像并不是CoordinatorLayout的子控件吧，为什么它却可以被监听到呢？  
&emsp;&emsp;其实道理很简单，还记得我们在Snackbar的make()方法中传入的第一个参数呢？这个参数就是用来指定Snackbar是基于哪个View来触发的，刚才我们传入的是FloatingActionButton本身，而FloatingActionButton是CoordinatorLayout中的子控件，因此这个事件就理所应当能被监听到了。你可以自己再做个实验，如果给Snackbar的make()方法传入一个DrawerLayout，那么Snackbar就会再次遮挡住悬浮按钮，因为DrawerLayout不是CoordinatorLayout中的子控件，CoordinatorLayout也就无法监听到Snackbar的弹出和隐藏事件了。接下来我们继续丰富MaterialTest项目，加入卡片式布局效果。  

## 12.5 卡片式布局  
&emsp;&emsp;虽然现在MaterialTest中已经应用了非常多的Material Design效果，不过你会发现，界面上最主要的一块区域还处于空白状态，这块区域通常都是用来放置应用的主体内容的。我准备使用一些精美的水果图片来填充这部分区域。  
&emsp;&emsp;那么为了要让水果图片也能Material化，本节中我们将会学习如何实现卡片式布局的效果。卡片式布局也是Materials Design中提出的一个新的概念，它可以让页面中的元素看起来就像在卡片中一样，并且还能拥有圆角和投影，下面我们就开始具体学习一下。  

### 12.5.1 CardView  
&emsp;&emsp;CardView是用于实现卡片式布局效果的重要控件，实际上CardView也是一个FrameLayout，只是额外提供了圆角和阴影等效果，看上去会有立体的感觉。我们先来看一下CardView的基本用法吧，其实非常简单，如下所示：  

```xml
    <androidx.cardview.widget.CardView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            app:cardCornerRadius="4dp"
            app:cardElevation="5dp">
        <TextView
                android:id="@+id/info_text"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"/>
    </androidx.cardview.widget.CardView>
```

&emsp;&emsp;这里定义了一个CardView布局，我们可以通过app:cardCornerRadius属性指定卡片圆角的弧度，数值越大，圆角的弧度也越大。另外还可以通过app:cardElevation属性指定卡片的高度，高度值越大，投影范围也越大，但是投影效果越淡，高度值越小，投影范围也越小，但是投影效果越浓，这一点和FloatingActionButton是一致的。然后我们在CardView布局中放置了一个TextView,那么这个TextView就会显示在一张卡片当中了，CardView的用法就是这么简单。  
&emsp;&emsp;但是我们显然不可能在如此宽阔的一块空白区域内只放置了一张卡片，为了能够充分利用屏幕的空间，这里用第3章中学到的知识，使用RecyclerView来填充MaterialTest项目的主界面部分。还记得之前实现过的水果列表效果吗？这次我们将升级一下，实现一个高配版的水果列表效果。  
&emsp;&emsp;然后由于我们还需要用到RecyclerView、CardView这几个控件，这里我使用的是Androidx库。可以多加一个Glide库依赖。Glide是一个超级强大的图片加载库，它不仅可以用于加载本地图片，还可以加载网络图片、GIF图片、甚至是本地视频。最重要的是Glide的用法非常简单，只需要一行代码就能轻松实现复杂的图片加载功能，因此这里我们准备用它来加载水果图片。Glide的项目主页地址是：https://github.com/bumptech/glide。接下来开始具体的代码实现，修改activity_main.xml中的代码，如下所示： 

```xml
<?xml version="1.0" encoding="utf-8"?>

<androidx.drawerlayout.widget.DrawerLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:layout_height="match_parent"
        android:layout_width="match_parent"
        android:id="@+id/drawer_layout">
    <androidx.coordinatorlayout.widget.CoordinatorLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent">
        <androidx.appcompat.widget.Toolbar
                android:id="@+id/toolbar"
                android:layout_width="match_parent"
                android:layout_height="?android:attr/actionBarSize"
                android:background="?android:attr/colorPrimary"
                android:theme="@style/ThemeOverlay.AppCompat.ActionBar"
                app:popupTheme="@style/ThemeOverlay.AppCompat.Light"/>
        <androidx.recyclerview.widget.RecyclerView
                android:id="@+id/recycler_view"
                android:layout_width="match_parent"
                android:layout_height="match_parent"/>
        <com.google.android.material.floatingactionbutton.FloatingActionButton
                android:id="@+id/fab"
                android:layout_gravity="bottom|end"
                android:layout_margin="16dp"
                app:elevation="8dp"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"/>
    </androidx.coordinatorlayout.widget.CoordinatorLayout>

    <TextView android:layout_width="match_parent"
              android:layout_height="match_parent"
              android:layout_gravity="start"
              android:text="This is menu"
              android:textSize="30sp"
              android:background="#FFF"/>
</androidx.drawerlayout.widget.DrawerLayout>

```

&emsp;&emsp;这里我们在CoordinatorLayout中添加了一个RecyclerView,，给它指定了一个id，然后将宽度和高度都设置为match_parent，这样RecyclerView也就占满了整个布局的空间。接着定义一个实体类，代码如下所示：

```java
package com.zj970.materialtest.entity;

/**
 * <p>
 * fruit_entity
 * </p>
 *
 * @author: zj970
 * @date: 2022/12/11
 */
public class Fruit {
    private String name;
    private int imageId;

    public Fruit(String name, int imageId) {
        this.name = name;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public int getImageId() {
        return imageId;
    }
}

```
&emsp;&emsp;Fruit类中只有两个字段，name表示水果对应图片的资源id。然后需要为RecyclerView的子项指定一个我们自定义的布局，在layout目录下新建一个fruit_item.xml，代码如下所示：  

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.cardview.widget.CardView xmlns:android="http://schemas.android.com/apk/res/android"
                                   xmlns:androidx="http://schemas.android.com/apk/res-auto"
                                   xmlns:app="http://schemas.android.com/tools"
                                   android:layout_margin="5dp"
                                   app:cardCornerRadius="4dp"
                                   android:layout_width="match_parent"
                                   android:layout_height="wrap_content" app:ignore="NamespaceTypo">
    <LinearLayout android:layout_width="match_parent"
                  android:layout_height="wrap_content"
                  android:orientation="vertical">
        <ImageView android:id="@+id/fruit_image" android:layout_width="match_parent" android:layout_height="100dp" android:scaleType="centerCrop"/>
        <TextView android:id="@+id/fruit_name" android:layout_width="wrap_content" android:layout_height="wrap_content" android:layout_gravity="center_horizontal" android:layout_margin="5dp" android:textSize="16sp"/>
    </LinearLayout>

</androidx.cardview.widget.CardView>
```
&emsp;&emsp;这里使用了CardView来作为子项的最外层布局，从而使得RecyclerView中的每个元素都是在卡片当中的。CardView由于是一个FrameLayout，因此它没有什么方便的定位方式，这里我们只好在CardView再嵌套一个LinearLayout，然后在LinearLayout中放置具体的内容。  
&emsp;&emsp;内容倒也没有什么特殊的地方，就是定义了一个ImageView用于显示水果的图片，又定义了一个TextView用于显示水果的名称，并让TextView在水平向上居中显示。注意在ImageView中我们使用了一个scaleType属性，这个属性可以指定图片的缩放模式。由于各种水果图片的长宽比例可能都不一致，为了让所有的图片都能填充整个ImageView，这里使用了centerCrop模式，它可以让图片保持原有比例填充满ImageView，并将超出屏幕的部分裁剪掉。接下来需要为RecyclerView准备一个适配器，新建FruitAdapter类，让这个适配器继承自RecyclerView.Adapter，并将泛型指定为FruitAdapter.ViewHolder，代码如下所示：  

```java
package com.zj970.materialtest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.zj970.materialtest.entity.Fruit;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author: zj970
 * @date: 2022/12/11
 */
public class FruitAdapter  extends RecyclerView.Adapter<FruitAdapter.ViewHolder>{
    private Context mContext;
    private List<Fruit> mFruitList;

    public FruitAdapter(List<Fruit> mFruitList) {
        this.mFruitList = mFruitList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_fruit, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Fruit fruit = mFruitList.get(position);
        holder.fruitName.setText(fruit.getName());
        Glide.with(mContext).load(fruit.getImageId()).into(holder.fruitImage);
    }

    @Override
    public int getItemCount() {
        return mFruitList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView fruitImage;
        TextView fruitName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            fruitImage = cardView.findViewById(R.id.fruit_image);
            fruitName = cardView.findViewById(R.id.fruit_name);
        }
    }
}

```
&emsp;&emsp;上述代码相信你一定很熟悉，和我们在第3章中编写的FruitAdapter几乎一模一样。唯一需要注意的是，在onBindViewHolder()方法中我们使用了Glide来加载水果图片。  
&emsp;&emsp;那么这里就顺便来看一下Glide的使用方法，其实并没有太多好讲的，因为Glide的用法实在太简单了。首先调用Glide.with()方法并传入一个Context、Activity或Fragment参数，然后调用into()方法将图片设置具体一个ImageView中就可以了。  
&emsp;&emsp;那么我们为什么要使用Glide而不是传统的设置图片方式呢？因为我们的水果图片像素都非常高，如果不进行压缩就直接展示的话，很容易就会引起内存溢出。而使用Glide就完全不需要担心这件事，因为Glide在内部做了许多复杂的逻辑操作，其中就包括了图片压缩，我们只需要安心按照Glide的标准用法去加载图片就可以了。这样我们就将RecyclerView的适配器也准备好了，最后修改MainActivity中的代码中的代码，如下所示：  

```java
package com.zj970.materialtest;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.zj970.materialtest.entity.Fruit;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private Fruit[] fruits = {new Fruit("Apple", R.drawable.apple), new Fruit("watermelon", R.drawable.watermelon),
            new Fruit("Orange", R.drawable.orange), new Fruit("Pear", R.drawable.pear),
            new Fruit("Strawberry", R.drawable.strawberry), new Fruit("Pineapple", R.drawable.pineapple),
            new Fruit("Banana", R.drawable.banana), new Fruit("Mango", R.drawable.mango),
            new Fruit("Grape", R.drawable.grape), new Fruit("cherry", R.drawable.cherry)};
    private List<Fruit> fruitList = new ArrayList<Fruit>();
    private FruitAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Data deleted", Snackbar.LENGTH_LONG)
                        .setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this, "FAB clicked", Toast.LENGTH_LONG).show();
                            }
                        }).show();
            }
        });
        initFruits();
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new FruitAdapter(fruitList);
        recyclerView.setAdapter(mAdapter);
    }

    private void initFruits() {
        fruitList.clear();
        for (int i = 0; i < fruits.length; i++) {
            Random random = new Random();
            int index = random.nextInt(fruits.length);
            fruitList.add(fruits[index]);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
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
&emsp;&emsp;在MainActivity中我们首先定义了一个数组，数组里面存放了很多个Fruit的实例，每个实例都代表着一个水果。然后在initFruits()方法中，先是清空了一下fruitList的数据，接着使用一个随机函数，从刚才定义的Fruit数组中随机挑选一个水果放入fruitList当中，这样每次打开程序看到的水果数据都会是不同的。另外，为了让界面上的数据多一些，这里使用了一个循环，随机挑选50个水果。  
&emsp;&emsp;之后的用法就是RecyclerView的标准用法了，不过这里使用了GridLayoutManager这种布局方式。在第三章中我们已经学过了LinearLayoutManager和StaggeredGridLayoutManager，现在终于将所有的布局方式都补齐了。GridLayoutManager的用法也没有什么特别之处，它的构建函数接收两个参数，第一个是Context，第二个是列数，现在重新运行一下程序，效果如下所示:  
![img_9.png](img_9.png)

&emsp;&emsp;可以看到，精美的水果图片成功展示出来。每个水果都是在一张单独的卡片当中，并且还拥有圆角和投影，是不是非常美观？另外，由于我们是使用随机的方式来获取水果数据的，因此界面上会有一些重复的水果出现，这属于正常现象。  
&emsp;&emsp;当你陶醉于当前精美的界面的时候，你是不是忽略了一个细节？我们的Toolbar不见了？仔细观察一下原来是被RecyclerView给挡住了。这个问题又该怎么解决呢？这就需要借助到另外一个工具了——AppBarLayout。  

### 12.5.2 AppBarLayout  
&emsp;&emsp;首先我们来分析一下为什么RecyclerView会把Toolbar给挡住了。其实并不难理解，由于RecyclerView和Toolbar都是放置在CoordinatorLayout中的，前面已经说过，CoordinatorLayout就是一个加强版的FrameLayout，那么FrameLayout中的所有控件在不进行明确定位的情况下，默认都会拜访在布局的左上角，从而也就产生了遮挡的现象。其实这已经不是你第一次遇到这种情况了，我们在3.3.3小节中学习FrameLayout的时候就早已见识过控件与控件之间遮挡的效果。  
&emsp;&emsp;既然已经找到了问题的原因，那么该如何解决呢？传统情况下，使用偏移是唯一的解决办法，即让RecyclerView向下偏移一个Toolbar的高度，从而保证不会遮挡住Toolbar。不过我们使用的并不是普通的FrameLayout，而是CoordinatorLayout，因此自然会有一些更加巧妙的解决方法。  
&emsp;&emsp;这里我准备使用Design Support库中提供的另外一个工具——AppBarLayout。AppBarLayout实际上是一个垂直方向的LinearLayout。它在内部做了很多滚动事件的封装，并应用了一些Material Design的设计理念。  
&emsp;&emsp;那么我们怎样使用AppbarLayout才能解决前面的覆盖问题呢？其实只需要两步就可以了，第一步将Toolbar嵌套到AppBarLayout中，第二步给RecyclerView指定一个布局行为。修改activity_main.xml中的代码，如下所示：  

```xml
<?xml version="1.0" encoding="utf-8"?>

<androidx.drawerlayout.widget.DrawerLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:layout_height="match_parent"
        android:layout_width="match_parent"
        android:id="@+id/drawer_layout">
    <androidx.coordinatorlayout.widget.CoordinatorLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent">
        <com.google.android.material.appbar.AppBarLayout android:layout_width="match_parent"
                                                         android:layout_height="wrap_content">

            <androidx.appcompat.widget.Toolbar
                    android:id="@+id/toolbar"
                    android:layout_width="match_parent"
                    android:layout_height="?android:attr/actionBarSize"
                    android:background="?android:attr/colorPrimary"
                    android:theme="@style/ThemeOverlay.AppCompat.ActionBar"
                    app:popupTheme="@style/ThemeOverlay.AppCompat.Light"/>
        </com.google.android.material.appbar.AppBarLayout>
        <androidx.recyclerview.widget.RecyclerView
                android:id="@+id/recycler_view"
                android:layout_width="match_parent"
                app:layout_behavior="@string/appbar_scrolling_view_behavior"
                android:layout_height="match_parent"/>
        <com.google.android.material.floatingactionbutton.FloatingActionButton
                android:id="@+id/fab"
                android:layout_gravity="bottom|end"
                android:layout_margin="16dp"
                app:elevation="8dp"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"/>
    </androidx.coordinatorlayout.widget.CoordinatorLayout>

    <TextView android:layout_width="match_parent"
              android:layout_height="match_parent"
              android:layout_gravity="start"
              android:text="This is menu"
              android:textSize="30sp"
              android:background="#FFF"/>
</androidx.drawerlayout.widget.DrawerLayout>

```

&emsp;&emsp;可以看到，布局文件并没有什么太大的变化。我们首先定义了一个AppBarLayout，并将Toolbar放置在了AppBarLayout里面，然后在RecyclerView中使用app:layout_behavior属性指定一个布局行为。其中appbar_scrolling_view_behavior这个字符串也是由Design Support库提供的。现在重新运行一下程序，你就会发现一切都正常了，如图所示：  

![img_10.png](img_10.png)  

&emsp;&emsp;虽说使用AppbarLayout已经成功解决了RecyclerView遮挡Toolbar的问题，但是刚才有提到过，说AppBarLayout中应用了Material Design的设计理念，好像从上面的例子完全体现不出来。事实上，当RecyclerView滚动的时候就已经将滚动事件都通知给AppBarLayout了，只是我们还没进行处理而已。那么下面就让我们来进一步优化，看看AppBarLayout到底能实现什么样的Material Design效果。  
&emsp;&emsp;当AppBarLayout接收到滚动事件的时候，它内部的子控件其实是可以指定如何去影响这些事件的，通过app:layout_scrollFlags 属性就能实现。修改activity_main.xml中的代码。如下所示：  

```xml
<?xml version="1.0" encoding="utf-8"?>

<androidx.drawerlayout.widget.DrawerLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:layout_height="match_parent"
        android:layout_width="match_parent"
        android:id="@+id/drawer_layout">
    <androidx.coordinatorlayout.widget.CoordinatorLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent">
        <com.google.android.material.appbar.AppBarLayout android:layout_width="match_parent"
                                                         android:layout_height="wrap_content">

            <androidx.appcompat.widget.Toolbar
                    android:id="@+id/toolbar"
                    android:layout_width="match_parent"
                    android:layout_height="?android:attr/actionBarSize"
                    android:background="?android:attr/colorPrimary"
                    android:theme="@style/ThemeOverlay.AppCompat.ActionBar"
                    app:layout_scrollFlags="scroll|enterAlways|snap"
                    app:popupTheme="@style/ThemeOverlay.AppCompat.Light"/>
        </com.google.android.material.appbar.AppBarLayout>
        <androidx.recyclerview.widget.RecyclerView
                android:id="@+id/recycler_view"
                android:layout_width="match_parent"
                app:layout_behavior="@string/appbar_scrolling_view_behavior"
                android:layout_height="match_parent"/>
        <com.google.android.material.floatingactionbutton.FloatingActionButton
                android:id="@+id/fab"
                android:layout_gravity="bottom|end"
                android:layout_margin="16dp"
                app:elevation="8dp"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"/>
    </androidx.coordinatorlayout.widget.CoordinatorLayout>

    <TextView android:layout_width="match_parent"
              android:layout_height="match_parent"
              android:layout_gravity="start"
              android:text="This is menu"
              android:textSize="30sp"
              android:background="#FFF"/>
</androidx.drawerlayout.widget.DrawerLayout>

```

&emsp;&emsp;这里在Toolbar中添加了一个app:layout_scrollFlags属性，并将这个属性的值指定成了scroll|enterAlways|snap。其中，scroll表示当RecyclerView向上滚动的时候，Toolbar会跟着一起向上滚动并实现影藏；enterAlways表示当RecyclerView向下滚动的时候，Toolbar会跟着一起向下滚动并重新显示。snap表示当Toolbar还没有完全隐藏或显示的时候，会根据当前滚动的距离，自动选择是隐藏还是显示。现在重新运行程序，并向上滚动，效果如下：  
![img_11.png](img_11.png)

&emsp;&emsp;可以看到，随着我们向上滚动RecyclerView，Toolbar竟然消失了，而向下滚动RecyclerView，Toolbar又会重新出现。这其实也是Material Design中的一项重要设计思想，因为当用户在向上滚动RecyclerView的时候，其注意力肯定是在RecyclerView的内容上面的，这个时候如果Toolbar还占据着屏幕空间，就会在一定程度上影响用户的阅读体验，而将Toolbar隐藏则可以让阅读体验达到最佳状态。当用户需要操作Toolbar上的功能时，只需要轻微向下滚动，Toolbar就会重新出现。这种设计方式，既保证了用户的最佳阅读效果，又不影响任何功能上的操作，Material Design考虑得就是这么细致入微。  
&emsp;&emsp;当然了，像这种功能，如果是使用ActionBar的话，那就完全不可能实现了，Toolbar的出现为我们提供了更多的可能。  

## 12.6 下拉刷新  
&emsp;&emsp;下拉刷新这种功能早就不是什么新鲜的东西了，几乎所有的应用都会有这个功能。不过市面上现有的下拉刷新功能在风格上都各不相同，并且和Material Design还有些格格不入的感觉。因此，谷歌为了让Android下拉刷新风格能有一个统一的标准，于是在Material Design中制定了一个官方的设计规范。当然，我们并不需要去深入了解这个规范是什么样的，因为谷歌早就提供了现成的控件，我们只需要在项目中直接使用就可以了。  
&emsp;&emsp;SwipeRefreshLayout就是用于实现下拉刷新功能的核心类，我们把想要实现下拉刷新功能的控件放置到SwipeRefreshLayout中，就可以迅速让这个控件支持下拉刷新。那么在MaterialTest项目中，应该支持下拉刷新功能的控件自然就是RecyclerView了。  
&emsp;&emsp;这里我使用的是androidx的库，需要在build.gradle中添加依赖，修改activity_main.xml中的代码： 

```xml
<?xml version="1.0" encoding="utf-8"?>

<androidx.drawerlayout.widget.DrawerLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:layout_height="match_parent"
        android:layout_width="match_parent"
        android:id="@+id/drawer_layout">
    <androidx.coordinatorlayout.widget.CoordinatorLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent">
        <com.google.android.material.appbar.AppBarLayout android:layout_width="match_parent"
                                                         android:layout_height="wrap_content">

            <androidx.appcompat.widget.Toolbar
                    android:id="@+id/toolbar"
                    android:layout_width="match_parent"
                    android:layout_height="?android:attr/actionBarSize"
                    android:background="?android:attr/colorPrimary"
                    android:theme="@style/ThemeOverlay.AppCompat.ActionBar"
                    app:layout_scrollFlags="scroll|enterAlways|snap"
                    app:popupTheme="@style/ThemeOverlay.AppCompat.Light"/>
        </com.google.android.material.appbar.AppBarLayout>
        <androidx.swiperefreshlayout.widget.SwipeRefreshLayout
                android:id="@+id/swipe_refresh_layout"
                app:layout_behavior="@string/appbar_scrolling_view_behavior"
                android:layout_width="match_parent"
                android:layout_height="match_parent">

            <androidx.recyclerview.widget.RecyclerView
                    android:id="@+id/recycler_view"
                    android:layout_width="match_parent"
                    app:layout_behavior="@string/appbar_scrolling_view_behavior"
                    android:layout_height="match_parent"/>
        </androidx.swiperefreshlayout.widget.SwipeRefreshLayout>

        <com.google.android.material.floatingactionbutton.FloatingActionButton
                android:id="@+id/fab"
                android:layout_gravity="bottom|end"
                android:layout_margin="16dp"
                app:elevation="8dp"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"/>
    </androidx.coordinatorlayout.widget.CoordinatorLayout>

    <TextView android:layout_width="match_parent"
              android:layout_height="match_parent"
              android:layout_gravity="start"
              android:text="This is menu"
              android:textSize="30sp"
              android:background="#FFF"/>
</androidx.drawerlayout.widget.DrawerLayout>

```
&emsp;&emsp;可以看到，这里我们在RecyclerView的外面又嵌套了一层SwipeRefreshLayout，这样RecyclerView就自动拥有下拉刷新功能了。另外需要注意，由于RecyclerView现在变成了SwipeRefreshLayout的子控件，因此之前使用的app:layout_behavior声明的布局行为现在也要移到SwipeRefreshLayout中才行。  
&emsp;&emsp;不过这还没有结束，虽然RecyclerView已经支持下拉刷新功能了，但是我们还要在代码中处理具体的刷新逻辑才行。修改MainActivity中的代码：

```java
package com.zj970.materialtest;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.zj970.materialtest.entity.Fruit;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private SwipeRefreshLayout swipeRefreshLayout;
    private DrawerLayout mDrawerLayout;
    private Fruit[] fruits = {
            new Fruit("Apple", R.drawable.apple),
            new Fruit("watermelon", R.drawable.watermelon),
            new Fruit("Orange", R.drawable.orange),
            new Fruit("Pear", R.drawable.pear),
            new Fruit("Strawberry", R.drawable.strawberry),
            new Fruit("Pineapple", R.drawable.pineapple),
            new Fruit("Banana", R.drawable.banana),
            new Fruit("Mango", R.drawable.mango),
            new Fruit("Grape", R.drawable.grape),
            new Fruit("cherry", R.drawable.cherry)};
    private List<Fruit> fruitList = new ArrayList<Fruit>();
    private FruitAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Data deleted", Snackbar.LENGTH_LONG)
                        .setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this, "FAB clicked", Toast.LENGTH_LONG).show();
                            }
                        }).show();
            }
        });
        initFruits();
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new FruitAdapter(fruitList);
        recyclerView.setAdapter(mAdapter);

        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.design_default_color_primary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFruits();
            }
        });
    }
    private  void  refreshFruits(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initFruits();
                        mAdapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    private void initFruits() {
        fruitList.clear();
        for (int i = 0; i < fruits.length; i++) {
            Random random = new Random();
            int index = random.nextInt(fruits.length);
            fruitList.add(fruits[index]);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
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

&emsp;&emsp;这段代码应该还是比较好理解的，首先通过findViewById()方法拿到SwipeRefreshLayout的实例，然后调用setColorSchemeResources()方法来设置下拉进度刷新进度条的颜色，这里我们就使用主题的colorPrimary作为进度条的颜色了。接着调用setOnRefreshListener()方法来设置一个下拉刷新的监听器，当触发了下拉刷新操作的时候就会回调这个监听器的onRefresh()方法，然后我们在这里去处理具体的刷新逻辑就可以了。  
&emsp;&emsp;通常情况下，onRefresh()方法中应该是去网络上请求最新的数据，然后再将这些数据展示出来。这里简单起见，我们就不和网络进行交互了，而是调用一个refreshFruits()方法进行本地刷新操作。refreshFruits()方法中先是开启了一个线程，然后将线程沉睡两秒钟。之所以这么做，是因为本地刷新操作速度非常快，如果不将线程沉睡的话，刷新立刻就结束了，从而看不到刷新的过程。沉睡结束之后，这里使用了runOnUiThread()方法将线程切回主线程，然后调用initFruits()，接着再调用FruitAdapter的notifyDataSetChanged()方法通知数据发生了变化，最后调用SwipeRefreshLayout的setRefreshing()方法并传入false，用于表述刷新事件结束，并隐藏刷新进度条。现在重新运行一下程序，下拉滑动，会有一个下拉刷新的进度条出现，松手后就自动刷新了，效果如下所示：

![img_12.png](img_12.png)

&emsp;&emsp;下拉刷新的进度条只停留两秒钟，之后就会自动消失了，界面的水果数据也会随之更新。这样我们就把下拉刷新的功能也成功实现了并且这就是Material Design中规定的最标准的下拉刷新效果，还有什么会比这个更好看呢？目前我们的项目中已经应用了众多Material Design的效果。接下来学习可折叠式标题栏。  

## 12.7 可折叠式标题栏  
&emsp;&emsp;虽然我们现在的标题栏是使用Toolbar来编写的，不过它看上去和传统的ActionBar其实没什么两样，只不过可以响应RecyclerView的滚动事件来进行隐藏和显示。而Material Design中并没有限定标题栏必须是长这个样子的，事实上，我们可以根据自己的喜好随意定制标题栏的样式。那么本节中我们就来实现一个可折叠式标题栏的效果，需要借助CollapsingToolbarLayout这个工具。  

### 12.7.1 CollapsingToolbarLayout

&emsp;&emsp;顾名思义，CollapsingToolbarLayout是一个作用于Toolbar基础上的布局，CollapsingToolbarLayout可以让Toolbar的效果更加丰富，不仅仅是展示一个标题栏，而是能够实现非常华丽的效果。  
&emsp;&emsp;不过，CollapsingToolbarLayout是不能独立存在的，它在设计的时候被限定只能作为AppBarLayout的直接子布局来使用。而AppBarLayout又必须是CoordinatorLayout的子布局，因此本节中我们要实现的功能其实需要综合运用前面所学的各种知识。  
&emsp;&emsp;首先我们需要一个额外的活动来作为水果的详情展示界面，创建一个FruitActivity，并将布局名指定成activity_fruit.xml，然后我们开始编写水果详细展示界面的布局。  

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.coordinatorlayout.widget.CoordinatorLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:layout_width="match_parent"
        android:layout_height="match_parent">
    <com.google.android.material.appbar.AppBarLayout
            android:id="@+id/appBar"
            android:layout_width="match_parent"
            android:layout_height="250dp">
        <com.google.android.material.appbar.CollapsingToolbarLayout
                android:id="@+id/collapsing_toolbar"
                android:theme="@style/ThemeOverlay.AppCompat.ActionBar"
                app:contentScrim="?android:attr/colorPrimary"
                app:layout_scrollFlags="scroll|exitUntilCollapsed"
                android:layout_width="match_parent"
                android:layout_height="match_parent">
            <ImageView
                    android:id="@+id/fruit_image_view"
                    android:scaleType="centerCrop"
                    app:layout_collapseMode="parallax"
                    android:layout_width="match_parent"
                    android:layout_height="match_parent"/>
            <androidx.appcompat.widget.Toolbar
                    android:id="@+id/toolbar"
                    android:layout_width="match_parent"
                    android:layout_height="?android:attr/actionBarSize"
                    app:layout_collapseMode="pin"/>
        </com.google.android.material.appbar.CollapsingToolbarLayout>
    </com.google.android.material.appbar.AppBarLayout>
    <androidx.core.widget.NestedScrollView
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            app:layout_behavior="@string/appbar_scrolling_view_behavior">
        <LinearLayout
                android:orientation="vertical"
                android:layout_width="match_parent"
                android:layout_height="wrap_content">
                <androidx.cardview.widget.CardView android:layout_width="wrap_content"
                                                   android:layout_height="wrap_content"
                                                   android:layout_marginBottom="15dp"
                                                   android:layout_marginLeft="15dp"
                                                   android:layout_marginRight="15dp"
                                                   android:layout_marginTop="35dp"
                                                   app:cardCornerRadius="4dp">
                    <TextView android:layout_width="wrap_content" android:layout_height="wrap_content" android:id="@+id/fruit_content_text" android:layout_margin="10dp"/>
                </androidx.cardview.widget.CardView>
        </LinearLayout>
    </androidx.core.widget.NestedScrollView>
    <com.google.android.material.floatingactionbutton.FloatingActionButton android:layout_width="wrap_content"
                                                                           android:layout_height="wrap_content"
                                                                           android:layout_margin="16dp"
                                                                           android:src="@drawable/ic_comment"
                                                                           app:layout_anchor="@id/appBar"
                                                                           app:layout_anchorGravity="bottom|end"/>
</androidx.coordinatorlayout.widget.CoordinatorLayout>
```
&emsp;&emsp;首先根布局是CoordinatorLayout，始终记得要定义一个xmlns:app的命名空间，在Material Design的开发中会经常用到它。接着我们这爱CoordinatorLayout中嵌套一个AppBarLayout，然后给AppBarLayout定义一个id，将它的宽度指定为match_parent，高度指定为250dp，这里的高度可以随意指定，不过尝试之后发现250dp感觉效果最好。  
&emsp;&emsp;接下来在AppBarLayout中再嵌套一个CollapsingToolbarLayout，android:theme属性指定了一个ThemeOverlay.AppCompat.ActionBar的主题，其实对于这部分我们并不陌生，因为在之前activity_main.xml中给Toolbar指定的也是这个主题，只不过这里要实现更加高级的Toolbar效果，因此需要将这个主题的指定提到上一层来。app:contentScrim属性用于指定CollapsingToolbarLayout在趋于折叠状态以及折叠之后的背景色，其实CollapsingToolbarLayout在折叠之后就是一个普通的Toolbar，那么背景色肯定应该是colorPrimary了，具体的效果我们待会就能看到。app:layout_scrollFlags属性我们也是见过的，只不过之前是给Toolbar指定的，现在也移到外面来了。其中,scroll表示CollapsingToolbarLayout会随着水果内容详情当然滚动一起滚动，exitUntilCollapsed表示当CollapsingToolbarLayout随着滚动完成折叠后就保留在界面上，不再移出屏幕。  
&emsp;&emsp;接下来在CollapsingToolbarLayout中定义标题栏的具体内容，可以看到，我们在CollapsingToolbarLayout中定义了一个ImageView和一个Toolbar，也就意味着，这个高级版的标题栏是由普通的标题栏加上图片组合而成的。这里定义的大多数属性我们都是见过的，只有一个app:layout_collapseMode比较陌生。它用于指定当前控件在CollapsingToolbarLayout折叠过程中的折叠模式，其中Toolbar指定成了pin，表示在折叠的过程中位置始终保持不变，ImageView指定成parallax，表示会在折叠过程中产生一定的错位偏移，这种模式的视觉会非常好。  
&emsp;&emsp;下面开始编写水果内容详细部分，水果内容详细的最外层布局使用了一个NestedScrollView，注意它和AppBarLayout是平级的，我们在9.2.1小节学过ScrollView的用法，它允许使用滚动的方式来查看屏幕以外的数据，而NestedScrollView在此基础上还增加了嵌套响应滚动事件的功能。由于CoordinatorLayout本身已经可以响应滚动事件了，因此我们在它的内部就需要使用NestedScrollView或RecyclerView这样的布局。另外，这里还通过app:layout_behavior属性指定了一个布局行为，这和之前在RecyclerView中的用法是一模一样。不过是ScrollView还是NestedScrollView，它们的内部都只允许存在一个直接子布局。因此，如果我们想要在这里放入很多东西的话，通常都会先嵌套一个LinearLayout，然后再在LinearLayout中放入具体的内容就可以了。这里嵌套一个垂直方向的LinearLayout，并将layout_width设置为match_parent，将layout_height设置为wrap_content。  
&emsp;&emsp;接下来在LinearLayout中放入具体的内容，这里使用一个TextView来显示水果的内容详情，并将TextView放在一个卡片式布局当中。这里为了让界面上更加美观，我在CardView和TextView上都加了一些边距。我中CardView的marginTop加了35dp的边距，这是为下面要编写的东西留出空间。  
&emsp;&emsp;这样就把水果标题栏和水果内容详情的界面都编写完了，不过我们还可以在界面上再添加了一个悬浮按钮。这个按钮不是必需的，根据具体的需求添加就可以了，如果加入了的话，我们将免费获得一些额外的动画效果，这里加入了一个悬浮按钮，它和AppBarLayout以及NestedScrollView是平级的。FloatingActionButton中使用app:layout_anchorGravity属性指定了一个锚点，我们将锚点设置为AppBarLayout，这样悬浮按钮就会出现在水果标题栏的区域内，接着又使用app:layout_anchorGravity属性阿静悬浮按钮定位在标题栏区域的右下角。界面完成后，接下来我们开始编写功能逻辑，修改FruitActivity中的代码：  

```java
package com.zj970.materialtest;

import android.content.Intent;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;

public class FruitActivity extends AppCompatActivity {
    public static final String FRUIT_NAME = "fruit_name";
    public static final String FRUIT_IMAGE_ID = "fruit_image_id";
    private static final int MAX_ITEMS = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruit);
        Intent intent = getIntent();
        String fruitName = intent.getStringExtra(FRUIT_NAME);
        int fruitImageId = intent.getIntExtra(FRUIT_IMAGE_ID, 0);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        CollapsingToolbarLayout layout = (CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar);
        ImageView fruitImageView = (ImageView) findViewById(R.id.fruit_image_view);
        TextView fruitContentText = (TextView) findViewById(R.id.fruit_content_text);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!= null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        layout.setTitle(fruitName);
        Glide.with(this).load(fruitImageId).into(fruitImageView);
        String fruitContent = generateFruitContent(fruitName);
        fruitContentText.setText(fruitContent);
    }

    private String generateFruitContent(String fruitName) {
        StringBuilder fruitContent = new StringBuilder();
        for (int i = 0; i < MAX_ITEMS; i++){
            fruitContent.append(fruitName);
        }
        return fruitContent.toString();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
```

&emsp;&emsp;FruitActivity中的代码并不是很复杂。首先，在onCreate()方法中，我们通过Intent货物到传入的水果名和水果图片的资源id，然后通过findViewById()方法拿到刚才在布局文件中定义的各个控件的实例。由于HomeAsUp按钮按钮的默认图标就是一个返回箭头，这正是我们所期望的，因此就不用再额外设置别的图标了。  
&emsp;&emsp;接下来开始填充界面上的内容，调用CollapsingToolbarLayout的setTitle()方法将水果名设置成当前界面的标题，然后使用Glide加载传入的水果图片，并设置到标题栏的ImageView上面。接着需要填充水果的内容详情，由于这只是一个示例程序，并不需要什么真实的数据，所以我使用了一个generateFruitContent()方法将水果名循环拼接500次，从而生成了一个比较长的字符串，将它设置了TextView上面。  
&emsp;&emsp;最后，我们在onOptionsItemSelected()方法中处理了HomeAsUp按钮的点击事件，当点击了这个按钮时，就调用finish()方法关闭当前的活动，从而返回上一个活动。  
&emsp;&emsp;最终我们需要处理RecyclerView的点击事件，修改FruitAdapter中的代码，如下所示：

```java
package com.zj970.materialtest;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.zj970.materialtest.entity.Fruit;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author: zj970
 * @date: 2022/12/11
 */
public class FruitAdapter  extends RecyclerView.Adapter<FruitAdapter.ViewHolder>{
    private Context mContext;
    private List<Fruit> mFruitList;

    public FruitAdapter(List<Fruit> mFruitList) {
        this.mFruitList = mFruitList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_fruit, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                Fruit fruit = mFruitList.get(position);
                Intent intent = new Intent(mContext,FruitActivity.class);
                intent.putExtra(FruitActivity.FRUIT_NAME,fruit.getName());
                intent.putExtra(FruitActivity.FRUIT_IMAGE_ID,fruit.getImageId());
                mContext.startActivity(intent);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Fruit fruit = mFruitList.get(position);
        holder.fruitName.setText(fruit.getName());
        Glide.with(mContext).load(fruit.getImageId()).into(holder.fruitImage);
    }

    @Override
    public int getItemCount() {
        return mFruitList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView fruitImage;
        TextView fruitName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            fruitImage = cardView.findViewById(R.id.fruit_image);
            fruitName = cardView.findViewById(R.id.fruit_name);
        }
    }
}

```

&emsp;&emsp;最关键的一步其实也是最简单的，这里我们给CardView注册了一个点击事件监听器，然后在点击事件中获取点击项的水果名和水果图片资源id，把它们传入到Intent中，最后调用startActivity()方法启动FruitActivity。现在重新运行一下程序，并点击界面上的水果，效果如下：  

![img_13.png](img_13.png)

&emsp;&emsp;这个界面上的内容分为三部分，水果标题栏、水果内容详情和悬浮按钮。Toolbar和水果背景图完美地融合到了一起，既保证了图片的展示空间，又不影响Toolbar的任何功能，那个向左的箭头就是用于返回上一个活动的。当尝试向上拖动水果内容详情，会发现水果背景图上的标题会慢慢缩小，并且背景图会产生一些错位偏移的效果，如下所示：

![img_14.png](img_14.png)

&emsp;&emsp;这是由于用户想要查看水果的内容详情，此时界面的重点在具体的内容上面，因此标题栏会自动进行折叠，从而节省屏幕空间。一直往上拖动，标题栏的背景图片不见了，悬浮按钮也会自动消失了，现在水果标题栏变成了一个普通的Toolbar。

### 12.7.2 充分利用系统状态栏空间

&emsp;&emsp;虽说现在水果详情展示界面的效果已经非常华丽了但并不代表我们不能再进一步地提升。你会发现水果的背景图片和西戎的装贪婪的总会有一些不搭的感觉，如果我们能将背景图和状态栏融合到一起，那这个视觉体验绝对能提升好几个档次。  
&emsp;&emsp;只不过可惜的是，在Android 5.0系统之前，我们是无法对状态栏的背景或颜色进行操作的，哪个时候也还没有Material Design的概念。但是在Android 5.0及以后的系统都是支持这个功能的，因此这里我们就实现一个系统差异型的效果，在Android 5.0及之后的系统中，使用背景图和状态栏融合的模式，在之前的系统中使用普通的模式。  
&emsp;&emsp;想要让背景图能够和系统状态栏融合，需要借助android:fitsSystemWindows这个属性来实现。在CoordinatorLayout、AppBarLayout、CollapsingToolbarLayout这种嵌套结构的布局中，将控件的android:fitsSystemWindows属性指定成true，就表示该控件会出现在系统状态栏里。对应我们程序那就是水果栏中的ImageView应该设置这个属性了。不过只给ImageView设置这个属性是没有用的，我们必须将ImageView布局结构中的所有父布局都设置上这个属性才可以，修改activity_fruit.xml中的代码:  

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.coordinatorlayout.widget.CoordinatorLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:fitsSystemWindows="true"
        android:layout_width="match_parent"
        android:layout_height="match_parent">
    <com.google.android.material.appbar.AppBarLayout
            android:id="@+id/appBar"
            android:fitsSystemWindows="true"
            android:layout_width="match_parent"
            android:layout_height="250dp">
        <com.google.android.material.appbar.CollapsingToolbarLayout
                android:id="@+id/collapsing_toolbar"
                android:theme="@style/ThemeOverlay.AppCompat.ActionBar"
                app:contentScrim="?android:attr/colorPrimary"
                app:layout_scrollFlags="scroll|exitUntilCollapsed"
                android:fitsSystemWindows="true"
                android:layout_width="match_parent"
                android:layout_height="match_parent">
            <ImageView
                    android:id="@+id/fruit_image_view"
                    android:scaleType="centerCrop"
                    app:layout_collapseMode="parallax"
                    android:fitsSystemWindows="true"
                    android:layout_width="match_parent"
                    android:layout_height="match_parent"/>
            <androidx.appcompat.widget.Toolbar
                    android:id="@+id/toolbar"
                    android:layout_width="match_parent"
                    android:layout_height="?android:attr/actionBarSize"
                    app:layout_collapseMode="pin"/>
        </com.google.android.material.appbar.CollapsingToolbarLayout>
    </com.google.android.material.appbar.AppBarLayout>
    <androidx.core.widget.NestedScrollView
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            app:layout_behavior="@string/appbar_scrolling_view_behavior">
        <LinearLayout
                android:orientation="vertical"
                android:layout_width="match_parent"
                android:layout_height="wrap_content">
                <androidx.cardview.widget.CardView android:layout_width="wrap_content"
                                                   android:layout_height="wrap_content"
                                                   android:layout_marginBottom="15dp"
                                                   android:layout_marginLeft="15dp"
                                                   android:layout_marginRight="15dp"
                                                   android:layout_marginTop="35dp"
                                                   app:cardCornerRadius="4dp">
                    <TextView android:layout_width="wrap_content" android:layout_height="wrap_content" android:id="@+id/fruit_content_text" android:layout_margin="10dp"/>
                </androidx.cardview.widget.CardView>
        </LinearLayout>
    </androidx.core.widget.NestedScrollView>
    <com.google.android.material.floatingactionbutton.FloatingActionButton android:layout_width="wrap_content"
                                                                           android:layout_height="wrap_content"
                                                                           android:layout_margin="16dp"
                                                                           android:src="@drawable/ic_comment"
                                                                           app:layout_anchor="@id/appBar"
                                                                           app:layout_anchorGravity="bottom|end"/>
</androidx.coordinatorlayout.widget.CoordinatorLayout>
```

&emsp;&emsp;但是，即使我们将android:fitsSystemWindows属性都设置好了还是没有用的，因为必须还要更改程序的主题中状态栏颜色指定成透明色。指定成透明色的方法很简单，在主题中将android:statusBarColor属性的值指定成@android:color/transaction就可以了。但问题在于，android:statusBarColor这个属性是从API 21，也就是Android 5.0 系统开始才有的，之前的系统无法指定这个属性。那么系统差异性功能实现就要从这里开始了。  
&emsp;&emsp;右击res目录->New->Directory，创建一个values-v21目录，然后右击values-v21目录->New->Values resource file，创建一个styles.xml文件。接着对这个文件进行编写，代码如下所示：  

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <style name="FruitActivityTheme" parent="Theme.MaterialDesign">
        <item name="android:statusBarColor">@android:color/transparent</item>
    </style>
</resources>
```
&emsp;&emsp;这里我们定义了一个FruitActivityTheme主题，它是专门给FruitActivityT使用的。我们在此主题中将状态看指定成透明色，由于values-v21目录是只有Android 5.0 及以上的系统才回去读取的，因此这么声明是没有问题的。但是Android 5.0之前的系统去无法识别FruitActivityTheme这个主题，因此我们还需要对values/themes.xml中进行修改。最后还需要让FruitActivity使用这个主题，修改AndroidManifest中的代码，运行一下程序，效果如下：  

![img_15.png](img_15.png)

&emsp;&emsp;这里与之前的效果好多了。

## 12.8 小节与总结  
&emsp;&emsp;本章中我们充分利用了一些开源库来实现一个高度Material化的应用程序，Material Design 的设计思维和设计理解才是更加重要的东西，可以参考一下Material Design的官方文章：https://material.google.com。