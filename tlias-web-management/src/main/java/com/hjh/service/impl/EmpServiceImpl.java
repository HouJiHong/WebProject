package com.hjh.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hjh.bean.Emp;
import com.hjh.bean.EmpExpr;
import com.hjh.bean.EmpQueryParam;
import com.hjh.bean.PageResult;
import com.hjh.mapper.EmpExprMapper;
import com.hjh.mapper.EmpMapper;
import com.hjh.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

//PageHelper(简化分页查询)
//使用步骤：
//1.引入PageHelper的依赖
//2．定义Mapper接口的查询方法(无需考虑分页）
//3.在Service方法中实现分页查询（调用静态方法）


@Service
public class EmpServiceImpl implements EmpService {
    @Autowired
    private EmpMapper empMapper;
    @Autowired
    private EmpExprMapper empExprMapper;
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



    //添加员工
    @Override
    public void save(Emp emp) {
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

    }
}
