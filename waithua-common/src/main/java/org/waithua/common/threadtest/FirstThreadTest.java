package org.waithua.common.threadtest;

/**
 * Created by jch on 18/4/20.
 * 继承Thread类创建线程类
 */
public class FirstThreadTest extends Thread{
    int i = 0;
    //重写run方法，run方法的方法体就是现场执行体
    public void run()
    {
        for(;i<100;i++){
            System.out.println(getName()+"  "+i);

        }
    }
    public static void main(String[] args)
    {
        for(int i = 0;i< 100;i++)
        {
            System.out.println(Thread.currentThread().getName()+"  : "+i);
            if(i==20)
            {
                FirstThreadTest firstThreadTest = new FirstThreadTest();
                FirstThreadTest firstThreadTest2 = new FirstThreadTest();
                firstThreadTest.start();
                firstThreadTest2.start();
            }
        }
    }

}
