# 第14章 进入实战——开发酷欧天气

&emsp;&emsp;我们将要在本章中编写一个功能较为完整的天气预报程序，学习了这么就久的Android开发，现在终于到了考核验收的时候了。那么第一步我们需要给这个软件起个好听的名字，这里就叫它酷欧天气吧，英文名就叫做Cool Weather。确定好名字之后，下面就开始动手了。  

## 14.1 功能需求及技术可行性分析  
&emsp;&emsp;在开始编码之前，我们需要对程序进行需求分析，想一想酷欧天气中应该具备有哪些功能。将这些功能全部整理出来之后，我们才好动手去一一实现。这里我认为酷欧天气中至少应该具备以下功能：  
- 可以罗列出全国所有的省、市、县；
- 可以查看全国任意城市的天气信息；
- 可以自由地切换城市，去查看其他城市的天气；
- 提供手动刷新以及后台自动更新天气的功能。

&emsp;&emsp;虽然看上去只有4个主要的功能点，但如果想要全部实现这些功能却需要用到UI、网络、数据存储、服务等技术。因此还是非常考验你的综合应用能力的。不过好在这些技术在前面的章节中我们全部都学习过了，只要你学得用心，相信完成这些功能对于你来说并不难。  
&emsp;&emsp;分析完了需求之后，接下来就要进行技术可行性分析了。首先需要考虑的一个问题就是，我们如何才能得到全国省市县的数据信息，以及如何才能获取到每个城市的天气信息。比较遗憾的是，现在网上免费的天气预报接口已经越来越少，很多之前可以使用的接口都慢慢关闭掉了，包括本书第1版中使用的中国天气网的接口。因此，这次我也是特意用心去找了一些更加稳定的天气预报服务，比如彩云天气以及和风天气都非常不错。这两个天气预报服务虽说都是收费的，但它们每天都提供了一定次数的免费天气预报请求。那么简单起见，这里我们就使用和风天气来作为天气预报的数据来源，每天3000次的免费请求对于学习而言已经是相当充足了。  
&emsp;&emsp;解决了天气数据的问题，接下来还需要解决全国省市县数据的问题。同样，现在网上也没有一个稳定的接口可以使用，那么为了方便你的学习，我专门架设了一台服务器用于提供全国所有省市县的数据信息，从而帮你把道路都铺平了。  
&emsp;&emsp;那么下面我们来看一下这些接口的具体用法。比如想要罗列出中国所有的省份，只需要访问如下地址：  
> http://guolin.tech/api/china

&emsp;&emsp;服务器会返回我们一段JSON格式的数据，其中包含了中国所有省份名称以及省份id，如下所示：  
```json
[
  {
    "id": 1,
    "name": "北京"
  },
  {
    "id": 2,
    "name": "上海"
  },
  {
    "id": 3,
    "name": "天津"
  },
  {
    "id": 4,
    "name": "重庆"
  },
  {
    "id": 5,
    "name": "香港"
  },
  {
    "id": 6,
    "name": "澳门"
  },
  {
    "id": 7,
    "name": "台湾"
  },
  {
    "id": 8,
    "name": "黑龙江"
  },
  {
    "id": 9,
    "name": "吉林"
  },
  {
    "id": 10,
    "name": "辽宁"
  },
  {
    "id": 11,
    "name": "内蒙古"
  },
  {
    "id": 12,
    "name": "河北"
  },
  {
    "id": 13,
    "name": "河南"
  },
  {
    "id": 14,
    "name": "山西"
  },
  {
    "id": 15,
    "name": "山东"
  },
  {
    "id": 16,
    "name": "江苏"
  },
  {
    "id": 17,
    "name": "浙江"
  },
  {
    "id": 18,
    "name": "福建"
  },
  {
    "id": 19,
    "name": "江西"
  },
  {
    "id": 20,
    "name": "安徽"
  },
  {
    "id": 21,
    "name": "湖北"
  },
  {
    "id": 22,
    "name": "湖南"
  },
  {
    "id": 23,
    "name": "广东"
  },
  {
    "id": 24,
    "name": "广西"
  },
  {
    "id": 25,
    "name": "海南"
  },
  {
    "id": 26,
    "name": "贵州"
  },
  {
    "id": 27,
    "name": "云南"
  },
  {
    "id": 28,
    "name": "四川"
  },
  {
    "id": 29,
    "name": "西藏"
  },
  {
    "id": 30,
    "name": "陕西"
  },
  {
    "id": 31,
    "name": "宁夏"
  },
  {
    "id": 32,
    "name": "甘肃"
  },
  {
    "id": 33,
    "name": "青海"
  },
  {
    "id": 34,
    "name": "新疆"
  }
]
```
&emsp;&emsp;可以看到，这是一个JSON数组，数组中的每一个元素都代表着一个省份。其中，北京的id是1，上海的id是2。那么如何才能知道某个省内有哪些城市呢？其实也很简单，比如江苏的id是16，访问如下地址即可：  
> http://guolin.tech/api/china/16

