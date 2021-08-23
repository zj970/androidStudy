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