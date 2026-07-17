package com.hjh.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hjh.bean.*;
import com.hjh.mapper.EmpExprMapper;
import com.hjh.mapper.EmpMapper;
import com.hjh.service.EmpLogService;
import com.hjh.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

//PageHelper(简化分页查询)
//使用步骤：
//1.引入PageHelper的依赖
//2．定义Mapper接口的查询方法(无需考虑分页）
//3.在Service方法中实现分页查询（调用静态方法）

//事务管理一般放在service层

@Service
public class EmpServiceImpl implements EmpService {
    @Autowired
    private EmpMapper empMapper;
    @Autowired
    private EmpExprMapper empExprMapper;

    //事务传播,在进行添加员工事务的时候，调用日志事务
    @Autowired
    private EmpLogService empLogService;

    //=======================分页查询--原始方法=========================
    /*@Override
    public PageResult<Emp> page(Integer page, Integer pageSize) {
        //1.调用mapper查询总记录数
        //2.调用mapper查询分页数据列表
        //3.封装PageResult对象并返回
        long count = empMapper.count();
        Integer start = (page-1)*pageSize;
        List<Emp> lists = empMapper.list(start, pageSize);
        return new PageResult<Emp>(count, lists);
    }*/

    //=======================分页查询--使用PageHelper=========================
    /*@Override
    public PageResult<Emp> page(Integer page, Integer pageSize) {
        //1.设置分页参数
        PageHelper.startPage(page, pageSize);
        //2.执行查询（此查询只返回符合要求的所有数据，并没有分页,分页的数据被PageHelper保存在page对象中）
        List<Emp> lists = empMapper.list();
        //由于此方法只进行了分页和查询所有数据记录，没有其他参数，而PageHelper只负责分页和查询所有数据记录，
         //所以此方法什么参数都不需要

        //3.解析查询结果，封装成PageResult对象并返回
        Page<Emp> p = (Page<Emp>) lists;    //page类是list接口的实现类，所以可以将lists强转为page对象
        return new PageResult<Emp>(p.getTotal(),p.getResult());
    }*/

    //=======================分页查询+模糊查询--使用PageHelper=========================
    /*@Override
    public PageResult<Emp> page(Integer page, Integer pageSize, String name, Integer gender, LocalDate begin, LocalDate end) {
        //1.设置分页参数
        PageHelper.startPage(page, pageSize);
        //2.执行查询（此查询只返回符合要求的所有数据，并没有分页,分页的数据被PageHelper保存在page对象中）
        List<Emp> lists = empMapper.list(name, gender, begin, end);
        //3.解析查询结果，封装成PageResult对象并返回
        Page<Emp> p = (Page<Emp>) lists; //page类是list接口的实现类，所以可以将lists强转为page对象
        return new PageResult<Emp>(p.getTotal(),p.getResult());
    }*/

    //=======================优化=========================
    @Override
    public PageResult<Emp> page(EmpQueryParam empQueryParam) {
        //1.设置分页参数
        PageHelper.startPage(empQueryParam.getPage(), empQueryParam.getPageSize());
        //2.执行查询（此查询只返回符合要求的所有数据，并没有分页,分页的数据被PageHelper保存在page对象中）
        List<Emp> lists = empMapper.list(empQueryParam);
        //3.解析查询结果，封装成PageResult对象并返回
        Page<Emp> p = (Page<Emp>) lists; //page类是list接口的实现类，所以可以将lists强转为page对象
        return new PageResult<Emp>(p.getTotal(),p.getResult());
    }


    /*事务管理：在数据库中，提交sql语句需要保证数据一致性，和完整性。为保证在提交sql语句时，操作失误或者其他原因，
    导致数据错误，此时需要在数据库中添加事务管理，使用start transaction启动事务，在写完全部sql语句后并且都执行成功
    则使用commit提交事务，有一个错误则使用rollback回滚事务。
    （开启事务后，只有commit后，才会将操作实现在数据库，并公开。未提交时，只更改了本地，未公开）*/

    //Spring事务管理-控制事务
    //数据库的事务管理平替。当方法存在多次对数据库增删改操作，则使用事务管理，保证数据一致性。
    //●注解：@Transactional (默认出现运行时异常才会回滚)
    //●作用：将当前方法交给spring进行事务管理，方法执行前，开启事务；成功执行完毕，提交事务；出现异常，回滚事务
    //●位置：业务（service）层的方法上（推荐）、类上、接口上

    //进阶
    //rollbackFor属性：用于控制出现何种异常类型，回滚事务。

