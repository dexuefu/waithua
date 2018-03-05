package org.waithua.common.just4fun;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jch on 18/2/23.
 *
 * 同步块未覆盖到所有场景。
 * 总结：读时不能写 写时不能读  可以并发读  不能并发写
 */
public class CacheManage {
    private Map<String, String> cache = new HashMap<String, String>();

    public static int THREADS_COUNT = 2;

    public void fresh() {
        synchronized (cache) {
            cache.clear();
        }
    }

    public void put(String key, String value) {
        synchronized (cache) {
            cache.put(key, value);
            System.out.println(Thread.currentThread().getName()
                    + "  cache.put--------" + value);
        }
    }

    public String get(String key) {
//        synchronized (cache) { // 加上这个就没问题了
            System.out.println(Thread.currentThread().getName()
                    + "  cache.get-----" + cache.get(key));
            return cache.get(key);
//        }
    }

    public static void main(String[] args) {

        List<String> list = new ArrayList<String>(10);
        list.add("dsfds");
        list.add("dsfds");
        list.add("dsfds");
        list.add(2,"fff");
        assert(1==1);
        final CacheManage cacheManage = new CacheManage();
        Thread[] threads = new Thread[THREADS_COUNT];
        for (int i = 0; i < THREADS_COUNT; i++) {
            threads[i] = new Thread(new Runnable() {
                public void run() {



                    for (int i = 0; i < 10; i++) {
                        cacheManage.put("a", i + 1 + "");
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        cacheManage.get("a");
                    }
                }
            });
            threads[i].start();
        }
    }
}
