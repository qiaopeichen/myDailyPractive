package com.example.qiaopc.cod;

/**
 * Created by qiaopc on 2018/11/8.
 */

public class Caretaker {
    Memoto memoto;
    public void archive(Memoto memoto) {
        this.memoto = memoto;
    }

    public Memoto getMemoto() {
        return memoto;
    }
}
