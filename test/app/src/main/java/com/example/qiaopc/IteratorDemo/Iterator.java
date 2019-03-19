package com.example.qiaopc.IteratorDemo;

/**
 * Created by qiaopc on 2018/11/9.
 */

public interface Iterator<T> {
    /**
     * 是否还有下一个元素
     * @return
     */
    boolean hasNext();

    /**
     * 返回当前位置的元素并将位置移至下一位
     * @return
     */
    T next();
}
