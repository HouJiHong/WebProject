package com.hjh.controller;

import com.hjh.bean.Emp;
import com.hjh.bean.EmpQueryParam;
import com.hjh.bean.PageResult;
import com.hjh.bean.Result;
import com.hjh.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RequestMapping("/emps")
@Slf4j
@RestController
public class EmpController {
    @Autowired
    private EmpService empService;

    /**
     * 分页查询-原始方法
     * 当数据库有大量数据要显示到前端页面上，一般采用分页查询的方式，来提高效率。
     * 1.前端通过url发送简单请求参数：当前页码和每页展示记录数
     * 2.后端接收参数，调用service方法进行分页查询的逻辑(根据页码计算起始索引)，
     * mapper要将 总记录数和 查询结果列表 返回
     */

    /**
     * 分页查询-使用PageHelper （只负责分页和查询总记录数）
     * service方法中只需调用PageHelper的静态方法，设置分页参数（无需计算）
     * mapper方法中 直接查询 符合要求的数据即可
     *
     * 原理：将 紧靠着静态方法 调用的 第一个 调用mapper 实现的sql语句，对其进行拆解，并添加count(),limit子句,
     * 使其实现查询总记录数和分页查询功能，并将查询结果封装到page对象中
     *
     * 分页模糊查询
     * 1.前端除了传递页码和每页记录数，还要传递各种简单模糊查询参数，所以controller需要定义多个参数接收
     * 2.service的静态方法不用变，只需要将模糊查询参数传递给mapper方法，mapper根据参数进行查询（分页和查询总记录是静态方法的事）
     */
    /*//@requestParam注解，除了可以获取简单请求参数，还可以给参数设置默认值
    @GetMapping
    public Result page(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam (defaultValue = "10") Integer pageSize,
                       //模糊查询 时添加
                       String name, Integer gender,
                       @DateTimeFormat (pattern = "yyyy-MM-dd") LocalDate begin,
                       @DateTimeFormat (pattern = "yyyy-MM-dd") LocalDate end
                       ){
        //log.info("分页查询，参数：{},{}", page, pageSize);
        //PageResult<Emp> pageResult =empService.page(page, pageSize);

        log.info("分页查询+模糊查询，参数：{},{},{},{},{},{}", page, pageSize, name, gender, begin, end);
        PageResult<Emp> pageResult =empService.page(page, pageSize, name, gender, begin, end);
        return Result.success(pageResult);
    }*/

    //如果controller方法的参数较多，且未来可能继续增加，这会使得方法签名变得复杂难以维护，此时可以考虑将多个
    //请求参数封装为一个对象。(EmpQueryParam)
    @GetMapping
    public Result page(EmpQueryParam empQueryParam){
        log.info("分页查询+模糊查询，参数：{}", empQueryParam);
        PageResult<Emp> pageResult =empService.page(empQueryParam);
        return Result.success(pageResult);
    }


    //添加员工(接收前端发来的员工基本信息和工作经历信息，json格式)
    @PostMapping
    public Result save(@RequestBody Emp emp){//将json格式的数据转为java对象
        log.info("添加员工，参数：{}", emp);
        empService.save(emp);
        return Result.success();
    }
}