&emsp;&emsp;也就是说，只需要将省份id添加到url地址的最后面就可以了，现在服务器返回的数据如下：  
```json
[
  {
    "id": 113,
    "name": "南京"
  },
  {
    "id": 114,
    "name": "无锡"
  },
  {
    "id": 115,
    "name": "镇江"
  },
  {
    "id": 116,
    "name": "苏州"
  },
  {
    "id": 117,
    "name": "南通"
  },
  {
    "id": 118,
    "name": "扬州"
  },
  {
    "id": 119,
    "name": "盐城"
  },
  {
    "id": 120,
    "name": "徐州"
  },
  {
    "id": 121,
    "name": "淮安"
  },
  {
    "id": 122,
    "name": "连云港"
  },
  {
    "id": 123,
    "name": "常州"
  },
  {
    "id": 124,
    "name": "泰州"
  },
  {
    "id": 125,
    "name": "宿迁"
  }
]
```
&emsp;&emsp;这样我们就得到江苏省内所有城市的信息了，可以看到，现在返回的数据格式和刚才查看省份信息时赶回的数据格式是一样的。相信此时你已经可以举一反三了，比如说苏州的id是116，那么想要知道苏州市下又有哪些县和区的时候，只需要访问如下地址：  
> http://guolin.tech/api/china/16/116

&emsp;&emsp;这次服务器返回的数据如下：

```json
[
  {
    "id": 937,
    "name": "苏州",
    "weather_id": "CN101190401"
  },
  {
    "id": 938,
    "name": "常熟",
    "weather_id": "CN101190402"
  },
  {
    "id": 939,
    "name": "张家港",
    "weather_id": "CN101190403"
  },
  {
    "id": 940,
    "name": "昆山",
    "weather_id": "CN101190404"
  },
  {
    "id": 941,
    "name": "吴中",
    "weather_id": "CN101190405"
  },
  {
    "id": 942,
    "name": "吴江",
    "weather_id": "CN101190407"
  },
  {
    "id": 943,
    "name": "太仓",
    "weather_id": "CN101190408"
  }
]
```
&emsp;&emsp;通过这种方式，我们就能把全国所有的省、市、县都罗列出来。那么解决了省市县数据的获取，我们又怎样才能查看到具体的天气信息呢？这就必须要用到每个地区对应的天气id了。观察上面返回的数据，你会发现每个县或区都会有一个weather_id，拿这个id再去访问对应的天气的接口，就能够获取到该地区具体的天气信息了。  
&emsp;&emsp;下面我们来看一下和风天气的接口该如何使用。首先你需要注册一个自己的账号，注册地址是http://guolin.tech/api/weather/register。注册好了之后使用这个账户登录，就能看到自己的API KEY，以及每天剩余的访问次数了。有了API KEY，再配合刚才的weather_id，我们就能获取到任意城市的天气信息了。比如说苏州的weather_id是CN101190401，那么访问如下接口即可查兰苏州的天气信息：  
> http://guolin.tech/api/weather?cityid=CN101190401&key=3906d8568ef8470d943c9765f5d891a8

&emsp;&emsp;返回的数据如下：

