package com.example.adapter;

/**
 * Created by qiaopc on 2018/11/27.
 */

public class Test {
    public static void main(String[] args) {
        VoltAdapter adapter = new VoltAdapter(new Volt220());
        System.out.println("输出电压：" + adapter.getVolt5());
    }
}
