package com.jjump.java;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jjump.R;
import com.jjump.java.adapter.TextAdapter;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView tab;

    public static ArrayList<String> tempDB;
    public static ArrayList<String> textContainer;  // Array list for holding recognized words
    public static long startTime=0;
    public static long endTime=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //fetching user profile
        /*
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getActivity());
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();
        }
        */

        // array list for holding recognized text
        textContainer = new ArrayList<>();
        tempDB=new ArrayList<>();
        tempDB.add("Yellow"); tempDB.add("Duck"); tempDB.add("Blue"); tempDB.add("Horse"); tempDB.add("Green"); tempDB.add("Frog"); tempDB.add("See");


        //for hide state bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //bottom navigation view
        tab=findViewById(R.id.tab);
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_contatiner, new HomeFragment()).commit(); // initiallize

        //바텀 네비게이션뷰 안의 아이템 설정
        tab.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    //item을 클릭시 id값을 가져와 FrameLayout에 fragment.xml띄우기
                    case R.id.book_tab:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contatiner, new HomeFragment()).commit();
                        break;
                    case R.id.wordlist_tab:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contatiner, new WordlistFragment()).commit();
                        break;
                    case R.id.profile_tab:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contatiner, new ProfileFragment()).commit();
                        break;
                }
                return true;
            }
        });

    }
}