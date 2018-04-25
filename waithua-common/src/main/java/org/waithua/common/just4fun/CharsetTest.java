package org.waithua.common.just4fun;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.SortedMap;

/**
 * Created by jch on 18/4/13.
 */
public class CharsetTest {

    public static void main(String[] args) throws UnsupportedEncodingException {
        //遍历所有可用的字符集
        SortedMap<String,Charset> map = Charset.availableCharsets();
        for(Map.Entry<String,Charset> entry: map.entrySet()) {
            System. out.println( entry.getKey());
        }
        //
        Charset charset = Charset.forName("Big5");

    }
}
