package com.example.qiaopc.myapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by qiaopc on 2018/11/9.
 */

public class NodeEditText extends android.support.v7.widget.AppCompatEditText {
    public NodeEditText(Context context) {
        this(context, null);
    }

    public NodeEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NodeEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    // 为编辑器创建Memoto对象
    public Memoto createMemoto() {
        Memoto noteMemoto = new Memoto();
        noteMemoto.text = getText().toString();
        noteMemoto.cursor = getSelectionStart();
        return noteMemoto;
    }

    // 恢复编辑器状态
    public void restore(Memoto memoto) {
        setText(memoto.text);
        setSelection(memoto.cursor);
    }
}
