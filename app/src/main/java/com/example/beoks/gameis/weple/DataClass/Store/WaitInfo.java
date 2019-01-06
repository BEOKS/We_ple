package com.example.beoks.gameis.weple.DataClass.Store;

public class WaitInfo {
    public String name;
    public int personNum;
    public String memo;
    public String status;
    public final String WAITING="입장대기",COMPLETE="입장완료";

    public WaitInfo() {

    }

    public WaitInfo(String name, int personNum, String request) {
        this.name = name;
        this.personNum = personNum;
        this.memo = request;
    }
}
