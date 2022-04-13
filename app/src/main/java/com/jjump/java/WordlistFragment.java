package com.jjump.java;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jjump.R;
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
        List<String> listDates = Arrays.asList("2022.03.17", "2022.03.16", "2022.03.15", "2022.03.14");

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