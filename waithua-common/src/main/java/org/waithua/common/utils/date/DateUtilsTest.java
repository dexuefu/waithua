package org.waithua.common.utils.date;

import org.junit.Test;

import java.time.LocalDate;

/**
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
