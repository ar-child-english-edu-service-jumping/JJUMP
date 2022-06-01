package com.jjump.java.adapter;

//단어 하나를 아이템으로 가지는 하위 아이템
public class SubItem {
    private String backWord;
    private String frontWord;
    private boolean bookmardAddedFlag = false;      //임시 플레그, 추후 카테고리별로 따로 플래그 작업 필요

    public SubItem(String back, String front){
        this.backWord=back;
        this.frontWord=front;
    }

    public String getBackWord (){
        return backWord;
    }

    public String getFrontWord (){
        return frontWord;
    }

    public void setBackWord(String backWord){
        this.backWord=backWord;
    }

    public void setFrontWord(String frontWord){
        this.frontWord=frontWord;
    }

    public boolean getFlag(){
        return bookmardAddedFlag;
    }
    public void setFlag(boolean flag){
        this.bookmardAddedFlag = flag;
    }
}
