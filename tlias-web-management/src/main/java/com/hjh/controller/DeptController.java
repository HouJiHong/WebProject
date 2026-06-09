package com.hjh.controller;

import com.hjh.bean.Dept;
import com.hjh.bean.Result;
import com.hjh.service.DeptService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//日志技术 (直接在方法中通过System.out.println在控制台输出日志太垃圾了)
//JUL：这是JavaSE平台提供的官方日志框架，也被称为JUL。配置相对简单，但不够灵活，性能较差。
//Log4j：一个流行的日志框架，提供了灵活的配置选项，支持多种输出目标。
//Logback：基于Log4j升级而来，提供了更多的功能和配置选项，性能优于Log4j。

//Slf4j（SimpleLoggingFacadeforJava)：简单日志门面，提供了一套日志操作的标准接口及抽象类，允许应用程序使用不同的底层日志框架。

//准备工作：引入logback的依赖（springboot项目中该依赖已传递）、配置文件logback.xml放在resources目录下。
//记录日志：定义日志记录对象Logger，记录日志。

@Slf4j // 日志对象
@RequestMapping("/depts")
@RestController
public class DeptController {
    //日志对象(也可以用注解)
    //private static final Logger log = LoggerFactory.getLogger(DeptController.class);

    @Autowired
    private DeptService deptService;
    //获取全部部门
    //@RequestMapping(value = "/depts",method = RequestMethod.GET)//如果要限定请求方式，需要指定method
    //@GetMapping("/depts")   //或者使用GetMapping替换，默认为Get请求
    @GetMapping     //公共路径抽取到@RequestMapping中
    public Result list() {
        //System.out.println("查询全部部门");
        log.info("查询全部部门");
        List<Dept> deptList = deptService.findAll();
        return Result.success(deptList);
    }

//如果操作数据库需要指定参数，需要前端输入参数，其中比如：网址/depts?id=8,这种为简单参数，直接在URL中输入参数

    //方式一：通过原始的HttpServletRequest对象获取请求参数。（不推荐，需要手动转类型）

    //方式二：通过Spring提供的@RequestParam注解，将请求参数绑定给方法形参。
    //注意：一旦申明了@RequestParam注解，该参数在请求时网址必须要有该参数，否则会报错。（因为注解里有required方法
    //                                                           默认为true。如果想要可以不传递，需要改为false）

    //方式三：省略@RequestParam，当前端传递的请求参数名称（网址上的参数）和服务器形参名称一致时。（推荐）

    //删除部门
    //法一：
    /*@DeleteMapping("/depts")    //限定请求方式为DELETE
    public Result delete(HttpServletRequest  request){
        String idStr = request.getParameter("id");
        int id = Integer.valueOf(idStr);
        System.out.println("删除部门"+ id);
        return Result.success();
    }*/

    //法二：
    /*@DeleteMapping("/depts") //限定请求方式为DELETE
    public Result delete(@RequestParam(value = "id", required = false) Integer deptId){//参数id会接收网址上的参数，并将id绑定给deptId
        System.out.println("删除部门"+ deptId);
        return Result.success();
    }*/

    //法三：
    //@DeleteMapping("/depts")
    @DeleteMapping
    public Result delete(Integer id){   //注意：一定要一致
        //System.out.println("删除部门"+ id);
        log.info("删除部门"+ id);
        deptService.deleteById(id);
        return Result.success();
    }

//当从前端发送的请求参数为json格式(常用在post和put)时，要将json格式的参数转为对象。（前提是json参数的属性名和对象属性名一致）

    //接收json格式的请求参数：POST    {"name":"教研部"}
    //JSON格式的参数，通常会使用一个实体对象进行接收。
    //规则：JSON数据的键名与方法形参对象的属性名相同，并需要使用@RequestBody注解标识（将json键值对的值封装到对象中）。

    //添加部门
    //@PostMapping("/depts")
    @PostMapping
    public Result add(@RequestBody Dept dept){
        //System.out.println("添加部门"+ dept);
        log.info("添加部门{}", dept);
        deptService.add(dept);
        return Result.success();
    }

    //接收请求参数（路径参数）：GET     /depts/{id} 此处id为路径参数，是个占位符，因为修改内容的时候，目标路径是未知的
    //路径参数：通过请求URL直接传递参数，使用{...}来标识该路径参数，需要使用@PathVariable("id")Integer deptId,将路径参数绑定给方法形参
                                //由于需要修改内容，可以通过主键来确定修改哪条数据，而占位符刚好是可以确定修改内容的

    //如果占位符有多个，如：/depts/{id}/{name},则同理使用@PathVariable("id")Integer deptId,
                                                    // @PathVariable String name

    //修改部门
    //1.查询回显（显示要修改的目标）
    //2.修改部门

    //1.查询回显
    /*@GetMapping("/depts/{id}")
    public Result getInfo(@PathVariable("id") Integer deptId){//将路径参数绑定给方法形参deptId
        System.out.println("修改部门"+ deptId);
        return Result.success();
    }*/

    //@GetMapping("/depts/{id}")
    @GetMapping("/{id}")
    public Result getInfo(@PathVariable Integer id){//将路径参数绑定给方法形参id
        //System.out.println("修改部门"+ id);
        log.info("修改部门{}", id);
        Dept dept = deptService.getById(id);
        return Result.success(dept);
    }

    //2.修改部门
    //@PutMapping("/depts")
    @PutMapping
    public Result update(@RequestBody Dept dept){ //前端发送的是json格式，通过@RequestBody将json转为java对象
        //System.out.println("修改部门"+ dept);
        log.info("修改部门{}", dept);
        deptService.update(dept);
        return Result.success();
    }


}
