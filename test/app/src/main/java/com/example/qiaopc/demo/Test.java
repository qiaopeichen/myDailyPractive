package com.example.qiaopc.demo;

/**
 * Created by qiaopc on 2018/11/7.
 */

public class Test {
    public static void main(String[] args) {
        // 被观察的角色
        DevTechFrontier DevTechFrontier = new DevTechFrontier();
        // 观察者
        Coder mrsiple = new Coder("mr.simple");
        Coder coder1 = new Coder("coder-1");
        Coder coder2 = new Coder("coder-2");
        Coder coder3 = new Coder("coder-3");

        // 将观察者注册到可观察对象到观察者列表中
        DevTechFrontier.addObserver(mrsiple);
        DevTechFrontier.addObserver(coder1);
        DevTechFrontier.addObserver(coder2);
        DevTechFrontier.addObserver(coder3);

        // 发布消息
        DevTechFrontier.postNewPublication("发布消息");
    }
}
