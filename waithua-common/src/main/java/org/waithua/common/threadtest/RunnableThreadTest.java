package org.waithua.common.threadtest;

/**
 * Created by jch on 18/4/20.
 * 通过Runnable接口创建线程类
 */
public class RunnableThreadTest implements Runnable
{

    private int i;
    public void run()
    {
        for(i = 0;i <100;i++)
        {
            System.out.println(Thread.currentThread().getName()+" "+i);
        }
    }
    public static void main(String[] args)
    {
        for(int i = 0;i < 100;i++)
        {
            System.out.println(Thread.currentThread().getName()+" "+i);
            if(i==20)
            {
                RunnableThreadTest rtt = new RunnableThreadTest();
                Thread thread = new Thread(rtt, "新线程1");
                Thread thread1 = new Thread(rtt, "新线程2");
                thread.start();
                thread1.start();
            }
        }

    }

}