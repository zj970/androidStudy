# 1. 常用控件的使用方法

## 1.1 TextView

TextView 是Android中最简单的一个控件，它主要用于在界面上显示一段文本信息。

```xml
<TextView
    android:id = "@+id/text_view"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:text="This is TextView"
/>
```

- android:id给当前控件定义了一个唯一的标识符
- android:layout_width 和 android:layout_height 指定了控件的宽度和高度。Android中所有的控件都有这两个属性，可选值有3种：
  - match_parent 表示让当前控件的大小和父布局的大小一样，也就是由父布局来决定当前控件的大小。
  - fill_parent 和 match_parent 意义相同，更推荐用 match_parent
  - wrap_content 表示让当前控件的大小能够刚好包含住里面的内容，也就是由控件的内容决定当前控件的大小。
- 除了使用上述值，也可以对控件的高和宽指定一个固定的大小，但是这样做会在不同手机屏幕的适配方面出现问题

```xml
<TextView
    android:id = "@+id/text_view"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:gravity="center"
    android:text="This is TextView"
/>
```

我们使用android:gravity来指定文字的对齐方式，可选值有top、bottom、left、right、center等，可以使用 "|" 来同时指定多个值，这里我们指定的center，效果等同于 center_vertical | center_horizontal，表示文字在垂直和水平方向都居中对齐。

另外我们还可以对 TextView 中文字大小和颜色进行修改

```xml
    <TextView
            android:id="@+id/text_view"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:gravity="center"
            android:textSize="24sp"
            android:textColor="#00ff00"
            android:text="Hello World!"/>
```

通过android:textSize属性可以指定文字的大小，通过android:textColor属性可以指定文字的颜色，在Android中字体大小使用sp作为单位，非文字一律使用dp为单位。
更多内容了解[Android developer](https://developer.android.google.cn/reference/kotlin/android/widget/TextView?hl=en)

## 1.2 Button

Button是程序用于和用户进行交互的一个重要控件。

```xml
 <Button
            android:id="@+id/button"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="Button"
            android:textAllCaps="false"/>
```
由于系统会对Button中的所有英文字母自动进行大写转换，可以使用 android:textAllCaps="false" 来禁用到默认特性

## 1.3 EditText

EditText是程序用于和用户进行交互的另一个重要的控件，它允许用户在控件里输入和编辑内容，并可以在程序中对这些内容进行处理。EditText的应用场景非常普遍，在进行发短信、发微信、发微博等操作时，你不得不使用EditText。

```xml
    <EditText
            android:id="@+id/edit_text"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"/>
```

Android控件的使用规律：给控件定义一个id，再指定控件的高度和宽度，然后再适当加入一些控件特有的属性

增加EditText提示功能 android:hint="Type something here"，当我们输入任何内容时，该提示文本就会消失

```xml
<EditText
            android:id="@+id/edit_text"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:hint="Type something there"/>
```

&emsp;不过，随着输入的内容不断增多，EditText会被不断地拉长，这时由于EditText的高度是指定的是wrap_content,因此它总能包含住里面的内容，但是输入内容过多时，界面就变得非常难看

我们可以使用 android:maxLines 属性来解决这个问题

```xml
<EditText
            android:id="@+id/edit_text"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:hint="Type something there"
            android:maxLines="2"/>
```
这里通过android:maxLines指定了EditText的最大行数为两行，当输入的内容超过两行时，文本就会向上滚动，而EditText则不会继续拉伸

## 1.4 ImageView

&emsp;&emsp;ImageView 是用于在界面上展示图片的一个控件，它可以让我们的程序界面变得更加丰富多彩。  
学习这个控件需要提前准备一些图片，这些图片通常放在 ”drawable“ 开头的目录下的。

```xml
<ImageView
            android:id="@+id/image_1"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:src="@drawable/img_1"/>
```
&emsp;&emsp;可以看到，这里使用android:src属性给ImageView指定了一张图片。由于图片的宽和高都是未知的，所有将ImageView的宽和高都设定了wrap_content，这样就保证了不管图片的尺寸是多少，图片都可以完整地显示出来。

## 1.5 ProgressBar

&emsp;emsp;ProgressBar用于在界面上显示一个进度条，表示我们的程序正在加载一些数据。

```xml
 <ProgressBar
            android:id="@+id/progress_bar"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"/>

```

&emsp;&emsp;旋转的进度条表明我们的程序正在加载数据，那么数据总有加载完的时候。此时需要Android控件的可见属性。  
所有的Android控件都具有这个属性，可以通过android:visibility进行指定，可选值有3种：
- visible 表示控件是可见的，这个值是默认值，不指定的时候就是可见的
- invisible 表示控件不可见，但它仍然占据原来的位置和大小，只是变为透明状态了。
- gone 不仅不可见，而且不再占用任何屏幕空间

我们还可以通过代码来设置控件的可见性，使用的是setVisibility()方法，可以传入View.VISIBLE、View.INVISIBLE和View.GONE这三种值  
另外我们还可以给ProgressBar指定不同的样式，通过style属性可以将它指定成水平进度条

```xml
<ProgressBar
            android:id="@+id/progress_bar"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            style="?android:attr/progressBarStyleHorizontal"
            android:max="100"/>
```
## 1.6 AlertDialog

AlertDialog 可以在当前的界面弹出一个对话框，这个对话框是置顶于所有界面元素之上的，能够屏蔽掉其他控件的交互能力，因此 AlertDialog 一般都是用于提示一些非常重要的内容或者警告信息。比如为了防止用户误删重要内容，在删除之前弹出一个确认对话框

```
AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setTitle("This is Dialog");
        dialog.setMessage("Something important.");
        dialog.setCancelable(false);
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.show();
```
首先通过AlertDialog.Builder创建一个AlertDialog实例，然后可以为这个对话框设置标题、内容、可否取消等属性，接下来调用setPositiveButton()方法为对话框设置确定点击按钮的点击事件，调用setNegativeButton()方法设置取消按钮点击事件，最后调用show()方法将对话框显示出来