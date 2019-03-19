package com.example.Decor;

/**
 * Created by qiaopc on 2018/11/28.
 */
// 装饰者具体实现类
public class ConcreteDecoratorA extends Decorator{

    protected ConcreteDecoratorA(Component component) {
        super(component);
    }

    @Override
    public void operate() {
        operateA();
        // 装饰方法A和B既可以在父类方法前调用也可在之后调用
        super.operate();
        operateB();
    }

    public void operateA() {

    }

    public void operateB() {

    }
}
