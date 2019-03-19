package com.example.qiaopc.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    NodeEditText mNodeEditText; // 编辑器
    TextView mSaveTv; // 保存按钮
    ImageView mUndoBtn; // 撤销按钮
    ImageView mRedoBtn; // 重做按钮
    // note备忘录管理器
    NoteCaretaker mCaretaker = new NoteCaretaker();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    private void initViews() {
        mNodeEditText = findViewById(R.id.note_edittext);
        mUndoBtn = findViewById(R.id.undo_btn);
        mUndoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 返回上一个记录点
                mNodeEditText.restore(mCaretaker.getPrevMemoto());
                makeToast("撤销:");
            }
        });

        mRedoBtn = findViewById(R.id.redo_btn);
        mRedoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 恢复记录，恢复到下一个记录点
                mNodeEditText.restore(mCaretaker.getNextMemoto());
                makeToast("重做：");
            }
        });

        mSaveTv = findViewById(R.id.save_btn);
        mSaveTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCaretaker.saveMemoto(mNodeEditText.createMemoto());
                makeToast("保存笔记：");
            }
        });
    }

    private void makeToast(String msgPrex) {
        Toast.makeText(this, msgPrex + mNodeEditText.getText() + ", 光标位置：" +
        mNodeEditText.getSelectionStart(), Toast.LENGTH_LONG).show();
    }
}