```json
{
  "HeWeather": [
    {
      "basic": {
        "cid": "CN101190401",
        "location": "苏州",
        "parent_city": "苏州",
        "admin_area": "江苏",
        "cnty": "中国",
        "lat": "26.07530212",
        "lon": "119.30623627",
        "tz": "+8.00",
        "city": "苏州",
        "id": "CN101190401",
        "update": {
          "loc": "2022-12-19 03:56",
          "utc": "2022-12-19 03:56"
        }
      },
      "update": {
        "loc": "2022-12-19 03:56",
        "utc": "2022-12-19 03:56"
      },
      "status": "ok",
      "now": {
        "cloud": "99",
        "cond_code": "104",
        "cond_txt": "阴",
        "fl": "16",
        "hum": "84",
        "pcpn": "0.0",
        "pres": "1012",
        "tmp": "17",
        "vis": "6",
        "wind_deg": "155",
        "wind_dir": "东南风",
        "wind_sc": "3",
        "wind_spd": "12",
        "cond": {
          "code": "104",
          "txt": "阴"
        }
      },
      "daily_forecast": [
        {
          "date": "2022-12-20",
          "cond": {
            "txt_d": "中雨"
          },
          "tmp": {
            "max": "20",
            "min": "15"
          }
        },
        {
          "date": "2022-12-21",
          "cond": {
            "txt_d": "多云"
          },
          "tmp": {
            "max": "27",
            "min": "14"
          }
        },
        {
          "date": "2022-12-22",
          "cond": {
            "txt_d": "阴"
          },
          "tmp": {
            "max": "17",
            "min": "10"
          }
        },
        {
          "date": "2022-12-23",
          "cond": {
            "txt_d": "阵雨"
          },
          "tmp": {
            "max": "25",
            "min": "15"
          }
        },
        {
          "date": "2022-12-24",
          "cond": {
            "txt_d": "多云"
          },
          "tmp": {
            "max": "27",
            "min": "14"
          }
        },
        {
          "date": "2022-12-25",
          "cond": {
            "txt_d": "阴"
          },
          "tmp": {
            "max": "28",
            "min": "10"
          }
        }
      ],
      "aqi": {
        "city": {
          "aqi": "59",
          "pm25": "32",
          "qlty": "良"
        }
      },
      "suggestion": {
        "comf": {
          "type": "comf",
          "brf": "舒适",
          "txt": "白天不太热也不太冷，风力不大，相信您在这样的天气条件下，应会感到比较清爽和舒适。"
        },
        "sport": {
          "type": "sport",
          "brf": "较不宜",
          "txt": "有较强降水，建议您选择在室内进行健身休闲运动。"
        },
        "cw": {
          "type": "cw",
          "brf": "不宜",
          "txt": "不宜洗车，未来24小时内有雨，如果在此期间洗车，雨水和路上的泥水可能会再次弄脏您的爱车。"
        }
      },
      "msg": "所有天气数据均为模拟数据，仅用作学习目的使用，请勿当作真实的天气预报软件来使用。"
    }
  ]
}
```

&emsp;&emsp;所有天气数据均为模拟数据，仅用作学习目的使用，请勿当作真实的天气预报软件来使用。返回数据的格式大体上就是这个样子，其中status代表请求的状态，ok表示成功。basic中包含城市的一些基本信息，aqi中会包含当前空气质量的情况，now中会包含当前的天气信息，suggestion中会包含一些天气相关的生活建议,daily_forecast中会包含未来几天的天气信息。访问http://guolin.tech/api/weather/doc这个网站可以查看更加详细的文档说明。  
&emsp;&emsp;数据都能获取到了之后，接下来就是JSON解析的工作了，这对于你来说应该很轻松了吧?确定了技术完全可行之后，接下来就可以开始编码了。至于将项目托管到github上这里就不赘述了。  

## 14.2 创建数据库和表  
&emsp;&emsp;从本节开始，我们就要真正地动手编码了，为了要让项目能够有更好的结构，这里需要在com.coolweather.android包下再新建几个包，如图所示：  
![img.png](img.png)

