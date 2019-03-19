package com.example.qiaopc.IteratorDemo;

/**
 * Created by qiaopc on 2018/11/9.
 */

/**
 * 容器接口
 * @param <T>
 */
public interface Aggregate<T> {
    void add(T obj);
    void remove(T obj);
    Iterator<T> iterator();
}
