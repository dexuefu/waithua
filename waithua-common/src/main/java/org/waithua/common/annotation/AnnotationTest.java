package org.waithua.common.annotation;

/**
 * Created by jch on 18/4/12.
 */
@Test()
public class AnnotationTest {
    @TestMethod("tomcat-method")
    public void test(){
    }
    public static void main(String[] args){
        Test t = AnnotationTest.class.getAnnotation(Test.class);
        System.out.println(t.value());
        TestMethod tm = null;
        try {
            tm = AnnotationTest.class.getDeclaredMethod("test",null).getAnnotation(TestMethod.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(tm.value());
    }
}
