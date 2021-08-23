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





## 主程序

```java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//SpringBootApplication:标注这个类是一个springboot的应用
@SpringBootApplication
public class MyPro02Application {

    public static void main(String[] args) {
        //将springboot应用启动
        SpringApplication.run(MyPro02Application.class, args);
        //System.out.println("helloWorld");
    }
}
```

- 注解

  - ```java
    @SpringBootConfiguration 
    //SpringBoot的配置
    	@Configuration
    	//spring配置类
    		@Component
    		//是一个spring的组件
    
    @EnableAutoConfiguration
    ```