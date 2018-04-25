package org.waithua.common.just4fun;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by jch on 18/4/16.
 * 深拷贝 浅拷贝
 */
public class BeanCopy {

    public static void main(String[] args) {
        List<String> a1 = new ArrayList<String>();
        a1.add("23hfiubvdshfskd");
        List<String> b1 = new ArrayList<String>(a1);
        System.out.println(b1.get(0));
        System.out.println(a1.get(0));
        List<String> c1 = new ArrayList<String>(1);
        Collections.copy(a1,c1);

        List<Node> a2 = new ArrayList<Node>();
        Node node = new Node("123", "456");
        a2.add(node);
        List<Node> b2 = new ArrayList<Node>(a2);
        System.out.println(b2.get(0));
        System.out.println(a2.get(0));

        List<Node> c2 = new ArrayList<Node>();

        Collections.copy(a2, c2);
        c2.get(0).nodeName="890";
        System.out.println(c2.get(0));
    }
}

class Node {
    public String nodeName;
    public String nodeOtherInfo;
    public Node(String nodeName, String nodeOtherInfo) {
        this.nodeName = nodeName;
        this.nodeOtherInfo = nodeOtherInfo;
    }
}

