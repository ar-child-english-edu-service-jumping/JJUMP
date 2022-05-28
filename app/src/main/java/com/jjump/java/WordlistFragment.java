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
    String[] text = {"동물 친구들", "다시 보고 싶어용", "헷갈려요","스크롤 되나염?"};
    List text_array = new ArrayList (Arrays.asList(text));

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

        //add folder to folder name array list
        text_array.add("동물 친구들");
        text_array.add("다시 보고 싶어용");
        text_array.add("헷갈려요");
        text_array.add("스크롤 되나염?");

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
                Intent intent = new Intent(getActivity(), QuizActivity.class);
                startActivity(intent);
            }
        });
        fab_folder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFolderButtonClicked();
            }
        });

        return rootView;
    }


    // 상위아이템 큰박스 아이템을 10개 만듭니다.
    private List<Item> buildItemList() {
        List<Item> itemList = new ArrayList<>();
        String[] itemText={"3.22","3.27","4.06","4.18","4.22","5.01","5.06",};
        for (int i=6; i>=0; i--) {
            Item item = new Item(itemText[i], buildSubItemList(i));
            itemList.add(item);
        }
        return itemList;
    }

    String[][] wordFront={{"Dog", "Cat", "House"},{"Zoo","Tiger"},{"Hippo","Drum","Flag","Mouse"},{"Earth"},{"Pencil","Eraser","Notebook"},{"Key","Spoon","Fork","Milk"},{"Bread","Cake"}};
    String[][] wordBack={{"개", "고양이", "집"},{"동물원","호랑이"},{"하마","드럼","깃발","쥐"},{"지구"},{"연필","지우개","노트북"},{"열쇠","숟가락","포크","우유"},{"빵","케이크"}};
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
        onAddButtonClicked();

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

        // list item in dialog showing existing folders
        DialogAdapter dialogAdapter=new DialogAdapter();
        for(int i=0;i<text.length;i++) {
            dialogAdapter.addItem(text[i]);
        }
        adapter.notifyDataSetChanged();

        listview.setAdapter(dialogAdapter);
        //리스트뷰 click event
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(),text[position],Toast.LENGTH_LONG).show();
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
                Intent intent=new Intent(getActivity(),CategoryActivity.class);
                startActivity(intent);
                Toast.makeText(getContext(),"몰라몰라잉 ㅜ~",Toast.LENGTH_LONG).show();
            }
        });
    }
}