&emsp;&emsp;其中db包用于存放数据库模型相关的代码，gson包用于存放GSON模型相关的代码，service包用于存放服务相关的代码，util包用于存放工具相关的代码。  
&emsp;&emsp;根据14.1节进行的技术可行性分析，第一阶段我们要做的就是创建好数据库和表，这样从服务器获取到的数据才能存储到本地。关于数据库和表的创建方式，我们早在第6章中就已经学过了。那么为了简化数据库操作，这里我准备使用LitePal来管理酷欧天气的数据库。  
&emsp;&emsp;首先需要将项目所需要的各种依赖库进行声明，编辑app/build.gradle文件，在dependencies闭包中添加如下内容：

```groovy
dependencies {

    implementation 'androidx.appcompat:appcompat:1.5.1'
    implementation 'com.google.android.material:material:1.7.0'
    testImplementation 'junit:junit:4.+'
    androidTestImplementation 'androidx.test.ext:junit:1.1.4'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.5.0'
    //https://github.com/guolindev/LitePal
    implementation 'org.litepal.guolindev:core:3.2.3'
    // define a BOM and its version
    implementation(platform("com.squareup.okhttp3:okhttp-bom:4.10.0"))
    // define any required OkHttp artifacts without version
    implementation("com.squareup.okhttp3:okhttp")
    implementation("com.squareup.okhttp3:logging-interceptor")
    // https://mvnrepository.com/artifact/com.google.code.gson/gson
    implementation 'com.google.code.gson:gson:2.10'
    // https://mvnrepository.com/artifact/com.github.bumptech.glide/glide
    implementation 'com.github.bumptech.glide:glide:4.14.2'
    // https://mvnrepository.com/artifact/org.projectlombok/lombok
    compileOnly 'org.projectlombok:lombok:1.18.24'
}
```

&emsp;&emsp;这里声明的4个库我们之前都是使用过的，LitePal用于对数据库进行操作，OkHttp用于进行网络请求，GSON用于解析JSON数据，Glide用于加载和展示图片。酷欧天气将会对这几个库进行综合运用，这里直接一次性将它们都添加进来,lombok是一种java实用工具，可以帮助开发人员消除Java的冗长，尤其是对于简单的Java对象(POJO)。  
&emsp;&emsp;然后我们来设计一下数据库的表结构，表的设计当然是仁者见仁，智者见智，并不是说哪种设计就是最规范最完美的。这里我准备建立3张表：province、city、county，分别用于存放省、市、县的数据信息。对应到实体类中的话，就应该建立Province、City、County这3个类。那么，在db包下新建一个Province类，代码如下所示：  
```java
package com.coolweather.android.db;

import lombok.Getter;
import lombok.Setter;
import org.litepal.crud.LitePalSupport;

/**
 * <p>
 * 省份实体类
 * </p>
 *
 * @author: zj970
 * @date: 2022/12/19
 */
@Getter
@Setter
public class Province extends LitePalSupport {
    private int id;
    private String provinceName;
    private int provinceCode;
}
```
&emsp;&emsp;其中，id是每个实体类中应该有的字段，provinceName记录省的名字，provinceCode记录省的代号。另外，LitePal中的每一个实体类都是必须继承自LitePalSupport而不是DataSupport。接着在db下新建一个City类，代码如下所示:  
```java
package com.coolweather.android.db;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 市实体类
 * </p>
 *
 * @author: zj970
 * @date: 2022/12/19
 */
@Setter
@Getter
public class City extends LitePalSupport {
    private int id;
    /**
     * 市的名字
     */
    private String cityName;
    /**
     * 市的代号
     */
    private int cityCode;
    /**
     * 当前市所属省份的id
     */
    private int provinceId;
}
```
&emsp;&emsp;其中，cityName记录市的名字，cityCode记录市的代号，provinceId记录当前市所属省的id值，然后在db包下新建一个County类，代码如下所示：  

