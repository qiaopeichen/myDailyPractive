package com.example.Proxy;

import java.lang.reflect.Proxy;

/**
 * Created by qiaopc on 2018/11/22.
 */

public class Client {
    public static void main(String[] args) {
        ILawsuit xiaomin = new XiaoMin();

        DynamicProxy proxy = new DynamicProxy(xiaomin);

        ClassLoader loader = xiaomin.getClass().getClassLoader();

        ILawsuit lawyer = (ILawsuit) Proxy.newProxyInstance(loader, new Class[]{ILawsuit.class}, proxy);

        lawyer.submit();

        lawyer.burden();

        lawyer.defend();
    }
}
