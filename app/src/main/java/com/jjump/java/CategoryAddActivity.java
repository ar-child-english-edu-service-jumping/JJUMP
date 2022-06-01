package com.jjump.java;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.jjump.R;
import com.jjump.java.adapter.Item;
import com.jjump.java.adapter.ItemAdapter;
import com.jjump.java.adapter.SubItem;

import java.util.ArrayList;
import java.util.List;

public class CategoryAddActivity extends AppCompatActivity {

    private ItemAdapter adapter;
    private RecyclerView recyclerView;
    private ImageButton btn_done;
    private EditText edit_folder_name;
    private androidx.appcompat.widget.SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_add);

        HomeActivity.bookmarkFlag=true;

        btn_done     = findViewById(R.id.btn_done);
        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //edittext에 입력된 새 폴더 이름 저장
                edit_folder_name = findViewById(R.id.edit_folder_name);
                HomeActivity.new_folder_name = edit_folder_name.getText().toString();
                finish();
            }
        });
        recyclerView = findViewById(R.id.recycler_view_);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new ItemAdapter(buildItemList());
        recyclerView.setAdapter(adapter);
    }

    // 상위아이템 큰박스 아이템을 10개 만듭니다.
    private List<Item> buildItemList() {
        List<Item> itemList = new ArrayList<>();
        String[] itemText={"3.27","4.13","4.29","5.16","5.28","5.31",};
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

}