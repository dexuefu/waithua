package org.waithua.common.just4fun;

/**
 * Created by jch on 18/1/26.
 */
public class TestException {

        public static void main(String args[]) {
            int i = 0;
            String greetings[] = { " Hello world !", " Hello World !! ",
                    " HELLO WORLD !!!" };
            while (i < 6) {
                try {
                    // 特别注意循环控制变量i的设计，避免造成无限循环
                    System.out.println(greetings[i++]);
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("数组下标越界异常");
                } finally {
                    System.out.println(i+"--------------------------");
                }
            }
        }
}
