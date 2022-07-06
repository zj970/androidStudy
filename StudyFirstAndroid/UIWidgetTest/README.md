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
由于系统会对Button中的所有英文字母自动进行大写转换