```java
package com.coolweather.android.db;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 城市实体类
 * </p>
 *
 * @author: zj970
 * @date: 2022/12/19
 */
@Getter
@Setter
public class County extends LitePalSupport {
    private int id;
    /**
     * 县名
     */
    private String countyName;
    /**
     * 县所对应的天气id
     */
    private String weatherId;
    /**
     * 当前县所属市的id值
     */
    private int cityId;
}

```
&emsp;&emsp;其中，countyName记录县的名字，weatherId记录县所对应的天气id，cityId记录当前县所属市的id值。可以看到，实体类的内容都非常简单，就是声明了一些需要的字段，并生成相应的getter和setter方法就可以了。  
&emsp;&emsp;接下来需要配置litepal.xml文件。创建一个assets目录，然后新建此文件，并编辑litepal.xml文件中的内容，如下所示：  

```xml
<?xml version="1.0" encoding="utf-8" ?>
<litepal>
    <dname value="cool_weather"></dname>
    <version value="1"></version>
    <list>
        <mapping class="com.coolweather.android.db.Province"/>
        <mapping class="com.coolweather.android.db.City"/>
        <mapping class="com.coolweather.android.db.County"/>
    </list>
</litepal>
```
&emsp;&emsp;这里我们将数据名指定成cool_weather，数据库版本指定成1，并将Province、City和County这3个实体类添加到映射列表当中。最后还需要配置一下LitePalApplication，这里使用自定义Application，代码如下所示：  
```java
package com.coolweather.android.base;

import android.app.Application;
import android.content.Context;
import org.litepal.LitePal;

/**
 * <p>
 *  全局获取Context
 * </p>
 *
 * @author: zj970
 * @date: 2022/12/19
 */
public class MyApplication extends Application {
    public static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        LitePal.initialize(this);
    }
}

```
&emsp;&emsp;最后还需要修改AndroidManifest.xml中的代码，如下所示：

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
          package="com.coolweather.android">

    <application
            android:name="com.coolweather.android.base.MyApplication"
            android:allowBackup="true"
            android:label="@string/app_name"
            android:icon="@mipmap/ic_launcher"
            android:roundIcon="@mipmap/ic_launcher_round"
            android:supportsRtl="true"
            android:theme="@style/Theme.CoolWeather"/>

</manifest>
```
## 14.3 遍历全国省市县数据

&emsp;&emsp;在第阶段中，我们准备把遍历全国省市县的功能加入，这一阶段需要编写的代码量比较大。我们已经知道，全国所有省市县的数据都是从服务器端获取到的，因此这里和服务器的交互是必不可少的，所以我们可以在util包先增加一个HttpUtil类，代码如下所示：  
```java
package com.coolweather.android.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * <p>
 * 网络工具类
 * </p>
 *
 * @author: zj970
 * @date: 2022/12/19
 */
public class HttpUtil {
    public static void sendOkHttpRequest(String address, okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }
}

```
&emsp;&emsp;由于OkHttp的出色封装，这里和服务器进行交互的代码非常简单，仅仅3行就完成了。现在我们发起一条HTTP请求只需要调用sendOkHttpRequest()方法，传入请求地址，并注册一个回调来处理服务器响应就可以了。  
&emsp;&emsp;另外，由于服务器返回的省市县数据都是JSON格式的，所有我们最好再提供一个工具来解析和处理这种数据。在util包下新建一个Utility类，代码如下所示：  
```java
package com.coolweather.android.util;

import android.text.TextUtils;
import com.coolweather.android.db.City;
import com.coolweather.android.db.County;
import com.coolweather.android.db.Province;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * <p>
 * 处理数据
 * </p>
 *
 * @author: zj970
 * @date: 2022/12/19
 */
