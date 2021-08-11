package com.wzn.java8test;

import lombok.Data;

import java.util.List;

@Data
public class Artist {

    private String name;

    private List<String> members;

    private String origin; // 乐队来自哪里

}
