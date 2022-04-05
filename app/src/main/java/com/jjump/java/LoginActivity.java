package com.jjump.java;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.jjump.R;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.exception.KakaoException;


public class LoginActivity extends AppCompatActivity {

    private ISessionCallback mSessionCallback;      //세션의 상태 변경에 따른 콜백 세션이 오픈되었을 때, 세션이 만료되어 닫혔을 때 세션 콜백을 호출

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mSessionCallback=new ISessionCallback() {
            @Override
            public void onSessionOpened() {
                //로그인 요청
                UserManagement.getInstance().me(new MeV2ResponseCallback() {

                    @Override
                    public void onFailure(ErrorResult errorResult) {
                        //로그인 실패
                        Toast.makeText(LoginActivity.this, "Login Failed!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSessionClosed(ErrorResult errorResult) {
                        //세션이 닫힘
                        Toast.makeText(LoginActivity.this, "Session Closed...", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(MeV2Response result) {
                        Toast.makeText(LoginActivity.this, "Login Success!", Toast.LENGTH_SHORT).show();

                        Intent intent=new Intent(LoginActivity.this,HomeActivity.class);
                        intent.putExtra("name",result.getKakaoAccount().getProfile().getNickname());
                        intent.putExtra("email",result.getKakaoAccount().getEmail());
                        intent.putExtra("gender",result.getKakaoAccount().getGender());
                        intent.putExtra("age",result.getKakaoAccount().getAgeRange());
                        intent.putExtra("birthday",result.getKakaoAccount().getBirthday());

                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onSessionOpenFailed(KakaoException exception) {

            }
        };
        //현재 세션에 콜백 추가, 로그인 성공시 화면 전환
        Session.getCurrentSession().addCallback(mSessionCallback);
        Session.getCurrentSession().checkAndImplicitOpen();
    }


}
