package com.jjump.java;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.jjump.R;
import com.jjump.java.QuizBottomActivity;
import com.jjump.java.adapter.RecyclerAdapter;
import com.jjump.java.data.WordlistDates;

import java.util.Arrays;
import java.util.List;

public class WordlistFragment extends Fragment {

    private RecyclerAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_wordlist, container, false);

        ImageButton btn_quiz = (ImageButton) rootView.findViewById(R.id.btn_quiz);
        btn_quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), QuizBottomActivity.class);

                startActivity(intent);
            }
        });

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new RecyclerAdapter();
        recyclerView.setAdapter(adapter);

        getData();

        return rootView;
    }


    private void getData() {
        // 임의의 데이터입니다.

        List<String> listDates = Arrays.asList("2022.03.17", "2022.03.16", "2022.03.15", "2022.03.14", "2022.03.13","2022.03.07","2022.02.22");


        for (int i = 0; i < listDates.size(); i++) {
            // 각 List의 값들을 data 객체에 set 해줍니다.
            WordlistDates data = new WordlistDates();
            data.setDates(listDates.get(i));
            // 각 값이 들어간 data를 adapter에 추가합니다.
            adapter.addItem(data);
        }

        // adapter의 값이 변경되었다는 것을 알려줍니다.
        adapter.notifyDataSetChanged();
    }

}