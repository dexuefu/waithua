package org.waithua.common.just4fun;

/**
 * 菲波那切数列
 * Created by jch on 18/2/1.
 */
public class FeiBuNaQieFunction {
    public static void main(String[] args) {
//        int i = 10;
        for (int i = 0;i<11;i++) {
            System.out.println("第"+i+"个数是:"+func(i));
        }

    }
    // 递归实现，实现
    private static Long func(int i) {
        if (i <= 0) { return 0L;}
        if (i == 1 || i == 2) {
            return 1L;
        } else {
            return func(i-1)+func(i-2);
        }
    }
}
