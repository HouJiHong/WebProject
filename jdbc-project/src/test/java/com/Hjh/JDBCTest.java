package com.Hjh;

import com.Hjh.bean.User;
import org.junit.jupiter.api.Test;

import java.sql.*;
//JDBC为java访问关系数据库的API

//DML语句: int rowsAffected = statement.executeUpdate(); 返回值为受影响的行数
//DQL语句: ResultSet rs = statement.executeQuery(); 返回值为结果集
public class JDBCTest {
    //增删改（有瑕疵）
    @Test
    public void testUpdate() throws Exception {
        //1.注册驱动
        Class.forName("com.mysql.cj.jdbc.Driver");//双击shift搜索Driver，右键类名，点击copy reference

        //2.获取连接
        String url = "jdbc:mysql://localhost:3306/student";
        String user = "root";
        String password = "123456";
        Connection conn = DriverManager.getConnection(url, user, password);
        //3.获取sql语句对象
        Statement stmt = conn.createStatement();

        //4.执行sql语句
        String sql = "update user set age = 25 where id = 1";//这样写并不好，应该用预编译sql
        int rows = stmt.executeUpdate(sql);//执行增删改的操作
        System.out.println("影响行数: " + rows);

        //5.释放资源
        stmt.close();
        conn.close();
    }

    //查询（正确编写）
    @Test
    public void testQuery() {
        String url = "jdbc:mysql://localhost:3306/student";
        String user = "root";
        String password = "123456";

        //这三个都是资源
        Connection conn = null;
        PreparedStatement stmt = null; //此为statement的子类
        ResultSet rs = null;//结果集
//        next（）：将光标从当前位置向前移动一行，并判断当前行是否为有效行，返回值为boolean。
//                  true：有效行，当前行有数据
//                  false：无效行，当前行没有数据
//        getXxx(..)：获取数据，可以根据列的编号获取，也可以根据列名获取（推荐）。



        try {
            //1.注册驱动与获取连接
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);

            //2.获取sql语句对象,预编译sql语句
            String sql = "select * from user where password = ?";  //预编译sql（防止sql注入，且这条代码会缓存在数据库中，下次遇到只需要改变？的值）
            stmt = conn.prepareStatement(sql); //创建sql语句对象
            stmt.setString(1, "123456"); //设置参数

            //3.执行查询操作并将结果集保存在rs中
            rs = stmt.executeQuery();

            //4.处理结果集,将结果集的数据封装成User对象
            while (rs.next()) {
                User u = new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getInt("age")
                );
                System.out.println(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (rs != null)rs.close();
                if (stmt != null)stmt.close();
                if (conn != null)conn.close();
                System.out.println("关闭连接");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
