package org.waithua.common.utils.date;

import org.junit.Test;

import java.time.LocalDate;

/**
 * 记录两篇博客，以后对日期时间有需要可以继续完善DateUtils
 * 1、http://www.jianshu.com/p/2949db9c3df5
 * 2、http://www.codeceo.com/article/java-8-20-datetime.html
 * Created by jch on 17/1/13.
 */
public class DateUtilsTest {
    @Test
    public void test() {
        System.out.println("sss");
        LocalDate localDate = LocalDate.now();
        localDate = DateUtils.getFirstDayOfMonth(localDate);
        localDate = DateUtils.getEndDayOfMonth(localDate);
        localDate = DateUtils.getNextDate(localDate);
        localDate = DateUtils.getLastDate(localDate);
    }
}
