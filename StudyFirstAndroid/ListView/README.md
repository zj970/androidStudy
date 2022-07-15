# ListView的简单用法
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:tools="http://schemas.android.com/tools"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        tools:context=".MainActivity">
    <ListView
            android:id="@+id/list_view"
            android:layout_width="match_parent"
            android:layout_height="match_parent"/>
</LinearLayout>
```
这里我们使ListView占满整个布局空间,然后在代码中修改

```java
package com.zj970.listview;

import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private String[] data = {"Apple","Banana","Orange","Watermelon","Pear","Grape","Pineapple","Strawberry","Cherry","Mango",
            "Apple","Banana","Orange","Watermelon","Pear","Grape","Pineapple","Strawberry","Cherry","Mango"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_expandable_list_item_1,data);
        ListView listView = findViewById(R.id.list_view);
        listView.setAdapter(adapter);
    }
}
```
&emsp;&emsp;既然ListView是用于展示大量数据的，那我们就应该先将数据提供好。这些数据可以是从网上下载的，也可以是从数据库中读取的，应该视具体的应用场景而定。这里我们就简单的使用了一个data数组来测试，里面包含了很多水果的名称。

&emsp;&emsp;不过，数组中的数据无法直接传递给ListView的，我们还需要借助适配器来完成。Android中提供了很多适配器的实现类，其中我认为最好用的就是ArrayAdapter。它可以通过泛型来指定要适配的数据类型，然后在构造函数中把要适配的数据传入。ArrayAdapter有多个构造函数的重载，你应该根据实际情况选择最合适的一种。这里由于我们提供的数据都是字符串，因此将ArrayAdapter的泛型指定为String，然后在ArrayAdapter的构造函数中依次传入当前上下文，List_item_1作为ListView子项布局的id，这是一个Android内置的布局文件，里面只有一个TextVIew，可用于简单地显示一段文本。这样适配器对象就构建好了。

最后，还需要调用ListView的setAdapter()方法，将构建好的适配器对象传递进来，这样ListView和数据之间的关联就建立完成了。

# 定制ListView的界面

&emsp;&emsp;只能显示一段文本的ListView实在是太单调了，我们现在就来对ListView的界面进行定制，让它可以显示更加丰富的内容。

&emsp;&emsp;首先需要准备好一组照片，分别对应上面提供的每一种水果，待会我们要这些水果名称旁边都有一个图样。

接着定义一个实体类，作为ListView适配器的适配类型。新建类Fruit

```java
package com.zj970.listview.entity;

/**
 * 水果实体类
 */
public class Fruit {
    /**
     * 水果名字
     */
    private String name;

    /**
     * 水果图片id
     */
    private int imageId;

