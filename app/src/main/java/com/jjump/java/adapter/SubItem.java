package com.jjump.java.adapter;

//단어 하나를 아이템으로 가지는 하위 아이템
public class SubItem {
    private String backWord;
    private String frontWord;

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
}
