package com.hjh;


public class TreadLocalTest {
    //线程的局部变量，每个线程都有单独的存储空间，相互隔离
    private static ThreadLocal<String> local = new ThreadLocal<>();

    public static void main(String[] args) {
        local.set("123");

        new Thread(new Runnable() {
            @Override
            public void run() {
                local.set("456");
                System.out.println(Thread.currentThread().getName()+": "+local.get());
            }
        }).start();

        System.out.println(Thread.currentThread().getName()+": "+local.get());

        local.remove();
        System.out.println(Thread.currentThread().getName()+": "+local.get());


    }
}