public class Utility {
    /**
     * 解析和处理服务器返回的省级数据
     * @param response 请求返回体
     * @return
     */
    public static boolean handleProvinceResponse(String response) {
        if (!TextUtils.isEmpty(response)){
            try {
                JSONArray allProvinces = new JSONArray(response);
                for (int i = 0; i < allProvinces.length(); i++) {
                    JSONObject provinceObject = allProvinces.getJSONObject(i);
                    Province province = new Province();
                    province.setProvinceName(provinceObject.getString("name"));
                    province.setProvinceCode(provinceObject.getInt("id"));
                    province.save();
                }
                return true;
            } catch (JSONException e){
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 解析和处理服务器返回的市级数据
     * @param response 请求返回体
     * @param provinceId 省级id
     * @return
     */
    public static boolean handleCityResponse(String response, int provinceId){
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray allCities = new JSONArray(response);
                for (int i = 0; i < allCities.length(); i++) {
                    JSONObject cityObject = allCities.getJSONObject(i);
                    City city = new City();
                    city.setCityName(cityObject.getString("name"));
                    city.setCityCode(cityObject.getInt("id"));
                    city.setProvinceId(provinceId);
                    city.save();
                }
                return true;
            } catch (JSONException e){
                e.printStackTrace();
            }

        }
        return false;
    }

    /**
     * 解析和处理服务器返回的县级数据
     * @param response 请求返回体
     * @param cityId 市id
     * @return
     */
    public static boolean handleCountyResponse(String response, int cityId){
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray allCounties = new JSONArray(response);
                for (int i = 0; i < allCounties.length(); i++) {
                    JSONObject countyObject = allCounties.getJSONObject(i);
                    County county = new County();
                    county.setCountyName(countyObject.getString("name"));
                    county.setWeatherId(countyObject.getString("weather_id"));
                    county.setCityId(cityId);
                    county.save();
                }
                return true;
            } catch (JSONException e){
                e.printStackTrace();
            }
        }
        return false;
    }
}

```

&emsp;&emsp;可以看到，我们提供了handleProvinceResponse()、handleCityResponse()和handleCountyResponse()这3个方法，分别用于解析和处理服务器返回的省级、市级和县级数据。处理的方式都是类似的，先使用JSONArray和JSONObject将数据解析出来，然后组装成实体类对象，再调用save()方法将数据存储到数据库当中。由于这里的JSON数据结构比较简单，我们就不使用GSON来进行解析了。  
&emsp;&emsp;需要准备的工具类就这么多，现在可以开始写界面了。由于遍历全国省市县的功能我们在后面还会复用，因此不写在活动里面，而是写在碎片里面。这样复用的时候直接在布局里面引用碎片就可以了。在res/layout目录中新建choose_area.xml布局，代码如下所示：  
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
              android:orientation="vertical"
              android:background="#fff"
              android:layout_width="match_parent"
              android:layout_height="match_parent">
    <RelativeLayout
        android:layout_width="match_parent"
        android:layout_height="?android:attr/actionBarSize"
        android:background="?android:attr/colorPrimary">
        <TextView
            android:id="@+id/title_text"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_centerInParent="true"
            android:textColor="#fff"
            android:textSize="20sp"/>
        <Button
            android:id="@+id/back_button"
            android:layout_width="25dp"
            android:layout_height="25dp"
            android:layout_marginLeft="10dp"
            android:layout_alignParentLeft="true"
            android:background="@drawable/ic_back"/>
    </RelativeLayout>
    <ListView
        android:id="@+id/list_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>
</LinearLayout>
```

