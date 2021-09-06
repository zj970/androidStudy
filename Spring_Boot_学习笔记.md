# 微服务阶段

MVC三层架构 MVVM 微服务架构



业务：service: userService===>模块

springmvc, controller ==>接口

http:rpc

消息队列



# 原理初探

## 自动配置

### pom.xml

- spring-boot-dependencies : 核心依赖在父工程中
- 我们在写或者引入一些Springboot依赖的时候，不需要指定版本，就是因为有这些版本仓库



### 启动器

- ```XML
   <dependency>
  <!--            web依赖：tomcat,dispatcherServlet,xml-->
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-starter-web</artifactId>
          </dependency>
  ```

- 启动器：就是Springboot的启动场景；

- 比如spring-boot-starter-web,他就会帮我们自动导入web环境所有的依赖！

- springboot会将所有的功能场景，都变成一个个的启动器

- 我们要使用什么功能，就只需要找到对应的启动器就可以了 starter





### 主程序

```java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//SpringBootApplication:标注这个类是一个springboot的应用:启动类下的所有资源被导入
@SpringBootApplication
public class MyPro02Application {

    public static void main(String[] args) {
        //将springboot应用启动
        SpringApplication.run(MyPro02Application.class, args);
        //System.out.println("helloWorld");
    }
}
```

SpringApplication这个类：

1. 推断应用的类型是普通的项目还是Web项目

2. 查找并加载所有可用初始化器，设置到initializers属性中

3. 找出所有的应用程序监听器，设置到listeners属性中

4. 推断并设置main方法的定义类，找到运行的主类

   JavaConfig @Configuration @Bean

   Docker:进程

- 注解

  - ```java
    @SpringBootConfiguration 
    //SpringBoot的配置
    	@Configuration
    	//spring配置类
    		@Component
    		//是一个spring的组件
    
    @EnableAutoConfiguration
    //自动配置
    	@AutoConfigurationPackage
    	//自动配置包
    		@Import({Registrar.class})
    		//导入选择器、包注册
    	@Import({AutoConfigurationImportSelector.class})
    	//自动导入选择
    	   List<String> configurations = this.getCandidateConfigurations(annotationMetadata, attributes);//获取所有的配置
    ```

  - 获取候选配置

    ```java
    protected List<String> getCandidateConfigurations(AnnotationMetadata metadata, AnnotationAttributes attributes) {
        List<String> configurations = SpringFactoriesLoader.loadFactoryNames(this.getSpringFactoriesLoaderFactoryClass(), this.getBeanClassLoader());
        Assert.notEmpty(configurations, "No auto configuration classes found in META-INF/spring.factories. If you are using a custom packaging, make sure that file is correct.");
        return configurations;
    }
    //META-INF/spring.factories :自动配置的核心文件
    ```

![image-20210823152701880](C:\Users\周建\AppData\Roaming\Typora\typora-user-images\image-20210823152701880.png)

```java
Properties properties = PropertiesLoaderUtils.loadProperties(resource);
//所有资源加载到配置类中
```

结论：Springboot所有自动配置都是在启动的时候扫描并加载:spring.fa ctories所有的自动配置类都在这里面，但是不一定生效，要判断条件是否成立，只要导入了对应的start,就有对应的启动器，有了启动器，我们自动装配就会生效，就配置成功！

1. springboot在启动的时候，从类路径下 /META-INF/spring.factories获取指定的值；
2. 将这些自动配置的类导入容器，自动配置就会生效，帮我们进行自动配置！
3. 以前我们需要自动配置的东西，现在springboot帮我们做了！
4. 整个javaEE ,解决方案和自动配置的东西都在spring-boot-a下spring-boot-2.3.2.RELEASE.jar这个包下
5. 它会把所有需要导入的组件，以类名的方式返回，这些组件就会被添加到容器；
6. 容器中也会存在非常多的xxxAutoConfiguration的文件，就是这些类给容器中导入了这个场景需要的所有组件，并自动配置，@Configuration，JavaConfig！
7. 有了自动配置类，免去了我们手动编写配置文件的工作





## SpringBoot配置

springboot使用一个全局的配置文件，配置文件名称是固定的

- application.properties
  - 语法结构： key=value
