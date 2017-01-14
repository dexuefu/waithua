package org.waithua.common.just4fun;

/**
 * JAVA 各种数值类型最大值和最小值 byte, int, short, char, long, float, double
 * Created by jch on 17/1/14.
 */
public class MaxMinTest {
    public static void main(String[] args) {
        int imax = Integer.MAX_VALUE;
        int imin = Integer.MIN_VALUE;
        int temp = imax + 1;
        System.out.println(imax);
        System.out.println(imin);
        System.out.println(temp);

        System.out.println("float max="+Float.MAX_VALUE);
        System.out.println("float min="+Float.MIN_VALUE);
        System.out.println("double max="+Double.MAX_VALUE);
        System.out.println("double min="+Double.MIN_VALUE);
        System.out.println("byte max="+Byte.MAX_VALUE);
        System.out.println("byte min="+Byte.MIN_VALUE);
        System.out.println("char max="+Character.MAX_VALUE);
        System.out.println("char min="+Character.MIN_VALUE);
        System.out.println("short max="+Short.MAX_VALUE);
        System.out.println("short min="+Short.MIN_VALUE);
        System.out.println("int max="+Integer.MAX_VALUE);
        System.out.println("int min="+Integer.MIN_VALUE);
        System.out.println("long max="+Long.MAX_VALUE);
        System.out.println("long min="+Long.MIN_VALUE);
    }
}