    /**
     * 构造水果实体类的有参构造----默认的构造器
     * @param name 水果名
     * @param imageId 水果图片id
     */
    public Fruit(String name,int imageId){
        this.name = name;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}

```

Fruit类中只有两个字段，name表示水果名字，imageId表示水果对应图片的资源id。然后需要为ListView的子项指定一个我们自定义的布局，在layout目录下新建fruit_item.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
              android:layout_width="match_parent"
              android:layout_height="match_parent">
    
    <ImageView
            android:id="@+id/fruit_image"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"/>
    <TextView
            android:id="@+id/fruit_name"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center_vertical"
            android:layout_marginLeft="10dp"/>

</LinearLayout>
```

&emsp;&emsp;在这个布局中，我们定义了一个ImageView用于显示水果的图片，又定义了一个TextView用于显示水果的名称，并让TextView在垂直方向上居中显示。接下来需要创建一个自定义的适配器，这个适配器继承自ArrayAdapter，并将泛型指定为Fruit类。新建类FruitAdapter，代码如下：

```java
package com.zj970.listview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.zj970.listview.R;
import com.zj970.listview.entity.Fruit;

import java.util.List;

/**
 * 自定义适配器，继承ArrayAdapter
 */
public class FruitAdapter extends ArrayAdapter<Fruit> {
    private int resourceId;

    /**
     * Constructor
     *
     * @param context  The current context.
     * @param resource The resource ID for a layout file containing a TextView to use when
     *                 instantiating views.
     * @param objects  The objects to represent in the ListView.
     */
    public FruitAdapter(@NonNull Context context, int resource, @NonNull List<Fruit> objects) {
        super(context, resource, objects);
        this.resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Fruit fruit = getItem(position);//获得当前项的Fruit示例
        View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        ImageView fruitImage = view.findViewById(R.id.fruit_image);
        TextView fruitName = view.findViewById(R.id.fruit_name);
        fruitImage.setImageResource(fruit.getImageId());
        fruitName.setText(fruit.getName());
        return view;
    }
}

```

&emsp;&emsp;FruitAdapter重写了弗雷的一组构造函数，用于将上下文、ListView子项布局的id和数据都传递进来。另外又重写了getView()方法，这个方法在每个子项都被滚到了屏幕内的时候会被调用。在getView()方法中，首先通过getItem()方法得到当前项的Fruit实例，然后使用LayoutInflater来为这个子项加载我们传入的布局。

&emsp;&emsp;这里LayoutInflater的inflate()方法接收3个参数，前面两个参数我们已经知道什么意思了，第三个参数指定成false，表示只让我们在父布局中声明的layout属性生效，但不为这个View添加父布局，一旦View有了父布局之后，它就不能再添加到ListView中了。

&emsp;&emsp;我们继续往下看，接下来调用View的findViewById()方法分别获取到ImageView和TextView的实例，并分别调用它们的setImageResource()和setText()方法来设置显示的图片和文字，最后将布局返回。修改MainActivity

```java
package com.zj970.listview;

import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.zj970.listview.adapter.FruitAdapter;
import com.zj970.listview.entity.Fruit;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    /**
     * Initialize the fruit container
     */
    private List<Fruit> fruitList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFruits();
        FruitAdapter adapter = new FruitAdapter(MainActivity.this,R.layout.fruit_item,fruitList);
        ListView listView = findViewById(R.id.list_view);
        listView.setAdapter(adapter);
    }

    /**
     * 初始化水果
     */
    private void initFruits(){
        for (int i = 0; i < 2; i++) {
            Fruit apple = new Fruit("Apple",R.drawable.apple_pic);
            fruitList.add(apple);
            Fruit banana = new Fruit("Banana",R.drawable.banana_pic);
            fruitList.add(banana);
            Fruit orange = new Fruit("Orange",R.drawable.orange_pic);
            fruitList.add(orange);
            Fruit watermelon = new Fruit("Watermelon",R.drawable.watermelon_pic);
            fruitList.add(watermelon);
            Fruit pear = new Fruit("Pear",R.drawable.pear_pic);
            fruitList.add(pear);
            Fruit grape = new Fruit("Grape",R.drawable.grape_pic);
            fruitList.add(grape);
            Fruit pineapple = new Fruit("Pineapple",R.drawable.pineapple_pic);
            fruitList.add(pineapple);
            Fruit strawberry = new Fruit("Strawberry",R.drawable.strawberry_pic);
            fruitList.add(strawberry);
            Fruit cherry = new Fruit("Cherry",R.drawable.cherry_pic);
            fruitList.add(cherry);
            Fruit mango = new Fruit("Mango",R.drawable.mango_pic);
            fruitList.add(mango);
        }
    }
}
```

&emsp;&emsp;可以看到，这里添加了一个initFruits()方法，用于初始化所有的水果数据。在Fruit类的构造函数中将水果的名字和对应的图片id传入，然后把创建好的对象添加到水果列表中。另外我们使用了一个for循环将所有的水果数据添加了两遍，这是因为如果只添加一遍的话，数据不足以充满整个屏幕。接着在onCreate()方法中创建了FruitAdapter对象，并将FruitAdapter作为适配器传递给ListView,这样就可以定制ListView界面了。