&emsp;&emsp;布局文件的内容并不复杂，我们先是定义了一个头布局来作为标题栏，将布局高度设置为actionBar的高度，背景色设置为colorPrimary。然后在头布局中放置了一个TextView用于显示标题内容放置了Button用于执行返回操作。这里之所以要自己定义标题栏，是因为碎片中最好不要直接使用ActionBar或Toolbar，不然在复用的时候可能会出现一些你不想看到的效果。  
&emsp;&emsp;接下来在头布局的下面定义了一个ListView，省市县的数据就将显示在这里。之所以这次使用了ListView，是因为它会自动给每个子项之间添加一条分割线，而如果使用RecyclerView想实现同样的功能则会比较麻烦，这里我们总是选择最优的实现方案。接下来也是最关键的一步，我们需要编写用于遍历省市县数据的碎片了。新建ChooseAreaFragment继承自Fragment，代码如下所示：  
```java
package com.coolweather.android;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.coolweather.android.db.City;
import com.coolweather.android.db.County;
import com.coolweather.android.db.Province;
import com.coolweather.android.util.HttpUtil;
import com.coolweather.android.util.Utility;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import org.litepal.LitePal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author: zj970
 * @date: 2022/12/19
 */
public class ChooseAreaFragment extends Fragment {
    public static final int LEVEL_PROVINCE = 0;
    public static final int LEVEL_CITY = 1;
    public static final int LEVEL_COUNTY = 2;
    private TextView titleText;
    private Button backButton;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> dataList = new ArrayList<String>();
    private static final String URL = "http://guolin.tech/api/china";
    private ProgressDialog progressDialog;
    /**
     * 省列表
     */
    private List<Province> provinceList;
    /**
     * 市列表
     */
    private List<City> cityList;
    /**
     * 县列表
     */
    private List<County> countyList;

    /**
     * 选中的省份
     */
    private Province selectedProvince = null;
    /**
     * 选中的城市
     */
    private City selectedCity = null;
    /**
     * 选中的级别
     */
    private int currentLevel;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.choose_area, container, false);
        titleText = view.findViewById(R.id.title_text);
        backButton = view.findViewById(R.id.back_button);
        listView = view.findViewById(R.id.list_view);
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, dataList);
        listView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (currentLevel == LEVEL_PROVINCE) {
                    selectedProvince = provinceList.get(position);
                    queryCities();
                } else if (currentLevel == LEVEL_CITY) {
                    selectedCity = cityList.get(position);
                    queryCounties();
                }
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentLevel == LEVEL_COUNTY) {
                    queryCities();
                } else if (currentLevel == LEVEL_CITY) {
                    queryProvinces();
                }
            }
        });
        queryProvinces();
    }

    /**
     * 查询全国所有的省，优先从数据库查询，如果没有查询到再去服务器上查询
     */
    private void queryProvinces() {
        titleText.setText("中国");
        backButton.setVisibility(View.GONE);
        provinceList = LitePal.findAll(Province.class);
        if (provinceList.size() > 0) {
            dataList.clear();
            for (Province province : provinceList) {
                dataList.add(province.getProvinceName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEVEL_PROVINCE;
        } else {
            String address = URL;
            queryFromServer(address, "province");
        }
    }

    /**
     * 查询选中省内所有的市，优先从数据库查询，，如果没有查询到再去服务器上查询
     */
    private void queryCities() {
        titleText.setText(selectedProvince.getProvinceName());
        backButton.setVisibility(View.VISIBLE);
        cityList = LitePal.where("provinceid = ?", String.valueOf(selectedProvince.getId())).find(City.class);

        if (cityList.size() > 0) {
            dataList.clear();
            for (City city : cityList) {
                dataList.add(city.getCityName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEVEL_CITY;
        } else {
            int provinceCode = selectedProvince.getProvinceCode();
            String address = URL + "/" + provinceCode;
            queryFromServer(address, "city");
        }
    }

    /**
     * 查询选中市内所有县，优先从数据库查询，如果没有查询到再去服务器上查询
     */
    private void queryCounties() {
        titleText.setText(selectedCity.getCityName());
        backButton.setVisibility(View.VISIBLE);
        countyList = LitePal.where("cityid = ?", String.valueOf(selectedCity.getId())).find(County.class);
        if (countyList.size() > 0) {
            dataList.clear();
            for (County county : countyList) {
                dataList.add(county.getCountyName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEVEL_COUNTY;
        } else {
            int provinceCode = selectedProvince.getProvinceCode();
            int cityCode = selectedCity.getCityCode();
            String address = URL + "/" + provinceCode + "/" + cityCode;
            queryFromServer(address, "county");
        }
    }

    /**
     * 根据传入的地址和类型从服务器上查询省市县数据
     */
    private void queryFromServer(String address, final String type){
        showProgressDialog();
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                //通过runOnUiThread()方法回到主线程处理逻辑
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(getActivity(), "加载失败",Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responseText = response.body().string();
                boolean result = false;
                if ("province".equals(type)) {
                    result = Utility.handleProvinceResponse(responseText);
                } else if ("city".equals(type)){
                    result = Utility.handleCityResponse(responseText,selectedProvince.getId());
                } else if ("county".equals(type)){
                    result = Utility.handleCountyResponse(responseText,selectedCity.getId());
                }

                if (result){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();
                            if("province".equals(type)){
                                queryProvinces();
                            } else if("city".equals(type)){
                                queryCities();
                            } else if("county".equals(type)){
                                queryCounties();
                            }
                        }
                    });
                }
            }
        });
    }

    /**
     * 显示进度对话框
     */
    private void showProgressDialog(){
        if (progressDialog != null){
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("正在加载...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    /**
     * 关闭进度对话框
     */
    private void closeProgressDialog(){
        if (progressDialog!= null){
            progressDialog.dismiss();
        }
    }
}

```

