package com.example.qiaopc.demo;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by qiaopc on 2018/11/7.
 */

public class Coder implements Observer{
    public String name;

    public Coder(String aName) {
        name = aName;
    }
    @Override
    public void update(Observable o, Object arg) {
        System.out.println("Hi, " + name + ", DevTechFrontier更新啦，内容：" + arg);
    }

    @Override
    public String toString() {
        return "coder : " + name;
    }
}
