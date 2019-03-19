package com.example.Decor;

/**
 * Created by qiaopc on 2018/11/28.
 */
// 抽象装饰者
public class Decorator extends Component {
    private Component component;
    @Override
    public void operate() {
        component.operate();
    }

    public Decorator(Component component) {
        this.component = component;
    }
}
