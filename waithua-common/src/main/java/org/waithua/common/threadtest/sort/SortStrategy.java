package org.waithua.common.threadtest.sort;

/**
 * 排序接口
 * Created by jch on 18/4/20.
 */
public interface SortStrategy {

    /**
     * 传入无序数组，返回有序的
     *
     * @param rawArray
     * @return
     */
    public int[] sort(int[] rawArray);
}