- application.yml(yaml语法)
  - 语法结构：key : 空格value

配置文件的作用：修改SpringBoot自动设置的默认值，因为SpringBoot在底层都给我们自动配置了

properties：只能存键值对

.yml

```yaml
server:
  port: 8081
  #可以存对象
  #对空格的要求十分高
  #可以注入到我们的配置类中
  student:
    name: zz
    age: 3
    #行内写法
    student: {name: zz,age: 3}
    #数组
    pets:
      - cat
      - dog
      - pig

    pet: [cat,dog,pig]
```

yaml可以直接给实体类赋值

```java
/*下面这个需要配置
ConfigurationProperties作用：将配置文件中配置的每一个属性的值，映射到这个组件中；
告诉SpringBoot将本类中所有属性和配置文件中相关的配置进行绑定
参数(prefix = "person")：将配置文件中的person下面的所有属性也一一对应
只有这个组件是容器中的组件，才能使用容器提供的@ConfigurationProperties功能 */
@ConfigurationProperties(prefix = "person")
public class Person{}
//注册bean
@Component
//单元测试
@SpringBootTest
class MyPro02ApplicationTests {

    //自动装配 必须有
    @Autowired
    private Dog dog;
    @Autowired
    private Person person;
    @Test
    void contextLoads() {
        System.out.println(dog);
        System.out.println(person);
    }
}
```



```
@PropertySource(value = "classpath:zj.properties")
//加载指定的配置文件
//javaConfig绑定自己配置文件的值
@Validated
//数据校验
```

JSR303校验数据

![image-20210823182707559](C:\Users\周建\AppData\Roaming\Typora\typora-user-images\image-20210823182707559.png)

### 多配置环境

springboot的多环境配置：可以选择激活

```yaml
server:
  port: 8081
spring:
  profiles:
    active: test

---
server:
  port: 8082
spring:
  profiles: dev

---
server:
  port: 8083
spring:
  profiles: test
#配置文件到底能写什么----联系----spring.factories
#XXXPropreties ---- XXXAutoConfiguration 配置文件绑定
#配置文件和类进行绑定
```

自动装配的原理

1. SpringBoot启动会加载大量的自动配置类
2. 我们看我们需要的功能有没有在SpringBoot默认写好的自动配置类当中；
3. 我们再来看这个自动装置类中到底配置了哪些组件；（只要我们要用的组件存在在其中，我们就不需要再手动配置了）
4. 给容器中自动装置类添加组件的时候，会从properties类中获取某些属性。我们只需要在配置文件中指定这些属性的值即可；
5. xxxxAutoConfiguration:自动装置类；给容器中添加组件
6. xxxxProperties:封装配置文件中相关属性；

Debug: true查看配置类 那些自动配置类生效，那些没有生效









# SpringBoot Web开发

jar:webapp!

自动装配

springboot 到底帮我们配置了什么？我们能不能进行修改？能修改哪些东西？能不能扩展？

- xxxxAutoConfiguration ...向容器中自动配置组件
- xxxxProperties:自动装置类，装配配置文件中自定义的一些内容



要解决的问题：

- 导入静态资源...
- 首页
- jsp,模板引擎Thymeleaf
- 装配扩展SpringMVC
- 增删改查
- 拦截器
- 国际化



## 静态资源

```java
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
			if (!this.resourceProperties.isAddMappings()) {
				logger.debug("Default resource handling disabled");
				return;
			}
			Duration cachePeriod = this.resourceProperties.getCache().getPeriod();
			CacheControl cacheControl = this.resourceProperties.getCache().getCachecontrol().toHttpCacheControl();
			if (!registry.hasMappingForPattern("/webjars/**")) {
				customizeResourceHandlerRegistration(registry.addResourceHandler("/webjars/**")
						.addResourceLocations("classpath:/META-INF/resources/webjars/")
						.setCachePeriod(getSeconds(cachePeriod)).setCacheControl(cacheControl));
			}
			String staticPathPattern = this.mvcProperties.getStaticPathPattern();
			if (!registry.hasMappingForPattern(staticPathPattern)) {
				customizeResourceHandlerRegistration(registry.addResourceHandler(staticPathPattern)
						.addResourceLocations(getResourceLocations(this.resourceProperties.getStaticLocations()))
						.setCachePeriod(getSeconds(cachePeriod)).setCacheControl(cacheControl));
			}
		}
```

