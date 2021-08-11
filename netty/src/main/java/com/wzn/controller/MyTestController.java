package com.wzn.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class MyTestController {


    @RequestMapping("/hello")
    public String hello(){
        return "HELLO";
    }

//    @GetMapping("wrong")
//    public int wrong(@RequestParam(value = "count",defaultValue = "10000000")int count){
//        Data.reset();
//        IntStream.rangeClosed(1, count).parallel().forEach(i -> new Data().wrong());
//        return Data.getCounter();
//    }

    @RequestMapping("/test")
    public String test(){
        log.info(" get into test ");
        return "this is test!!!";
    }

//    @GetMapping("wrong")
//    public int wrong(@RequestParam(value = "count",defaultValue = "10000000")int count){
//        Data.reset();
//        IntStream.rangeClosed(1, count).parallel().forEach(i -> new Data().wrong());
//        return Data.getCounter();
//    }
}
