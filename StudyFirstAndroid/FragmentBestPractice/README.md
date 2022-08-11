# 碎片的最佳实践——一个简易版的新闻应用

&emsp;&emsp;现在已经将碎片的重要知识点掌握差不多了，碎片很多时候都是在平板开发当中使用的，主要是为了解决屏幕空间不能充分利用的问题，那是不是表明我们开发程序都需要提供一个手机版和一个pad版？确实会有不少公司这么做的，但是这样会浪费很多的人力物力。因为维护两个版本的代码成本很高，每当增加什么新功能时，需要在两份代码中各写一遍，每当发现有一个BUG时，需要在两份代码中各修改一次。因此，如何编写同时兼容手机和平板的应用程序，下面我们来尝试编写一个简易的新闻应用。

&emsp;&emsp;由于待会在编写新闻列表时会使用到RecyclerView，因此首先需要在app/build.gradle当中添加依赖库：

```groovy
dependencies {
    compile fileTree(dir: 'libs',include: ['*.jar'])
    compile 'com.android.support:appcompat-v7:24.2.1'
    testCompile 'junit:junit:4.12'
    compile 'com.android.support:recyclerView-v7:24.2.1'
}
```
接下来新建一个新闻实体类News:

```java
package com.zj970.fragmentbestpractice.entity;

public class News {
    private String title;
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

```

News类的代码比较简单的，title字段表示新闻标题，content字段表示新闻内容。接着新建布局文件news_content_frag.xml，用于作为新闻内容的标布局：

```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
                android:layout_width="match_parent"
                android:layout_height="match_parent">
    <LinearLayout
            android:id="@+id/visiblity_layout"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:orientation="vertical"
            android:visibility="invisible">
        <TextView
                android:id="@+id/news_title"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:gravity="center"
                android:padding="10dp"
                android:textSize="20sp"/>
        <View
                android:layout_width="match_parent"
                android:layout_height="1dp"
                android:background="#000"/>
        <TextView
                android:id="@+id/news_content"
                android:layout_width="match_parent"
                android:layout_height="0dp"
                android:layout_weight="1"
                android:padding="15dp"
                android:textSize="18sp"/>
    </LinearLayout>
    <View
            android:layout_width="1dp"
            android:layout_height="match_parent"
            android:layout_alignParentLeft="true"
            android:background="#000"/>
</RelativeLayout>
```

新闻内容的布局主要可以分为两个部分，头部部分显示新闻标题，正文部分显示新闻内容，中间使用一条细线分隔开。这里的细线是利用view来实现的，将View的宽或高设置为1dp，再通过background属性给细线设置一下颜色就可以了。这里我们设置细线设置为黑色。然后新建一个NewsContentFragment类，继承自Fragment。

```java
package com.zj970.fragmentbestpractice.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.zj970.fragmentbestpractice.R;

public class NewsContentFragment extends Fragment {
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.news_content_frag,container,false);
        return view;
    }

    /**
     * 刷新页面
     * @param newsTitle 新闻标题
     * @param newsContent 新闻内容
     */
    private void refresh(String newsTitle,String newsContent){
        View visibilityLayout = view.findViewById(R.id.visiblity_layout);
        visibilityLayout.setVisibility(View.VISIBLE);
        TextView newsTitleText = view.findViewById(R.id.news_title);
        TextView newsContentText = view.findViewById(R.id.news_content);
        newsContentText.setText(newsContent);
        newsTitleText.setText(newsTitle);
    }
}

```
首先在onCreateView()方法里加载了我们刚刚创建的news_content_flag布局，然后又提供了一个refresh()方法，这个方法就是利用将新闻的标题和内容显示在界面上的。然后通过findViewById()方法分别获取到新闻标题和内容的控件，然后将方法传递进来的参数设置进去。这样我们就把新闻内容的碎片和布局都创建好了，但是他们都是在双页模式中使用的，如果想要在单页模式中使用的话，我们还需要创建一个活动。新建一个NewsContentActivity,并将布局名指定成news_content，然后修改布局代码：

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:tools="http://schemas.android.com/tools"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        tools:context=".NewsContentActivity">
    <fragment
            android:id="@+id/news_content_fragment"
            android:name="com.zj970.fragmentbestpractice.fragment.NewsContentFragment"
            android:layout_width="match_parent"
            android:layout_height="match_parent"/>
</LinearLayout>
```

这里我们充分发挥了代码的复用性，直接在布局中引入了NewContentFragment，这样也就相当于把news_content_frag布局的内容自动加了进来，修改NewsContentActivity中的代码，如下所示：

```java
package com.zj970.fragmentbestpractice;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.zj970.fragmentbestpractice.fragment.NewsContentFragment;

public class NewsContentActivity extends AppCompatActivity {

    public static void  actionStart(Context context,String newsTitle,String newsContent){
        Intent intent = new Intent(context,NewsContentActivity.class);
        intent.putExtra("news_title",newsTitle);
        intent.putExtra("news_content",newsContent);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_cotent);
        String newsTitle = getIntent().getStringExtra("news_title");//获取传入的所有标题
        String newsContent = getIntent().getStringExtra("news_content");//获取传入的新闻内容
        NewsContentFragment newsContentFragment = (NewsContentFragment) getSupportFragmentManager().findFragmentById(R.id.news_content_fragment);
        newsContentFragment.refresh(newsTitle, newsContent);//刷新NewContentFragment界面
    }
}
```
&emsp;&emsp;可以看到，在onCreate()方法中我们通过Intent获取到了传入的新闻标题和新闻内容，然后调用FragmentManager的findFragmentById()方法得到了NewsContentFragment的实例，接着调用它的refresh()方法，并将新闻的标题和内容传入，就可以把这些数据显示出来了。注意我们这里还提供了一个actionStart()方法，它的作用是为了更好地启动活动，见2.6.3。接下来还需要再创建一个用于显示新闻列表的布局，新建一个news_title_flag.xml:

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
              android:orientation="vertical"
              android:layout_width="match_parent"
              android:layout_height="match_parent">
    <androidx.recyclerview.widget.RecyclerView
            android:id="@+id/news_title_recycle_view"
            android:layout_width="match_parent"
            android:layout_height="match_parent"/>
</LinearLayout>
```
这个布局的代码就非常简单了，里面只有一个用于显示新闻列表的RecyclerView。既然要用到RecyclerView，那么就必定少不了子项的布局。新建new_item.xml作为RecyclerView子项的布局，代码如下：

```xml
<TextView xmlns:andoird="http://schemas.android.com/apk/res/android"
          andoird:id="@+id/news_title"
          andoird:layout_height="match_parent"
          andoird:layout_width="match_parent"
          andoird:singleLine="true"
          andoird:ellipsize="end"
          andoird:textSize="18sp"
          andoird:paddingLeft="10dp"
          andoird:paddingRight="10dp"
          andoird:paddingTop="15dp"
          andoird:paddingBottom="15dp"/>

```
子项的布局也非常简单，只有一个TextView。仔细观察TextView，会发现其中有几个属性是我们之前没有见过,android:padding表示给控件的周围加上补白，这样不至于让文本内容会紧靠在边缘上。android:singleLine设置为true表示让这个TextView只能单行显示。android:ellipsize用于设定当文本的内容超出控件宽度时，文本的缩略方式，这里指定在尾部进行缩略。既然新闻列表和子项的布局都已经创建好了，那么接下来我们就需要一个用于展示新闻列表的地方。这里新建NewsTitleFragment作为展示新闻列表的碎片，代码如下：

