package com.example.bridge;

/**
 * Created by qiaopc on 2018/11/29.
 */

public abstract class Abstraction {
    private Implementor mImplementor; // 声明一个私有成员变量引用实现部分的对象

    /**
     * 通过实现部分对象的引用构造抽象部分的对象
     * @param implementor 实现部分对象的引用
     */
    public Abstraction(Implementor implementor) {
        mImplementor = implementor;
    }

    /**
     * 通过调用实现部分具体的方法实现具体的功能
     */
    public void operation() {
        mImplementor.operationImpl();
    }
}
