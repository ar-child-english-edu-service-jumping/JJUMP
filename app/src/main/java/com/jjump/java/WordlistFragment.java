package com.jjump.java;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jjump.R;
import com.jjump.java.adapter.DialogAdapter;
import com.jjump.java.adapter.ItemAdapter;
import com.jjump.java.adapter.Item;
import com.jjump.java.adapter.SubItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordlistFragment extends Fragment {

    private static final String TAG_TEXT = "text";

    private ItemAdapter adapter;
    private RecyclerView recyclerView;

    // fab animation
    private Animation rotateOpen;
    private Animation rotateClose;
    private Animation fromBottom;
    private Animation toBottom;

    private FloatingActionButton fab_open;
    private FloatingActionButton fab_ar;
    private FloatingActionButton fab_folder;
    private FloatingActionButton fab_quiz;
    private View hide_view;     // blocking bg when fam opened

    private boolean fab_clicked=false;

    //배열을 arrayList로 바꿔서 요소 추가 가능하도록
    String[] text = {"다시 보고 싶어용", "헷갈려요","내가 좋아하는 단어들","전체 단어"};
    String newFolder = HomeActivity.new_folder_name;
    int position = 0;
    int newTextLength = text.length +1;
    String[] newText = new String[newTextLength];
    //List text_array = new ArrayList (Arrays.asList(text));

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_wordlist, container, false);

        //bookmark invisible
        HomeActivity.bookmarkFlag = false;

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new ItemAdapter(buildItemList());
        recyclerView.setAdapter(adapter);

        rotateOpen= AnimationUtils.loadAnimation(getContext(),R.anim.fab_open);
        rotateClose= AnimationUtils.loadAnimation(getContext(),R.anim.fab_close);
        fromBottom= AnimationUtils.loadAnimation(getContext(),R.anim.fab_up);
        toBottom= AnimationUtils.loadAnimation(getContext(),R.anim.fab_down);

        hide_view = rootView.findViewById(R.id.hide_view);

        fab_open=rootView.findViewById(R.id.fab_more);
        fab_ar=rootView.findViewById(R.id.fab_ar);
        fab_folder=rootView.findViewById(R.id.fab_folder);
        fab_quiz=rootView.findViewById(R.id.fab_quiz);

        fab_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddButtonClicked();
            }
        });
        fab_quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddButtonClicked();
                onQuizButtonClicked();
            }
        });
        fab_folder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddButtonClicked();
                onFolderButtonClicked();
            }
        });
        fab_ar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeActivity.bookmarkFlag=false;
                onAddButtonClicked();
                onArButtonClicked();
            }
        });

        return rootView;
    }


    // 상위아이템 큰박스 아이템을 10개 만듭니다.
    private List<Item> buildItemList() {
        List<Item> itemList = new ArrayList<>();
        String[] itemText={"3.27","5.13","5.29","7.16","7.28","8.31",};
        for (int i=5; i>=0; i--) {
            Item item = new Item(itemText[i], buildSubItemList(i));
            itemList.add(item);
        }
        return itemList;
    }

    String[][] wordFront={{"Pencil","Earth","Notebook"},{"Bird","Drum","Kangaroo","Dolphin"},{"Zoo","Frog"},{"Dog", "Cat", "House","Deer"},{"Key","Spoon","Fork","Milk"},{"Wolf"}};
    String[][] wordBack={{"연필","지구","노트북"},{"새","드럼","캥거루","돌고래"},{"동물원","개구리"},{"개", "고양이", "집","사슴"},{"열쇠","숟가락","포크","우유"},{"늑대"}};
    // 그안에 존재하는 하위 아이템 박스(3개씩 보이는 아이템들)
    private List<SubItem> buildSubItemList(int i) {
        List<SubItem> subItemList = new ArrayList<>();
        for (int j=0; j<wordBack[i].length; j++) {
            SubItem subItem = new SubItem(wordBack[i][j], wordFront[i][j]);
            subItemList.add(subItem);
        }
        return subItemList;
    }

    private void onAddButtonClicked(){
        setVisibility(fab_clicked);
        setAnimation(fab_clicked);
        fab_clicked=!fab_clicked;
    }

    private void setVisibility(boolean flag){
        if(!flag){
            fab_ar.setVisibility(fab_ar.VISIBLE);
            fab_folder.setVisibility(fab_folder.VISIBLE);
            fab_quiz.setVisibility(fab_quiz.VISIBLE);
            hide_view.setVisibility(hide_view.VISIBLE);
        }else{
            fab_ar.setVisibility(fab_ar.INVISIBLE);
            fab_folder.setVisibility(fab_folder.INVISIBLE);
            fab_quiz.setVisibility(fab_quiz.INVISIBLE);
            hide_view.setVisibility(hide_view.INVISIBLE);
        }
    }

    private void setAnimation(boolean flag){
        if(!flag){
            fab_ar.startAnimation(fromBottom);
            fab_folder.startAnimation(fromBottom);
            fab_quiz.startAnimation(fromBottom);
            fab_open.startAnimation(rotateOpen);
        }else{
            fab_ar.startAnimation(toBottom);
            fab_folder.startAnimation(toBottom);
            fab_quiz.startAnimation(toBottom);
            fab_open.startAnimation(rotateClose);
        }
    }

    private void onFolderButtonClicked(){
        ImageButton btn_back;
        Button btn_add_folder;

        // bottom sheet dialog
        Dialog dialog=new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_folder);

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations=R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

        ListView listview = dialog.findViewById(R.id.listview_folder);

        if(HomeActivity.new_folder_name != null){
            for( int i = 0; i < newTextLength; i++)  {
                if(i < position)    {
                    newText[i] = text[i];
                } else if( i == position)   {
                    newText[i] = HomeActivity.new_folder_name;
                } else {
                    newText[i] = text[i - 1];
                }
            }
            DialogAdapter dialogAdapter=new DialogAdapter();
            for(int i=0;i<newText.length;i++) {
                dialogAdapter.addItem(newText[i]);
            }
            adapter.notifyDataSetChanged();
            listview.setAdapter(dialogAdapter);
        }else{
            DialogAdapter dialogAdapter=new DialogAdapter();
            for(int i=0;i<text.length;i++) {
                dialogAdapter.addItem(text[i]);
            }
            adapter.notifyDataSetChanged();
            listview.setAdapter(dialogAdapter);
        }
        // list item in dialog showing existing folders

        //리스트뷰 click event
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getActivity(),AnimalCategoryActivity.class);
                startActivity(intent);
            }
        });

        btn_back = dialog.findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btn_add_folder = dialog.findViewById(R.id.btn_add_folder);
        btn_add_folder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),CategoryAddActivity.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });
    }

    private void onQuizButtonClicked(){

        ImageButton btn_back;
        ListView listview;

        // bottom sheet dialog
        Dialog dialog=new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_folder_select);

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations=R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

        listview = dialog.findViewById(R.id.listview_folder_select);
        // list item in dialog showing existing folders
        if(HomeActivity.new_folder_name != null){
            for( int i = 0; i < newTextLength; i++)  {
                if(i < position)    {
                    newText[i] = text[i];
                } else if( i == position)   {
                    newText[i] = HomeActivity.new_folder_name;
                } else {
                    newText[i] = text[i - 1];
                }
            }
            DialogAdapter dialogAdapter=new DialogAdapter();
            for(int i=0;i<newText.length;i++) {
                dialogAdapter.addItem(newText[i]);
            }
            adapter.notifyDataSetChanged();
            listview.setAdapter(dialogAdapter);
        }else{
            DialogAdapter dialogAdapter=new DialogAdapter();
            for(int i=0;i<text.length;i++) {
                dialogAdapter.addItem(text[i]);
            }
            adapter.notifyDataSetChanged();
            listview.setAdapter(dialogAdapter);
        }

        //리스트뷰 click event
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dialog.dismiss();
                Intent intent = new Intent(getActivity(), QuizActivity.class);
                startActivity(intent);
            }
        });

        btn_back = dialog.findViewById(R.id.btn_back_select);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void onArButtonClicked(){
        ImageButton btn_back;
        ListView listview;

        // bottom sheet dialog
        Dialog dialog=new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_folder_select);

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations=R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

        listview = dialog.findViewById(R.id.listview_folder_select);
        // list item in dialog showing existing folders
        if(HomeActivity.new_folder_name != null){
            for( int i = 0; i < newTextLength; i++)  {
                if(i < position)    {
                    newText[i] = text[i];
                } else if( i == position)   {
                    newText[i] = HomeActivity.new_folder_name;
                } else {
                    newText[i] = text[i - 1];
                }
            }
            DialogAdapter dialogAdapter=new DialogAdapter();
            for(int i=0;i<newText.length;i++) {
                dialogAdapter.addItem(newText[i]);
            }
            adapter.notifyDataSetChanged();
            listview.setAdapter(dialogAdapter);
        }else{
            DialogAdapter dialogAdapter=new DialogAdapter();
            for(int i=0;i<text.length;i++) {
                dialogAdapter.addItem(text[i]);
            }
            adapter.notifyDataSetChanged();
            listview.setAdapter(dialogAdapter);
        }
        //리스트뷰 click event
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dialog.dismiss();
                Intent intent = new Intent(getActivity(), ArCategoryActivity.class);
                startActivity(intent);
            }
        });

        btn_back = dialog.findViewById(R.id.btn_back_select);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}