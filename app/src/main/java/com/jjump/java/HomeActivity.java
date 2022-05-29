package com.jjump.java;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.jjump.R;
import com.jjump.java.adapter.TextAdapter;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    ChipNavigationBar tab;

    public static boolean bookmarkFlag=false;

    public static ArrayList<String> tempDB;
    public static ArrayList<String> textContainer;  // Array list for holding recognized words
    public static long startTime=0;
    public static long endTime=0;

    public static int quiz_taken_int;

    private FragmentManager fragmentManager;
    private Fragment f1, f2, f3;


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

        bookmarkFlag     = false;
        quiz_taken_int   = 0;

        // array list for holding recognized text
        textContainer = new ArrayList<>();
        tempDB=new ArrayList<>();
        tempDB.add("Yellow"); tempDB.add("Duck"); tempDB.add("Blue"); tempDB.add("Horse"); tempDB.add("Green"); tempDB.add("Frog"); tempDB.add("See");


        //for hide state bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //bottom navigation view
        tab=findViewById(R.id.tab);
        //getSupportFragmentManager().beginTransaction().add(R.id.fragment_contatiner, new HomeFragment()).commit(); // initiallize

        fragmentManager = getSupportFragmentManager();
        f1 = new HomeFragment();
        fragmentManager.beginTransaction().replace(R.id.fragment_contatiner,f1).commit();
        tab.setItemSelected(R.id.book_tab,false);       //set default item

        //바텀 네비게이션뷰 안의 아이템 설정
        tab.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                switch (i) {
                    //item을 클릭시 id값을 가져와 FrameLayout에 fragment.xml띄우기
                    case R.id.book_tab:

                        f1 = new HomeFragment();
                        getSupportFragmentManager().beginTransaction().add(R.id.fragment_contatiner, f1).commit();
                        if (f1 != null) fragmentManager.beginTransaction().show(f1).commit();
                        if (f2 != null) fragmentManager.beginTransaction().hide(f2).commit();
                        if (f3 != null) fragmentManager.beginTransaction().hide(f3).commit();
                        break;

                    case R.id.wordlist_tab:
                        if (f2 == null){
                            f2 = new WordlistFragment();
                            getSupportFragmentManager().beginTransaction().add(R.id.fragment_contatiner, f2).commit();
                        }
                        if (f1 != null) fragmentManager.beginTransaction().hide(f1).commit();
                        if (f2 != null) fragmentManager.beginTransaction().show(f2).commit();
                        if (f3 != null) fragmentManager.beginTransaction().hide(f3).commit();
                        break;

                    case R.id.profile_tab:
                        if (f3 == null){
                            f3 = new ProfileFragment();
                            getSupportFragmentManager().beginTransaction().add(R.id.fragment_contatiner, f3).commit();
                        }
                        if (f1 != null) fragmentManager.beginTransaction().hide(f1).commit();
                        if (f2 != null) fragmentManager.beginTransaction().hide(f2).commit();
                        if (f3 != null) fragmentManager.beginTransaction().show(f3).commit();
                        break;
                }
            }
        });
    }
}