&emsp;&emsp;这个类里的代码虽然非常多，可是逻辑却不复杂，我们来慢慢理一下。在onCreate()方法中先是获取到了一些控件的实例，然后去初始化了ArrayAdapter，并将它设置为ListView的适配器。接着在onActivityCreated()方法中给ListView和Button设置了点击事件，到这里我们的初始化工作就算是完成了。  
&emsp;&emsp;在onCreated()方法的最后，调用了queryProvinces()方法，也就是从这里开始加载省级数据。queryProvinces()方法首先会将有布局的标题设置成中国，将返回的按钮隐藏起来，因为省级列表已经不能再返回了。然后调用LitePal的查询接口来从数据库中读取省级数据，如果读取到了就直接将数据显示到界面上，如果没有读取到就按照14.1节讲述的接口组装出一个请求地址，然后调用queryFromServer()方法来从服务器上查询数据。  
&emsp;&emsp;queryFromServer()方法中会调用HttpUtil的sendOkHttpRequest()方法来向服务器发送请求，响应的数据会回调到onResponse()方法中，然后我们在这里去调用Utility的handleProvinceResponse()方法来解析和处理服务器返回的数据，并存储到数据库中。接下来的一步很关键，在解析和处理完数据之后，我们再次调用了queryProvinces()方法重新加载省级数据，由于queryProvinces()方法牵扯到了UI操作，因此必须要在主线程中调用，这里借助了runOnUiThread()方法来实现从子线程切回主线程。现在数据库中已经存在了数据，因此调用queryProvinces()就会直接将数据显示到界面上。  
&emsp;&emsp;当你点击了某个省份的时候会进入到ListView的onItemClick()方法中，这个时候会根据当前的级别来判断是去调用queryCities()方法还是queryCounties()方法，queryCities()方法是去查询市级数据，而queryCounties()方法是去查询县级数据，这两个方法内部的流程和queryProvinces()方法基本相同，这里就不重复讲解了。  
&emsp;&emsp;另外还有一点需要注意，在返回按钮的点击事件里，会对当前ListView的列表级别进行判断。如果当前是县级列表，那么就返回市级列表，如果当前是市级列表，那么就返回到省级列表。当返回到省级列表时，返回按钮会自动隐藏，从而也就不需要再进一步的处理了。  
&emsp;&emsp;这样我们就把遍历全国省市县的功能完成了，可是碎片是不能直接显示到界面上，因此我们还需要把它添加活动里面才行。修改activity_main.xml中的代码，如下所示：  
```xml
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:tools="http://schemas.android.com/tools"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        tools:context=".MainActivity">
    <fragment
        android:id="@+id/chose_area_fragment"
        android:name="com.coolweather.android.ChooseAreaFragment"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>

</FrameLayout>
```

&emsp;&emsp;布局文件很简单，只是定义了一个FrameLayout，然后将ChooseAreaFragment添加进来，并让它充满整个布局。另外，我们刚才在碎片的布局里面已经自定义了一个标题栏，因此就不再需要原生的ActionBar了，修改res/values/theme.xml中的代码，如下所示：  

```xml
<resources xmlns:tools="http://schemas.android.com/tools">
    <!-- Base application theme. -->
    <style name="Theme.CoolWeather" parent="Theme.MaterialComponents.DayNight.NoActionBar">
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

&emsp;&emsp;现在第二阶段的开发工作也完成得差不多了，我们可以运行一下运行看一看效果。在运行一下之前加入声明程序所需的权限。运行效果如下：  

- 省份显示图
![img_1.png](img_1.png)

- 市级显示图(江苏省)

![img_2.png](img_2.png)

- 县级显示图(苏州市)

![img_3.png](img_3.png)