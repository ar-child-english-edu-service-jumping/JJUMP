package com.jjump.java;
import static android.app.PendingIntent.getActivity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.jjump.R;
import com.jjump.java.data.ReqNicknameData;
import com.jjump.java.data.ResNicknameData;
import com.jjump.java.network.ApiInterface;
import com.jjump.java.network.HttpClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChildNameDialog extends Dialog implements View.OnClickListener{

    private Button btn_confirm;
    private Button btn_cancel;
    private EditText et_child_name;
    private Context context;
    private ApiInterface api;
    String childName;
    String email;

    // POST 통신요청
    public void requestPost() {
        ReqNicknameData reqNicknameData = new ReqNicknameData( email, childName);
        Call<ResNicknameData> call = api.requestNickname( reqNicknameData );

        // 비동기로 백그라운드 쓰레드로 동작
        call.enqueue( new Callback<ResNicknameData>() {
            // 통신성공 후 텍스트뷰에 결과값 출력
            @Override
            public void onResponse(Call<ResNicknameData> call, Response<ResNicknameData> response) {
                Log.i("my tag", response.body().toString());
            }

            @Override
            public void onFailure(Call<ResNicknameData> call, Throwable t) {
                Log.i("my tag", "fail!!!!!!!!!!!!!!!!");
                Log.i("erre", t.toString());
            }
        } );
    }

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

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getContext());
        if (acct != null) {
            email = acct.getEmail();
        }

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
                childName = et_child_name.getText().toString();
                customDialogListener.onPositiveClicked(childName);
                // api call
                api = HttpClient.getRetrofit().create( ApiInterface.class );
                requestPost();
                dismiss();
                break;
            case R.id.btn_cancel:
                cancel();
                break;
        }

    }
}