package com.wzn.java8test;

import lombok.Data;

@Data
public class Track {

    private String name; // 曲目名

    private int length;//长度

    public Track(String name, int length){
        this.name = name;
        this.length = length;
    }
}
