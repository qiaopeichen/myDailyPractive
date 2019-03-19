package com.example.bridge;

/**
 * Created by qiaopc on 2018/11/29.
 */

public class RefindAbstraction extends Abstraction {
    /**
     * 通过实现部分对象的引用构造抽象部分的对象
     *
     * @param implementor 实现部分对象的引用
     */
    public RefindAbstraction(Implementor implementor) {
        super(implementor);
    }

    public void refindOperation() {
        // 对Abstraction中的方法进行扩展
    }
}
