package com.example.test;

import android.util.Log;

import java.util.concurrent.Semaphore;

/**
 * Created by qiaopc on 2019/3/1.
 */

public class SemaphoreTest {




    public static void main(String[] args) {
        Test a = new Test();
        a.start();
    }

    public static class Test {
        private String LogString;
        private Semaphore semaphore = new Semaphore(1, true);
        private void start() {
            for (int i = 10; i >= 0; i--) {
                try {
                    System.out.println("Semaphore " + "semaphore.acquire();" + i);
                    semaphore.acquire();
                } catch (Exception ex) {
                    System.out.println("error " + "semaphore.acquire();" + i);

                }
                LogString = "num:" + i;
                printLogString(i);
            }
        }

        private void printLogString(final int delayTime) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("Semaphore " + "semaphore.release();");
                    System.out.println("TAG " + LogString);
                    semaphore.release();
                }
            }).start();
        }
    }
}
