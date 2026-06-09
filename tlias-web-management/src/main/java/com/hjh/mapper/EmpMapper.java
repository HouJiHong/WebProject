package com.hjh.mapper;

import com.hjh.bean.Emp;
import com.hjh.bean.EmpExpr;
import com.hjh.bean.EmpQueryParam;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;
//mapper中使用的 查询sql 要注意两点：1.sql的查询返回值要注意能不能封装（包括数据库的字段名和封装对象属性名是否一致，
//                                                            sql查询返回值在封装对象中是否存在）。
//                               2.sql的查询条件是用方法中的对象传的，还是多个形参直接传。

/**
 * 员工信息
 */
@Mapper
public interface EmpMapper {
    //==============================分页查询--原始方法===============================================
    /*//查询总记录数
    @Select("select count(*) from emp e left join dept d on e.dept_id = d.id")
    public long count();

    //分页查询（多个形参，需要用@param,但此可以省略）
    //由于查询的结果要封装到Emp对象中，而emp中没有deptName属性，所以要先添加deptName属性，
    // 其次d.name无法映射到deptName属性中，所以要起别名
    @Select("select e.* ,d.name deptName from emp e left join dept d on e.dept_id = d.id " +
                 "order by e.update_time desc limit #{start},#{pageSize}")
    public List<Emp> list(Integer start, Integer pageSize);*/

    //==============================分页查询--使用pagehelper=========================================
    /*@Select("select e.* ,d.name deptName from emp e left join dept d on e.dept_id = d.id " +
            "order by e.update_time desc")
    public List<Emp> list();*/

    //==============================分页查询+模糊查询=========================================
    //由于sql比较复杂，所以使用xml映射实现,没有定义sql，定义完方法后对方法名使用快捷键alt+insert，选择第一个generate,
    //会自动在xml中定义sql，并且它会根据方法有没有返回值，返回值类型，方法名，影响行数，自动选择适合的sql（select，insert，delete，update）
    //public List<Emp> list(String name, Integer gender, LocalDate begin, LocalDate end);

    public List<Emp> list(EmpQueryParam empQueryParam);



    //主键返回，useGeneratedKeys：使用数据库生成的主键，keyProperty：将数据库生成的主键值封装到哪里
    @Options(useGeneratedKeys = true, keyProperty = "id")
    //添加员工基本信息
    @Insert("insert into emp(username, name, gender, phone, job, salary, image, entry_date, dept_id, create_time, update_time) " +
            "values(#{username},#{name},#{gender},#{phone},#{job},#{salary},#{image},#{entryDate},#{deptId},#{createTime},#{updateTime}) ")
    void insert(Emp emp);

}
