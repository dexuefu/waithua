package org.waithua.common.just4fun;

/**
 * 参考博客地址：http://blog.csdn.net/hguisu/article/details/6155636
 * <p>
 * 比较熟悉的RumtimeException类的子类有
 * Java.lang.ArithmeticException
 * Java.lang.ArrayStoreExcetpion
 * Java.lang.ClassCastException
 * Java.lang.IndexOutOfBoundsException
 * Java.lang.NullPointerException
 * <p>
 * Error是throwable的子类，代表编译时间和系统错误，用于指示合理的应用程序不应该试图捕获的严重问题。
 * Error由Java虚拟机生成并抛出，包括动态链接失败，虚拟机错误等。程序对其不做处理。
 * Created by jch on 17/1/14.
 *
 * Throwable Error      VirtualMachineError  StackOverFlowError
 *                                           OutOfMemoryError
 *                      AWTError
 *           Exception  IOException          EOFException
 *                                           FileNotFoundException
 *                      RuntimeException     ArrithmeticException
 *                                           MissingResourceException
 *                                           ClassNotFoundException
 *                                           NullPointerException
 *                                           IllegalArgumentException
 *                                           ArrayIndexOutOfBoundsException
 *                                           UnknowTypeException
 *
 */
public class ExceptionTest {
    boolean testEx() throws Exception {
        boolean ret = true;
        try {
            ret = testEx1();
        } catch (Exception e) {
            System.out.println("testEx, catch exception");
            ret = false;
            throw e;
        } finally {
            System.out.println("testEx, finally; return value=" + ret);
            return ret;
        }
    }

    boolean testEx1() throws Exception {
        boolean ret = true;
        try {
            ret = testEx2();
            if (!ret) {
                return false;
            }
            System.out.println("testEx1, at the end of try");
            return ret;
        } catch (Exception e) {
            System.out.println("testEx1, catch exception");
            ret = false;
            throw e;
        } finally {
            System.out.println("testEx1, finally; return value=" + ret);
            return ret;
        }
    }

    boolean testEx2() throws Exception {
        boolean ret = true;
        try {
            int b = 12;
            int c;
            for (int i = 2; i >= -2; i--) {
                c = b / i;
                System.out.println("i=" + i);
            }
            return true;
        } catch (Exception e) {
            System.out.println("testEx2, catch exception");
            ret = false;
            throw e;
        } finally {
            System.out.println("testEx2, finally; return value=" + ret);
            return ret;
        }
    }

    /**
     * i=2
     * i=1
     * testEx2, catch exception
     * testEx2, finally; return value=false
     * testEx1, finally; return value=false
     * @param args
     */

    public static void main(String[] args) {
        ExceptionTest testException1 = new ExceptionTest();
        try {
            testException1.testEx();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
