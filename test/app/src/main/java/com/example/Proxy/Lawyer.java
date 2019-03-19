package com.example.Proxy;

/**
 * Created by qiaopc on 2018/11/22.
 */

public class Lawyer implements ILawsuit{
    private ILawsuit mLawsuit;

    public Lawyer(ILawsuit lawsuit) {
        mLawsuit = lawsuit;
    }

    @Override
    public void submit() {
        mLawsuit.submit();
    }

    @Override
    public void burden() {
        mLawsuit.burden();
    }

    @Override
    public void defend() {
        mLawsuit.defend();
    }

    @Override
    public void finish() {
        mLawsuit.finish();
    }
}