什么是webjars?

http://localhost:8023/webjars/jquery/3.6.0/jquery.js

```java
private static final String[] CLASSPATH_RESOURCE_LOCATIONS = { "classpath:/META-INF/resources/",
      "classpath:/resources/", "classpath:/static/", "classpath:/public/" };
//5个可识别静态资源位置
```



```yaml
spring:
  mvc:
    static-path-pattern:
    //自定义的资源路径
```

总结：

1. 在springboot,我们可以使用以下方式处理静态资源
   - webjars localhost:8080/webjars/
   - public , static, /**, resources
2. 优先级：resources>static>public





### 首页定制



在templates目录下的所有页面，只能通过controller来跳转！（这个需要模板引擎的支持thymeleaf）

public、static等下面的index.html也可以

```yaml
#关闭默认图标
spring:
	mvc:
	favicon:
	enabled: false
```

1. 首页配置：
   1. 所有页面的静态资源都需要使用thymeleaf接管；
   2. url :  @{}
   
2. 页面国际化：
   1. 我i们需要配置i18n文件
   2. 我们如果需要在项目中进行按钮自动切换，我们需要自定义一个组件LocaleResolver
   3. 记得将自己写的组件配置到Spring容器 @Bean
   4. #{}
   
3. 登录+拦截器

4. 员工列表展示

   1. 提取公共页面

      1. ```html
         替换：
         	<div th:replace="~{commons/commons::topBar}"></div>
         ```

      2. ```html
         提取：
         <nav class="navbar navbar-dark sticky-top bg-dark flex-md-nowrap p-0" th:fragment="topBar"></nav>
         ```

      3. ```html
         插入(在view层多加一层)：
         <div th:insert="~{commons/commons::sideBar(active='list.html')}"></div>
         ```

      4. 如果要传递参数，可以直接使用()传参，接受判断即可

   2. 列表循环显示

5. 添加员工

   1. 按钮提交
   2. 跳转到添加页面
   3. 添加成功
   4. 返回首页
   
6. CRUD搞定

7. 404



前端：

- 模板：别人写好的，我们拿来改成自己需要的
- 框架：组件：自己手动组合拼接！ Bootstrap,Layui,semantic-ui
  - 栅格系统



1. 前端页面
2. 设计数据库
3. 前端自动运行，独立化工程
4. 数据接口如何对接：json,对象all in one!
5. 前后端联调测试



1. 有一套自己熟悉的后台模板：工作必要！X-admin

2. 前端界面：至少自己能够通过前端框架，组合出来一个网站页面

   -index

   -about

   -blog

   -post

   -user

3. 让这个网站能够独立运行！





# 知识回顾

- SpringBoot是什么？
- 微服务
- 探究源码  自动装配原理
- 配置 yaml
- 多文档环境切换
- 静态资源映射
- Thymeleaf th:XXX
- SpringBoot 如何扩展MVC     javaconfig
- 如何修改SpringBoot的默认配置
- CRUD
- 国际化
- 拦截器
- 定制首页，错误页



新的：

- JDBC
- Mybatis
- Druid
- Shiro:安全
- Spring Security:安全
- 异步任务~，邮件
- Swagger
- Dubbo+Zookeepe

在yaml或者properties配置文件中的配置属性，注入Configuration容器中，首先要注册Bean,再加上@ConfigurationProperties注解表示绑定成功（参数prefix = "spring.datasources"）



# DATA

注意：yaml文件非常敏感缩进，层级

application.yaml配置文件：

```yaml	
spring:
  datasource:
    username: root
    password: admin
    #假如时区报错了，就增加一个时区的配置serverTimezone=UTC
    url: jdbc:mysql://localhost:3306/pet?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8
    driver-class-name: com.mysql.cj.jdbc.Driver
    type: com.alibaba.druid.pool.DruidDataSource
    #SpringBoot默认是不注入这些的，需要自己绑定
    #druid数据源专有配置
    initialSize: 5
    minIdle: 5
    maxActive: 20
    maxWait: 60000
    timeBetweenEvictionRunsMillis: 60000
    minEvictableIdleTimeMillis: 300000
    validationQuery: SELECT 1 FROM DUAL
    testWhileIdle: true
    testOnBorrow: false
    testOnReturn: false
    poolPreparedStatements: true

    #配置监控统计拦截的filters，stat：监控统计、log4j：日志记录、wall：防御sql注入
    #如果允许报错，java.lang.ClassNotFoundException: org.apache.Log4j.Properity
    #则导入log4j 依赖就行
    filters: stat,wall,log4j
    maxPoolPreparedStatementPerConnectionSize: 20
    useGlobalDataSourceStat: true
    connectionProperties: druid.stat.mergeSql=true;druid.stat.slowSqlMillis=500
```



## Mybatis

整合包

mybatis-spring-boot-starter



M：数据和业务

V：HTML

C:  交接



1. 导入包
2. 配置文件
3. Mybatis配置
4. 编写sql
5. service层调用dao层
6. controller调用service层





## SpringSecurity(安全)

在Web开发中，安全第一位！ 过滤器，拦截器~

功能需求：否

做网站：安全应该在什么时候考虑？设计之初！

- 漏洞，隐私泄露~
- 架构
  - shiro
  - SpringSecurity

认证，授权

- 功能权限

- 访问权限

- 菜单权限

- ....拦截器，过滤器；大量的原生代码~冗余

  MVC -- Spring ---SpringBoot--		框架思想





AOP:横切编程， 配置类



### 简介

Spring Security是针对Spring项目的安全框架，也是Spring Boot底层安全模块默认的技术选型，他可以实现强大的Web安全控制，对于安全控制，我们仅需要引入 spring-bbot-sarter-security模块,进行少量的配置，即可实现强大的安全管理！

记住几个类：

- WebSecurityConfigurerAdapter:自定义Secuity策略
- AuthenticationManagerBuilder:自定义认证策略
- @EnableWebSecurity:开启WedSecurity模式，  @Enablexxxx开启某个功能

Spring Security的主要目标是“认证”和“授权”（访问控制）。

“认证”（Authentication）

“授权”（Authorization）

这个概念是通用的，而不是只在Spring Security中存在。





## Shiro

- 什么是Shiro?
  - Apache Shiro是一个Java的安全（权限）框架。
  - Shiro可以非常容易开发足够好的应用，其不仅可以用在JavaSE环境，也可以用在JavaEE环境。
  - Shiro可以完成，认证，授权，加密，会话管理，Web集成，缓存等。
  - 下载地址：http://shiro.apache.org/

1. 导入依赖

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <project xmlns="http://maven.apache.org/POM/4.0.0"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
       <parent>
           <artifactId>SpringBootShiro</artifactId>
           <groupId>org.example</groupId>
           <version>1.0-SNAPSHOT</version>
       </parent>
       <modelVersion>4.0.0</modelVersion>
   
       <artifactId>hello-shiro</artifactId>
   
       <properties>
           <maven.compiler.source>8</maven.compiler.source>
           <maven.compiler.target>8</maven.compiler.target>
       </properties>
   
       <dependencies>
           <dependency>
               <groupId>org.apache.shiro</groupId>
               <artifactId>shiro-core</artifactId>
               <version>1.8.0</version>
           </dependency>
   
           <dependency>
               <groupId>org.slf4j</groupId>
               <artifactId>jcl-over-slf4j</artifactId>
               <version>2.0.0-alpha5</version>
           </dependency>
   
           <dependency>
               <groupId>org.slf4j</groupId>
               <artifactId>slf4j-log4j12</artifactId>
               <version>2.0.0-alpha5</version>
           </dependency>
   
           <dependency>
               <groupId>log4j</groupId>
               <artifactId>log4j</artifactId>
               <version>1.2.17</version>
           </dependency>
   
   
       </dependencies>
   
   </project>
   ```

2. 配置文件

   1. log4j.properties

      ```properties
      log4j.rootLogger=INFO, stdout
      
      log4j.appender.stdout=org.apache.log4j.ConsoleAppender
      log4j.appender.stdout.layout=org.apache.log4j.PatternLayout
      log4j.appender.stdout.layout.ConversionPattern=%d %p [%c] - %m %n
      
      # General Apache libraries
      log4j.logger.org.apache=WARN
      
      # Spring
      log4j.logger.org.springframework=WARN
      
      # Default Shiro logging
      log4j.logger.org.apache.shiro=INFO
      
      # Disable verbose logging
      log4j.logger.org.apache.shiro.util.ThreadContext=WARN
      log4j.logger.org.apache.shiro.cache.ehcache.EhCache=WARN
      ```

   2. shiro.ini

      ```ini
      [users]
      # user 'root' with password 'secret' and the 'admin' role
      root = secret, admin
      # user 'guest' with the password 'guest' and the 'guest' role
      guest = guest, guest
      # user 'presidentskroob' with password '12345' ("That's the same combination on
      # my luggage!!!" ;)), and role 'president'
      presidentskroob = 12345, president
      # user 'darkhelmet' with password 'ludicrousspeed' and roles 'darklord' and 'schwartz'
      darkhelmet = ludicrousspeed, darklord, schwartz
      # user 'lonestarr' with password 'vespa' and roles 'goodguy' and 'schwartz'
      lonestarr = vespa, goodguy, schwartz
      
      # -----------------------------------------------------------------------------
      # Roles with assigned permissions
      #
      # Each line conforms to the format defined in the
      # org.apache.shiro.realm.text.TextConfigurationRealm#setRoleDefinitions JavaDoc
      # -----------------------------------------------------------------------------
      [roles]
      # 'admin' role has all permissions, indicated by the wildcard '*'
      admin = *
      # The 'schwartz' role can do anything (*) with any lightsaber:
      schwartz = lightsaber:*
      # The 'goodguy' role is allowed to 'drive' (action) the winnebago (type) with
      # license plate 'eagle5' (instance specific id)
      goodguy = winnebago:drive:eagle5
      ```

      Spring Secutiry~类似

```java
//获取当前的用户对象 Subject
Subject currentUser = SecurityUtils.getSubject();
//通过当前用户拿到Session
Session session = currentUser.getSession();
currentUser.isAuthenticated();//认证
currentUser.getPrincipal();//获得当前用户的认证
currentUser.hasRole("schwartz");//是否拥有这个角色
currentUser.isPermitted("lightsaber:wield");//获得当前权限，根据参数不同，产生不同的效果
   //注销
        //all done - log out!
        currentUser.logout();

```

SpringBoot中集成







# 任务

异步任务~

定时任务~ 表达式

邮件发送~

```
TaskScheduler 任务调度者
TaskExecutor 任务执行者

@EnableAsync    //开启异步注解功能
@EnableScheduling   //开启定时功能的注解

@Scheduled //什么时候执行
```

cron:

- 计划任务，是任务在约定的时间执行已经计划好的工作，这是表面的意思。在Linux中，我们经常用到 cron 服务器来完成这项工作。cron服务器可以根据配置文件约定的时间来执行特定的任务。

  ```java
  //30 15 10 * * ? 每天10点15分30秒执行 一次
  //30 0/5 10,18 * * ? 每天10点和18点，每个5分钟执行一次
  //0 15 10 ? * 1-6 每个月的周一到周六10点15分执行一次
  ```



# 分布式Dubbo + Zookeeper + SpringBoot

|阿里云|腾讯云|华为云   140~  3000~



RPC

HTTP SpringCloud

RPC两个核心模块：通讯、序列化。

序列化：数据传输需要转换（Netty框架）

Apache Dubbo:是一款搞性能的、轻量级的开源java RPC框架，它提供了三大核心能力：==面向接口的远程方法调用==，==智能容错和负载均衡==，==以及服务自动注册和发现==。





zookeeper:	注册中心

dubbo-admin:是一个监控管理后台，查看我们注册了哪些服务，哪些服务被消费了

Dubbo:	jar包 	



```properties
       <!--导入依赖-->
        <!--dubbo-->
        <dependency>
            <groupId>org.apache.dubbo</groupId>
            <artifactId>dubbo-spring-boot-starter</artifactId>
            <version>3.0.2.1</version>
        </dependency>
        <!--zookeeper_zkclient-->
        <!-- https://mvnrepository.com/artifact/com.github.sgroschupf/zkclient -->
        <dependency>
            <groupId>com.github.sgroschupf</groupId>
            <artifactId>zkclient</artifactId>
            <version>0.1</version>
        </dependency>


        <!--日志会冲突-->
        <!--引入zookeeper-->
        <dependency>
            <groupId>org.apache.curator</groupId>
            <artifactId>curator-framework</artifactId>
            <version>5.1.0</version>
        </dependency>
        <dependency>
            <groupId>org.apache.curator</groupId>
            <artifactId>curator-recipes</artifactId>
            <version>5.1.0</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/org.apache.zookeeper/zookeeper -->
        <dependency>
            <groupId>org.apache.zookeeper</groupId>
            <artifactId>zookeeper</artifactId>
            <version>3.7.0</version>
        </dependency>

        <!--排除这个slf4j-log4j12-->
        <!-- https://mvnrepository.com/artifact/org.slf4j/slf4j-log4j12 -->
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-log4j12</artifactId>
            <version>2.0.0-alpha5</version>
        </dependency>
        <dependency>
            <groupId>com.google.code.findbugs</groupId>
            <artifactId>jsr305</artifactId>
            <version>3.0.2</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/org.apache.curator/curator-x-discovery -->
        <dependency>
            <groupId>org.apache.curator</groupId>
            <artifactId>curator-x-discovery</artifactId>
            <version>5.1.0</version>
        </dependency>
```

步骤：

前提：	zookeeper服务已开启

1. 提供者提供服务
   1. 导入依赖
   2. 配置注册中心的地址，以及服务发现名，和要扫描的包
   3. 在想要被注册的服务上面~增加一个注解@DubboService
2. 消费者如何消费
   1. 导入依赖
   2. 配置注册中心的地址，配置自己的服务名
   3. 从远程注入服务！@DubboReference











# 回顾

架构

- 三层架构 + MVC
    架构　－－＞　解耦

- 开发架构
    	Spring
    		IOC  AOP
    		IOC： 控制反转
    		AOP： 切面(本质，动态代理)
    		为了解决什么？不影响业务本来的情况下，实现动态增加功能，大量应用在日志，事务......等
    		Spring是一个轻量级的java开源框架，容器
    		目的： ==解决企业开发的复杂性问题==
    		配置文件比较复杂
    	SpringBoot
    		SpringBoot并不是新东西，就是Spring的升级版
    		新一代JavaEE的开发标准，开箱即用
    		自动装配了很多底层逻辑
    		特性：==约定大于配置==

-   微服务架构--> 新架构
    模块化、功能化。
    用户、支付、签到、娱乐.....
    人多余多：一台服务器解决不了；横向解决;增加服务器

    假设A服务器占用98%资源，B服务器只占用了10%。==负载均衡==
        

将原来的整体项目，分成模块化，用户就是一个单独的项目，签到也是一个单独的项目，项目和项目之间需要通信，如何通信？---RPC
 用户非常多，而签到十分少|  给用户多 一点服务器，给签到少一点服务器！

微服务架构问题？

分布式架构会遇到的四个核心问题？

1. 这么多服务，客户端改如何去访问？
2. 这么多服务，服务之间如何进行通信？
3. 这么多服务 ，如何治理？
4. 服务器挂了，怎么办？



解决方案：

​	SpringCloud,是一套生态，就是来解决以上分布式架构的4个问题

​	想使用SpringCloud，必须要掌握SpringBoot,因为SpringCloud是基于SpringBoot;



- SpringCloud NetFlix 

1. Api网关，zuul组件
2. Feign --> httpClient ---> http的通信方式，同步并阻塞
3. 服务注册与发现，Eureka
4. 熔断机制，Hystrix

2018年年底，NetFlix宣布无限期停止维护。生态不再维护，就会脱结

- Apache Dubbo zookeeper,第二套解决系统
  - API：没有
  - Dubbo是一个搞性能的基于Java实现的RPC通信框架！
  - 服务注册与发现，zookeeper:动物园管理者(Hadoop,Hive)
- SpringCloud Alibaba 一站式解决方案
- 服务网格：下一代微服务标准 ，Server Mesh
  - 代表解决方案:	istio

==解决的问题==

1. API网关，服务路由
2. HTTP，RPC，异步调用
3. 服务注册与发现，高可用
4. 熔断机制，服务降级

==网络是不可靠的==



