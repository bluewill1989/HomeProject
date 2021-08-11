package com.wzn.java8test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalTime;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public class Test1 {

    @Test
    public void test1(){
        System.out.println("this is my test1");
        log.info("good lombok is ready！！");
    }

    @Test
    public void test2(){
        String payLoad = IntStream.rangeClosed(1, 1000000).mapToObj(__ -> "a").collect(Collectors.joining(""));
        log.info(payLoad);
        try {
            Path p = Paths.get("demo.txt");
            log.info("p的绝对路径{}",p.toAbsolutePath());
            Files.write(p, "111111".getBytes(), StandardOpenOption.CREATE,StandardOpenOption.TRUNCATE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
