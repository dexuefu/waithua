package org.waithua.common.just4fun;

/**
 * 测试java 内存 可见性
 * Created by jch on 18/3/5.
 */
class MyThread implements Runnable {
    int num = 1000000;
    public void run() {
        if (Thread.currentThread().getName().equals("t1")) {
            increment();
        } else {
            decrement();
        }
    }

    public void increment() {
        for (int i = 0; i < 10000; i++) {
            num++;
        }
    }

    public void decrement() {
        for (int i = 0; i < 10000; i++) {
            num--;
        }
    }
}

class MyThread1 implements Runnable {
    public boolean flag = false;
    public void run() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        flag = true;
        System.out.println("flag = " + flag);
    }
}

public class TestVolatile {
    public static void main(String[] args) {
//        MyThread thread = new MyThread();
//        Thread a = new Thread(thread, "t1");
//        Thread b = new Thread(thread, "t2");
//
//        a.start();
//        b.start();
//
//        try {
//            a.join();
//            b.join();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        System.out.println(thread.num);
        MyThread1 thread1 = new MyThread1();
        Thread a = new Thread(thread1);
        a.start();
        while (true) {
            while (thread1.flag) {
                System.out.println("flag1 = " + thread1.flag);
                break;

            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
