package com.example.qiaopc.cod;

/**
 * Created by qiaopc on 2018/11/8.
 */

public class CallOfDuty {
    private int mCheckpoint = 1;
    private int mLifeValue = 100;
    private String mWeapon = "mp5";

    public void play() {
        System.out.println("玩游戏：" + String.format("第%d关", mCheckpoint));
        mLifeValue -= 10;
        System.out.println("进度升级");
        mCheckpoint++;
        System.out.println("到达 " + String.format("第%d关", mCheckpoint));
    }

    public void quit() {
        System.out.println("------");
        System.out.println("退出前的游戏属性：" + this.toString());
        System.out.println("退出游戏");
        System.out.println("------");
    }

    public Memoto createMemoto() {
        Memoto memoto = new Memoto();
        memoto.mCheckpoint = mCheckpoint;
        memoto.mLifeValue = mLifeValue;
        memoto.mWeapon = mWeapon;
        return memoto;
    }

    public void restore(Memoto memoto) {
        this.mCheckpoint = memoto.mCheckpoint;
        this.mLifeValue = memoto.mLifeValue;
        this.mWeapon = memoto.mWeapon;
        System.out.println("恢复后的游戏属性：" + this.toString());
    }

    @Override
    public String toString() {
        return "CallOfDuty{" +
                "mCheckpoint=" + mCheckpoint +
                ", mLifeValue=" + mLifeValue +
                ", mWeapon='" + mWeapon + '\'' +
                '}';
    }
}
