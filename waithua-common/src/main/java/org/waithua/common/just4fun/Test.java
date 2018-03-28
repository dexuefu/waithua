package org.waithua.common.just4fun;

/**
 * Created by jch on 18/3/12.
 */
public class Test {
    public static void main(String [] args){
//        System.out.println(new B().getValue());
//        B b = new B();
//        b.setValue(10);
//        System.out.println(b.getValue());

        char[] c = {'a','b','\0'};
        String ab = "ab";
        System.out.println(ab.equals(c));
        final char a = 1;
        final char b =2;
        char c1 = a+b;
    }
    static class A{
        protected int value;
        public A(int v) {
            setValue(v);
        }
        public void setValue(int value){
            this.value = value;
        }
        public int getValue(){
            try{
                value++;
                return value;
            } catch(Exception e){
                System.out.println(e.toString());
            } finally {
                this.setValue(value);
                System.out.println(value);
            }
            return value;
        }
    }
    static class B extends A{
        public B() {
            super(5);
            setValue(getValue() - 3);
        }
        public void setValue(int value){
            super.setValue(2 * value);
        }
    }
}
