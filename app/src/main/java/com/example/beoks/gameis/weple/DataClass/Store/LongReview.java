package com.example.beoks.gameis.weple.DataClass.Store;

public class LongReview {
    public String writer;// 작성자 profile키
    public int grade;
    public String description;

    public LongReview() {

    }

    public LongReview(String writer, int grade, String description) {
        this.writer = writer;
        this.grade = grade;
        this.description = description;
    }
}
