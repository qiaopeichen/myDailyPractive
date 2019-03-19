package com.example.bank;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.qiaopc.myapplication.R;

public class BankActivity extends AppCompatActivity implements View.OnClickListener {

    private IBankAIDL mBankBinder; // 服务端端Binder对象

    private TextView tvMsg; // 显示处理结果的文本控件

    // 用于绑定Service的ServiceConnection对象
    private ServiceConnection conn = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBankBinder = IBankAIDL.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank);

        // 绑定service
        bindService(new Intent("com.example.bank.BankService"), conn, BIND_AUTO_CREATE);

        // 初始化文本控件
        tvMsg = findViewById(R.id.aidl_bank_msg_tv);

        // 初始化按钮以及事件
        init(R.id.aidl_bank_open_btn);
        init(R.id.aidl_bank_save_btn);
        init(R.id.aidl_bank_take_btn);
        init(R.id.aidl_bank_close_btn);
    }

    private void init(int resID) {
        Button b = findViewById(resID);
        b.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.aidl_bank_open_btn:
                try {
                    tvMsg.setText(mBankBinder.openAccount("aige", "123456789"));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.aidl_bank_save_btn:
                try {
                    tvMsg.setText(mBankBinder.saveMoney(666, "10010"));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.aidl_bank_take_btn:
                try {
                    tvMsg.setText(mBankBinder.takeMoney(250, "10010", "123456789"));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.aidl_bank_close_btn:
                try {
                    tvMsg.setText(mBankBinder.closeAccount("10010", "123456789"));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
