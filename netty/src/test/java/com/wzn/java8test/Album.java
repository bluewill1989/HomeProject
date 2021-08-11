package com.wzn.java8test;

import lombok.Data;

import java.util.List;

@Data
public class Album {

    private String name;//专辑名

    private List<Track> tracks;

    private List<String> musicians;//艺术家列表
}
