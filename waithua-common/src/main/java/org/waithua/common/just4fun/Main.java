package org.waithua.common.just4fun;

/**
 * Created by jch on 18/3/18.
 */
public class Main {
    public static void main(String[] args) {
//        String str = "ABSIB T";

        String str = "ABSIB T\n".trim();
        if (str.indexOf(' ') > -1) {
            System.out.println(str.length() - str.lastIndexOf(' ')-1);
        } else {
            System.out.println(str.length());
        }
    }
}
