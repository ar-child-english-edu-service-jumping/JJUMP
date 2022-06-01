package com.jjump.java.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.jjump.R;
import com.jjump.java.CategoryActivity;

import java.util.ArrayList;

public class DialogAdapter extends BaseAdapter {

    private TextView titleTextView;
    private Button addBtn;

    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<DialogItem> listViewItemList = new ArrayList<DialogItem>();

    // ListViewAdapter의 생성자
    public DialogAdapter() {

    }

    // Adapter에 사용되는 데이터의 개수를 리턴
    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_folder, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        titleTextView = (TextView) convertView.findViewById(R.id.tv_folder_name);
        addBtn=(Button) convertView.findViewById(R.id.btn_add_word_to_folder);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, CategoryActivity.class);
                context.startActivity(intent);
                //Toast.makeText(parent.getContext(),"Folder add",Toast.LENGTH_LONG).show();
            }
        });

        DialogItem listViewItem = listViewItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        titleTextView.setText(listViewItem.getTitle());

        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴
    @Override
    public long getItemId(int position) {
        return position;
    }

    // 지정한 위치(position)에 있는 데이터 리턴
    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position);
    }

    // 아이템 데이터 추가를 위한 함수.
    public void addItem(String title) {
        DialogItem item = new DialogItem();

        item.setTitle(title);

        listViewItemList.add(item);
    }

}
