package com.hjh.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页结果封装类
 */

//1.分页查询前端需要给后端传递的参数？
//page：当前页码
//pageSize：每页展示记录数
//2.分页查询后端需要给前端返回的结果？
//total:总记录数 select count(*) from emp e left join dept d on e.dept_id = d.id;
//rows：结果列表 select * from emp e left join dept d on e.dept_id = d.id limit ?,?;
    //(页码-1)*每页展示记录数
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult< T> {
    private Long total;//总记录数
    private List<T> rows;//当前页数据
}
