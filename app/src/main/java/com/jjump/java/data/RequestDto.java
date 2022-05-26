package com.jjump.java.data;

public class RequestDto {
    String email;
    String word;
    String nick;
    public RequestDto( String email ) {
        this.nick = "null";
        this.email = email;
        this.word = "null";
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public void setWord(String word) {
        this.word = word;
    }

}
