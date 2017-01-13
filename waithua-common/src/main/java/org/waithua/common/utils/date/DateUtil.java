package org.waithua.common.utils.date;

import org.joda.time.DateTime;

import java.util.Date;

/**
 * Desc:开始时间&结束时间处理
 * Created by jch on 17/1/13.
 */
public class DateUtil {
    /**
     * 获取当天的开始时间
     * @param startAt   开始时间
     * @return  开始时间
     */
    public static Date startAt (Date startAt) {
        return startAt != null ? new DateTime(startAt).withTimeAtStartOfDay().toDate() : null;
    }

    /**
     * 获取当天的结束时间
     * @param endAt   结束时间
     * @return  结束时间
     */
    public static Date endAt (Date endAt) {
        return endAt != null ? new DateTime(endAt).plusDays(1).withTimeAtStartOfDay().minusSeconds(1).toDate() : null;
    }
}
