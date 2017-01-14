package org.waithua.common.just4fun;

/**
 * 参考博客地址：http://www.weixueyuan.net/view/6318.html
 * String 的值是不可变的，每次对String的操作都会生成新的String对象，不仅效率低，而且耗费大量内存空间。
 * StringBuffer类和String类一样，也用来表示字符串，但是StringBuffer的内部实现方式和String不同，在进行字符串处理时，不生成新的对象，在内存使用上要优于String。
 * StringBuilder类和StringBuffer类功能基本相似，方法也差不多，主要区别在于StringBuffer类的方法是多线程安全的，而StringBuilder不是线程安全的，相比而言，StringBuilder类会略微快一点
 * <p>
 * 线程安全：
 * StringBuffer：线程安全
 * StringBuilder：线程不安全
 * <p>
 * 速度：
 * 一般情况下，速度从快到慢为 StringBuilder > StringBuffer > String，当然这是相对的，不是绝对的。
 * <p>
 * 使用环境：
 * 操作少量的数据使用 String；
 * 单线程操作大量数据使用 StringBuilder；
 * 多线程操作大量数据使用 StringBuffer。
 * <p>
 * Created by jch on 17/1/14.
 */
public class StringAndBuffer {
    public static void main(String[] args) {
//        StringBuffer str1 = new StringBuffer();  // 分配16个字节长度的缓冲区
//        StringBuffer str2 = new StringBuffer(512);  // 分配512个字节长度的缓冲区
//        // 在缓冲区中存放了字符串，并在后面预留了16个字节长度的空缓冲区
//        StringBuffer str3 = new StringBuffer("www.weixueyuan.net");


        String fragment = "abcdefghijklmnopqrstuvwxyz";
        int times = 10000;

        // 通过String对象
        long timeStart1 = System.currentTimeMillis();
        String str1 = "";
        for (int i = 0; i < times; i++) {
            str1 += fragment;
        }
        long timeEnd1 = System.currentTimeMillis();
        System.out.println("String: " + (timeEnd1 - timeStart1) + "ms");

        // 通过StringBuffer
        long timeStart2 = System.currentTimeMillis();
        StringBuffer str2 = new StringBuffer();
        for (int i = 0; i < times; i++) {
            str2.append(fragment);
        }
        long timeEnd2 = System.currentTimeMillis();
        System.out.println("StringBuffer: " + (timeEnd2 - timeStart2) + "ms");
    }
}
