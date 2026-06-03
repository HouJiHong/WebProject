package com.Hjh;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
//单元测试
//1.在pom.xml中，引入JUnit的依赖。
//2.在test/java目录下，创建测试类，并编写对应的测试方法，并在方法上声明@Test注解。

//JUnit提供了一些辅助方法，用来帮我们确定被测试的方法是否按照预期的效果正常工作，这种方式称为断言。

//Assertions.assertEquals(object exp, Object act, String msg)
//检查两个值是否相等，不相等就报错。
//Assertions.assertNotEquals(object unexp, Object act, String msg)
//检查两个值是否不相等，相等就报错。
//Assertions.assertNull(object act, String msg)
//检查对象是否为null，不为null，就报错。
//Assertions.assertNotNull(object act,String msg)
//检查对象是否不为null，为null，就报错。
//Assertions.assertTrue(boolean condition, String msg)
//检查条件是否为true，不为true，就报错。
//Assertions.assertFalse(boolean condition, String msg)
//检查条件是否为false，不为false，就报错。
//Assertions.assertThrows(class expType, Executable exec, String msg)
//检查程序运行抛出的异常，是否符合预期。Executable exec它是一个函数式接口，可以用lambda



//在JUnit中还提供了一些注解，还增强其功能，常见的注解有以下几个：
//@Test
//测试类中的方法用它修饰才能成为测试方法，才能启动执行

//@ParameterizedTest
//参数化测试的注解（可以让单个测试运行多次，每次运行时仅参数不同）
//用了该注解，就不需要@Test注解了

//@ValueSource
//参数化测试的参数来源，赋予测试方法参数
//与参数化测试注解配合使用

//@DisplayName
//指定测试类、测试方法显示的名称
//（默认为类名、方法名）

//@BeforeEach
//用来修饰一个实例方法，该方法会在 每一个 测试方法执行之 前 执行一次。
//初始化资源（准备工作）
//@AfterEach
//用来修饰一个实例方法，该方法会在 每一个 测试方法执行之 后 执行一次。
//释放资源（清理工作）

//@BeforeAll
//用来修饰一个静态方法，该方法会在 所有 测试方法之 前 只执行一次。
//初始化资源（准备工作）
//@AfterAll
//用来修饰一个静态方法，该方法会在 所有 测试方法之 后 只执行一次。
//释放资源（清理工作）


@DisplayName("用户服务测试类")
public class UserServiceTest {

    @BeforeAll
    public static void BeforeAll(){
        System.out.println("BeforeAll");
    }
    @AfterAll
    public static void AfterAll(){
        System.out.println("AfterAll");
    }

    @BeforeEach
    public void BeforeEach(){
        System.out.println("BeforeEach");
    }

    @AfterEach
    public void AfterEach(){
        System.out.println("AfterEach");
    }






    @Test
    public void testGetAge() {          //测试方法必须是public void
        UserService userService = new UserService();
        int age = userService.getAge("320582200403060012");
        System.out.println(age);
    }

    @Test
    public void testGetGender(){
        UserService userService = new UserService();
        String gender = userService.getGender("320582200403060012");
        System.out.println(gender);
    }


    //断言
    //单元测试方法运行不报错，不代表业务方法没问题。
    //通过断言可以检测方法运行结果是否和预期一致，从而判断业务方法的正确性
    @Test
    public void testGetGenderWithAssert(){
        UserService userService = new UserService();
        String gender = userService.getGender("320582200403060012");
        Assertions.assertEquals("男", gender,"性别获取有问题");
    }

    //断言异常(我lambda写的异常与期望异常是否一致)
    @Test
    public void testGetGenderWithAssertException(){
        UserService userService = new UserService();

        Assertions.assertThrowsExactly(RuntimeException.class,()->
                userService.getGender("32058220040306001")
        );
    }


    //参数化测试
    @DisplayName("参数化测试性别")
    @ParameterizedTest
    @ValueSource(strings = {"320582200403060012","320582200403060013"})
    public void testGetGenderWithParameterized(String idCard){ //将strings值直接传入idCard
        UserService userService = new UserService();
        String gender = userService.getGender(idCard);
        Assertions.assertEquals("男", gender,"性别获取有问题");
    }

}
