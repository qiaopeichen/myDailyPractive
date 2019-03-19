package com.example.qiaopc.IteratorDemo;

/**
 * Created by qiaopc on 2018/11/9.
 */

public class Client {
    public static void main(String[] args) {
        Aggregate<String> a = new ConcreteAggregate<>();
        a.add("Aige");
        a.add("Studio\n");
        a.add("abc");
        a.add(" def");
        Iterator<String> i = a.iterator();
        while (i.hasNext()) {
            System.out.print(i.next());
        }
    }
}
