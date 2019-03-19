package com.example.bank;

import android.os.Binder;

/**
 * Created by qiaopc on 2018/11/23.
 */

public class BankBinder extends IBankAIDL.Stub {
    @Override
    public String openAccount(String name, String password) {
        return name + " 开户成功";
    }

    @Override
    public String saveMoney(int money, String account) {
        return "账户 " + account + " 存入 " + money;
    }

    @Override
    public String takeMoney(int money, String account, String password) {
        return "账户 " + account + " 支出 " + money;
    }

    @Override
    public String closeAccount(String account, String password) {
        return account + " 销户成功";
    }
}
