package com.example.adapter;

/**
 * Created by qiaopc on 2018/11/27.
 */

//public class VoltAdapter extends Volt220 implements FiveVolt {
//    @Override
//    public int getVolt5() {
//        return 5;
//    }
//}
public class VoltAdapter implements FiveVolt {

    Volt220 mVolt220;

    public VoltAdapter(Volt220 adaptee) {
        mVolt220 = adaptee;
    }

    @Override
    public int getVolt5() {
        return 5;
    }

    public int getVolt220() {
        return mVolt220.getVolt220();
    }
}