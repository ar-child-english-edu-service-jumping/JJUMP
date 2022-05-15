package com.jjump.java;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.jjump.R;
import com.jjump.java.data.ReqLoginData;
import com.jjump.java.data.ResLoginData;
import com.jjump.java.network.ApiInterface;
import com.jjump.java.network.HttpClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {

    private GoogleSignInClient mGoogleSignInClient;
    private SignInButton signInButton;
    private int RC_SIGN_IN=0;
    private ApiInterface api;
    String email;

    // POST 통신요청
    public void requestPost() {
        ReqLoginData reqLoginData = new ReqLoginData(email);
        Call<ResLoginData> call = api.requestLogin( reqLoginData );

        // 비동기로 백그라운드 쓰레드로 동작
        call.enqueue( new Callback<ResLoginData>() {
            // 통신성공 후 텍스트뷰에 결과값 출력
            @Override
            public void onResponse(Call<ResLoginData> call, Response<ResLoginData> response) {
                Log.i("my tag", response.body().toString());
            }

            @Override
            public void onFailure(Call<ResLoginData> call, Throwable t) {
                Log.i("my tag", "fail!!!!!!!!!!!!!!!!");
                Log.i("erre", t.toString());
            }
        } );
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //for google
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        signInButton=findViewById(R.id.btn_google);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.btn_google:
                        signIn();
                        break;
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        if(account!=null){
            // goto home
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            //get email address from account and save to "email"
            email = account.getEmail();
            // api call
            api = HttpClient.getRetrofit().create( ApiInterface.class );
            requestPost();

            // Signed in successfully, show authenticated UI.
            Intent intent=new Intent(LoginActivity.this,HomeActivity.class);
            intent.putExtra("name",account.getAccount().name);
            intent.putExtra("email",account.getEmail());
            // 닉네임 post 하는 childname dialog에 이메일 값 전달
            Intent intent2=new Intent(LoginActivity.this,ChildNameDialog.class);
            intent2.putExtra("email",account.getEmail());
            Intent intent3=new Intent(LoginActivity.this,ProfileFragment.class);
            intent3.putExtra("name",account.getDisplayName());

            startActivity(intent);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Login Failed", "signInResult:failed code=" + e.getStatusCode());
        }
    }
}
