package org.waithua.common.just4fun;

/**
 * Created by jch on 18/2/23.
 * "=="和equals方法的作用
 * "=="：如果是基本数据类型，则直接对值进行比较，如果是引用数据类型，则是对他们的地址进行比较
 * （但是只能比较相同类型的对象，或者比较父类对象和子类对象。类型不同的两个对象不能使用==）
 * equals方法继承自Object类，在具体实现时可以覆盖父类中的实现。看一下Object中qeuals的源码发现，
 * 它的实现也是对对象的地址进行比较，此时它和"=="的作用相同。而JDK类中有一些类覆盖了
 * Object类的equals()方法，比较规则为：如果两个对象的类型一致，并且内容一致，则返回true
 *
 * "=="在遇到非算术运算符的情况下不会自动拆箱，以及他们的equals方法不处理数据类型转换的关系
 */
public class EqualsAndEqual {

    public static void main(String args[]) {
        Integer a = 1;
        Integer b = 2;
        Integer c = 3;
        Integer d = 3;
        Integer e = 321;
        Integer f = 321;
        Long g = 3L;
        int x = 3;
        long y = 3L;

        //x,y虽然类型不同但是可以直接进行数值比较
        System.out.println(x == y);// T
        //System.out.println(c == g); 提示出错，不可比较的类型。说明此时没有自动拆箱
        System.out.println(c == d); // T
        System.out.println(e == f); // F
        System.out.println(c == (a+b)); // T  自动拆箱
        System.out.println(c.equals(a+b)); // T
        //此时进行了自动的拆箱
        System.out.println(g == (a+b)); // T
        System.out.println(g.equals(a+b)); // F
    }
}
