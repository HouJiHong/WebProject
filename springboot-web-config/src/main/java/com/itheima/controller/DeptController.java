package com.itheima.controller;

import com.itheima.pojo.Dept;
import com.itheima.pojo.Result;
import com.itheima.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;
import java.util.List;

//bean作用域
//Spring支持五种作用域，后三种在web环境才生效：
//singleton     容器内同名称的bean只有一个实例（单例）（默认）
//prototype     每次使用该bean时会创建新的实例（非单例/多例）
//request       每个请求范围内会创建新的实例（web环境中，了解）
//session       每个会话范围内会创建新的实例（web环境中，了解）
//application   每个应用范围内会创建新的实例（web环境中，了解）

//默认bean是单例的，默认单例的bean是在项目启动时创建

//@Lazy //懒加载，延迟初始化，在第一次使用的时候创建

//单例的使用场景：当bean是无状态的（类中除了成员对象和成员方法，没有需要保存的数据是无状态），
 // 由于没有需要保存到数据，bean相互间没有共享数据，不依赖，则使用单例，节省内存空间提高创建销毁的效率。

//多例的使用场景：bean有状态的，bean需要保存数据，如果设置为单例，保存的数据则会被每次调用时都
// 共享（此时数据不是单个对象访问的结果，而是所有访问的结果），线程不安全。
// 设置为多例，则每次调用时都创建新的实例，对象与对象之间是相互独立，线程安全。


//第三方Bean
//●如果要管理的bean对象来自于第三方（不是自定义的），是无法用@Component及衍生注解声明bean的，
// 就需要用到@Bean注解。
//使用方法：
//    1.直接在启动类SpringbootWebConfigApplication，创建一个方法，方法上添加@Bean注解，
//    返回值类型为创建的bean对象，方法返回值会作为bean对象加入到ioc容器中。如果创建的bean对象
//    依赖某个bean对象，则在方法的形参中添加依赖的bean对象。ioc容器会自动创建bean对象，
//    并返回给调用方，形参这里默认使用了@Autowired注解用于注入。

//注意：
//如果第三方bean需要依赖其它bean对象，直接在bean定义方法中设置形参即可，容器会根据类型自动装配。
//注意：
//通过@Bean注解的name或value属性可以声明bean的名称，如果不指定，默认bean的名称就是方法名。

//    2.若要管理的第三方bean对象，建议对这些bean进行集中分类配置，可以通过@configuration注解
//    声明一个配置类。


//@Scope("prototype")  //将作用域设置为多例
@RestController
public class DeptController {
    //查看ioc是什么时候创建对象
    public DeptController(DeptService deptService) {
        System.out.println("创建DeptController对象");
    }

    @Autowired
    private DeptService deptService;

    /**
     * 查询全部部门
     */
    @GetMapping("/depts")
    public Result findAll(){
        List<Dept> deptList = deptService.findAll();
        return Result.success(deptList);
    }

    /**
     * 根据ID删除部门
     */
    @DeleteMapping("/depts")
    public Result delete(Integer id){
        System.out.println("根据ID删除部门数据: " + id);
        deptService.delete(id);
        return Result.success();
    }

    /**
     * 新增部门
     */
    @PostMapping("/depts")
    public Result save(@RequestBody Dept dept){
        System.out.println("新增部门数据: " + dept);
        deptService.save(dept);
        return Result.success();
    }

    /**
     * 根据ID查询部门信息
     */
    @GetMapping("/depts/{id}")
    public Result getInfo(@PathVariable Integer id){
        System.out.println("根据ID查询部门数据: " + id);
        Dept dept = deptService.getInfo(id);
        return Result.success(dept);
    }

    /**
     * 修改部门
     */
    @PutMapping("/depts")
    public Result update(@RequestBody Dept dept){
        System.out.println("修改部门数据: " + dept);
        deptService.update(dept);
        return Result.success();
    }
}
