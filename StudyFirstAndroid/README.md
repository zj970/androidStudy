# 第一行代码 Android 第2版 郭霖著

- 第1章 [开启启程——你的第一行Android代码](https://github.com/zj970/androidStudy/tree/master/StudyFirstAndroid/HelloWorld)
- 第2章 [先从看得到的入手——探究活动](https://github.com/zj970/androidStudy/tree/master/StudyFirstAndroid/ActivityStudy)
- 第3章 [软件也要拼脸蛋——UI开发的点点滴滴](https://github.com/zj970/androidStudy/tree/master/StudyFirstAndroid/UIStudy)
- 第4章 [手机平板要兼顾——探究碎片](https://github.com/zj970/androidStudy/tree/master/StudyFirstAndroid/FragmentsStudy)
- 第5章 [全局大喇叭——详解广播机制](https://github.com/zj970/androidStudy/tree/master/StudyFirstAndroid/BroadcastStudy)
- 第6章 [数据存储全方案——详解持久化技术](https://github.com/zj970/androidStudy/tree/master/StudyFirstAndroid/PersistenceTechnology)
- 第7章 [跨程序共享数据——探究内容提供器](https://github.com/zj970/androidStudy/tree/master/StudyFirstAndroid/ConTentProviders)
- 第8章 [丰富你的程序——运用手机多媒体](https://github.com/zj970/androidStudy/tree/master/StudyFirstAndroid/Multimedia)
- 第9章 [看看精彩的世界——使用网络技术](https://github.com/zj970/androidStudy/tree/master/StudyFirstAndroid/NetworkStudy)
- 第10章 [后台默默的劳动者——探究服务](https://github.com/zj970/androidStudy/tree/master/StudyFirstAndroid/ServerStudy)
- 第11章 [Android特色开发——基于位置的服务](https://github.com/zj970/androidStudy/tree/master/StudyFirstAndroid/AndroidLocation)
- 第12章 [最佳的UI体验——MaterialDesign实战](https://github.com/zj970/androidStudy/tree/master/StudyFirstAndroid/MaterialDesign)
- 第13章 [继续进阶——你还应该掌握的高级技巧](https://github.com/zj970/androidStudy/tree/master/StudyFirstAndroid/AdvancedSkills)
- 第14章 [进入实战——开发酷欧天气](https://github.com/zj970/androidStudy/tree/master/StudyFirstAndroid/CoolWeather)
- 第15章 [最后一步——将应用发布到360应用商店](https://github.com/zj970/androidStudy/tree/master/StudyFirstAndroid/CoolWeather)

# 总结  
&emsp;&emsp;观看郭老师的第一行代码书籍，始于7月终于12月。从一个懵懂无知的Android小白，如今总算入了门。恰逢2023年元旦来临之际，感触颇多。  
&emsp;&emsp;还记得刚接触Android时配置环境的手忙脚乱，踩了很多坑。比如jdk、gradle、gradle plugins之间的关系，gradle的语法。第三方jar的引用等，仿佛还在昨日。当第一次启动了HelloWord时，才发现，我与安卓开发者如此之近.  
&emsp;&emsp;在开发过程中，我常与framework打交道，对于我来说，是痛苦的。刚开始不了解Android四大基础组件，Activity、Service、Broadcast、Content Provider。然后就直接去看framework的源码，是十分头疼的。但是Linux鼻祖Linus Torvalds 说过：Read The Fucking Source Code。  
&emsp;&emsp;刚开始时，由于公司的项目都是采用Eclipse阅读查看，并没有使用gradle构建，而是使用Android.mk去编译。调试代码特别麻烦，只能使用jdb去调试。然后我就把公司的项目导入到AS里面进行开发调试。在此过程中，由于不熟悉groovy语法以及Android Studio的项目结构，吃了好大的亏。后面对Android.mk转换也是吃了很大的亏，不清楚如何这个项目的到底有哪些三方库的导入，以及framework的优先级问题等等，在不断地查询与学习中，一个一个地解决了，这对后面的二次开发奠定了很大的作用。  
&emsp;&emsp;后面被老大安排了一个开机向导的任务，需要重写老项目的样式，将几个单页面放在一个视图中，其中有国家、语言页面可以上下滑动选择，并且将所有的页面连在一起，采用画卷的方式展示出来。当时刚好看到郭老师的常用布局方式，单页面里面的垂直滑动选择可以采用RecyclerView，但是其他该怎么办呢？经过搜索查询，知道了ViewPager的存在。立马锁定了采取ViewPager+RecyclerView的组合，一开始想法很简单，无非就是照着书上，写适配器搭数据进去不就行了吗？后来，数据的显示，View之间的交互、搭建开发环境都弄了好久。  
&emsp;&emsp;再后面又是对原生态Settings下手，二次开发。网络模块、蓝牙、基础设置、系统信息这些等等。
&emsp;&emsp;在工作之余我会将郭老师的所有案列所有笔记都敲一遍，我只是一个普通人，没有超凡的记忆和过人的天赋，用自己对编程的热爱，只是做着自己认为的努力。不知道未来如何，但是每改一个bug，每实现一个功能，倍感欣悦，愿新的一年，还是如今的少年，不被社会公司打败，无论多少年，愿亦是如此。