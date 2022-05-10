package com.jjump.java;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.jjump.R;

public class ChildNameDialog extends Dialog implements View.OnClickListener{

    private Button btn_confirm;
    private Button btn_cancel;
    private EditText et_child_name;
    private Context context;

    private CustomDialogListener customDialogListener;

    public ChildNameDialog(@NonNull Context context) {
        super(context);
        //this.context = context;
    }

    interface CustomDialogListener{
        void onPositiveClicked(String childName);
        void onNegativeClicked();
    }
    public void setDialogListener(CustomDialogListener customDialogListener){
        this.customDialogListener = customDialogListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_name_dialog);

        btn_confirm = findViewById(R.id.btn_confirm);
        btn_cancel = findViewById(R.id.btn_cancel);
        et_child_name = findViewById(R.id.et_child_name);

        //버튼 클릭 리스너 등록
        btn_confirm.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_confirm:
                String childName = et_child_name.getText().toString();
                customDialogListener.onPositiveClicked(childName);
                dismiss();
                break;
            case R.id.btn_cancel:
                cancel();
                break;
        }

    }
}