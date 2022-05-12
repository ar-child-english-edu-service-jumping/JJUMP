package com.jjump.java.adapter;

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
