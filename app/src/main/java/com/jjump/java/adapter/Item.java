package com.jjump.java.adapter;

import java.util.List;


// 가로 리사이클려뷰를 아이템으로 가지는 상위 세로 리사이클러뷰 아이템
public class Item {
    private String itemTitle;
    // 하위 리사이클러뷰 아이템으로 정의한 subItemList를 전역변수로 선언한다.
    private List<SubItem> subItemList;


    public Item(String itemTitle, List<SubItem> subItemList) {
        this.itemTitle = itemTitle;
        // 하위 리사이클러뷰
        this.subItemList = subItemList;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public List<SubItem> getSubItemList() {
        return subItemList;
    }

    public void setSubItemList(List<SubItem> subItemList) {
        this.subItemList = subItemList;
    }
}
