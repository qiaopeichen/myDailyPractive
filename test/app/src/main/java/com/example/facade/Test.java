package com.example.facade;

import dalvik.system.DexClassLoader;

/**
 * Created by qiaopc on 2018/11/28.
 */

public class Test {
    public static void main(String[] args) {
        MobilePhone nexus6 = new MobilePhone();
        nexus6.takePicture();
        nexus6.videoChat();
    }
}