    //事务传播行为：指的就是1.当一个 方法 被一个 事务方法 调用时，这个方法会被 调用者事务管理，要么一起提交，要么一起回滚。
    //                   2.当一个 事务方法 被另一个 事务方法 调用时，这个被调用事务方法应该设置如何进行事务控制。
    //REQUIRED          【默认值】需要事务，调用者有事务则加入，无则创建新事务
    //REQUIRES_NEW       需要新事务，无论调用者有无，总是创建新事务，调用者和被调用者相互不相关
    //SUPPORTS           支持事务，调用者有则加入，无则在无事务状态中运行
    //NOT_SUPPORTED      不支持事务，在无事务状态下运行，如果当前存在已有事务，则挂起当前事务
    //MANDATORY          调用者必须有事务，否则抛异常
    //NEVER              调用者必须没事务，否则抛异常

    //事务的四大特性：原子性，一致性，隔离性，持久性
        //事务是不可分割的最小单元，要么全部成功，要么全部失败
        //事务完成时，必须使所有的数据都保持一致状态
        //数据库系统提供的隔离机制，保证事务在不受外部并发操作影响的独立环境下运行(也就是未commit（未公开），其他窗口看不到操作结果)
        //事务一旦提交或回滚，它对数据库中的数据的改变就是永久的

    //●需求：在新增员工信息时，无论是成功还是失败，都要记录操作日志。
    //●步骤:
    //1.准备日志表emp_log、实体类EmpLog、Mapper接口EmpLogMapper
    //2.在新增员工时记录日志


    //添加员工
    @Transactional(rollbackFor = {Exception.class})//添加事务管理，并指定回滚的异常类型
    @Override
    public void save(Emp emp) {
        try {
            //1.保存员工基本信息(先补全信息)
            emp.setCreateTime(LocalDateTime.now());
            emp.setUpdateTime(LocalDateTime.now());
            empMapper.insert(emp);


            //2.保存员工工作经历信息
            //由于前端发送的json数据中，员工的基本信息和工作经历是一起的，而在service中是分开保存，
            //此时要想员工的基本信息与工作经历绑定，需要获取员工id，将员工id设置给员工工作经历，
            //但是前端发送的员工工作经历json数据中没有员工id，此时就需要在刚刚插入的员工基本信息中用主键返回，
            //其实就是上面的insert先执行，在数据库中会新增一条数据，对应的员工id也会生成，使用主键返回option，
            //会将数据库中新增的id赋给emp对象，然后将emp对象的id遍历给员工工作经历对象
            List<EmpExpr> exprList = emp.getExprList();
            if(!CollectionUtils.isEmpty(exprList)){ //判断集合是否为空,不能用集合的isEmpty方法，因为集合为空时，
                                                        // 集合的方法会报空指针异常
                //遍历集合，将empMapper.insert(emp);获取到的员工Id设置给 每个员工 工作经历记录
                for (EmpExpr expr : exprList) {
                    expr.setEmpId(emp.getId());
                }
                empExprMapper.insertBatch(exprList);//批量保存
            }
        } finally {
            //记录操作日志（仅仅使用try-finally不能实现在新增员工信息时，无论是成功还是失败，都要记录操作日志。
            // 因为被调用方法insertLog没有添加事务，会被调用者save管理，而前面添加员工的操作一旦出错，就会回滚所有
            // 操作，不会记录日志。所以 需要在insertLog上添加事务）
            EmpLog empLog = new EmpLog(null, LocalDateTime.now(), "新增员工：" + emp);
            empLogService.insertLog(empLog);
        }
    }

    //批量删除员工
    @Transactional(rollbackFor = {Exception.class})//多次操作数据库，需要添加事务
    @Override
    public void delete(List<Integer> ids) {
        //1.删除员工基本信息
        empMapper.deleteByIds(ids);

        //2.删除员工工作经历信息
        empExprMapper.deleteByEmpIds(ids);
    }



    //修改员工信息（查询回显）
    @Override
    public Emp getInfo(Integer id) {
        return empMapper.getInfo(id);
    }


    //修改员工（保存信息）
    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void update(Emp emp) {
        //1.修改员工的基本信息
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.updateById(emp);


        //2。修改员工工作经历信息（先删在添加）
        empExprMapper.deleteByEmpIds(Arrays.asList(emp.getId()));
        List<EmpExpr> exprList = emp.getExprList();
        //删完后要将将emp里的集合遍历插入员工工作经历表中，但是现在集合里面没有员工的id，
        // 所以要遍历集合，将员工id设置给员工工作经历对象
        if(!CollectionUtils.isEmpty(exprList)){
            exprList.forEach(empExpr -> {
                empExpr.setEmpId(emp.getId());
            });
            empExprMapper.insertBatch(exprList);
        }
    }

}
