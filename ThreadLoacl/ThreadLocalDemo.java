package com.ibm;

public class ThreadLocalDemo {

    ThreadLocal<Integer> integerThreadLocal = new ThreadLocal<>();

    ThreadLocal<String> stringThreadLocal = new ThreadLocal<>();

    public Integer getIntegerThreadLocal() {
        return integerThreadLocal.get();
    }

    public void setIntegerThreadLocal() {
        integerThreadLocal.set(Integer.valueOf(((int)Thread.currentThread().getId())));
    }

    public String getStringThreadLocal() {
        return stringThreadLocal.get();
    }

    public void setStringThreadLocal() {
        stringThreadLocal.set(Thread.currentThread().getName());
    }


    public static void main(String[] args) throws InterruptedException {

        ThreadLocalDemo threadLocalDemo = new ThreadLocalDemo();

        threadLocalDemo.setIntegerThreadLocal();
        threadLocalDemo.setStringThreadLocal();
        System.out.println(threadLocalDemo.getIntegerThreadLocal());
        System.out.println(threadLocalDemo.getStringThreadLocal());

        Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();
                threadLocalDemo.setIntegerThreadLocal();
                threadLocalDemo.setStringThreadLocal();
                System.out.println(threadLocalDemo.getIntegerThreadLocal());
                System.out.println(threadLocalDemo.getStringThreadLocal());
            }
        };
        thread.start();
        thread.join();

        System.out.println(threadLocalDemo.getIntegerThreadLocal());
        System.out.println(threadLocalDemo.getStringThreadLocal());
    }

}
