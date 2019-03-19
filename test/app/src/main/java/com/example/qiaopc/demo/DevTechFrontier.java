package com.example.qiaopc.demo;

import java.util.Observable;

/**
 * Created by qiaopc on 2018/11/7.
 */

public class DevTechFrontier extends Observable {

    public void postNewPublication(String content) {
        // 标识状态或者内容发生改变
        setChanged();
        notifyObservers(content);
    }
}
