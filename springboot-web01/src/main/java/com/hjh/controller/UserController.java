package com.hjh.controller;

import cn.hutool.core.io.IoUtil;
import com.hjh.bean.User;
import com.hjh.service.UserService;
import com.hjh.service.impl.UserServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
//总步骤：
//1.创建学生类，用来封装数据，创建controller类，并添加@RestController注解用来响应数据，并返回数据给前端，
//创建方法，添加@RequestMapping来指定请求路径，并编写相关业务逻辑(包括读取数据，处理数据，返回数据)。
//2.发现在一个Controller类中，定义了所有功能太过繁杂，将功能进行拆分，将功能进行封装，分成三层结构(控制层，
// 业务逻辑层，数据访问层)，此时将功能封装成三个接口，并进行实现，类与类之间进行相互调用(存在依赖关系)。
//3.为了高内聚低耦合，不能直接new对象，调用它的方法，需要使用Spring容器管理对象。使用@Component在【类上】注解
// 将被调用的类，会自动创建bean对象添加到Spring容器中，用@Autowired在【需要new对象】注解进行注入。
//4.在注入的时候，有三种方法(具体看需求)。
//5.当分层时，创建的接口的实现类有多个实现类，此时需要进行优先级配置，三种方法。

//访问路径：http://localhost:8080/user.html后会调mounted方法，会调用list方法，会返回数据给前端。






//controller：控制层，接收前端发送的请求，对请求进行处理，并响应数据。
//service：业务逻辑层，处理具体的业务逻辑。
//dao：数据访问层(DataAccessObject）（持久层），负责数据访问操作，包括数据的增、删、改、查。

//分了三层结构后发现，controller层和service层之间存在依赖关系，controller层需要new对象去调用service层，service和dao层一样
//不符合高内聚低耦合，此时需要一个容器来管理对象，这个容器就是Spring容器，Spring容器会自动管理对象，对象之间的依赖关系。
//控制反转：Inversion Of Control，简称IOC。对象的创建控制权由 程序 自身转移到 外部(容器)，这种思想称为控制反转。
//依赖注入：DependencyInjection，简称DI。容器为应用程序提供运行时，所依赖的资源，称之为依赖注入。
//Bean对象：IOC容器中创建、管理的对象，称之为Bean。

//1.在需要控制反转的类上加@Controller注解        (注意：所有注解是加在实现类上，非接口)
//        @Component    声明bean的基础注解       不属于以下三类时，用此注解
//        @Controller   @Component的衍生注解     标注在控制层类上(一般不用标注，在@RestController中默认就有)
//        @Service      @Component的衍生注解     标注在业务层类上
//        @Repository   @Component的衍生注解     标注在数据访问层类上（由于与mybatis整合，用的少）
//前面声明bean的四大注解，要想生效，还需要被组件扫描注解@componentScan扫描。
//该注解虽然没有显式配置，但是实际上已经包含在了启动类声明注解@SpringBootApplication中，默认扫描的范围是启动类所在包及其子包。

//2.在需要注入的类成员对象变量上加@Autowired注解
//@Autowired注解，默认是按照类型进行注入的。如果存在多个相同类型的bean(也就是UserService是父接口，但是实现类有多个，
// 注入的时候不知道是哪一个)，将会报错
//三种方法解决：
// @Primary                                    加在要注入的实现类对象上优先匹配
// @Qualifier("bean对象(实现类名首字母小写)")     和@Autowired一起用
// @Resource(name="bean对象(实现类名首字母小写)") 不需要@Autowired

//@Autowired是Spring框架提供的注解，而@Resource是JavaEE规范提供的
//@Autowired默认是按照类型注入，而@Resource默认是按照名称注入





@RestController  //将controller返回值直接作为响应数据返回给前端，如果返回值是对象或者集合，SpringBoot会自动将数据转为json格式返回给前端
public class UserController {
//    由于在案例中，需要读取文本中的数据，并且还需要将对象转为json格式，所以这里呢，我们在项目中再引入一个非
//    常常用的工具包hutool。然后调用里面的工具类,   要引入依赖

    //private UserService userService = new UserServiceImpl();  不符合高内聚低耦合
    //方法一：属性注入(就是只添加注解)
    /*@Qualifier("userServiceImpl")//和@Autowired一起用，指定哪个bean对象
    @Autowired //注入对象
    private UserService userService;*/

    //方法二：构造方法注入
    /*private final UserService userService;
    @Autowired  //如果只有一个构造方法，那么@Autowired可以省略
    public UserController(UserService userService) {
        this.userService = userService;
    }*/

    //方法三：setter方法注入
    private UserService userService;
    @Resource(name = "userServiceImpl")  //不需要@Autowired了
    public void setUserService(UserService userService) {
        this.userService = userService;
    }



    @RequestMapping("/list") //请求路径
    public List<User> list() throws  Exception{
        /*//1．加载并读取user.txt文件，获取用户数据
        //java和resources文件在编译后会放在同一class(类)文件下
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("user.txt");
        //使用hutool工具读取文件所有行
        ArrayList<String> lines = IoUtil.readLines(in, StandardCharsets.UTF_8, new ArrayList<>());

        //2，解析用户信息，封装为User对象->list集合
        List<User> userList = lines.stream().map(line -> {
            String[] parts = line.split(",");
            Integer id = Integer.parseInt(parts[0]);
            String username = parts[1];
            String password = parts[2];
            String name = parts[3];
            Integer age = Integer.parseInt(parts[4]);
            LocalDateTime updateTime = LocalDateTime.parse(parts[5], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            return new User(id, username, password, name, age, updateTime);
        }).toList();


        //3.返回数据(json）
        return userList;*/

        List<User> userList = userService.getUserList();
        return userList;

    }

}
