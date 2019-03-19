package com.example.bank;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by qiaopc on 2018/11/23.
 */

public class BankService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new BankBinder();
    }
}
