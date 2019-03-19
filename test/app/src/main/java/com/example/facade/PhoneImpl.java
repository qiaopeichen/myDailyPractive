package com.example.facade;

/**
 * Created by qiaopc on 2018/11/28.
 */

public class PhoneImpl implements Phone {

    // 打电话
    @Override
    public void dail() {
        System.out.println("打电话");
    }

    // 挂断
    @Override
    public void hangup() {
        System.out.println("挂断");
    }
}
