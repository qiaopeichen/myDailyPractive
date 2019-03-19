package com.example.qiaopc.IteratorDemo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qiaopc on 2018/11/9.
 */

public class ConcreteIterator<T> implements Iterator {

    private List<T> list = new ArrayList<>();
    private int cursor = 0;

    public ConcreteIterator(List<T> list) {
        this.list = list;
    }

    @Override
    public boolean hasNext() {
        return cursor != list.size();
    }

    @Override
    public Object next() {
        T obj = null;
        if (this.hasNext()) {
            obj = this.list.get(cursor++);
        }
        return obj;
    }
}
