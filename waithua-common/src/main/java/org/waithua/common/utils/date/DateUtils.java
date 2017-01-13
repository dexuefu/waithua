package org.waithua.common.utils.date;


import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjusters;

/**
 * 鉴于java8对日期做了大的调整，决定使用java8的日期时间类
 * java.time包下
 * <p>
 * Instant：时间戳
 * Duration：持续时间，时间差
 * LocalDate：只包含日期，比如：2016-10-20
 * LocalTime：只包含时间，比如：23:12:10
 * LocalDateTime：包含日期和时间，比如：2016-10-20 23:14:21
 * Period：时间段
 * ZoneOffset：时区偏移量，比如：+8:00
 * ZonedDateTime：带时区的时间
 * Clock：时钟，比如获取目前美国纽约的时间
 * <p>
 * java.time.format包
 * DateTimeFormatter：时间格式化
 * Created by jch on 17/1/13.
 */

public class DateUtils {

    /**
     * 获取当月的开始日期
     *
     * @param localDate 日期
     * @return 开始日期
     */
    public static LocalDate getFirstDayOfMonth(LocalDate localDate) {
        LocalDate firstDay = localDate.with(TemporalAdjusters.firstDayOfMonth());
        return firstDay;
    }

    /**
     * 获取当月的结束日期
     *
     * @param localDate 日期
     * @return 结束日期
     */
    public static LocalDate getEndDayOfMonth(LocalDate localDate) {
        LocalDate lastDay = localDate.with(TemporalAdjusters.lastDayOfMonth());
        return lastDay;
    }

    /**
     * 获取明天日期
     *
     * @param localDate 日期
     * @return 明天日期
     */
    public static LocalDate getNextDate(LocalDate localDate) {
        LocalDate tomorrow = localDate.plusDays(1);
        return tomorrow;
    }

    /**
     * 获取昨天日期
     *
     * @param localDate 日期
     * @return 昨天日期
     */
    public static LocalDate getLastDate(LocalDate localDate) {
        LocalDate yesterday = localDate.plusDays(-1);
        return yesterday;
    }
}
