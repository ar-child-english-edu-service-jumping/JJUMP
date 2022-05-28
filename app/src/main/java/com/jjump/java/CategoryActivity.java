package com.jjump.java;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.jjump.R;
import com.jjump.java.adapter.Item;
import com.jjump.java.adapter.ItemAdapter;
import com.jjump.java.adapter.SubItem;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    private ItemAdapter adapter;
    private RecyclerView recyclerView;
    private ImageButton btn_done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        HomeActivity.bookmarkFlag=true;

        btn_done     = findViewById(R.id.btn_done);
        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
}